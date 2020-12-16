package se.slackers.stml.typechecking;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import se.slackers.stml.STMLBaseVisitor;
import se.slackers.stml.STMLParser;
import se.slackers.stml.error.ErrorCode;
import se.slackers.stml.error.ErrorCollection;
import se.slackers.stml.io.IOLayer;
import se.slackers.stml.mapper.SourcePosition;
import se.slackers.stml.mapper.SourcePositionId;
import se.slackers.stml.mapper.SourcePositionMapper;
import se.slackers.stml.mapper.SourceRange;
import se.slackers.stml.model.*;
import se.slackers.stml.parser.ParserError;
import se.slackers.stml.util.FindSimilar;
import se.slackers.stml.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class TypeCheckingVisitor extends STMLBaseVisitor<FullySpecifiedType> {
    private final Logger logger = Logger.getLogger(TypeCheckingVisitor.class.getName());
    private final SourcePositionMapper mapper;
    private final Registry registry;
    private final IOLayer io;
    private final ErrorCollection errors;
    private final TypeHelper typeHelper;

    public TypeCheckingVisitor(SourcePositionMapper mapper, IOLayer ioLayer, Registry registry) {
        this.mapper = mapper;
        this.io = ioLayer;
        this.registry = registry;

        this.errors = new ErrorCollection(mapper);
        this.typeHelper = new TypeHelper(errors);
    }

    public List<ParserError> getErrors() {
        return errors.getErrors();
    }

    @Override
    public FullySpecifiedType visitAssignment_statement(STMLParser.Assignment_statementContext ctx) {
        final String variable = ctx.IDENTIFIER().getText();
        final STMLParser.ValueContext valueContext = ctx.value();
        final FullySpecifiedType variableType = valueContext.accept(this);

        TypedValue typedValue = registry.getNodeValueMap().get(valueContext);
        if (typedValue == null) {
            //
            // this is probably due to some error down the parse tree, lets assign the variable a dummy value
            //
            typedValue = TypedValue.builtInType(SourceRange.create(ctx.value()), TypeClass.ANY, null);
        }

        registry.getVariableLookup().set(variable, typedValue);
        return variableType;
    }

    @Override
    public FullySpecifiedType visitType_constructor_map_short(STMLParser.Type_constructor_map_shortContext ctx) {
        final SourceRange position = SourceRange.create(ctx);
        final FullySpecifiedType subType = ctx.template_type_specifier() == null ? null : ctx.template_type_specifier().accept(this);
        final FullySpecifiedType type = FullySpecifiedType.withSubType(position, TypeClass.MAP, subType);

        STMLParser.Key_value_listContext keyValueListCtx = ctx.key_value_list();
        if (keyValueListCtx == null) {
            registry.getNodeValueMap().put(ctx, new TypedValue(position, type, Collections.emptyMap()));
            return type;
        }

        final Map<String, TypedValue> valueMap = parseKeyValueCtx(keyValueListCtx);

        if (type.getTypeClass() == TypeClass.MAP && type.getSubType() != null) {
            validateMapSubType(valueMap, type);
        }

        registry.getNodeValueMap().put(ctx, new TypedValue(position, type, valueMap));
        return type;
    }

    @Override
    public FullySpecifiedType visitType_constructor_list_short(STMLParser.Type_constructor_list_shortContext ctx) {
        final SourceRange position = SourceRange.create(ctx);
        final FullySpecifiedType subType = ctx.template_type_specifier() == null ? null : ctx.template_type_specifier().accept(this);
        final FullySpecifiedType type = FullySpecifiedType.withSubType(position, TypeClass.LIST, subType);

        STMLParser.Value_listContext valueListCtx = ctx.value_list();
        if (valueListCtx == null) {
            registry.getNodeValueMap().put(ctx, new TypedValue(position, type, Collections.emptyMap()));
            return type;
        }

        // parse all values
        List<TypedValue> valueList = new ArrayList<>();
        for (STMLParser.ValueContext valueCtx : valueListCtx.value()) {
            // parse the value
            valueCtx.accept(this);
            valueList.add(registry.getNodeValueMap().get(valueCtx));
        }

        if (type.getSubType() != null) {
            // the list has a sub type, verify that all values in the list
            // are compatible with that sub type

            for (TypedValue typedValue : valueList) {
                if (!typeHelper.assignable(type.getSubType(), typedValue)) {
                    errors.report(
                            typedValue.getValueSourceRange(),
                            ErrorCode.INCOMPATIBLE_TYPES,
                            String.format("Type %s is not compatible with the list type : %s",
                                    typedValue.getType().format(),
                                    type.getSubType().format()
                            )
                    );
                }
            }
        }

        registry.getNodeValueMap().put(ctx, new TypedValue(position, type, valueList));
        return type;
    }

    public FullySpecifiedType visitType_constructor_map(STMLParser.Type_constructor_mapContext ctx) {
        SourceRange position = SourceRange.create(ctx.type_specifier());
        FullySpecifiedType constructorType = ctx.type_specifier().accept(this);

        if (constructorType.getTypeClass() == TypeClass.ENUM) {
            // can't initialize an enum type like this
            errors.report(ctx.type_specifier(), ErrorCode.ENUM_INITIALIZATION, constructorType.format());
            return abortFurtherChecking(ctx.type_specifier());
        }

        STMLParser.Key_value_listContext keyValueListCtx = ctx.key_value_list();
        if (keyValueListCtx == null) {
            // The constructor has been invoked without any arguments, this can be important for maps

            if (constructorType.getTypeClass() == TypeClass.MAP) {
                // if the argument list is empty, there's nothing to validate for map types
                registry.getNodeValueMap().put(ctx, new TypedValue(position, constructorType, Collections.emptyMap()));
                return constructorType;
            }

            if (constructorType.getTypeClass() == TypeClass.LIST) {
                // if the argument list is empty, the parser won't know which rule it should chose for the constructor
                // so an empty list for a list might end up here
                registry.getNodeValueMap().put(ctx, new TypedValue(position, constructorType, Collections.emptyMap()));
                return constructorType;
            }
        }

        if (constructorType.getTypeClass() == TypeClass.LIST) {
            // can't initialize a list type like this
            errors.report(ctx.type_specifier(), ErrorCode.LIST_INITIALIZATION, constructorType.format());
            return abortFurtherChecking(ctx.type_specifier());
        }

        // parse the key values
        final Map<String, TypedValue> fieldValueMap = new LinkedHashMap<>();
        if (keyValueListCtx != null) {
            fieldValueMap.putAll(parseKeyValueCtx(keyValueListCtx));
        }

        if (constructorType.getTypeClass() == TypeClass.STRUCT) {
            FullySpecifiedType fullySpecifiedType = registry.getDefinition(constructorType.getTypeName());
            if (fullySpecifiedType == null) {
                errors.report(ctx.type_specifier(), ErrorCode.UNRESOLVED_TYPE, constructorType.format());
                return abortFurtherChecking(ctx.type_specifier());
            }
            // re-assign the constructor type so that it contains the full type information
            constructorType = fullySpecifiedType;

            Pair<Boolean, Map<String, TypedValue>> result = verifyValueMapTypes(position, constructorType, fieldValueMap);

            // switch out the maps to keep the fields in the declared order
            fieldValueMap.clear();
            fieldValueMap.putAll(result.getSecond());
        }

        if (constructorType.getTypeClass() == TypeClass.MAP && constructorType.getSubType() != null) {
            validateMapSubType(fieldValueMap, constructorType);
        }

        registry.getNodeValueMap().put(ctx, new TypedValue(position, constructorType, fieldValueMap));
        return constructorType;
    }

    private Pair<Boolean, Map<String, TypedValue>> verifyValueMapTypes(SourceRange position, FullySpecifiedType constructorType, Map<String, TypedValue> fieldValueMap) {
        Set<String> declaredFields = new HashSet<>();
        Map<String, TypedValue> valueMap = new LinkedHashMap<>();

        List<Pair<FullySpecifiedType, FieldDefinition>> fieldList = constructorType.getAllFields().collect(Collectors.toList());
        boolean success = true;

        // verify field types and default values
        for (Pair<FullySpecifiedType, FieldDefinition> pair : fieldList) {
            final FullySpecifiedType containingType = pair.getFirst();
            final FieldDefinition fieldDefinition = pair.getSecond();
            final String fieldName = fieldDefinition.getName();

            declaredFields.add(fieldName);

            // get the parsed value from the map
            TypedValue typedValue = fieldValueMap.get(fieldName);
            if (typedValue == null) {
                // no value found in the constructor, verify that it has a default value
                if (fieldDefinition.hasDefaultValue()) {
                    // store default value in map
                    valueMap.put(fieldName, fieldDefinition.getDefaultValue());
                } else {
                    // no default value found for the field, check if the type is null
                    if (typeHelper.getTypeClass(fieldDefinition.getTypes(), TypeClass.NULL).isPresent()) {
                        // no value is allowed
                        valueMap.put(fieldName, TypedValue.builtInType(fieldDefinition.getSourceRange(), TypeClass.NULL, null));
                    } else {
                        success = false;
                        errors.report(position,
                                ErrorCode.FIELD_NOT_INITIALIZED,
                                String.format("Field '%s.%s' is not initialized : declared at %s",
                                        containingType.format(),
                                        fieldDefinition.getName(),
                                        mapper.map(fieldDefinition.getSourceRange())
                                )
                        );
                    }
                }

            } else {
                if (fieldDefinition.isConstant()) {
                    // the field is declared as a constant and still is specified in the construction
                    success = false;
                    errors.report(typedValue.getValueSourceRange(),
                            ErrorCode.FIELD_IS_CONSTANT,
                            String.format("Field '%s.%s' is a constant and can't be assigned any values.",
                                    containingType.format(),
                                    fieldName
                            )
                    );
                } else {
                    Optional<FullySpecifiedType> optionalList = typeHelper.getTypeClass(fieldDefinition.getTypes(), TypeClass.LIST);
                    FullySpecifiedType listSubType = optionalList
                            .map(FullySpecifiedType::getSubType)
                            .orElse(null);

                    if (listSubType != null && listSubType.getTypeClass() == TypeClass.STRUCT && typedValue.getType().getTypeClass() == TypeClass.LIST) {
                        // check if the items in the list are compatible with the list subtype

                        List<TypedValue> valueList = new ArrayList<>();

                        for (TypedValue item : (List<TypedValue>) typedValue.getValue()) {
                            if (item == null) {
                                continue;
                            }
                            if (item.getType().getTypeClass() == TypeClass.MAP) {
                                Pair<Boolean, Map<String, TypedValue>> result = verifyValueMapTypes(
                                        typedValue.getValueSourceRange(),
                                        listSubType,
                                        (Map<String, TypedValue>) item.getValue()
                                );

                                if (result.getFirst()) {
                                    // add the enriched map to the value list
                                    valueList.add(new TypedValue(item.getValueSourceRange(), listSubType, result.getSecond()));
                                } else {
                                    errors.report(typedValue.getValueSourceRange(),
                                            ErrorCode.INCOMPATIBLE_TYPES,
                                            String.format("Type '%s' can't be assigned values of type '%s'",
                                                    listSubType.format(),
                                                    item.getType().format()
                                            )
                                    );
                                }
                            } else {
                                if (!typeHelper.assignable(listSubType, item)) {
                                    success = false;
                                    errors.report(typedValue.getValueSourceRange(),
                                            ErrorCode.INCOMPATIBLE_TYPES,
                                            String.format("Type '%s' can't be assigned values of type '%s'",
                                                    listSubType.format(),
                                                    item.getType().format()
                                            )
                                    );
                                } else {
                                    // add the value to the map
                                    valueList.add(item);
                                }
                            }
                        }

                        if (success) {
                            // add the value to the map
                            valueMap.put(fieldName, new TypedValue(typedValue.getValueSourceRange(), optionalList.get(), valueList));
                            continue;
                        }
                    }

                    Optional<FullySpecifiedType> optionalStruct = typeHelper.getTypeClass(fieldDefinition.getTypes(), TypeClass.STRUCT);
                    if (optionalStruct.isPresent() && typedValue.getType().getTypeClass() == TypeClass.MAP) {
                        // do a recursive type check for possible implicit conversion
                        Pair<Boolean, Map<String, TypedValue>> result = verifyValueMapTypes(
                                typedValue.getValueSourceRange(),
                                optionalStruct.get(),
                                (Map<String, TypedValue>) typedValue.getValue()
                        );

                        if (!result.getFirst()) {
                            success = false;
                            errors.report(typedValue.getValueSourceRange(),
                                    ErrorCode.INCOMPATIBLE_TYPES,
                                    String.format("Type '%s' can't be assigned values of type '%s'",
                                            typeHelper.format(fieldDefinition.getTypes()),
                                            typedValue.getType().format()
                                    )
                            );
                        } else {
                            TypedValue enrichedValue = new TypedValue(typedValue.getValueSourceRange(), typedValue.getType(), result.getSecond());
                            valueMap.put(fieldName, enrichedValue);
                        }
                    } else if (!typeHelper.assignable(fieldDefinition.getTypes(), typedValue)) {
                        success = false;
                        errors.report(typedValue.getValueSourceRange(),
                                ErrorCode.INCOMPATIBLE_TYPES,
                                String.format("Type '%s' can't be assigned values of type '%s'",
                                        typeHelper.format(fieldDefinition.getTypes()),
                                        typedValue.getType().format()
                                )
                        );
                    } else {
                        // add the value to the map
                        valueMap.put(fieldName, typedValue);
                    }
                }
            }
        }

        // check for extra fields in the value map
        final Set<String> fieldsWithValues = new HashSet<>(fieldValueMap.keySet());
        fieldsWithValues.removeAll(declaredFields);

        if (!fieldsWithValues.isEmpty()) {
            final List<String> existingFields = fieldList.stream().map(Pair::getSecond)
                    .map(FieldDefinition::getName)
                    .collect(Collectors.toList());

            for (String extraFieldName : fieldsWithValues) {
                // extract the name of the extraneous field
                final TypedValue typedValue = fieldValueMap.get(extraFieldName);
                String message = String.format("field '%s' has not been declared", extraFieldName);

                // find other possible fields
                Collection<String> similarFields = FindSimilar.findSimilar(extraFieldName, 2, existingFields);
                if (!similarFields.isEmpty()) {
                    String fields = similarFields.stream()
                            .map(s -> "'" + s + "'")
                            .collect(Collectors.joining(", "));
                    message = String.format("field '%s' has not been declared : did you mean %s?", extraFieldName, fields);
                }

                errors.report(
                        typedValue.getValueSourceRange(),
                        ErrorCode.FIELD_NOT_DECLARED,
                        message
                );
            }
        }

        return Pair.of(success, valueMap);
    }

    @Override
    public FullySpecifiedType visitType_constructor_list(STMLParser.Type_constructor_listContext ctx) {
        SourceRange position = SourceRange.create(ctx.type_specifier());
        FullySpecifiedType type = ctx.type_specifier().accept(this);

        if (type.getTypeClass() == TypeClass.ENUM) {
            // can't initialize an enum type
            errors.report(ctx.type_specifier(), ErrorCode.ENUM_INITIALIZATION, type.format());
            return abortFurtherChecking(ctx.type_specifier());
        }

        if (type.getTypeClass() == TypeClass.STRUCT) {
            errors.report(ctx.type_specifier(), ErrorCode.STRUCT_INITIALIZATION, type.format());
            return abortFurtherChecking(ctx.type_specifier());
        }
        if (type.getTypeClass() == TypeClass.MAP) {
            errors.report(ctx.type_specifier(), ErrorCode.MAP_INITIALIZATION, type.format());
            return abortFurtherChecking(ctx.type_specifier());
        }

        STMLParser.Value_listContext valueListCtx = ctx.value_list();
        if (valueListCtx == null) {
            // if the argument list is empty, there's nothing to validate
            registry.getNodeValueMap().put(ctx, new TypedValue(position, type, Collections.emptyList()));
            return type;
        }

        // parse all values
        List<TypedValue> valueList = new ArrayList<>();
        for (STMLParser.ValueContext valueCtx : valueListCtx.value()) {
            // parse the value
            valueCtx.accept(this);
            valueList.add(registry.getNodeValueMap().get(valueCtx));
        }

        if (type.getSubType() != null) {
            // the list has a sub type, verify that all values in the list
            // are compatible with that sub type

            for (TypedValue typedValue : valueList) {
                if (!typeHelper.assignable(type.getSubType(), typedValue)) {
                    errors.report(
                            typedValue.getValueSourceRange(),
                            ErrorCode.INCOMPATIBLE_TYPES,
                            String.format("Type %s is not compatible with the list type : %s",
                                    typedValue.getType().format(),
                                    type.getSubType().format()
                            )
                    );
                }
            }
        }

        registry.getNodeValueMap().put(ctx, new TypedValue(position, type, valueList));
        return type;
    }

    @Override
    public FullySpecifiedType visitType_constructor_import(STMLParser.Type_constructor_importContext ctx) {
        final SourceRange position = SourceRange.create(ctx);
        final Map<String, TypedValue> valueMap = parseKeyValueCtx(ctx.key_value_list());

        // prepare the allowed types
        final FullySpecifiedType stringType = FullySpecifiedType.basicType(null, TypeClass.STRING);
        final Set<FullySpecifiedType> types = new HashSet<>();
        types.add(stringType);
        types.add(FullySpecifiedType.withSubType(null, TypeClass.LIST, stringType));

        // prepare the field values
        final List<Pair<SourceRange, String>> paths = new ArrayList<>();
        final List<Predicate<String>> include = new ArrayList<>();
        final List<Predicate<String>> exclude = new ArrayList<>();

        // go through the arguments
        for (Map.Entry<String, TypedValue> entry : valueMap.entrySet()) {
            final TypedValue typedValue = entry.getValue();
            final FullySpecifiedType valueType = typedValue.getType();

            if (!typeHelper.assignable(types, typedValue)) {
                errors.report(
                        typedValue.getValueSourceRange(),
                        ErrorCode.INCOMPATIBLE_TYPES,
                        String.format(
                                "'field '%s' of type '%s' can't be assigned a values of type '%s'",
                                entry.getKey(), typeHelper.format(types), valueType.format()
                        )
                );

                // since this is an import we might as well abort here
                return abortFurtherChecking(ctx);
            }

            List<Pair<SourceRange, String>> values;
            if (typeHelper.assignable(stringType, typedValue)) {
                // create a singleton list
                values = Collections.singletonList(
                        Pair.of(typedValue.getValueSourceRange(), Objects.toString(typedValue.getValue()))
                );
            } else {
                // collect all string values in the list
                values = ((List<TypedValue>) typedValue.getValue()).stream()
                        .map(tv -> Pair.of(tv.getValueSourceRange(), Objects.toString(tv.getValue())))
                        .collect(Collectors.toList());
            }

            boolean brokenImport = false;
            switch (entry.getKey()) {
                case "from":
                    paths.addAll(values);
                    break;
                case "include":
                    for (Pair<SourceRange, String> pair : values) {
                        try {
                            include.add(Pattern.compile(pair.getSecond(), Pattern.DOTALL).asPredicate());
                        } catch (PatternSyntaxException e) {
                            errors.report(pair.getFirst(), ErrorCode.INVALID_REGEX_PATTERN, e.getMessage() + ":" + e.getPattern());
                            brokenImport = true;
                        }
                    }
                    break;
                case "exclude":
                    for (Pair<SourceRange, String> pair : values) {
                        try {
                            exclude.add(Pattern.compile(pair.getSecond(), Pattern.DOTALL).asPredicate());
                        } catch (PatternSyntaxException e) {
                            errors.report(pair.getFirst(), ErrorCode.INVALID_REGEX_PATTERN, e.getMessage() + ":" + e.getPattern());
                            brokenImport = true;
                        }
                    }
                    break;
                default:
                    errors.report(typedValue.getValueSourceRange(), ErrorCode.FIELD_NOT_DECLARED, "Field '" + entry.getKey() + "' is not declared.");
                    brokenImport = true;
            }

            if (brokenImport) {
                // some of the imports and exports are broken, so it doesn't make sense to continue this import
                return abortFurtherChecking(ctx);
            }
        }

        if (paths.isEmpty()) {
            errors.report(ctx, ErrorCode.FIELD_NOT_INITIALIZED, "Field 'path' is required to be able to import something.");
            return abortFurtherChecking(ctx);
        }

        if (include.isEmpty()) {
            include.add((x) -> true);
        }
        if (exclude.isEmpty()) {
            exclude.add((x) -> x.startsWith("\\."));
        }

        Predicate<String> isIncluded = string -> include.stream().anyMatch(p -> p.test(string));
        Predicate<String> isExcluded = string -> exclude.stream().anyMatch(p -> p.test(string));

        Map<String, TypedValue> propertyMap = new HashMap<>();

        // remap the source file for relative paths
        SourcePositionId originalSourceId = mapper.map(position.getStart());
        Path sourceDirectory = io.resolveDirectory(Paths.get(originalSourceId.getId()));

        // loop through each file and check if it should be read or not
        for (Pair<SourceRange, String> pair : paths) {
            String path = pair.getSecond();
            Path directory = io.resolveFile(sourceDirectory, path);

            logger.info("Converting files in " + directory + " to properties");

            try {
                io.listDirectory(directory)
                        .filter(Files::isRegularFile)
                        .forEach(file -> {
                            String filename = file.getFileName().toString();
                            if (isIncluded.test(filename) && !isExcluded.test(filename)) {
                                try {
                                    logger.info("Importing " + file);
                                    final String content = io.readFile(file);
                                    final TypedValue value = TypedValue.builtInType(pair.getFirst(), TypeClass.STRING, content);
                                    propertyMap.put(filename, value);
                                } catch (IOException e) {
                                    errors.report(pair.getFirst(), ErrorCode.CAN_READ_FILE, file.toString());
                                }
                            }
                        });
            } catch (IOException e) {
                errors.report(ctx, ErrorCode.CAN_NOT_READ_DIRECTORY, directory.toAbsolutePath().toString());
            }
        }

        // construct a typed value and store it in the node value map
        TypedValue typedValue = TypedValue.builtInType(position, TypeClass.MAP, propertyMap);
        registry.getNodeValueMap().put(ctx, typedValue);

        return FullySpecifiedType.basicType(position, TypeClass.MAP);
    }

    @Override
    public FullySpecifiedType visitKey_value_list(STMLParser.Key_value_listContext ctx) {
        throw new UnsupportedOperationException("Should not be called like this");
    }

    @Override
    public FullySpecifiedType visitKey_value(STMLParser.Key_valueContext ctx) {
        throw new UnsupportedOperationException("Should not be called like this");
    }

    @Override
    public FullySpecifiedType visitValue_list(STMLParser.Value_listContext ctx) {
        throw new UnsupportedOperationException("Should not be called like this");
    }

    @Override
    public FullySpecifiedType visitType_field(STMLParser.Type_fieldContext ctx) {
        final String variableName = ctx.getText().replace("\"", "");
        TypedValue typedValue = registry.getVariableLookup().resolve(variableName).orElse(null);
        if (typedValue == null) {
            errors.report(ctx, ErrorCode.UNRESOLVED_IDENTIFIER, variableName);
            return abortFurtherChecking(ctx);
        }
        registry.getNodeValueMap().put(ctx, typedValue);
        return typedValue.getType();
    }

    @Override
    public FullySpecifiedType visitParseVariableUse(STMLParser.ParseVariableUseContext ctx) {
        final String variableName = removeQuotes(ctx.getText());
        TypedValue typedValue = registry.getVariableLookup().resolve(variableName).orElse(null);
        if (typedValue == null) {
            errors.report(ctx, ErrorCode.UNRESOLVED_IDENTIFIER, variableName);
            return abortFurtherChecking(ctx);
        }
        registry.getNodeValueMap().put(ctx, typedValue);
        return typedValue.getType();
    }

    @Override
    public FullySpecifiedType visitEnum_type_declaration(STMLParser.Enum_type_declarationContext ctx) {
        final String typeName = text(ctx.IDENTIFIER(0));
        final String superTypeName = text(ctx.IDENTIFIER(1));
        final SourceRange position = SourceRange.create(ctx);

        if (!verifyIdentifierIsFree(ctx, typeName)) {
            return abortFurtherChecking(ctx);
        }

        // create the type of the enum
        FullySpecifiedTypeBuilder typeBuilder = FullySpecifiedType.from(position, TypeClass.ENUM).typeName(typeName);

        if (superTypeName != null) {
            // a super type is declared

            FullySpecifiedType superType = registry.getDefinition(superTypeName);
            if (superType == null) {
                errors.report(ctx.IDENTIFIER(1), ErrorCode.UNRESOLVED_TYPE, superTypeName);
                return abortFurtherChecking(ctx);
            }
            if (superType.getTypeClass() != TypeClass.ENUM) {
                // enums can only extend other enums
                errors.report(ctx.IDENTIFIER(1), ErrorCode.ENUM_EXTENDS_NON_ENUM, superType.format() + " is not an enum type");
                return abortFurtherChecking(ctx);
            }

            typeBuilder.superType(superType);
        }

        // add all fields
        STMLParser.Enum_type_member_listContext memberListCtx = ctx.enum_type_member_list();
        if (memberListCtx != null) {
            for (TerminalNode fieldNode : memberListCtx.IDENTIFIER()) {
                SourceRange fieldPosition = SourceRange.create(fieldNode);
                String fieldName = text(fieldNode);

                TypedValue fieldValue = new TypedValue(
                        fieldPosition,
                        FullySpecifiedType.basicType(fieldPosition, TypeClass.STRING),
                        fieldName
                );

                FieldDefinition existingField = typeBuilder.getField(fieldName);
                if (existingField != null) {
                    SourcePositionId existing = mapper.map(existingField.getSourceRange().getStart());
                    errors.report(
                            fieldNode,
                            ErrorCode.FIELD_ALREADY_DECLARED,
                            "The enum constant has already been declared at " + existing.format()
                    );
                }

                // add the field, we don't care about redefinitions since it's enum constants
                typeBuilder.addField(FieldDefinition.hasDefaultValue(fieldPosition, fieldName, fieldValue.getType(), fieldValue, true));
            }
        }

        // create the final type
        FullySpecifiedType fullySpecifiedType = typeBuilder.create();

        // register all fields as a variables with the final enum type
        fullySpecifiedType.getAllFields().forEach(pair -> {
            final FieldDefinition field = pair.getSecond();
            final String variableName = typeName + "." + field.getName();

            final TypedValue fieldValue = new TypedValue(
                    field.getSourceRange(),
                    fullySpecifiedType,
                    field.getName()
            );
            registry.getVariableLookup().set(variableName, fieldValue);
        });

        registry.registerTypeDefinition(fullySpecifiedType);
        return fullySpecifiedType;
    }

    private boolean verifyIdentifierIsFree(ParserRuleContext ctx, String typeName) {
        // verify that the type or variable doesn't exist
        Optional<TypedValue> optional = registry.getVariableLookup().resolve(typeName);

        if (optional.isPresent()) {
            TypedValue typedValue = optional.get();
            final SourcePositionId positionId = mapper.map(typedValue.getType().getSourceRange().getStart());
            errors.report(ctx, ErrorCode.IDENTIFIER_REDEFINITION, "Identifier already used for variable declared at " + positionId.format());
        }

        FullySpecifiedType previousDefinition = registry.getDefinition(typeName);
        if (previousDefinition != null) {
            final SourcePositionId positionId = mapper.map(previousDefinition.getSourceRange()).getStart();
            errors.report(ctx, ErrorCode.IDENTIFIER_REDEFINITION, "Identifier '" + typeName + "' already used for type declared at " + positionId.format());
            return false;
        }

        return true;
    }

    @Override
    public FullySpecifiedType visitEnum_type_member_list(STMLParser.Enum_type_member_listContext ctx) {
        return super.visitEnum_type_member_list(ctx);
    }

    @Override
    public FullySpecifiedType visitStruct_type_declaration(STMLParser.Struct_type_declarationContext ctx) {
        final SourceRange position = SourceRange.create(ctx);
        final String typeName = text(ctx.IDENTIFIER(0));

        if (!verifyIdentifierIsFree(ctx, typeName)) {
            return abortFurtherChecking(ctx);
        }

        // create the type of the struct
        FullySpecifiedTypeBuilder typeBuilder = FullySpecifiedType.from(position, TypeClass.STRUCT).typeName(typeName);

        // resolve the super type
        final String superTypeName = text(ctx.IDENTIFIER(1));
        final FullySpecifiedType superType = superTypeName == null ? null : registry.getDefinition(superTypeName);

        if (superTypeName != null) {
            // we have a super type specified in the declaration
            if (superType == null) {
                // the super type has not been defined
                errors.report(ctx.IDENTIFIER(1), ErrorCode.UNRESOLVED_TYPE, superTypeName);
                return abortFurtherChecking(ctx);
            }

            if (superType.getTypeClass() != TypeClass.STRUCT) {
                // the super type is not a struct, this is not supported
                errors.report(ctx.IDENTIFIER(1), ErrorCode.STRUCT_EXTENDS_NON_STRUCT, superType.format() + " is not a struct");
                return abortFurtherChecking(ctx);
            }

            typeBuilder.superType(superType);
        }

        STMLParser.Struct_type_member_listContext memberListCtx = ctx.struct_type_member_list();
        if (memberListCtx != null) {
            // The field declaration is not null and will contain fields

            for (STMLParser.Struct_type_memberContext fieldCtx : memberListCtx.struct_type_member()) {
                final SourceRange fieldPosition = SourceRange.create(fieldCtx);
                final String fieldName = identifier(fieldCtx.any_identifier());
                final STMLParser.ValueContext valueCtx = fieldCtx.value();

                final Set<FullySpecifiedType> types = new HashSet<>();
                if (fieldCtx.type_specifier_list() != null) {
                    // all types are explicitly declared on the field
                    types.addAll(parseTypeSpecifierList(fieldCtx.type_specifier_list()));
                }

                if (fieldCtx.QUESTION() != null) {
                    // the field is marked with a question mark indicating that it is optional and accepts null
                    types.add(FullySpecifiedType.basicType(SourceRange.create(fieldCtx.QUESTION()), TypeClass.NULL));
                }

                TypedValue defaultValue = null;
                if (valueCtx != null) {
                    // a default value has been provided in the field declaration

                    FullySpecifiedType valueType = valueCtx.accept(this);
                    defaultValue = registry.getNodeValueMap().get(valueCtx);

                    if (fieldCtx.type_specifier_list() == null) {
                        // no types were declared on the field so the type needs to be inferred from the value
                        types.add(valueType);
                    } else {
                        // verify that the type declaration is compatible with the default value
                        if (!typeHelper.assignable(types, defaultValue)) {
                            errors.report(
                                    fieldCtx,
                                    ErrorCode.INCOMPATIBLE_TYPES,
                                    String.format(
                                            "'%s' of type '%s' can't be assigned a value of type '%s'",
                                            typeHelper.format(types), fieldName, valueType.format()
                                    )
                            );
                        }
                    }
                }

                // check if this is declared as a constant
                final boolean isConstant = fieldCtx.CONST() != null;

                final FieldDefinition existingField = typeBuilder.getField(fieldName);
                final FieldDefinition superField = superType == null ? null : superType.getField(fieldName);

                if (existingField != null) {
                    // field with the same name has already been declared in the same struct
                    SourcePositionId existing = mapper.map(existingField.getSourceRange().getStart());
                    errors.report(
                            fieldCtx,
                            ErrorCode.FIELD_ALREADY_DECLARED,
                            "'" + fieldName + "' is already declared at " + existing.format()
                    );
                }

                if (superField != null) {
                    // a field with the same name has been declared in a super type
                    if (superField.isConstant()) {
                        SourcePositionId existing = mapper.map(superField.getSourceRange().getStart());
                        errors.report(
                                fieldCtx,
                                ErrorCode.FIELD_PARENT_IS_CONSTANT,
                                "'" + fieldName + "' is not compatible with previous declaration at " + existing.format()
                        );
                    }

                    // check that the redeclaration of the field is compatible
                    if (!typeHelper.isCompatible(superField.getTypes(), types, defaultValue)) {
                        SourcePositionId existing = mapper.map(superField.getSourceRange().getStart());
                        errors.report(
                                fieldCtx,
                                ErrorCode.FIELD_ALREADY_DECLARED,
                                "'" + fieldName + "' is not compatible with previous declaration at " + existing.format()
                        );
                    }
                }

                // add the field to the type
                final FieldDefinition fieldDefinition = new FieldDefinition(fieldPosition, fieldName, types, defaultValue, isConstant);
                typeBuilder.addField(fieldDefinition);
            }
        }

        // create and register the type
        FullySpecifiedType fullySpecifiedType = typeBuilder.create();
        registry.registerTypeDefinition(fullySpecifiedType);
        return fullySpecifiedType;
    }

    private Set<FullySpecifiedType> parseTypeSpecifierList(STMLParser.Type_specifier_listContext ctx) {
        Set<FullySpecifiedType> typeSet = new HashSet<>();
        for (STMLParser.Type_specifierContext typeCtx : ctx.type_specifier()) {
            typeSet.add(typeCtx.accept(this));

        }
        return typeSet;
    }

    @Override
    public FullySpecifiedType visitStruct_type_member_list(STMLParser.Struct_type_member_listContext ctx) {
        return super.visitStruct_type_member_list(ctx);
    }

    @Override
    public FullySpecifiedType visitType_specifier_list(STMLParser.Type_specifier_listContext ctx) {
        return super.visitType_specifier_list(ctx);
    }

    @Override
    public FullySpecifiedType visitType_specifier(STMLParser.Type_specifierContext ctx) {
        SourceRange position = SourceRange.create(ctx);

        if (ctx.ANY() != null) {
            return FullySpecifiedType.basicType(position, TypeClass.ANY);
        }
        if (ctx.BOOLEAN() != null) {
            return FullySpecifiedType.basicType(position, TypeClass.BOOL);
        }
        if (ctx.NULL() != null) {
            return FullySpecifiedType.basicType(position, TypeClass.NULL);
        }
        if (ctx.NUMBER() != null) {
            return FullySpecifiedType.basicType(position, TypeClass.NUMBER);
        }
        if (ctx.STRING() != null) {
            if (ctx.REGEX() == null) {
                return FullySpecifiedType.basicType(position, TypeClass.STRING);
            }
            try {
                final String pattern = removeSlashes(ctx.REGEX().getText());
                // verify the pattern
                Pattern.compile(pattern);

                final FullySpecifiedType patternType = FullySpecifiedType.customType(
                        SourceRange.create(ctx.REGEX()),
                        TypeClass.PATTERN,
                        pattern
                );

                return FullySpecifiedType.withSubType(position, TypeClass.STRING, patternType);
            } catch (PatternSyntaxException e) {
                errors.report(ctx.REGEX(), ErrorCode.INVALID_STRING_PATTERN, e.getMessage());
                return FullySpecifiedType.basicType(position, TypeClass.STRING);
            }
        }

        // list
        if (ctx.LIST() != null) {
            if (ctx.template_type_specifier() == null) {
                return FullySpecifiedType.basicType(position, TypeClass.LIST);
            }
            final FullySpecifiedType templateType = ctx.template_type_specifier().accept(this);
            return FullySpecifiedType.withSubType(position, TypeClass.LIST, templateType);
        }

        // map
        if (ctx.MAP() != null) {
            if (ctx.template_type_specifier() == null) {
                return FullySpecifiedType.basicType(position, TypeClass.MAP);
            }
            final FullySpecifiedType templateType = ctx.template_type_specifier().accept(this);
            return FullySpecifiedType.withSubType(position, TypeClass.MAP, templateType);
        }

        // custom type
        final String typeName = ctx.IDENTIFIER().getText();

        // verify that the type exist
        FullySpecifiedType definition = registry.getDefinition(typeName);
        if (definition == null) {
            errors.report(ctx, ErrorCode.UNRESOLVED_TYPE, typeName);
            return abortFurtherChecking(ctx);
        }

        return definition;
    }

    @Override
    public FullySpecifiedType visitTemplate_type_specifier(STMLParser.Template_type_specifierContext ctx) {
        return ctx.type_specifier().accept(this);
    }

    @Override
    public FullySpecifiedType visitValue(STMLParser.ValueContext ctx) {
        Map<ParserRuleContext, TypedValue> valueMap = registry.getNodeValueMap();

        ParseTree child = ctx.getChild(0);
        FullySpecifiedType type = child.accept(this);

        // propagate the value of the child node to this node
        valueMap.put(ctx, valueMap.get(child));

        return type;
    }

    @Override
    public FullySpecifiedType visitParseString(STMLParser.ParseStringContext ctx) {
        final SourceRange position = SourceRange.create(ctx);
        final String value = removeQuotes(ctx.getText());
        final FullySpecifiedType type = FullySpecifiedType.basicType(position, TypeClass.STRING);

        TypedValue typedValue = new TypedValue(position, type, value);
        registry.getNodeValueMap().put(ctx, typedValue);
        return type;
    }

    @Override
    public FullySpecifiedType visitParseInteger(STMLParser.ParseIntegerContext ctx) {
        try {
            Long.parseLong(ctx.getText());
        } catch (NumberFormatException e) {
            errors.report(ctx, ErrorCode.ILLEGAL_FORMAT, "Invalid integer number " + ctx.getText());
        }

        final SourceRange position = SourceRange.create(ctx);
        final FullySpecifiedType type = FullySpecifiedType.basicType(position, TypeClass.NUMBER);

        TypedValue typedValue = new TypedValue(position, type, ctx.getText());
        registry.getNodeValueMap().put(ctx, typedValue);
        return type;
    }

    @Override
    public FullySpecifiedType visitParseFloat(STMLParser.ParseFloatContext ctx) {
        try {
            Double.parseDouble(ctx.getText());
        } catch (NumberFormatException e) {
            errors.report(ctx, ErrorCode.ILLEGAL_FORMAT, "Invalid floating point number " + ctx.getText());
        }

        final SourceRange position = SourceRange.create(ctx);
        final FullySpecifiedType type = FullySpecifiedType.basicType(position, TypeClass.NUMBER);

        TypedValue typedValue = new TypedValue(position, type, ctx.getText());
        registry.getNodeValueMap().put(ctx, typedValue);
        return type;
    }

    @Override
    public FullySpecifiedType visitParseBoolean(STMLParser.ParseBooleanContext ctx) {
        switch (ctx.getText().toLowerCase()) {
            case "true":
            case "yes":
            case "on":
                TypedValue trueValue = TypedValue.builtInType(SourceRange.create(ctx), TypeClass.BOOL, Boolean.TRUE);
                registry.getNodeValueMap().put(ctx, trueValue);
                return trueValue.getType();
            case "false":
            case "no":
            case "off":
                TypedValue falseValue = TypedValue.builtInType(SourceRange.create(ctx), TypeClass.BOOL, Boolean.FALSE);
                registry.getNodeValueMap().put(ctx, falseValue);
                return falseValue.getType();
            default:
                errors.report(ctx, ErrorCode.ILLEGAL_FORMAT, "Invalid boolean " + ctx.getText());
                TypedValue errorValue = TypedValue.builtInType(SourceRange.create(ctx), TypeClass.BOOL, Boolean.FALSE);
                registry.getNodeValueMap().put(ctx, errorValue);
                return errorValue.getType();
        }
    }

    @Override
    public FullySpecifiedType visitParseInlineImport(STMLParser.ParseInlineImportContext ctx) {
        final String filename = removeQuotes(ctx.StringConstant().getText());

        SourcePositionId sourcePositionId = mapper.map(SourcePosition.create(ctx.start));

        Path sourceDirectory = io.resolveDirectory(Paths.get(sourcePositionId.getId()));

        Path file = sourceDirectory.resolve(filename);
        try {
            logger.info("Reading value from file " + file);

            final SourceRange position = SourceRange.create(ctx);
            final String content = io.readFile(file);
            final FullySpecifiedType type = FullySpecifiedType.basicType(position, TypeClass.STRING);

            TypedValue typedValue = new TypedValue(position, type, content);
            registry.getNodeValueMap().put(ctx, typedValue);
            return type;
        } catch (IOException e) {
            errors.report(ctx, ErrorCode.CAN_NOT_OPEN_FILE, e.getMessage());
        }

        return abortFurtherChecking(ctx);
    }

    @Override
    public FullySpecifiedType visitParseNull(STMLParser.ParseNullContext ctx) {
        final SourceRange position = SourceRange.create(ctx);
        FullySpecifiedType type = FullySpecifiedType.basicType(position, TypeClass.NULL);
        registry.getNodeValueMap().put(ctx, new TypedValue(position, type, null));
        return type;
    }

    private FullySpecifiedType abortFurtherChecking(ParserRuleContext ctx) {
        registry.getNodeValueMap().put(ctx, new TypedValue(SourceRange.create(ctx), FullySpecifiedType.basicType(SourceRange.create(ctx), TypeClass.ANY), null));
        return FullySpecifiedType.basicType(SourceRange.create(ctx), TypeClass.ANY);
    }

    private void validateMapSubType(Map<String, TypedValue> valueMap, FullySpecifiedType type) {
        // the map has a sub type, verify that all values in the map
        // are compatible with that sub type

        for (Map.Entry<String, TypedValue> entry : valueMap.entrySet()) {
            final TypedValue typedValue = entry.getValue();

            if (!typeHelper.assignable(type.getSubType(), typedValue)) {
                errors.report(
                        typedValue.getValueSourceRange(),
                        ErrorCode.INCOMPATIBLE_TYPES,
                        String.format("Type '%s' for field '%s' is not compatible with the type of the Map : %s",
                                typedValue.getType().format(),
                                entry.getKey(),
                                type.getSubType().format()
                        )
                );
            }
        }
    }

    private Map<String, TypedValue> parseKeyValueCtx(STMLParser.Key_value_listContext keyValueListCtx) {
        final Map<String, TypedValue> valueMap = new LinkedHashMap<>();

        // parse all values
        for (STMLParser.Key_valueContext keyValueCtx : keyValueListCtx.key_value()) {
            // parse the value
            keyValueCtx.value().accept(this);

            final String fieldName = identifier(keyValueCtx.any_identifier());
            if (fieldName.length() == 0) {
                errors.report(keyValueCtx, ErrorCode.FIELD_NAME_INVALID, "Empty field name");
                continue;
            }

            final TypedValue typedValue = registry.getNodeValueMap().get(keyValueCtx.value());

            TypedValue previousValue = valueMap.put(fieldName, typedValue);
            if (previousValue != null) {
                SourcePositionId positionId = mapper.map(previousValue.getValueSourceRange().getStart());
                errors.report(keyValueCtx, ErrorCode.FIELD_ALREADY_INITIALIZED, fieldName + " already initialized at " + positionId.format());
            }
        }

        return valueMap;
    }

    private String removeQuotes(String text) {
        if (text.length() == 0) {
            return text;
        }
        if (text.charAt(0) == '"') {
            int length = text.length();
            return text.substring(1, length - 1);
        }
        return text;
    }

    private String removeSlashes(String text) {
        if (text.length() == 0) {
            return text;
        }
        if (text.charAt(0) == '/') {
            int length = text.length();
            return text.substring(1, length - 1);
        }
        return text;
    }

    private String text(ParseTree ctx) {
        if (ctx == null) {
            return null;
        }
        return ctx.getText();
    }

    private String identifier(STMLParser.Any_identifierContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            return text(ctx.IDENTIFIER());
        }
        return removeQuotes(text(ctx.StringConstant()));
    }
}
