package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Properties;

public class PropertyFileUtils {
    public static Properties getFromResources(String filePath) throws IOException, URISyntaxException {
        String propFilePath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(filePath))
                .toURI().getPath();
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(propFilePath)) {
            properties.load(inputStream);
        }
        return properties;
    }
}
