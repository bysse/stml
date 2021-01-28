package se.slackers.stml;

import picocli.CommandLine;

class STMLExceptionHandler implements CommandLine.IExecutionExceptionHandler {
    public int handleExecutionException(Exception exception, CommandLine cmd, CommandLine.ParseResult parseResult) {

        if (exception instanceof CLIException) {
            cmd.getErr().println(cmd.getColorScheme().errorText("ERROR: " + exception.getMessage()));
            if (((CLIException) exception).showUsage()) {
                cmd.getErr().println("");
                cmd.usage(System.err);
            }
        } else {
            cmd.getErr().println(cmd.getColorScheme().errorText("ERROR: " + exception.getMessage()));
            cmd.getErr().println("");

            exception.printStackTrace();
        }

        return cmd.getExitCodeExceptionMapper() != null
                ? cmd.getExitCodeExceptionMapper().getExitCode(exception)
                : cmd.getCommandSpec().exitCodeOnExecutionException();
    }
}