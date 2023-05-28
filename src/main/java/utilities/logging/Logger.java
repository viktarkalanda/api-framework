package utilities.logging;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class Logger {
    private static final ThreadLocal<Long> THREAD_ID_HOLDER = ThreadLocal.withInitial(() -> Thread.currentThread().getId());
    private static Logger instance;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);
    private final Map<Long, File> threadsFiles = new HashMap<>();

    private Logger() {
    }

    private org.slf4j.Logger getLogger() {
        if (threadsFiles.get(THREAD_ID_HOLDER.get()) == null) {
            initFile();
        }
        return logger;
    }

    private void initFile() {
        String postfix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + THREAD_ID_HOLDER.get();
        MDC.put(String.valueOf(THREAD_ID_HOLDER.get()), postfix);
        File file = new File(String.format("target/log/logfile_%s.txt", postfix));
        threadsFiles.put(THREAD_ID_HOLDER.get(), file);
    }

    private File getFile() {
        return threadsFiles.get(THREAD_ID_HOLDER.get());
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void info(String message) {
        getLogger().info(message);
    }

    public void error(String message) {
        getLogger().error(message);
    }

    public void logTestStart(ITestResult iTestResult) {
        String message = String.format("=====[  Test Start: %s  ]=====", TestResultHelper.getTestName(iTestResult));
        info(message);
    }

    public void logTestFinish(ITestResult iTestResult) {
        String message = String.format("=====[  Test %1$s finished with status: %2$s  ]=====",
                TestResultHelper.getTestName(iTestResult), TestStatus.getStatus(iTestResult.getStatus()));
        info(message);
    }

    public File getLogFile() {
        return getFile();
    }

    public void releaseAppender() {
        MDC.remove(String.valueOf(THREAD_ID_HOLDER.get()));
        threadsFiles.remove(THREAD_ID_HOLDER.get());
    }

    public void releaseLogger() {
        THREAD_ID_HOLDER.remove();
    }

}
