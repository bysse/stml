package se.slackers.stml.parser;

import se.slackers.stml.STMLParser;
import se.slackers.stml.model.Registry;

import java.util.List;

public class ParserResult {
    private STMLParser.Statement_listContext context;
    private Registry registry;
    private List<ParserError> errors;

    public ParserResult(
            STMLParser.Statement_listContext context,
            Registry registry,
            List<ParserError> errors) {
        this.context = context;
        this.registry = registry;
        this.errors = errors;
    }

    public boolean successful() {
        return errors.isEmpty();
    }

    public STMLParser.Statement_listContext getContext() {
        return context;
    }

    public Registry getRegistry() {
        return registry;
    }

    public List<ParserError> getErrors() {
        return errors;
    }
}
