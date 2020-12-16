package se.slackers.stml.parser;

import se.slackers.stml.preprocessor.PreProcessor;
import se.slackers.stml.preprocessor.Source;
import se.slackers.stml.yaml.YamlVisitor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ParserTest {
    private static Stream<Arguments> getSourceFiles() {
        return Stream.of(
                arguments("src/test/resources/stml/tiny.stml")
        );
    }

    @ParameterizedTest
    @MethodSource("getSourceFiles")
    void testFiles(String filename) {
        PreProcessor processor = new PreProcessor();
        processor.process(Paths.get(filename));
        Source source = processor.getSource();

        Parser parser = new Parser();
        ParserResult result = parser.parse(source);

        if (!result.successful()) {
            result.getErrors().forEach((error) -> {
                System.out.println(error.getRange().format());
                System.out.println(error.format());
            });
        } else {
            YamlVisitor visitor = new YamlVisitor(source.getMapper(), result.getRegistry());
            result.getContext().accept(visitor);
            System.out.println(visitor.getOutput());
        }

        //assertTrue(result.successful());
    }
}
