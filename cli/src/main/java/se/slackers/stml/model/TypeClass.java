package se.slackers.stml.model;

public enum TypeClass {
    ANY("any", false),
    BOOL("bool", false),
    ENUM("enum", false),
    IMPORT("import", false),
    LIST("List", true),
    MAP("Map", true),
    PATTERN("pattern", false),
    NUMBER("number", false),
    NULL("null", false),
    STRING("string", true),
    STRUCT("struct", false)
    ;

    private final String token;
    private final boolean allowSubType;

    TypeClass(String token, boolean allowSubType) {
        this.token = token;
        this.allowSubType = allowSubType;
    }

    public String getToken() {
        return token;
    }

    public boolean allowsSubType() {
        return allowSubType;
    }
}
