package utils.CustomListners;

import drivers.WebDriverProvider;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import utils.actions.FilesManager;
import utils.allureReporting.AllureConstants;
import utils.allureReporting.AllureEnviroment;
import utils.allureReporting.AllureReportGenerator;
import utils.dataManager.PropertyReader;
import utils.logs.LogsManager;
import utils.media.ScreenRecord;
import utils.media.ScreenShots;
import utils.validations.SoftAssertion;

import java.io.File;
import java.io.IOException;


public class TestNGListeners implements ITestListener,IInvokedMethodListener,IExecutionListener//,IRetryAnalyzer
{

    private String getStatusName(int status) {
        switch (status) {
            case 1: return "SUCCESS";
            case 2: return "FAILURE";
            case 3: return "SKIP";
            default: return "UNKNOWN";
        }
    }

    public void onExecutionStart() {
        LogsManager.info(" Execution Started ");
        cleanTestOutputDirectory();
        createTestOutputDirectory();
        LogsManager.info(" Test Output Directories are created/cleaned ");
        PropertyReader.loadProperties();
        AllureEnviroment.setEnviromentVariable();
        LogsManager.info("Properties Loaded & Execution Environment is set in Allure ");
    }

    public void onExecutionFinish() {
        AllureReportGenerator.generateAllureReport(false);
        AllureReportGenerator.copyHistoryToResultsFolder();
        AllureReportGenerator.generateAllureReport(true);
        AllureReportGenerator.openAllureReport(AllureReportGenerator.renameReport());
        LogsManager.info(" Execution Finished ");
    }
    ///////////////////////////////////////////////////////////////////////////////


    public void onTestSuccess(ITestResult result) {

        LogsManager.info("Test Case "+ result.getName()+" Passed Successfully.");
    }

    public void onTestFailure(ITestResult result) {
        LogsManager.info("Test Case "+ result.getName()+" Failed.");
    }

    public void onTestSkipped(ITestResult result) {
        LogsManager.warn("Test Case " + result.getName()+" Skipped.");
    }
    ///////////////////////////////////////////////////////////////////////////////
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            ScreenRecord.startRecording();
            LogsManager.debug("Starting method: " + method.getTestMethod().getMethodName());
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        WebDriver driver = null;
        if (method.isTestMethod()) {
            ScreenRecord.stopRecording(testResult.getName());
            SoftAssertion.assertAll();
            if(testResult.getInstance() instanceof WebDriverProvider provider) {
                driver=provider.getWebDriver();
            }
            switch (testResult.getStatus()) {
                case ITestResult.SUCCESS-> ScreenShots.takeFullPageScreenShot(driver,"Success-"+testResult.getName());
                case ITestResult.FAILURE-> ScreenShots.takeFullPageScreenShot(driver,"Failure-"+testResult.getName());
                case ITestResult.SKIP-> ScreenShots.takeFullPageScreenShot(driver,"Skipped-"+testResult.getName());
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////

    private void createTestOutputDirectory() {
        FilesManager.createDirectory(ScreenShots.SCREENSHOTPATH);
        FilesManager.createDirectory(ScreenRecord.SCREENRECORDPATH);
    }

    private void cleanTestOutputDirectory() {
        FilesManager.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FilesManager.cleanDirectory(new File(ScreenShots.SCREENSHOTPATH));
        FilesManager.cleanDirectory(new File(ScreenRecord.SCREENRECORDPATH));
        FilesManager.cleanDirectory(new File(LogsManager.LOGS_PATH));
    }

}
