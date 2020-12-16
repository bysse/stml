package se.slackers.stml.typechecking;

import se.slackers.stml.error.ErrorCollection;
import se.slackers.stml.model.FieldDefinition;
import se.slackers.stml.model.FullySpecifiedType;
import se.slackers.stml.model.TypeClass;
import se.slackers.stml.model.TypedValue;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TypeHelper {
    private final ErrorCollection errors;

    public TypeHelper(ErrorCollection errors) {
        this.errors = errors;
    }

    public boolean isCompatible(Set<FullySpecifiedType> lvalue, Set<FullySpecifiedType> rtypes, TypedValue rvalue) {
        for (FullySpecifiedType rtype : rtypes) {
            if (!lvalue.contains(rtype)) {
                return false;
            }
        }

        if (rvalue != null) {
            return assignable(lvalue, rvalue);
        }
        return true;
    }

    public boolean assignable(Set<FullySpecifiedType> lvalue, TypedValue rvalue) {
        return lvalue.stream().anyMatch(fst -> assignable(fst, rvalue));
    }

    public boolean assignable(FullySpecifiedType lvalue, TypedValue rvalue) {
        return assignable(lvalue, rvalue.getType(), rvalue);
    }

    public boolean assignable(FullySpecifiedType lvalue, FullySpecifiedType rtype, TypedValue rvalue) {
        if (lvalue.getTypeClass() == TypeClass.ANY || rtype.getTypeClass() == TypeClass.ANY || lvalue.equals(rtype)) {
            return true;
        }

        if (lvalue.getTypeClass() == TypeClass.LIST && rtype.getTypeClass() == TypeClass.LIST) {
            final FullySpecifiedType subType = lvalue.getSubType();
            if (subType == null) {
                return true;
            }

            // even though a List is not explicitly declared as a subtype it can implicitly
            // be of the same type depending on which values it contains

            List<TypedValue> valueList = (List<TypedValue>)rvalue.getValue();
            if (valueList.stream().allMatch(tv -> assignable(subType, tv))) {
                return true;
            }
        }

        if (lvalue.getTypeClass() == TypeClass.MAP && rtype.getTypeClass() == TypeClass.MAP) {
            final FullySpecifiedType subType = lvalue.getSubType();
            if (subType == null) {
                return true;
            }

            // even though a Map is not explicitly declared as a subtype it can implicitly
            // be of the same type depending on which values it contains

            Map<String, TypedValue> valueMap = (Map<String, TypedValue>)rvalue.getValue();
            if (valueMap.values().stream().allMatch(tv -> assignable(subType, tv))) {
                return true;
            }
        }

        if (lvalue.getTypeClass() == TypeClass.STRING && rtype.getTypeClass() == TypeClass.STRING) {
            final FullySpecifiedType subType = lvalue.getSubType();
            if (subType == null) {
                return true;
            }
            if (subType.getTypeClass() != TypeClass.PATTERN) {
                // insanity check
                throw new UnsupportedOperationException("Wow such error");
            }

            // do the regex check
            Pattern pattern = Pattern.compile(subType.getTypeName());
            Matcher matcher = pattern.matcher(Objects.toString(rvalue.getValue()));

            return matcher.matches();
        }

        if (lvalue.getTypeClass() == TypeClass.STRUCT && rtype.getTypeClass() == TypeClass.MAP) {
            // verify implicit assignments of maps to structs
            final FullySpecifiedType subType = rtype.getSubType();
            if (subType != null) {
                // verify that all fields in lvalue are matching the subType
                return lvalue.getAllFields().anyMatch(pair -> {
                    final FieldDefinition field = pair.getSecond();
                    if (field.isConstant()) {
                        return false;
                    }
                    return field.getTypes().contains(subType);
                });
            }



        }

        FullySpecifiedType superType = rtype.getSuperType();
        if (superType != null) {
            return assignable(lvalue, superType);
        }

        return false;
    }

    private boolean assignable(FullySpecifiedType lvalue, FullySpecifiedType rtype) {
        if (lvalue.getTypeClass() == TypeClass.ANY || rtype.getTypeClass() == TypeClass.ANY || lvalue.equals(rtype)) {
            return true;
        }
        if (lvalue.getTypeClass() == TypeClass.LIST && rtype.getTypeClass() == TypeClass.LIST) {
            final FullySpecifiedType subType = lvalue.getSubType();
            if (subType == null) {
                return true;
            }
        }

        if (lvalue.getTypeClass() == TypeClass.MAP && rtype.getTypeClass() == TypeClass.MAP) {
            final FullySpecifiedType subType = lvalue.getSubType();
            if (subType == null) {
                return true;
            }
        }

        if (lvalue.getTypeClass() == TypeClass.STRING && rtype.getTypeClass() == TypeClass.STRING) {
            final FullySpecifiedType subType = lvalue.getSubType();
            if (subType == null) {
                return true;
            }
            if (subType.getTypeClass() != TypeClass.PATTERN) {
                // insanity check
                throw new UnsupportedOperationException("Wow such error");
            }

        }

        return false;
    }

    public static Optional<FullySpecifiedType> getTypeClass(Set<FullySpecifiedType> types, TypeClass typeClass) {
        return types.stream().filter(fst -> fst.getTypeClass() == typeClass).findFirst();
    }

    public static String format(Set<FullySpecifiedType> typeSet) {
        return typeSet.stream()
                .map(FullySpecifiedType::format)
                .collect(Collectors.joining(" | "));
    }
}
