package se.slackers.stml.mapper;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Objects;

/**
 * Created by erikb on 2020-11-13.
 */
public class SourceRange implements Comparable<SourceRange> {
    public static final SourceRange TOP = SourceRange.create(SourcePosition.TOP, SourcePosition.TOP);

    private final SourcePosition start;
    private final SourcePosition end;

    public static SourceRange create(ParserRuleContext ctx) {
        if (ctx.start.getLine() == ctx.stop.getLine() && ctx.start.getCharPositionInLine() == ctx.stop.getCharPositionInLine()) {
            SourcePosition start = SourcePosition.create(ctx.start);
            SourcePosition end = SourcePosition.create(start.getLine(), start.getColumn() + ctx.getText().length());
            return new SourceRange(start, end);
        }
        return create(ctx.start, ctx.stop);
    }

    public static SourceRange create(Token start, Token end) {
        return new SourceRange(SourcePosition.create(start), SourcePosition.create(end));
    }

    public static SourceRange create(TerminalNode node) {
        SourcePosition start = SourcePosition.create(node.getSymbol());
        SourcePosition end = SourcePosition.create(start.getLine(), start.getColumn() + node.getSymbol().getText().length());
        return new SourceRange(start, end);
    }

    public static SourceRange create(SourcePosition from, SourcePosition end) {
        return new SourceRange(from, end);
    }

    private SourceRange(SourcePosition start, SourcePosition end) {
        this.start = start;
        this.end = end;
    }

    public SourcePosition getStart() {
        return start;
    }

    public SourcePosition getEnd() {
        return end;
    }

    @Override
    public int compareTo(SourceRange o) {
        int res = start.compareTo(o.start);
        if (res == 0) {
            return end.compareTo(o.end);
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SourceRange that = (SourceRange) o;
        return Objects.equals(start, that.start) &&
                Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    public String format() {
        if (start.getLine() == end.getLine()) {
            return start.getLine() + "(" + start.getColumn() + "-" + end.getColumn() + ")";
        }
        return start.format() + "->" + end.format();
    }

    public String toString() {
        return format();
    }
}
