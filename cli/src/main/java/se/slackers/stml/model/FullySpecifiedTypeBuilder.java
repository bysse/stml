package se.slackers.stml.model;

import se.slackers.stml.mapper.SourceRange;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FullySpecifiedTypeBuilder {
    private final SourceRange sourceRange;
    private final TypeClass typeClass;
    private TypedValue defaultValue = null;
    private FullySpecifiedType subType = null;
    private String typeName = null;
    private FullySpecifiedType superType = null;
    private Map<String, FieldDefinition> fields = null;

    public FullySpecifiedTypeBuilder(SourceRange sourceRange, TypeClass typeClass) {
        this.sourceRange = sourceRange;
        this.typeClass = typeClass;
    }

    public FullySpecifiedTypeBuilder typeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public FullySpecifiedTypeBuilder defaultValue(TypedValue defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public FullySpecifiedTypeBuilder subType(FullySpecifiedType subType) {
        this.subType = subType;
        return this;
    }

    public FullySpecifiedTypeBuilder superType(FullySpecifiedType superType) {
        this.superType = superType;
        return this;
    }

    public FullySpecifiedTypeBuilder addField(FieldDefinition fieldDefinition) {
        if (fields == null) {
            fields = new LinkedHashMap<>();
        }

        fields.put(fieldDefinition.getName(), fieldDefinition);
        return this;
    }

    public FieldDefinition getField(String name) {
        if (fields == null) {
            return null;
        }
        return fields.get(name);
    }

    public Stream<FieldDefinition> getFields() {
        if (fields == null) {
            return Stream.empty();
        }
        return fields.values().stream();
    }

    public FullySpecifiedType create() {
        return new FullySpecifiedType(sourceRange, typeClass, typeName, superType, subType, defaultValue, fields);
    }
}
