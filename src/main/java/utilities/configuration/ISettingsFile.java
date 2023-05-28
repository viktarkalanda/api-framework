package utilities.configuration;

public interface ISettingsFile {
    Object getValue(String path);

    boolean isValuePresent(String path);

    default Object getValueOrDefault(String path, Object defaultValue) {
        return this.isValuePresent(path) ? this.getValue(path) : defaultValue;
    }

    default String getValueOrEmptyIfNull(String jPath) {
        String value = getValueOrDefault(jPath, "null").toString();
        return value.equals("null") ? "" : value;
    }
}
