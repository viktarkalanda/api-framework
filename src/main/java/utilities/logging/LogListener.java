package utilities.logging;

import io.qameta.allure.Attachment;
import org.testng.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class LogListener implements ITestListener, IExecutionListener, IInvokedMethodListener {
    private static final String SETUP_LOG_ATTRIBUTE = "setup.log";

    private final Logger logger = Logger.getInstance();

    @Override
    public void onStart(ITestContext iTestContext) {
        logger.info("=====[  Starting Tests Preconditions  ]=====");
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        iTestResult.getTestContext().setAttribute(SETUP_LOG_ATTRIBUTE, logger.getLogFile());
        logger.releaseAppender();
        logger.logTestStart(iTestResult);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        logger.logTestFinish(iTestResult);
        Object preconditionsLogFile = iTestResult.getTestContext().getAttribute(SETUP_LOG_ATTRIBUTE);
        if (preconditionsLogFile instanceof File) {
            attachSetupLogFile((File) preconditionsLogFile);
        }
        attachLogAndReleaseAppender();
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        onTestSuccess(iTestResult);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        onTestSuccess(iTestResult);
    }

    @Override
    public void onExecutionFinish() {
        logger.releaseLogger();
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        ITestNGMethod testNGMethod = method.getTestMethod();
        if (testNGMethod.isAfterMethodConfiguration()
                || testNGMethod.isAfterClassConfiguration()
                || testNGMethod.isAfterGroupsConfiguration()
                || testNGMethod.isAfterSuiteConfiguration()
                || testNGMethod.isAfterTestConfiguration()) {
            logger.releaseAppender();
        }
    }

    private void attachLogAndReleaseAppender() {
        File logFile = logger.getLogFile();
        if (logFile != null) {
            attachTestLogFile(logFile);
        }
        logger.releaseAppender();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Attachment(value = "Setup logs", type = "text/plain", fileExtension = ".txt")
    private static String attachSetupLogFile(File file) {
        return getLogFileContentsAsString(file);
    }

    @SuppressWarnings("UnusedReturnValue")
    @Attachment(value = "Test logs", type = "text/plain", fileExtension = ".txt")
    private static String attachTestLogFile(File file) {
        return getLogFileContentsAsString(file);
    }

    private static String getLogFileContentsAsString(File file) {
        String line = EMPTY;
        try {
            line = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (FileNotFoundException e) {
            Logger.getInstance().error(String.format("File was not found: %s", e.getMessage()));
        } catch (IOException e) {
            Logger.getInstance().error(String.format("%nIO exception occurred during the conversion of the file '%s': %s", file.getAbsolutePath(), e.getMessage()));
        }
        return line;
    }

}
