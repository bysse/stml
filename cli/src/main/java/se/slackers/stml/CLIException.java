package se.slackers.stml;

public class CLIException extends RuntimeException {
    private final boolean showUsage;

    public CLIException(String message) {
        this(message, false);
    }

    public CLIException(String message, boolean showUsage) {
        super(message);
        this.showUsage = showUsage;
    }

    public boolean showUsage() {
        return showUsage;
    }
}
