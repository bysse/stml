package se.slackers.stml.io;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface IOLayer {
    Path resolveDirectory(Path path);
    Stream<Path> listDirectory(Path directory) throws IOException;

    Path resolveFile(Path directory, String filename);
    String readFile(Path file) throws IOException;

}
