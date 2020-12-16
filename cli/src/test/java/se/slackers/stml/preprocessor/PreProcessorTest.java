package se.slackers.stml.preprocessor;

import se.slackers.stml.mapper.SourcePosition;
import se.slackers.stml.mapper.SourcePositionId;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class PreProcessorTest {

    @Test
    void testImports() throws STMLException {
        final Path main = Paths.get("src/test/resources/preprocessor/main.stml").toAbsolutePath();
        final Path file1 = Paths.get("src/test/resources/preprocessor/file1.stml").toAbsolutePath();

        // run the preprocessor
        PreProcessor processor = new PreProcessor();
        processor.process(main);
        Source source = processor.getSource();

        // verify the result
        assertEquals(read("src/test/resources/preprocessor/main.fixture.stml"), source.getCode());

        // verify the mapper
        SourcePositionId line0 = source.getMapper().map(SourcePosition.create(0, 0));
        assertEquals(main.toString(), line0.getId());

        SourcePositionId line1 = source.getMapper().map(SourcePosition.create(1, 0));
        assertEquals(file1.toString(), line1.getId());

        SourcePositionId line2 = source.getMapper().map(SourcePosition.create(2, 0));
        assertEquals(main.toString(), line2.getId());

        SourcePositionId line4 = source.getMapper().map(SourcePosition.create(4, 0));
        assertEquals(main.toString(), line4.getId());
    }

    String read(String filename) {
        try {
            final String content = new String(Files.readAllBytes(Paths.get(filename)));
            return content.trim();
        } catch (IOException e) {
            fail(e.getMessage());
            return null;
        }
    }
}