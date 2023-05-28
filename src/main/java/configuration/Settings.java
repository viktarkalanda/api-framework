package configuration;


import lombok.experimental.UtilityClass;
import utilities.configuration.ISettingsFile;
import utilities.configuration.JsonSettingsFile;

@UtilityClass
public final class Settings {
    private static final ISettingsFile SETTINGS_FILE = new JsonSettingsFile("settings.json");

    public static String getBaseUrl() {
        return getStringProperty("/settings/baseUrl");
    }

    public static String getEditor() {
        return getStringProperty("/settings/editor");
    }

    private static String getStringProperty(String jPath) {
        return SETTINGS_FILE.getValueOrEmptyIfNull(jPath);
    }
}
