package se.slackers.stml;

public enum STMLFlag {
    RENDER_NULL("render-null", "Enable rendering of null values"),
    ;

    private final String option;
    private final String description;

    STMLFlag(String option, String description) {
        this.option = option;
        this.description = description;
    }

    public static STMLFlag flagFromString(String value) {
        for (STMLFlag flag : values()) {
            if (flag.name().equalsIgnoreCase(value) || flag.option.equalsIgnoreCase(value)) {
                return flag;
            }
        }

        throw new IllegalArgumentException("Unknown value " + value);
    }

    public String getOption() {
        return option;
    }

    public String getDescription() {
        return description;
    }
}
