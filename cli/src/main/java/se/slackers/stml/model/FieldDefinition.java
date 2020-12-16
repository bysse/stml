package se.slackers.stml.model;

import se.slackers.stml.mapper.SourceRange;
import se.slackers.stml.typechecking.TypeHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class FieldDefinition {
    private final SourceRange position;
    private final String name;
    private final Set<FullySpecifiedType> types;
    private final TypedValue defaultValue;
    private final boolean isConstant;

    public static FieldDefinition noDefaultValue(SourceRange sourcePosition, String name, FullySpecifiedType... types) {
        return new FieldDefinition(sourcePosition, name, Arrays.stream(types).collect(Collectors.toSet()), null, false);
    }

    public static FieldDefinition hasDefaultValue(SourceRange sourcePosition, String name, FullySpecifiedType type, TypedValue defaultValue, boolean isConstant) {
        return new FieldDefinition(sourcePosition, name, Collections.singleton(type), null, isConstant);
    }

    public FieldDefinition(SourceRange position, String name, Set<FullySpecifiedType> types, TypedValue defaultValue, boolean isConstant) {
        this.position = position;
        this.name = name;
        this.types = types;
        this.defaultValue = defaultValue;
        this.isConstant = isConstant;
    }

    public SourceRange getSourceRange() {
        return position;
    }

    public String getName() {
        return name;
    }

    public Set<FullySpecifiedType> getTypes() {
        return types;
    }

    public boolean hasDefaultValue() {
        return defaultValue != null;
    }

    public boolean isConstant() {
        return isConstant;
    }

    public TypedValue getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldDefinition that = (FieldDefinition) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String toString() {
        return name + ": " + TypeHelper.format(types);
    }
}
