package se.slackers.stml.model;

import se.slackers.stml.mapper.SourceRange;

import java.util.Objects;

public class TypedValue {
    private final SourceRange sourceRange;
    private final FullySpecifiedType type;
    private final Object value;

    public static  TypedValue builtInType(SourceRange sourceRange, TypeClass typeClass, Object value) {
        return new TypedValue(sourceRange, FullySpecifiedType.basicType(sourceRange, typeClass), value);
    }

    public static <V> TypedValue builtInType(SourceRange sourceRange, TypeClass typeClass, FullySpecifiedType template) {
        return new TypedValue(sourceRange, FullySpecifiedType.withSubType(sourceRange, typeClass, template), null);
    }

    /**
     * @param sourceRange The source position of the value.
     * @param type
     * @param value
     */
    public TypedValue(SourceRange sourceRange, FullySpecifiedType type, Object value) {
        this.sourceRange = sourceRange;
        this.type = type;
        this.value = value;
    }

    public SourceRange getValueSourceRange() {
        return sourceRange;
    }

    public FullySpecifiedType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Objects.toString(value) + ": " + type;
    }
}
