package se.slackers.stml.mapper;

/**
 * Created by erikb on 2020-11-13.
 */
public class SourceRangeId {
    private final SourcePositionId start;
    private final SourcePositionId end;

    public static SourceRangeId create(SourcePositionId start, SourcePositionId end) {
        return new SourceRangeId(start, end);
    }

    public static SourceRangeId create(SourcePositionId position) {
        return new SourceRangeId(position, position);
    }

    private SourceRangeId(SourcePositionId start, SourcePositionId end) {
        this.start = start;
        this.end = end;
    }

    public SourcePositionId getStart() {
        return start;
    }

    public SourcePositionId getEnd() {
        return end;
    }

    public String format() {
        if (start.equals(end)) {
            return start.format();
        }

        if (start.getId().equals(end.getId())) {
            if (start.getPosition().getLine() == end.getPosition().getLine()) {
                return start.getId() + ": line " + start.getPosition().getLine() + "(" + start.getPosition().getColumn() + "-" + end.getPosition().getColumn() + ")";
            }
            return start.getId() + ": line " + start.getPosition().format() + "->" + end.getPosition().format();
        }

        return start.format() + "->" + end.format();
    }

    public String toString() {
        return format();
    }
}
