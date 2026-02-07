package mechsim.util;

import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;

// Helper for locating test resource files by relative path.
public class TestResources {

    public static Path mechFile(String filename) throws Exception {
        return getPath("data/mechs/" + filename);
    }

    public static Path configFile(String filename) throws Exception {
        return getPath("configs/" + filename);
    }

    public static Path logFile(String filename) throws Exception {
        return getPath("logs/" + filename);
    }

    // Resolve a classpath resource into a filesystem Path.
    private static Path getPath(String relativePath) throws Exception {
        final URL resource = TestResources.class.getClassLoader().getResource(relativePath);
        assertNotNull(resource, "Test resource not found: " + relativePath);
        return Path.of(resource.toURI());
    }
}
