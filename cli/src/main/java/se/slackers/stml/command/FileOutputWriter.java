package se.slackers.stml.command;

import se.slackers.stml.CLIException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class FileOutputWriter {
    private static final Logger logger = Logger.getLogger(FileOutputWriter.class.getName());

    private final Path destinationPath;
    private final boolean directoryMode;

    public FileOutputWriter(File destination) {
        this.destinationPath = destination.toPath();

        if (!Files.exists(destinationPath)) {
            if (!destination.getName().contains(".")) {
                if (!destination.mkdirs()) {
                    throw new CLIException("Destination directory is not writable : " + destinationPath.toAbsolutePath());
                }
            }
        }

        if (destination.isDirectory()) {
            if (!Files.isWritable(destinationPath)) {
                throw new CLIException("Destination directory is not writable : " + destinationPath.toAbsolutePath());
            }
            directoryMode = true;
        } else {
            directoryMode = false;
        }
    }

    public boolean isDirectoryMode() {
        return directoryMode;
    }

    public void write(Path path, String content) throws IOException {
        if (path.isAbsolute()) {
            if (path.getFileName() == null) {
                throw new IllegalStateException("Path contains no filename : " + path);
            }
            path = path.getFileName();
        }

        if (directoryMode) {
            Path output = switchExtension(destinationPath.resolve(path));
            writeToFile(output, content);
        } else {
            writeToFile(path, content);
        }
    }

    private void writeToFile(Path path, String content) throws IOException {
        Path directory = path.getParent();
        if (directory == null) {
            throw new IllegalStateException("Parent directory is null : " + path);
        }

        if (!Files.exists(directory)) {
            if (!directory.toFile().mkdirs()) {
                throw new CLIException("Failed to create directory : " + directory);
            }
        }

        try {
            logger.info("Writing YAML to " + path.toAbsolutePath());
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IOException("Failed to write to file : " + path.toAbsolutePath() + " : " + e.getMessage(), e);
        }
    }

    private Path switchExtension(Path path) {
        String filePath = path.toAbsolutePath().toString();
        if (filePath.toLowerCase().endsWith(".stml")) {
            int index = filePath.lastIndexOf('.');
            return Paths.get(filePath.substring(0, index) + ".yaml");
        }
        return Paths.get(filePath + ".yaml");
    }
}
