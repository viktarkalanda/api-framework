package utilities.configuration;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class EnvironmentConfiguration {

    static String getEnvironmentVariable(String key) {
        List<String> variables = new ArrayList<>(4);
        variables.add(System.getenv(key));
        variables.add(System.getenv(key.toUpperCase()));
        variables.add(System.getenv(key.toLowerCase()));
        variables.add(System.getenv(key.toUpperCase().replace('.', '_')));
        return variables.stream().filter(variable -> variable != null && !variable.isEmpty())
                .findFirst().orElse(null);
    }
}
