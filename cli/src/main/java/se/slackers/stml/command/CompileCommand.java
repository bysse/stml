package se.slackers.stml.command;

import picocli.CommandLine;
import se.slackers.stml.CLIException;
import se.slackers.stml.ExitCode;
import se.slackers.stml.VersionProvider;
import se.slackers.stml.parser.Parser;
import se.slackers.stml.parser.ParserError;
import se.slackers.stml.parser.ParserResult;
import se.slackers.stml.preprocessor.STMLException;
import se.slackers.stml.preprocessor.Source;
import se.slackers.stml.yaml.YamlVisitor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.lang.System.err;

@CommandLine.Command(
        name = "compile",
        aliases = "c",
        description = "Compiles one or many STML files to YAML",
        versionProvider = VersionProvider.class
)
public class CompileCommand implements Callable<Integer> {
    static final Logger logger = Logger.getLogger(CompileCommand.class.getName());

    @CommandLine.Parameters(index = "*", paramLabel = "file")
    private File[] inputFiles;

    @CommandLine.Option(names = {"-V", "--version"}, versionHelp = true, description = "display version info")
    private boolean versionInfoRequested;

    @CommandLine.Option(names = {"-o", "--output"}, description = "output file or directory")
    private File output;

    @CommandLine.Option(names = {"-m", "--merge"}, description = "merge input files before compilation")
    private boolean mergeInputs = false;

    @CommandLine.Option(names = {"-r", "--recurse"}, description = "recurse through input directories")
    private boolean recursiveRead = false;

    @CommandLine.Option(names = {"--fail-fast"}, description = "abort compilation on the first file with errors")
    private boolean failFast = false;

    public CompileCommand() {
    }

    public CompileCommand(File inputFiles, File output) {
        this.inputFiles = new File[]{inputFiles};
        this.output = output;
    }

    @Override
    public Integer call() {
        if (inputFiles == null || inputFiles.length == 0) {
            throw new CLIException("No input files specified", true);
        }

        // check output
        FileOutputWriter outputWriter = null;
        if (output != null) {
            outputWriter = new FileOutputWriter(output, mergeInputs);
        }

        // process input
        List<Source> sources = PreProcessUtil.preprocessFiles(inputFiles, mergeInputs, recursiveRead).collect(Collectors.toList());
        int errors = 0;

        for (Source source : sources) {
            try {
                Parser parser = new Parser();
                ParserResult result = parser.parse(source);

                if (!result.successful()) {
                    for (ParserError error : result.getErrors()) {
                        err.println("ERROR: " + error.format());
                        logger.severe(error.format());
                    }
                    errors++;
                    if (failFast) {
                        throw new CLIException("Compilation errors during compilation");
                    }
                    continue;
                }

                logger.info("Generating YAML");
                YamlVisitor visitor = new YamlVisitor(source.getMapper(), result.getRegistry());
                result.getContext().accept(visitor);
                final String yaml = visitor.getOutput();

                try {
                    if (outputWriter == null) {
                        System.out.println(yaml);
                    } else {
                        outputWriter.write(source.getPath(), yaml);
                    }
                } catch (IOException e) {
                    errors++;
                    if (failFast) {
                        throw new CLIException(e.getMessage());
                    }

                    err.println("ERROR: " + e.getMessage());
                    logger.log(Level.SEVERE, e.getMessage(), e);
                }
            } catch (STMLException e) {
                errors++;
                if (failFast) {
                    logger.log(Level.SEVERE, e.getSourcePositionId().format() + ": " + e.getMessage(), e);
                }
                err.println(e.getSourcePositionId().format() + ": " + e.getMessage());
            }
        }

        if (errors > 0) {
            if (errors == 1) {
                throw new CLIException("An error was found during compilation");
            }
            throw new CLIException("Multiple errors were found during compilation");
        }

        return ExitCode.RET_OK;
    }
}
