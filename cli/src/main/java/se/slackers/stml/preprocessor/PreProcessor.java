package se.slackers.stml.preprocessor;

import se.slackers.stml.error.ErrorCode;
import se.slackers.stml.mapper.SourcePosition;
import se.slackers.stml.mapper.SourcePositionId;
import se.slackers.stml.mapper.SourcePositionMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreProcessor {
    private static final Pattern INCLUDE_PATTERN = Pattern.compile("\\s*#\\s*include\\s+<([a-zA-Z0-9.-/]+)>.*", Pattern.CASE_INSENSITIVE);
    private static final Pattern IMPORT_PATTERN = Pattern.compile("\\s*import\\s+([a-zA-Z0-9.-/]+)", Pattern.CASE_INSENSITIVE);

    private final Logger logger = Logger.getLogger(PreProcessor.class.getName());
    private final SourcePositionMapper mapper;
    private final StringBuilder content;
    private final Set<Path> pathSet;
    private Path path;

    private int lineCount;

    public PreProcessor() {
        this.mapper = new SourcePositionMapper();
        this.content = new StringBuilder();
        this.pathSet = new HashSet<>();
        this.lineCount = 0;
    }

    public void process(Path path) throws STMLException {
        this.path = path;
        logger.info("Processing file " + path.toAbsolutePath());
        appendFile(content, path, null);
    }

    public Source getSource() {
        return new Source(
                content.toString().trim(),
                path,
                mapper
        );
    }

    private void appendFile(StringBuilder content, Path path, Path origin) throws STMLException {
        if (pathSet.contains(path)) {
            logger.warning("File " + path + " has already been included");
            return;
        }
        pathSet.add(path);

        if (!Files.isReadable(path)) {
            if (origin == null) {
                throw new STMLException(ErrorCode.CAN_NOT_OPEN_FILE, path.toString());
            }
            throw new STMLException(ErrorCode.CAN_NOT_OPEN_FILE, path + " : from " + formatOrigin(origin));
        }

        // start the remap
        mapper.remap(SourcePosition.create(lineCount, 0), SourcePositionId.create(path.toAbsolutePath().toString(), 0, 0));

        Path directory = path.getParent();
        if (directory == null) {
            directory = Paths.get(".");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // check for preprocessor arguments
                if (line.contains("include") || line.contains("import")) {

                    // check for include directives
                    final Matcher includeMatcher = INCLUDE_PATTERN.matcher(line);
                    if (includeMatcher.matches()) {
                        Path file = directory.resolve(includeMatcher.group(1));
                        appendFile(content, file, path);
                        mapper.remap(SourcePosition.create(lineCount, 0), SourcePositionId.create(path.toAbsolutePath().toString(), 0, 0));
                        continue;
                    }

                    // check for import directives
                    final Matcher importMatcher = IMPORT_PATTERN.matcher(line);
                    if (importMatcher.matches()) {
                        Path file = directory.resolve(importMatcher.group(1));
                        appendFile(content, file, path);
                        mapper.remap(SourcePosition.create(lineCount, 0), SourcePositionId.create(path.toAbsolutePath().toString(), 0, 0));
                        continue;
                    }
                }

                content.append(line).append('\n');
                lineCount++;
            }
        } catch (IOException e) {
            throw new STMLException(ErrorCode.CAN_READ_FILE, path.toAbsolutePath().toString(), e);
        }
    }

    private String formatOrigin(Path origin) {
        if (origin == null) {
            return "";
        }

        return origin + "(" + lineCount + "): ";
    }
}
