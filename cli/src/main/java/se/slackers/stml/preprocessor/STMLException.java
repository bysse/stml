package se.slackers.stml.preprocessor;

import se.slackers.stml.error.ErrorCode;
import se.slackers.stml.mapper.SourcePositionId;

public class STMLException extends RuntimeException {
    private final SourcePositionId position;
    private final ErrorCode errorCode;

    public STMLException(ErrorCode errorCode, String message) {
        super(String.format("0x%x: %s : %s", errorCode.getCode(), errorCode.getMessage(), message));
        this.errorCode = errorCode;
        this.position = null;
    }

    public STMLException(SourcePositionId position, ErrorCode errorCode, String message) {
        super(String.format(position.format() +": 0x%x: %s : %s", errorCode.getCode(), errorCode.getMessage(), message));
        this.position = position;
        this.errorCode = errorCode;
    }

    public STMLException(ErrorCode errorCode, String message, Throwable cause) {
        super(String.format("0x%x: %s : %s", errorCode.getCode(), errorCode.getMessage(), message), cause);
        this.errorCode = errorCode;
        this.position = null;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public SourcePositionId getSourcePositionId() {
        return position;
    }
}
