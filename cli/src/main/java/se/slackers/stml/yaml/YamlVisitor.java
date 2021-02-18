package se.slackers.stml.yaml;

import se.slackers.stml.STMLBaseVisitor;
import se.slackers.stml.STMLFlag;
import se.slackers.stml.STMLParser;
import se.slackers.stml.error.ErrorCode;
import se.slackers.stml.mapper.SourcePosition;
import se.slackers.stml.mapper.SourcePositionMapper;
import se.slackers.stml.model.Registry;
import se.slackers.stml.model.TypeClass;
import se.slackers.stml.model.TypedValue;
import se.slackers.stml.preprocessor.STMLException;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class YamlVisitor extends STMLBaseVisitor<Void> {
    private final SourcePositionMapper mapper;
    private final Registry registry;
    private final Output output;

    private final String INDENT = "  ";
    private boolean emit = false;
    private boolean linebreak = false;
    private boolean renderNull = false;

    public YamlVisitor(SourcePositionMapper mapper, Registry registry) {
        this.mapper = mapper;
        this.registry = registry;
        this.output = new Output();
    }

    public void setFlags(Set<STMLFlag> flags) {
        renderNull = flags.contains(STMLFlag.RENDER_NULL);
    }

    public String getOutput() {
        return output.toString();
    }

    @Override
    public Void visitEmit_statement(STMLParser.Emit_statementContext ctx) {
        if (!output.endsWithNewLine()) {
            output.newLine();
        }
        output.newStructure();
        linebreak = false;

        emit = true;
        super.visitEmit_statement(ctx);
        emit = false;

        return null;
    }

    @Override
    public Void visitParseString(STMLParser.ParseStringContext ctx) {
        TypedValue typedValue = registry.getNodeValueMap().get(ctx);
        emitValue(typedValue, "");
        return null;
    }

    @Override
    public Void visitParseInteger(STMLParser.ParseIntegerContext ctx) {
        TypedValue typedValue = registry.getNodeValueMap().get(ctx);
        emitValue(typedValue, "");
        return null;
    }

    @Override
    public Void visitParseFloat(STMLParser.ParseFloatContext ctx) {
        TypedValue typedValue = registry.getNodeValueMap().get(ctx);
        emitValue(typedValue, "");
        return null;
    }

    @Override
    public Void visitParseBoolean(STMLParser.ParseBooleanContext ctx) {
        TypedValue typedValue = registry.getNodeValueMap().get(ctx);
        emitValue(typedValue, "");
        return null;
    }

    @Override
    public Void visitParseNull(STMLParser.ParseNullContext ctx) {
        TypedValue typedValue = registry.getNodeValueMap().get(ctx);
        emitValue(typedValue, "");
        return null;
    }

    @Override
    public Void visitParseVariableUse(STMLParser.ParseVariableUseContext ctx) {
        if (!emit) return null;

        String variable = ctx.getText();
        TypedValue typedValue = registry.getVariableLookup().resolve(variable)
                .orElseThrow(() -> error(ctx, ErrorCode.UNRESOLVED_IDENTIFIER, variable));

        emitValue(typedValue, "");
        return null;
    }

    @Override
    public Void visitType_field(STMLParser.Type_fieldContext ctx) {
        if (!emit) return null;

        String variable = ctx.getText();
        TypedValue typedValue = registry.getVariableLookup().resolve(variable)
                .orElseThrow(() -> error(ctx, ErrorCode.UNRESOLVED_IDENTIFIER, variable));

        emitValue(typedValue, "");
        return null;
    }

    @Override
    public Void visitType_constructor_map(STMLParser.Type_constructor_mapContext ctx) {
        if (!emit) return null;

        TypedValue typedValue = registry.getNodeValueMap().get(ctx);
        emitValue(typedValue, "");

        return null;
    }

    @Override
    public Void visitType_constructor_list(STMLParser.Type_constructor_listContext ctx) {
        if (!emit) return null;

        TypedValue typedValue = registry.getNodeValueMap().get(ctx);
        emitValue(typedValue, "");

        return null;
    }

    private void emitValue(TypedValue typedValue, String indentation) {
        switch (typedValue.getType().getTypeClass()) {
            case ANY:
            case NULL:
                if (typedValue.getValue() == null) {
                    output.write("null");
                    break;
                }
            case BOOL:
            case ENUM:
            case NUMBER:
            case STRING:
                output.writeValue(indentation, typedValue.getValue(), typedValue.getType().getTypeClass());
                break;
            case LIST:
                emitList((List<TypedValue>) typedValue.getValue(), indentation);
                break;
            case MAP:
            case STRUCT:
                emitMap((Map<String, TypedValue>) typedValue.getValue(), indentation);
                break;
        }
    }

    private void emitList(List<TypedValue> valueList, String indentation) {
        for (TypedValue value : valueList) {
            if (linebreak) {
                output.newLine();
                output.write(indentation);
            }
            linebreak = false;
            output.writeListItem();
            emitValue(value, indentation + INDENT);
            linebreak = true;
        }
    }

    private void emitMap(Map<String, TypedValue> valueMap, String indentation) {
        for (Map.Entry<String, TypedValue> entry : valueMap.entrySet()) {
            if (!renderNull && entry.getValue().getValue() == null) {
                continue;
            }

            if (linebreak) {
                output.newLine();
                output.write(indentation);
            }
            output.writeKey(entry.getKey());
            linebreak = true;

            if (isEmptyMap(entry.getValue())) {
                output.write("{}");
                continue;
            }
            if (isType(entry.getValue(), TypeClass.LIST)) {
                emitValue(entry.getValue(), indentation);
            } else {
                emitValue(entry.getValue(), indentation + INDENT);
            }
        }
    }

    private boolean isEmptyMap(TypedValue typedValue) {
        if (!isType(typedValue, TypeClass.MAP)) {
            return false;
        }
        return ((Map<String, ?>)typedValue.getValue()).isEmpty();
    }

    private boolean isType(TypedValue value, TypeClass typeClass) {
        return value.getType().getTypeClass().equals(typeClass);
    }

    private STMLException error(ParserRuleContext ctx, ErrorCode code, String message) {
        return new STMLException(
                mapper.map(SourcePosition.create(ctx.start)),
                code, message);
    }

}
