package se.slackers.stml.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import se.slackers.stml.mapper.SourcePositionMapper;
import se.slackers.stml.preprocessor.Source;
import se.slackers.stml.yaml.YamlVisitor;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SnippetConversionTest {
    private static Stream<Arguments> getSnippets() {
        return Stream.of(
                arguments("emit \"test\";", "test"),
                arguments("emit 3;", "3"),
                arguments("emit 3.14;", "3.14"),
                arguments("emit true;", "true"),
                arguments("emit null;", "null"),

                arguments("emit 1;\nemit 2", "1\n---\n2")
        );
    }

    @ParameterizedTest
    @MethodSource("getSnippets")
    void testSnippets(String stml, String yaml) {
        Source source = new Source(
                stml,
                Paths.get("."),
                new SourcePositionMapper()
        );

        Parser parser = new Parser();
        ParserResult result = parser.parse(source);

        if (!result.successful()) {
            result.getErrors().forEach((error) -> {
                System.out.println(error.getRange().format());
                System.out.println(error.format());
            });

            fail("Compilation failed");
        } else {
            YamlVisitor visitor = new YamlVisitor(source.getMapper(), result.getRegistry());
            result.getContext().accept(visitor);
            assertEquals("---\n" + yaml, visitor.getOutput());
        }
    }
}
