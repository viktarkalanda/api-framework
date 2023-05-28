package tests;

import org.testng.annotations.Listeners;
import utilities.AllureReportEnvPropsPrinterListener;
import utilities.logging.LogListener;

@Listeners({LogListener.class, AllureReportEnvPropsPrinterListener.class})
public abstract class BaseTest {

}
