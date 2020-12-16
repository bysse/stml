package se.slackers.stml.typechecking;

import se.slackers.stml.error.ErrorCollection;
import se.slackers.stml.mapper.SourcePosition;
import se.slackers.stml.mapper.SourcePositionId;
import se.slackers.stml.mapper.SourcePositionMapper;
import se.slackers.stml.model.FieldDefinition;
import se.slackers.stml.model.FullySpecifiedType;
import se.slackers.stml.model.TypedValue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static se.slackers.stml.mapper.SourceRange.TOP;
import static se.slackers.stml.model.FullySpecifiedType.*;
import static se.slackers.stml.model.TypeClass.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TypeHelperTest {
    private static TypedValue value(FullySpecifiedType type, Object value) {
        return new TypedValue(TOP, type, value);
    }

    private static Map<String, TypedValue> map(FullySpecifiedType type, Object val) {
        Map<String, TypedValue> map = new HashMap<>();
        map.put("field", value(type, val));
        return map;
    }

    private static List<TypedValue> list(FullySpecifiedType type, Object val) {
        return Collections.singletonList(value(type, val));
    }

    private static Stream<Arguments> typePairs() {
        final FullySpecifiedType t_null = basicType(TOP, NULL);
        final FullySpecifiedType t_string = basicType(TOP, STRING);
        final FullySpecifiedType t_bool = basicType(TOP, BOOL);
        final FullySpecifiedType t_number = basicType(TOP, NUMBER);
        final FullySpecifiedType t_map = basicType(TOP, MAP);
        final FullySpecifiedType t_list = basicType(TOP, LIST);

        final FullySpecifiedType t_string_a = withSubType(TOP, STRING, customType(TOP, PATTERN,"a+"));
        final FullySpecifiedType t_map_string = withSubType(TOP, MAP, t_string);
        final FullySpecifiedType t_map_num = withSubType(TOP, MAP, t_number);
        final FullySpecifiedType t_list_string = withSubType(TOP, LIST, t_string);
        final FullySpecifiedType t_list_num = withSubType(TOP, LIST, t_number);

        final FullySpecifiedType t_struct_string = FullySpecifiedType.from(TOP, STRUCT)
                .typeName("S")
                .addField(new FieldDefinition(TOP, "field", Collections.singleton(t_string), null, false))
                .create();

        final FullySpecifiedType t_list_struct = withSubType(TOP, LIST, t_struct_string);

        final FullySpecifiedType t_struct_parent = FullySpecifiedType.from(TOP, STRUCT).typeName("S1").create();
        final FullySpecifiedType t_struct_child = FullySpecifiedType.from(TOP, STRUCT).typeName("S2").superType(t_struct_parent).create();

        return Stream.of(
                // bool checks
                arguments(true, t_bool, value(t_bool, true)),
                arguments(false, t_bool, value(t_number, 2)),

                // numeric checks
                arguments(true, t_number, value(t_number, 5.12f)),
                arguments(false, t_number, value(t_string, "hello")),

                // string checks
                arguments(true, t_string, value(t_string, "abc")),
                arguments(true, t_string, value(t_string_a, "aaa")),
                arguments(true, t_string_a, value(t_string, "aaa")),
                arguments(true, t_string_a, value(t_string_a, "aaa")),

                // map checks
                arguments(true, t_map, value(t_map_string, map(t_string, "string"))),
                arguments(true, t_map, value(t_map_string, map(t_string, "string"))),
                arguments(true, t_map_string, value(t_map, map(t_string, "string"))),

                arguments(false, t_map_string, value(t_map, map(t_number, 1))),
                arguments(true, t_map_num, value(t_map_num, map(t_string, 1))),
                arguments(true, t_map_num, value(t_map, map(t_number, 1))),
                arguments(false, t_map_num, value(t_map_string, map(t_string, "string"))),

                // list checks
                arguments(true, t_list, value(t_list_string, list(t_string, "string"))),
                arguments(true, t_list, value(t_list_string, list(t_string, "string"))),
                arguments(true, t_list_string, value(t_list, list(t_string, "string"))),
                arguments(false, t_list_string, value(t_list, list(t_number, 1))),
                arguments(true, t_list_num, value(t_list_num, list(t_string, 1))),
                arguments(true, t_list_num, value(t_list, list(t_number, 1))),
                arguments(false, t_list_num, value(t_list_string, list(t_string, "string"))),

                // arguments(true, t_list_struct, value(t_list, list(t_map, map(t_string, "string")))),

                // structs
                arguments(true, t_struct_string, value(t_map_string, map(t_string, "string"))),
                arguments(true, t_struct_parent, value(t_struct_child, new HashMap<String, TypedValue>())),

                // null checks
                arguments(false, t_null, value(t_string, "abc")),
                arguments(false, t_string, value(t_null, null))
        );
    }

    @ParameterizedTest(name="[{index}] {1}   <=   {2}   = {0}")
    @MethodSource("typePairs")
    void testAssignments(boolean ok, FullySpecifiedType type, TypedValue value) {
        SourcePositionMapper mapper = new SourcePositionMapper();
        mapper.remap(SourcePosition.TOP, SourcePositionId.DEFAULT);
        ErrorCollection errors = new ErrorCollection(mapper);
        TypeHelper typeHelper = new TypeHelper(errors);

        if (ok) {
            assertTrue(typeHelper.assignable(type, value));
        } else {
            assertFalse(typeHelper.assignable(type, value));
        }
    }
}