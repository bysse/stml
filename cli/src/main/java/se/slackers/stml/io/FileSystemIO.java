package se.slackers.stml.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileSystemIO implements IOLayer {
    @Override
    public Path resolveDirectory(Path path) {
        if (Files.isDirectory(path)) {
            return path;
        }
        return path.getParent();
    }

    @Override
    public Stream<Path> listDirectory(Path directory) throws IOException {
        return Files.list(directory);
    }

    @Override
    public Path resolveFile(Path directory, String filename) {
        if (filename.startsWith("/")) {
            return Paths.get(filename);
        }
        Path dir = resolveDirectory(directory);
        return dir.resolve(filename);
    }

    @Override
    public String readFile(Path file) throws IOException {
        return new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
    }
}
