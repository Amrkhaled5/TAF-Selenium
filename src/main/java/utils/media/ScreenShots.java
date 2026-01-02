package utils.media;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.allureReporting.AllureAttachmentManager;
import utils.logs.LogsManager;

import java.io.File;
import java.io.IOException;

public class ScreenShots {

    public static final String SCREENSHOTPATH="test-results/screenshoots/";

    public static void takeFullPageScreenShot(WebDriver driver, String screenShotName){
        try{
            File screenshotSrc=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            File screenshotDest=new File(SCREENSHOTPATH+screenShotName+".png");
            FileUtils.copyFile(screenshotSrc,screenshotDest);

            AllureAttachmentManager.attachScreenshot(screenShotName,screenshotDest.getAbsolutePath());

            LogsManager.info("Screenshot taken: ",screenshotDest.getAbsolutePath());
        } catch (IOException e) {
            LogsManager.error("Failed to take screenshot: ",e.getMessage());
        }
    }

    public static void takeScreenShotForElement(WebDriver driver, By locator){
        try{
            File screenshotSrc=driver.findElement(locator).getScreenshotAs(OutputType.FILE);
            String accessibleName=driver.findElement(locator).getAccessibleName();

            File screenshotDest=new File(SCREENSHOTPATH+accessibleName+".png");
            FileUtils.copyFile(screenshotSrc,screenshotDest);

            AllureAttachmentManager.attachScreenshot(accessibleName,screenshotDest.getAbsolutePath());

            LogsManager.info("Element screenshot taken: ",screenshotDest.getAbsolutePath());
        } catch (IOException e) {
            LogsManager.error("Failed to take element screenshot: ",e.getMessage());
        }
    }
}
