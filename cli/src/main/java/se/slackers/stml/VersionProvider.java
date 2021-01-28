package se.slackers.stml;

import picocli.CommandLine;

public class VersionProvider implements CommandLine.IVersionProvider {
    @Override
    public String[] getVersion() throws Exception {
        return new String[]{Main.class.getPackage().getImplementationVersion()};
    }
}
