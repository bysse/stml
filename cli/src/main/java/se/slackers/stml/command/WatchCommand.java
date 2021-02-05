package se.slackers.stml.command;

import picocli.CommandLine;
import se.slackers.stml.CLIException;
import se.slackers.stml.VersionProvider;

import java.io.File;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@CommandLine.Command(
        name = "watch",
        aliases = "w",
        description = "Watches a directory for one or many STML files to YAML",
        versionProvider = VersionProvider.class
)

public class WatchCommand implements Callable<Integer> {
    static final Logger logger = Logger.getLogger(WatchCommand.class.getName());

    @CommandLine.Parameters(index = "0", paramLabel = "directory")
    private File directory;

    @CommandLine.Option(names = {"-V", "--version"}, versionHelp = true, description = "display version info")
    private boolean versionInfoRequested;

    @CommandLine.Option(names = {"-o", "--output"}, description = "output directory")
    private File output;

    @Override
    public Integer call() throws Exception {
        if (directory == null) {
            throw new CLIException("No directory specified", true);
        }
        Path root = directory.toPath();
        if (!Files.isDirectory(root)) {
            throw new CLIException("Specified directory is in fact not a directory");
        }
        if (!Files.isReadable(root)) {
            throw new CLIException("Specified directory is not readable");
        }

        if (output == null) {
            output = directory;
        } else {
            Path outputPath = output.toPath();
            if (!Files.isDirectory(outputPath) || !Files.isWritable(outputPath)) {
                throw new CLIException("Output directory is not writable");
            }
        }

        // create a watch service for the configured path
        WatchService watchService = FileSystems.getDefault().newWatchService();
        root.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        // perform an initial scan of the folder
        Files.list(root).forEach(this::handleFile);

        try {
            System.err.printf("Watching %s for changes\n", root);
            while (true) {
                // check watch service for filesystem changes
                WatchKey key = watchService.poll(1000, TimeUnit.MILLISECONDS);
                if (key == null) {
                    continue;
                }
                readAndNotifyChange(key.pollEvents());
                key.reset();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        watchService.close();
        return 0;
    }

    @SuppressWarnings("unchecked")
    private void readAndNotifyChange(List<WatchEvent<?>> events) {
        for (WatchEvent<?> event : events) {
            if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                // discard
                continue;
            }

            WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
            Path path = pathEvent.context();
            if (path != null) {
                handleFile(path);
            }
        }
    }

    private void handleFile(Path path) {
        if (!Files.isRegularFile(path)) {
            return;
        }
        try {
            CompileCommand compileCommand = new CompileCommand(path.toFile(), output);
            compileCommand.call();
        } catch (CLIException e) {
            System.err.println(e.getMessage());
        }
    }
}
