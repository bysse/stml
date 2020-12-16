package se.slackers.stml.model;

import se.slackers.stml.mapper.SourceRange;
import se.slackers.stml.util.Pair;

import java.util.*;
import java.util.stream.Stream;

public class FullySpecifiedType {
    private final SourceRange sourceRange;
    private final TypeClass typeClass;
    private final String typeName;
    private final FullySpecifiedType superType;
    private final FullySpecifiedType subType;
    private final TypedValue defaultValue;
    private final Map<String, FieldDefinition> fields = new LinkedHashMap<>();

    public static FullySpecifiedTypeBuilder from(SourceRange sourceRange, TypeClass typeClass) {
        return new FullySpecifiedTypeBuilder(sourceRange, typeClass);
    }

    public static FullySpecifiedType basicType(SourceRange sourceRange, TypeClass typeClass) {
        return new FullySpecifiedType(sourceRange, typeClass, null, null, null, null, null);
    }

    public static FullySpecifiedType customType(SourceRange sourceRange, TypeClass typeClass, String customType) {
        return new FullySpecifiedType(sourceRange, typeClass, customType, null, null, null, null);
    }

    public static FullySpecifiedType withSubType(SourceRange sourceRange, TypeClass typeClass, FullySpecifiedType subType) {
        return new FullySpecifiedType(sourceRange, typeClass, null, null, subType, null, null);
    }

    FullySpecifiedType(SourceRange sourceRange, TypeClass typeClass, String typeName, FullySpecifiedType superType, FullySpecifiedType subType, TypedValue defaultValue, Map<String, FieldDefinition> fields) {
        this.sourceRange = sourceRange;
        this.typeClass = typeClass;
        this.typeName = typeName;
        this.superType = superType;
        this.subType = subType;
        this.defaultValue = defaultValue;
        if (fields != null) {
            this.fields.putAll(fields);
        }

        if (superType != null && defaultValue != null) {
            throw new IllegalArgumentException("Types with a super type can't have default values");
        }
    }

    public TypeClass getTypeClass() {
        return typeClass;
    }

    public String getTypeName() {
        if (typeName == null) {
            return typeClass.getToken();
        }
        return typeName;
    }

    public FullySpecifiedType getSuperType() {
        return superType;
    }

    public FullySpecifiedType getSubType() {
        return subType;
    }

    public TypedValue getDefaultValue() {
        return defaultValue;
    }

    public Stream<FieldDefinition> getFieldsInType() {
        if (fields.isEmpty()) {
            return Stream.empty();
        }
        return fields.values().stream();
    }

    public Stream<Pair<FullySpecifiedType, FieldDefinition>> getAllFields() {
        if (superType == null) {
            return getFieldsInType().map(fd -> Pair.of(this, fd));
        }

        final Map<String, Pair<FullySpecifiedType, FieldDefinition>> map = new LinkedHashMap<>();

        // gather all field from the super type
        superType.getAllFields()
                .forEach((pair) -> map.put(pair.getSecond().getName(), pair));

        // then add the field from this type
        getFieldsInType()
                .map(fd -> Pair.of(this, fd))
                .forEach((pair) -> map.put(pair.getSecond().getName(), pair));

        return map.values().stream();
    }

    public FieldDefinition getField(String name) {
        FieldDefinition definition = fields.get(name);
        if (definition != null) {
            return definition;
        }
        if (superType != null) {
            return superType.getField(name);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullySpecifiedType that = (FullySpecifiedType) o;
        return typeClass == that.typeClass &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(superType, that.superType) &&
                Objects.equals(subType, that.subType) &&
                Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeClass, typeName, superType, subType, fields);
    }

    public String format() {
        String result = typeName == null ? typeClass.getToken() : typeName;
        if (typeClass.allowsSubType() && subType != null) {
            if (subType.getTypeClass() == TypeClass.PATTERN) {
                result += "</" + subType.format() + "/>";
            } else {
                result += "<" + subType.format() + ">";
            }
        }
        return result;
    }

    public SourceRange getSourceRange() {
        return sourceRange;
    }

    public String toString() {
        return format();
    }
}
