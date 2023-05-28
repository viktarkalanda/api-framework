package utilities;

import lombok.SneakyThrows;
import org.testng.IExecutionListener;

import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.util.Properties;


import static java.lang.System.getProperty;
import static java.util.Optional.ofNullable;

public class AllureReportEnvPropsPrinterListener implements IExecutionListener {
    public static final String ALLURE_PROPERTIES = "allure.properties";
    public static final String ENVIRONMENT_PROPERTY = "environment";
    public static final String ALLURE_ENV_PROPERTIES_FILE_NAME = "environment.properties";
    public static final String MVN_POM_PROP_TESTSUITE = "testsuite";

    @Override
    @SneakyThrows
    public void onExecutionFinish() {
        Properties allureProps = PropertyFileUtils.getFromResources(ALLURE_PROPERTIES);
        String allureResultsDir = allureProps.getProperty("allure.results.directory");
        String allureEnvPropsOutputPath = allureResultsDir + FileSystems.getDefault().getSeparator() + ALLURE_ENV_PROPERTIES_FILE_NAME;
        try (FileOutputStream out = new FileOutputStream(allureEnvPropsOutputPath)) {
            Properties props = new Properties();
            String propEnvironmentValue = getProperty(ENVIRONMENT_PROPERTY);
            ofNullable(propEnvironmentValue).ifPresent(value -> props.setProperty(ENVIRONMENT_PROPERTY, value));
            String propTestsuiteValue = getProperty(MVN_POM_PROP_TESTSUITE);
            ofNullable(propTestsuiteValue).ifPresent(value -> props.setProperty(MVN_POM_PROP_TESTSUITE, value));
            props.store(out, "See https://docs.qameta.io/allure/#_environment");
        }
    }
}
