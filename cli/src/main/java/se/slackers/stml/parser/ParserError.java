package se.slackers.stml.parser;

import se.slackers.stml.error.ErrorCode;
import se.slackers.stml.mapper.SourceRangeId;

public class ParserError implements Comparable<ParserError> {
    private final SourceRangeId range;
    private final ErrorCode errorCode;
    private final String message;

    public ParserError(SourceRangeId range, ErrorCode errorCode, String message) {
        this.range = range;
        this.errorCode = errorCode;
        this.message = message;
    }

    public SourceRangeId getRange() {
        return range;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int compareTo(ParserError parserError) {
        return range.getStart().getPosition().compareTo(parserError.getRange().getStart().getPosition());
    }

    public String format() {
        return String.format("%s: 0x%x : %s : %s",
                range.getStart().format(), errorCode.getCode(), errorCode.getMessage(), message
        );
    }

    public String toString() {
        return format();
    }
}
