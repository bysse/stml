package se.slackers.stml.error;

import se.slackers.stml.mapper.SourcePosition;
import se.slackers.stml.mapper.SourcePositionMapper;
import se.slackers.stml.mapper.SourceRange;
import se.slackers.stml.mapper.SourceRangeId;
import se.slackers.stml.parser.ParserError;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class ErrorCollection {
    private final List<ParserError> errors = new ArrayList<>();
    private final SourcePositionMapper mapper;

    public ErrorCollection(SourcePositionMapper mapper) {
        this.mapper = mapper;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<ParserError> getErrors() {
        return errors;
    }

    public void report(ParserRuleContext ctx, ErrorCode code, String message) {
        report(SourcePosition.create(ctx.start), SourcePosition.create(ctx.stop), code, message);
    }

    public void report(TerminalNode node, ErrorCode code, String message) {
        SourcePosition start = SourcePosition.create(node.getSymbol());
        SourcePosition end = SourcePosition.create(start.getLine(), start.getColumn() + node.getSymbol().getText().length());
        report(start, end, code, message);
    }

    public void report(SourcePosition start, SourcePosition end, ErrorCode code, String message) {
        report(SourceRange.create(start, end), code, message);
    }

    public void report(SourceRange range, ErrorCode code, String message) {
        SourceRangeId sourceRangeId = mapper.map(range);
        errors.add(new ParserError(sourceRangeId, code, message));
    }
}
