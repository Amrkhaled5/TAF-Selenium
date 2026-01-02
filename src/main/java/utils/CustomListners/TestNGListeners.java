package CustomListners;

import base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.*;

import java.io.File;
import java.io.IOException;

import static org.example.LogUtils.logger;

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

    public void onTestSuccess(ITestResult result) {
        logger().info("Test Successfully Passed: " + result.getName());
    }

    public void onTestFailure(ITestResult result) {
        logger().error("!!! Test Failed: " + result.getName());
        logger().error("Failure Reason: " + result.getThrowable());
        // 1. Get the driver from the current test instance
        Object currentClass = result.getInstance();
        WebDriver driver = ((BaseTest) currentClass).getDriver();

        // 2. Take the screenshot
        if (driver != null) {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // 3. Define destination (e.g., screenshots/testName.png)
            String screenshotName = result.getName() + ".png";
            File destFile = new File("screenshots/" + screenshotName);

            try {
                FileUtils.copyFile(srcFile, destFile);
                System.out.println("Screenshot saved to: " + destFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onTestSkipped(ITestResult result) {
        logger().warn("Test Skipped: " + result.getName());    }
    ///////////////////////////////////////////////////////////////////////////////
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        logger().debug("Invoking method: " + method.getTestMethod().getMethodName()
                + " inside class: " + testResult.getTestClass().getName());    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        String status = getStatusName(testResult.getStatus());

        logger().debug("Finished method: " + method.getTestMethod().getMethodName()
                + " | Status: " + status);    }
    ///////////////////////////////////////////////////////////////////////////////
    public void onExecutionStart() {
        logger().info(" Execution Started ");    }

    public void onExecutionFinish() {
        logger().info(" Execution finished ");

    }
    ///////////////////////////////////////////////////////////////////////////////
//    private boolean attemps=false;
//    @Override
//    public boolean retry(ITestResult iTestResult) {
//        if(iTestResult.getStatus()==ITestResult.FAILURE && !attemps){
//            attemps=true;
//            return true;
//        }
//        return false;
//    }
    ///////////////////////////////////////////////////////////////////////////////


}
