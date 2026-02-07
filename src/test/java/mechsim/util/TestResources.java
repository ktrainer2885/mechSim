package mechsim.util;

import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    private static Path getPath(String relativePath) throws Exception {
        final URL resource = TestResources.class.getClassLoader().getResource(relativePath);
        assertNotNull(resource, "Test resource not found: " + relativePath);
        return Path.of(resource.toURI());
    }
}
