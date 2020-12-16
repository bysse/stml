package se.slackers.stml.model;

import java.util.LinkedHashMap;
import java.util.Optional;

public class ContextLookup<T> {
    private LinkedHashMap<String, ContextLookup<T>> contextMap = new LinkedHashMap<>();
    private LinkedHashMap<String, T> fieldMap = new LinkedHashMap<>();

    public Optional<ContextLookup<T>> resolveContext(String variable) {
        return resolveContext(variable, false);
    }

    public Optional<T> resolve(String variable) {
        return resolveContext(variable).flatMap((context) -> {
            int index = variable.lastIndexOf('.');
            String field = (index < 0) ? variable : variable.substring(index);
            return context.resolveField(field);
        });
    }

    public void set(String variable, T value) {
        resolveContext(variable, true)
                .ifPresent((context) -> {
                    int index = variable.lastIndexOf('.');
                    String field = (index < 0) ? variable : variable.substring(index);
                    context.setField(field, value);
                });
    }

    private Optional<ContextLookup<T>> resolveContext(String variable, boolean createMissingContexts) {
        final int index = variable.indexOf('.');
        if (index < 0) {
            return Optional.of(this);
        }

        final String contextName = variable.substring(0, index);
        if (index + 1 >= variable.length()) {
            return Optional.empty();
        }

        ContextLookup<T> context = contextMap.get(contextName);
        if (context == null) {
            if (!createMissingContexts) {
                return Optional.empty();
            }

            context = new ContextLookup<>();
            contextMap.put(contextName, context);
        }

        String field = variable.substring(index + 1);
        return context.resolveContext(field, createMissingContexts);
    }

    private Optional<T> resolveField(String field) {
        return Optional.ofNullable(fieldMap.get(field));
    }

    private void setField(String field, T value) {
        fieldMap.put(field, value);
    }
}
