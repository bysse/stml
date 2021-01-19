/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package se.slackers.stml;

import se.slackers.stml.mapper.SourcePositionId;
import se.slackers.stml.parser.Parser;
import se.slackers.stml.parser.ParserError;
import se.slackers.stml.parser.ParserResult;
import se.slackers.stml.preprocessor.PreProcessor;
import se.slackers.stml.preprocessor.STMLException;
import se.slackers.stml.preprocessor.Source;
import se.slackers.stml.yaml.YamlVisitor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.lang.System.err;
import static java.lang.System.out;

public class Main {
    static {
        try {
            InputStream stream = Main.class.getClassLoader().getResourceAsStream("logging.properties");
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Starting STML v" + Main.class.getPackage().getImplementationVersion());

        List<String> sources = new ArrayList<>();
        Set<STMLFlag> flags = new HashSet<>();
        String output = null;
        boolean multipleOutput = false;
        boolean noOutput = false;

        int arg = 0;
        while (arg < args.length) {
            switch (args[arg]) {
                case "-h":
                case "--help":
                    printSyntax();
                    System.exit(0);
                case "--version":
                    printVersion();
                    System.exit(0);
                case "-c":
                    arg++;
                    if (arg >= args.length) {
                        err.println("Syntax error: Missing argument for option '-c'");
                        System.exit(1);
                    }
                    generateCompletion(args[arg]);
                    System.exit(0);
                case "--kubectl":
                    generateKubectl();
                    System.exit(0);
                case "-f":
                    arg++;
                    if (arg >= args.length) {
                        err.println("Syntax error: Missing argument for option '-f'");
                        System.exit(1);
                    }
                    try {
                        flags.add(STMLFlag.flagFromString(args[arg]));
                    } catch (IllegalArgumentException e) {
                        err.println("Syntax error: Unknown flag '" + args[arg] + "'");
                        System.exit(1);
                    }
                    break;
                case "-s":
                    multipleOutput = true;
                    break;
                case "-no":
                    noOutput = true;
                    break;
                case "-o":
                    arg++;
                    if (arg >= args.length) {
                        err.println("Syntax error: Missing argument for option '-o'");
                        System.exit(1);
                    }
                    output = args[arg];
                    break;
                default:
                    if (args[arg].startsWith("-")) {
                        err.println("Syntax error: Unknown option '" + args[arg] + "'");
                        System.exit(1);
                    }
                    sources.add(args[arg]);
                    break;
            }

            arg++;
        }

        if (sources.isEmpty()) {
            err.println("ERROR: No source files specified");
            printSyntax();
            System.exit(2);
        }

        if (multipleOutput) {
            processMultiFiles(sources, flags, output, noOutput);
        } else {
            processMultiInput(sources, flags, output, noOutput);

        }
    }

    private static void generateCompletion(String shell) {
        InputStream resource = Main.class.getClassLoader().getResourceAsStream("completion/c_" + shell.toLowerCase() + ".sh");
        if (resource == null) {
            err.println("ERROR: No completion support for " + shell.toLowerCase());
            System.exit(1);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
            String line;
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }
        } catch (IOException e) {
            err.println("ERROR: Failed to produce completion script : " + e.getMessage());
            System.exit(1);
        }
    }

    private static void generateKubectl() {
        InputStream resource = Main.class.getClassLoader().getResourceAsStream("kubectl/kubectl-stml");
        if (resource == null) {
            err.println("ERROR: Unknown error when creating kubectl plugin");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
            String line;
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }
        } catch (IOException e) {
            err.println("ERROR: Failed to produce kubectl plugin : " + e.getMessage());
            System.exit(1);
        }
    }

    private static void processMultiInput(List<String> sources, Set<STMLFlag> flags, String output, boolean noOutput) {
        // preprocessor
        PreProcessor preProcessor = new PreProcessor();
        for (String source : sources) {
            try {
                preProcessor.process(Paths.get(source));
            } catch (STMLException e) {
                reportError(e.getSourcePositionId(), e.getMessage());
                logger.log(Level.SEVERE, e.getMessage(), e);
                System.exit(3);
            }
        }

        Source source = preProcessor.getSource();
        try {
            Parser parser = new Parser();
            ParserResult result = parser.parse(source);

            if (!result.successful()) {
                for (ParserError error : result.getErrors()) {
                    err.println("ERROR: " + error.format());
                    logger.severe(error.format());
                }
                System.exit(3);
            }

            if (noOutput) {
                logger.info("Skipping output generation");
            } else {
                logger.info("Generating YAML");
                YamlVisitor visitor = new YamlVisitor(source.getMapper(), result.getRegistry());
                visitor.setFlags(flags);
                result.getContext().accept(visitor);
                final String yaml = visitor.getOutput();

                try {
                    if (output != null) {
                        Path path = Paths.get(output);
                        if (Files.isDirectory(path)) {
                            path = path.resolve("output.yaml");
                        }

                        Files.write(path, yaml.getBytes(StandardCharsets.UTF_8));
                    } else {
                        out.println(yaml);
                    }
                } catch (IOException e) {
                    err.println("ERROR: Failed to write to " + output + ": " + e.getMessage());
                    logger.log(Level.SEVERE, e.getMessage(), e);
                    System.exit(3);
                }
            }
        } catch (STMLException e) {
            reportError(e.getSourcePositionId(), e.getMessage());
            logger.log(Level.SEVERE, e.getMessage(), e);
            System.exit(3);
        }

        System.exit(0);
    }

    private static void processMultiFiles(List<String> sources, Set<STMLFlag> flags, String output, boolean noOutput) {
        if (output == null) {
            output = ".";
        }
        Path path = Paths.get(output);
        if (!Files.isDirectory(path)) {
            if (Files.exists(path)) {
                err.println("ERROR: Output needs to be a directory when processing multiple files!");
                System.exit(1);
            }

            if (!path.toFile().mkdirs()) {
                err.println("ERROR: Failed to created output directory");
                System.exit(1);
            }
        }

        Path cwd = Paths.get("").toAbsolutePath();

        for (String sourceFile : sources) {
            logger.info("Processing " + sourceFile);
            PreProcessor preProcessor = new PreProcessor();

            try {
                Path sourcePath = Paths.get(sourceFile);

                out.println("Processing " + cwd.relativize(sourcePath.toAbsolutePath()));

                preProcessor.process(sourcePath);
            } catch (STMLException e) {
                reportError(e.getSourcePositionId(), e.getMessage());
                logger.log(Level.SEVERE, e.getMessage(), e);
                System.exit(3);
            }

            Source source = preProcessor.getSource();
            try {
                Parser parser = new Parser();
                ParserResult result = parser.parse(source);

                if (!result.successful()) {
                    for (ParserError error : result.getErrors()) {
                        err.println("ERROR: " + error.format());
                        logger.severe(error.format());
                    }
                    System.exit(3);
                }

                if (noOutput) {
                    logger.info("Skipping output generation for " + sourceFile);
                    continue;
                }

                logger.info("Generating YAML for " + sourceFile);
                YamlVisitor visitor = new YamlVisitor(source.getMapper(), result.getRegistry());
                visitor.setFlags(flags);
                result.getContext().accept(visitor);
                final String yaml = visitor.getOutput();

                try {
                    Path outputFile = getOutputName(path, sourceFile);
                    logger.info("Writing YAML to " + outputFile);
                    Files.write(outputFile, yaml.getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    err.println("ERROR: Failed to write to " + output + ": " + e.getMessage());
                    logger.log(Level.SEVERE, e.getMessage(), e);
                    System.exit(3);
                }
            } catch (STMLException e) {
                reportError(e.getSourcePositionId(), e.getMessage());
                logger.log(Level.SEVERE, e.getMessage(), e);
                System.exit(3);
            }
        }

        System.exit(0);
    }

    private static Path getOutputName(Path path, String sourceFile) {
        String filename = Paths.get(sourceFile).getFileName().toString();
        int index = filename.lastIndexOf('.');
        if (index < 0) {
            return path.resolve(filename + ".yaml");
        }
        String yamlFile = filename.substring(0, index) + ".yaml";
        return path.resolve(yamlFile);
    }

    private static void reportError(SourcePositionId position, String format, Object... args) {
        if (position == null) {
            err.println("ERROR: " + String.format(format, args));
            return;
        }
        err.println(position.format() + ": " + String.format(format, args));
    }

    private static void printVersion() {
        String version = Main.class.getPackage().getImplementationVersion();
        out.println("STML v" + version);
    }

    private static void printSyntax() {
        out.println("usage: stml [-h] [-o <output>] [(-f <FLAG>) ...] SOURCE ...");
        out.println();
        printVersion();
        out.println("STML is a meta-language with the sole purpose of reducing the pain you feel inside when reading and writing YAML.");
        out.println();
        out.println("positional arguments:");
        out.println("  SOURCE             One or many source files.");
        out.println();
        out.println("optional arguments:");
        out.println("  -h, --help         Show this help screen.");
        out.println("  -c SHELL           Generate completion script for the given shell. \"source <(stml -c bash)\"");
        out.println("  --kubectl          Generate plugin script for kubectl that should be placed somewhere on the path.");
        out.println("  -f FLAG            Set a transpiler options.");
        out.println("  -no                No output generated. Overrides all other output options.");
        out.println("  -o FILE            The output file to write to.");
        out.println("  -s                 Treat each input as a separate file.");
        out.println("  --version          Prints the current tool version.");
        out.println();
        out.println("flags:");
        for (STMLFlag flag : STMLFlag.values()) {
            out.println(String.format("  %-17s %s", flag.getOption(), flag.getDescription()));
        }
        out.println();
    }
}
