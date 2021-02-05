package se.slackers.stml.command;

import picocli.CommandLine;
import se.slackers.stml.CLIException;
import se.slackers.stml.ExitCode;
import se.slackers.stml.VersionProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "generate-kubectl",
        description = "Generate plugin for kubectl",
        versionProvider = VersionProvider.class
)
public class GenerateCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        try {
            String script = loadResource("kubectl/kubectl-stml");
            System.out.println(script);
        } catch (IOException e) {
            throw new CLIException("Failed to load internal resource kubectl/kubectl-stml : " + e.getMessage());
        }

        return ExitCode.RET_OK;
    }

    private static String loadResource(String resource) throws IOException {
        InputStream resourceStream = GenerateCommand.class.getClassLoader().getResourceAsStream(resource);
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
