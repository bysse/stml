package se.slackers.stml.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import se.slackers.stml.mapper.SourcePosition;
import se.slackers.stml.mapper.SourcePositionId;
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

                arguments("emit 1;\nemit 2", "1\n---\n2"),

                arguments("struct A {\nv:const:\"v1\",b:string}\n emit A(b:\"b\");", "v: v1\nb: b"),
                arguments("struct A {\nv:Map(a:1),b:string}\n emit A(b:\"b\");", "v: \n  a: 1\nb: b"),
                arguments("struct A {\nv:List:[1,2,3],b:string}\n emit A(b:\"b\");", "v: \n- 1\n- 2\n- 3\nb: b")
        );
    }

    @ParameterizedTest
    @MethodSource("getSnippets")
    void testSnippets(String stml, String yaml) {
        SourcePositionMapper mapper = new SourcePositionMapper();
        mapper.remap(SourcePosition.TOP, SourcePositionId.DEFAULT);

        Source source = new Source(
                stml,
                Paths.get("."),
                mapper
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
