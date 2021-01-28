package se.slackers.stml.command;

import se.slackers.stml.CLIException;
import se.slackers.stml.preprocessor.PreProcessor;
import se.slackers.stml.preprocessor.STMLException;
import se.slackers.stml.preprocessor.Source;
import se.slackers.stml.util.Pair;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class PreProcessUtil {
    static final Logger logger = Logger.getLogger(PreProcessUtil.class.getName());

    public static Stream<Source> preprocessFiles(File[] inputFiles, boolean mergeInputs, boolean recursiveRead) {
        List<Pair<File, String>> fileList = new ArrayList<>();

        // expand all directories to lists of files
        for (File inputFile : inputFiles) {
            verifyReadable(inputFile);

            if (inputFile.isDirectory()) {
                fileList.addAll(handleDirectory(inputFile, recursiveRead));
            }
        }

        if (mergeInputs) {
            PreProcessor preProcessor = new PreProcessor();
            for (Pair<File, String> pair : fileList) {
                try {
                    preProcessor.process(pair.getFirst().toPath());
                } catch (STMLException e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                    throw new CLIException(e.getSourcePositionId().format() + ": " + e.getMessage());
                }
            }

            return Stream.of(preProcessor.getSource());
        }

        return fileList.stream().map(pair -> {
            PreProcessor preProcessor = new PreProcessor();
            try {
                preProcessor.process(pair.getFirst().toPath());
            } catch (STMLException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                throw new CLIException(e.getSourcePositionId().format() + ": " + e.getMessage());
            }
            Source source = preProcessor.getSource();

            return new Source(source.getCode(), Paths.get(pair.getSecond()), source.getMapper());
        });
    }

    public static List<Pair<File, String>> handleDirectory(File directory, boolean recursiveRead) {
        List<Pair<File, String>> fileList = new ArrayList<>();

        File[] contents = directory.listFiles(file -> file.isDirectory() || file.getName().endsWith(".stml"));
        if (contents != null) {
            for (File file : contents) {
                if (file.isDirectory() && !recursiveRead) {
                    // disregard folders is the recursion flag is off
                    continue;
                }

                verifyReadable(file);

                if (file.isDirectory()) {
                    handleDirectory(file, recursiveRead).stream()
                            // append the directory name to the file path
                            .map(pair -> Pair.of(pair.getFirst(), file.getName() + File.separatorChar + pair.getSecond()))
                            .forEach(fileList::add);
                } else {
                    fileList.add(Pair.of(file, file.getName()));
                }
            }
        }

        return fileList;
    }

    public static void verifyReadable(File file) {
        final Path path = file.toPath();
        if (!Files.isReadable(path)) {
            if (file.isFile()) {
                throw new CLIException(String.format("File is not readable '%s'", path.toAbsolutePath()));
            } else {
                throw new CLIException(String.format("Directory is not readable '%s'", path.toAbsolutePath()));
            }
        }
    }
}
