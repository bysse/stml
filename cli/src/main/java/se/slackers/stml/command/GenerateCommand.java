package se.slackers.stml.command;

import picocli.CommandLine;
import se.slackers.stml.CLIException;
import se.slackers.stml.ExitCode;
import se.slackers.stml.Main2;
import se.slackers.stml.VersionProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "generate",
        aliases = "g",
        description = "Generate different types of support scripts for STML",
        versionProvider = VersionProvider.class,
        subcommands = {CommandLine.HelpCommand.class}
)
public class GenerateCommand implements Callable<Integer> {
    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @Override
    public Integer call() throws Exception {
        spec.commandLine().usage(System.err);
        return ExitCode.RET_SYNTAX;
    }

    @CommandLine.Command(
            name = "completion",
            description = "Generate shell completion script for the provided shell"
    )
    public Integer handleCompletion(
            @CommandLine.Option(names = {"-l", "--list"}, description = "List all supported shells", defaultValue = "false") boolean list,
            @CommandLine.Parameters(arity = "0..1", description = "The name of the shell to generate completion script for", paramLabel = "SHELL") String shell
            ) {

        if (!list && shell == null) {
            spec.commandLine().usage(System.err);
            return ExitCode.RET_SYNTAX;
        }

        if (list) {
            System.out.println("Supported shells:");
            System.out.println("  bash");
            return ExitCode.RET_OK;
        }

        try {
            String script = loadResource("completion/c_" + shell.toLowerCase() + ".sh");
            System.out.println(script);
        } catch (IOException e) {
            throw new CLIException("Failed to load internal resource completion/c_" + shell.toLowerCase() + ".sh : " + e.getMessage());
        }

        return ExitCode.RET_OK;
    }

    @CommandLine.Command(
            name = "kubectl",
            description = "Generate plugin for kubectl"
    )
    public Integer handleKubectl() {
        try {
            String script = loadResource("kubectl/kubectl-stml");
            System.out.println(script);
        } catch (IOException e) {
            throw new CLIException("Failed to load internal resource kubectl/kubectl-stml : " + e.getMessage());
        }

        return ExitCode.RET_OK;
    }

    private static String loadResource(String resource) throws IOException {
        InputStream resourceStream = Main2.class.getClassLoader().getResourceAsStream(resource);
        if (resourceStream == null) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream))) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            return builder.toString();
        } catch (IOException e) {
            throw new IOException("Failed to load resource : " + resource + " : " + e.getMessage());
        }
    }
}
