package utilities.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import utilities.JsonUtils;


public class JsonSettingsFile implements ISettingsFile {
    private final String content;

    public JsonSettingsFile(String resourceName) {
        ResourceFile resourceFile = new ResourceFile(resourceName);
        this.content = resourceFile.getFileContent();
    }

    @Override
    public Object getValue(String jsonPath) {
        return getEnvValueOrDefault(jsonPath, true);
    }

    private Object getEnvValueOrDefault(String jsonPath, boolean throwIfEmpty) {
        String envVar = getEnvValue(jsonPath);
        JsonNode node = getJsonNode(jsonPath, throwIfEmpty && envVar == null);
        return node.isMissingNode()
                ? envVar
                : castEnvOrDefaultValue(node, envVar);
    }

    private Object castEnvOrDefaultValue(JsonNode node, String envVar) {
        if (node.isBoolean()) {
            return envVar == null ? node.asBoolean() : Boolean.parseBoolean(envVar);
        } else if (node.isLong()) {
            return envVar == null ? node.asLong() : Long.parseLong(envVar);
        } else if (node.isInt()) {
            return envVar == null ? node.asInt() : Integer.parseInt(envVar);
        } else if (node.isDouble()) {
            return envVar == null ? node.asDouble() : Double.parseDouble(envVar);
        } else if (node.isObject()) {
            return envVar == null ? node.toString() : envVar;
        } else {
            return envVar == null ? node.asText() : envVar;
        }
    }

    private String getEnvValue(String jsonPath) {

        String key = jsonPath.replace("/", ".").substring(1, jsonPath.length());

        String value = System.getProperty(key);
        if (value == null || value.isEmpty()) {
            value = EnvironmentConfiguration.getEnvironmentVariable(key);
        }
        return value;
    }

    private JsonNode getJsonNode(String jsonPath, boolean throwIfEmpty) {
        JsonNode nodeAtPath;
        String errorMessage = String.format("Json field by json-path %1$s was not found in the file %2$s", jsonPath, content);
        JsonNode node = JsonUtils.readTree(content);
        nodeAtPath = node.at(jsonPath);
        if (throwIfEmpty && nodeAtPath.isMissingNode()) {
            throw new IllegalArgumentException(errorMessage);
        }
        return nodeAtPath;
    }

    @Override
    public boolean isValuePresent(String path) {
        Object value = getEnvValueOrDefault(path, false);
        return value != null && !value.toString().trim().isEmpty();
    }

}
