package se.slackers.stml;

import com.ginsberg.junit.exit.SystemExitExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SystemExitExtension.class)
class MainTest {
    private static final String TESTBED = "src/test/resources/testbed";

    @TempDir
    File baseDir;

    @Test
    @DisplayName("Compile single file and generate name")
    void testCompile_1() {
        execute(() ->
                Main.main(new String[]{"compile", TESTBED + "/compile/stml_1.stml", "-o", baseDir.getAbsolutePath()})
        );
        validateFile("stml_1.yaml", "---\nSTML1");

        execute(() ->
                Main.main(new String[]{"compile", TESTBED + "/compile/stml_2.stml", "-o", baseDir.getAbsolutePath()})
        );
        validateFile("stml_2.yaml", "---\nSTML2");
    }

    @Test
    @DisplayName("Compile single file and specify name")
    void testCompile_2() {
        execute(() -> {
            Path path = baseDir.toPath().resolve("output.yaml").toAbsolutePath();
            Main.main(new String[]{"compile", TESTBED + "/compile/stml_1.stml", "-o", path.toString()});
        });
        validateFile("output.yaml", "---\nSTML1");

        execute(() -> {
            Path path = baseDir.toPath().resolve("output.yaml").toAbsolutePath();
            Main.main(new String[]{"compile", TESTBED + "/compile/stml_2.stml", "-o", path.toString()});
        });
        validateFile("output.yaml", "---\nSTML2");
    }

    @Test
    @DisplayName("Compile directory")
    void testCompile_3() {
        execute(() -> Main.main(new String[]{"compile", TESTBED + "/compile", "-o", baseDir.getAbsolutePath()}));

        validateFile("stml_1.yaml", "---\nSTML1");
        validateFile("stml_2.yaml", "---\nSTML2");
    }

    @Test
    @DisplayName("Compile directory and merge")
    void testCompile_4() {
        execute(() -> Main.main(new String[]{"compile", TESTBED + "/compile", "-m", "-o", baseDir.getAbsolutePath()}));

        validateFile("stml_1.yaml", "---\nSTML1\n---\nSTML2");
    }

    @Test
    @DisplayName("Compile directory and merge")
    void testCompile_5() {
        execute(() -> {
            Path path = baseDir.toPath().resolve("dir_merged.yaml").toAbsolutePath();
            Main.main(new String[]{"compile", TESTBED + "/compile", "-m", "-o", path.toString()});
        });

        validateFile("dir_merged.yaml", "---\nSTML1\n---\nSTML2");
    }

    @Test
    @DisplayName("Compile multiple files")
    void testCompile_7() {
        execute(() ->
                Main.main(new String[]{"compile",
                        TESTBED + "/compile/stml_1.stml",
                        TESTBED + "/compile/stml_2.stml",
                        "-o", baseDir.getAbsolutePath()})
        );

        validateFile("stml_1.yaml", "---\nSTML1");
        validateFile("stml_2.yaml", "---\nSTML2");
    }

    @Test
    @DisplayName("Compile multiple files and merge")
    void testCompile_8() {
        execute(() -> {
            Path path = baseDir.toPath().resolve("subdir").toAbsolutePath();
            Main.main(new String[]{"compile",
                    TESTBED + "/compile/stml_1.stml",
                    TESTBED + "/compile/stml_2.stml",
                    "-m",
                    "-o", path.toString()});
                }
        );

        validateFile("subdir/stml_1.yaml", "---\nSTML1\n---\nSTML2");
    }

    @Test
    @DisplayName("Compile multiple files and merge to specified file")
    void testCompile_9() {
        execute(() -> {
                    Path path = baseDir.toPath().resolve("merged.yaml").toAbsolutePath();
                    Main.main(new String[]{"compile",
                            TESTBED + "/compile/stml_1.stml",
                            TESTBED + "/compile/stml_2.stml",
                            "-m",
                            "-o", path.toString()});
                }
        );

        validateFile("merged.yaml", "---\nSTML1\n---\nSTML2");
    }


    private void execute(Executable executable) {
        assertThrows(SecurityException.class, executable, "No system exit encountered");
    }

    private void validateFile(String filename, String contents) {
        final Path filePath = baseDir.toPath().resolve(filename);
        assertTrue(Files.exists(filePath), "File " + filePath + " doesn't exist!");
        assertDoesNotThrow(() -> {
            byte[] bytes = Files.readAllBytes(filePath);
            assertEquals(contents, new String(bytes, StandardCharsets.UTF_8));
        });
    }
}