package se.slackers.stml.typechecking;

import se.slackers.stml.STMLLexer;
import se.slackers.stml.STMLParser;
import se.slackers.stml.error.ErrorCode;
import se.slackers.stml.io.IOLayer;
import se.slackers.stml.mapper.SourcePosition;
import se.slackers.stml.mapper.SourcePositionId;
import se.slackers.stml.mapper.SourcePositionMapper;
import se.slackers.stml.model.Registry;
import se.slackers.stml.parser.ParserError;
import se.slackers.stml.yaml.YamlVisitor;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static se.slackers.stml.error.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;

class TypeCheckingVisitorTest {
    private static Stream<Arguments> codeSnippets() {
        String tree = "struct P { a: string | number } struct D { p: P }\n";
        String struct = "struct A { a: string; b: 1, d: const = true }\n";
        String string = "struct A { a: string</a+/> }\n";
        return Stream.of(
                arguments(true, "enum Status { }", OK),
                arguments(true, "enum Status { Ok; Error; }", OK),

                // maps
                arguments(true, "let m = Map();", OK),
                arguments(true, "let m = Map<String>();", OK),
                arguments(true, "let m = Map(a=1, b=2);", OK),
                arguments(true, "let m = Map<Number>(a=1, b=2);", OK),
                arguments(true, "let m = Map(a=1, b=\"str\");", OK),

                arguments(false, "let m = Map(a=1, a=2);", FIELD_ALREADY_INITIALIZED),
                arguments(false, "let m = Map<String>(a=1);", INCOMPATIBLE_TYPES),
                arguments(false, "let m = Map(1, 2, 3);", MAP_INITIALIZATION),

                // enums
                arguments(true, "enum A { a, b } enum B < A { c } let d = B.a", OK),

                // lists
                arguments(true, "let l = List();", OK),
                arguments(true, "let l = List<String>();", OK),
                arguments(true, "let l = List(1,2,3);", OK),
                arguments(true, "let l = List<String>(\"a\", \"b\", \"c\");", OK),
                arguments(false, "let l = List(a=1, b=2);", LIST_INITIALIZATION),
                arguments(true, "struct A{f:number} struct B{a:List<A>} emit B(a:[{f:1},A(f:2)])", OK),

                // struct
                arguments(true, "struct S{s?:string} let s=S()", OK),
                arguments(true, struct + "let s = A(a=\"aa\");", OK),
                arguments(false, struct + "let s = A();", FIELD_NOT_INITIALIZED),
                arguments(false, struct + "let s = MISSING();", UNRESOLVED_TYPE),
                arguments(false, struct + "let s = A(a=\"aa\", c=3);", FIELD_NOT_DECLARED),
                arguments(false, struct + "let s = A(a=\"aa\", d=3);", FIELD_IS_CONSTANT),

                arguments(true, "struct S{a?:string} struct T{s:S} let t=T(s:{}) emit t", OK),
                arguments(true, "struct S{a:string} struct T{s:S} let t=T(s:{a:\"test\"}) emit t", OK),
                arguments(true, "struct S{\"string\":string} let t=S(\"string\": \"test\")", OK),

                // inheritance overrides
                arguments(true, tree + "struct C1<P { } emit C1(a: \"test\")", OK),
                arguments(true, tree + "struct C1<P { a: number } emit C1(a: 2)", OK),
                arguments(true, tree + "struct C1<P { a = 1  } emit C1(a: 2)", OK),
                arguments(true, tree + "struct C1<P { a: const : 1  } emit C1()", OK),

                arguments(false, tree + "struct C1<P { a = true  }", FIELD_ALREADY_DECLARED),
                arguments(false, tree + "struct C1<P { a: const : 1  } emit C1(a: \"test\")", FIELD_IS_CONSTANT),

                // inheritance
                arguments(true, tree + "struct C1<P { a = 1 } emit D(p: C1())", OK),

                // imports
                arguments(true, "let t={a: import(from: \"/\")}", OK),
                arguments(true, "let t={a: import(from: [\"a\",\"b\"])}", OK),
                arguments(true, "let t={a: import(from: \"/\", include: \".\", exclude: \".\")}", OK),
                arguments(false, "let t={a: import(paths: \"/\")}", FIELD_NOT_DECLARED),
                arguments(false, "let t={a: import(from: 2)}", INCOMPATIBLE_TYPES),
                arguments(false, "let t={a: import(include: \".*\")}", FIELD_NOT_INITIALIZED),

                // strings
                arguments(true, string + "let s = A(a=\"aa\");", OK),
                arguments(false, string + "let s = A(a=\"ab\");", INCOMPATIBLE_TYPES)
        );
    }

    @ParameterizedTest
    @MethodSource("codeSnippets")
    void testFiles(boolean ok, String source, ErrorCode expectedError) {
        CharStream input = CharStreams.fromString(source);
        STMLLexer lexer = new STMLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        STMLParser parser = new STMLParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());

        STMLParser.Statement_listContext context = parser.statement_list();

        IOLayer ioMock = mock(IOLayer.class);

        SourcePositionMapper mapper = new SourcePositionMapper();
        mapper.remap(SourcePosition.TOP, SourcePositionId.DEFAULT);

        Registry registry = new Registry();
        TypeCheckingVisitor typeChecking = new TypeCheckingVisitor(mapper, ioMock, registry);
        try {
            context.accept(typeChecking);
            List<ParserError> errors = typeChecking.getErrors();

            errors.forEach(System.err::println);

            if (ok) {
                assertTrue(errors.isEmpty());
            } else {
                if (errors.isEmpty()) {
                    fail("Snippet contains error but didn't fail");
                } else {
                    for (ParserError error : errors) {
                        assertEquals(expectedError, error.getErrorCode());
                    }
                }
            }

            if (errors.isEmpty() && source.contains("emit")) {
                YamlVisitor visitor = new YamlVisitor(mapper, registry);
                context.accept(visitor);
                System.err.println("OUTPUT\n" + visitor.getOutput());
            }
        } catch (Exception e) {
            fail("Snippet shouldn't fail", e);
        }
    }
}