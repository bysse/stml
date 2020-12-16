package se.slackers.stml.model;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.HashMap;
import java.util.Map;

public class Registry {
    private final ContextLookup<TypedValue> variableLookup = new ContextLookup<>();
    private final Map<ParserRuleContext, TypedValue> nodeValueMap = new HashMap<>(1000);

    private final Map<String, FullySpecifiedType> typeNameMap = new HashMap<>();

    public ContextLookup<TypedValue> getVariableLookup() {
        return variableLookup;
    }

    public Map<ParserRuleContext, TypedValue> getNodeValueMap() {
        return nodeValueMap;
    }

    public FullySpecifiedType getDefinition(String typeName) {
        return typeNameMap.get(typeName);
    }

    public void registerTypeDefinition(FullySpecifiedType type) {
        typeNameMap.put(type.getTypeName(), type);
    }
}
