package se.slackers.stml.error;

public enum ErrorCode {
    OK(0x00, "Success"),
    CAN_NOT_OPEN_FILE(0x100, "Can't open file"),
    CAN_NOT_READ_DIRECTORY(0x101, "Can't read directory"),
    CAN_READ_FILE(0x102, "Can't read from file"),

    SYNTAX_ERROR(0x200, "Syntax Error"),

    UNRESOLVED_IDENTIFIER(0x500, "Unresolved identifier"),
    UNRESOLVED_TYPE(0x501, "Unresolved type"),

    IDENTIFIER_REDEFINITION(0x510, "Redefinition of identifier"),
    ILLEGAL_FORMAT(0x550, "Illegal format"),
    INVALID_STRING_PATTERN(0x560, "Invalid string regex subtype pattern"),
    INVALID_REGEX_PATTERN(0x561, "Invalid regex pattern"),

    FIELD_ALREADY_DECLARED(0x600, "Member field already exists"),
    FIELD_NOT_INITIALIZED(0x601, "Field not initialized"),
    FIELD_NOT_DECLARED(0x602, "Undeclared field"),
    FIELD_PARENT_IS_CONSTANT(0x603, "Field is declared as a constant in parent type"),
    FIELD_IS_CONSTANT(0x604, "Field is declared as a constant"),
    FIELD_ALREADY_INITIALIZED(0x605, "Field has already been initialized"),
    FIELD_NAME_INVALID(0x606, "The field has an invalid name"),

    ENUM_EXTENDS_NON_ENUM(0x700, "Enum types can only extend other enum types"),
    STRUCT_EXTENDS_NON_STRUCT(0x701, "Struct types can only extend other struct types"),

    ENUM_INITIALIZATION(0x705, "Enum types can be initialized"),
    LIST_INITIALIZATION(0x706, "List types can't be initialized with field names, that's a Map"),
    MAP_INITIALIZATION(0x707, "Map types can't be initialized with lists of values, that's a List"),
    STRUCT_INITIALIZATION(0x708, "Struct types can't be initialized with lists of values, that's a List"),

    INCOMPATIBLE_TYPES(0x710, "Incompatible types"),
    ;

    private final int code;
    private final String format;

    ErrorCode(int code, String format) {
        this.code = code;
        this.format = format;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return format;
    }
}
