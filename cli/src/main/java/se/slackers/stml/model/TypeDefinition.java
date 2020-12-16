package se.slackers.stml.model;

import se.slackers.stml.mapper.SourceRange;
import se.slackers.stml.util.Pair;

import java.util.*;
import java.util.stream.Stream;

public class TypeDefinition {
    private final FullySpecifiedType type;
    private final TypeDefinition parent;
    private final LinkedHashMap<String, FieldDefinition> fields = new LinkedHashMap<>();

    public TypeDefinition(FullySpecifiedType type) {
        this(type, null);
    }

    public TypeDefinition(FullySpecifiedType type, TypeDefinition parent) {
        this.type = type;
        this.parent = parent;
    }

    public SourceRange getSourceRange() {
        return type.getSourceRange();
    }

    public FullySpecifiedType getType() {
        return type;
    }

    public boolean addField(FieldDefinition fieldDefinition) {
        final String fieldName = fieldDefinition.getName();

        if (parent != null) {
            FieldDefinition parentFieldDefinition = parent.getField(fieldName);
            if (parentFieldDefinition != null) {
                // verify type compliance for overloaded fields

                // TODO: implement
                /*
                if (fieldDefinition.getDefaultValue() == null && parentFieldDefinition.getDefaultValue() != null) {
                    // if the overload doesn't specify a default value
                    // make sure the previous default value is assignable to the new types
                    if (TypeHelper.assignable(fieldDefinition.getTypes(), parentFieldDefinition.getDefaultValue())) {

                    }
                }

                if (!TypeHelper.assignable(parentFieldDefinition.getTypes(), fieldDefinition)) {
                    return false;
                }
                */
            }
        }

        FieldDefinition definition = fields.get(fieldName);
        if (definition == null) {
            fields.put(fieldName, fieldDefinition);
            return true;
        }

        return false;
    }

    public FieldDefinition getField(String fieldName) {
        FieldDefinition field = fields.get(fieldName);
        if (field != null) {
            return field;
        }
        if (parent != null) {
            return parent.getField(fieldName);
        }
        return null;
    }

    public Stream<FieldDefinition> getFields() {
        return fields.values().stream();
    }

    public Stream<Pair<TypeDefinition, FieldDefinition>> getAllFields() {
        if (parent == null) {
            return getFields().map(fd -> Pair.of(TypeDefinition.this, fd));
        }

        Map<String, Pair<TypeDefinition, FieldDefinition>> map = new HashMap<>();
        List<String> order = new ArrayList<>();

        parent.getAllFields().forEach((pair) -> {
            if (null == map.put(pair.getSecond().getName(), pair)) {
                order.add(pair.getSecond().getName());
            }
        });

        getFields().map(fd -> Pair.of(TypeDefinition.this, fd))
                .forEach((pair) -> {
                    if (null == map.put(pair.getSecond().getName(), pair)) {
                        order.add(pair.getSecond().getName());
                    }
                });

        return order.stream().map(map::get);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeDefinition that = (TypeDefinition) o;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
