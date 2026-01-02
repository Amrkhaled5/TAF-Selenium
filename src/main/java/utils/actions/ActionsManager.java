package utils.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utils.logs.LogsManager;

import java.io.File;

public class ActionsManager {
    private final WebDriver driver;
    private WaitingManager waitingManager;

    public ActionsManager(WebDriver driver){
        this.driver=driver;
        this.waitingManager=new WaitingManager(driver);
    }


    public void click(By locator){
        waitingManager.getFluentWait().until(webDriver -> {
            try {
                WebElement element=webDriver.findElement(locator);
                new Actions(webDriver).scrollToElement(element).click();
                LogsManager.info("Clicked on element located by: ",locator.toString());
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    public void type(By locator, String text){
        waitingManager.getFluentWait().until(webDriver -> {
            try {
                WebElement element=webDriver.findElement(locator);
                scrollToElement(locator);
                element.clear();
                element.sendKeys(text);
                LogsManager.info("Typed text '",text,"' into element located by: ",locator.toString());
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    public String getText(By locator){
        return waitingManager.getFluentWait().until(webDriver -> {
            try {
                WebElement element=webDriver.findElement(locator);
                scrollToElement(locator);
                String text=element.getText();
                LogsManager.info("Retrieved text '",text,"' from element located by: ",locator.toString());
                return !text.isEmpty() ? text : null;
            }
            catch (Exception e){
                return null;
            }
        });
    }

    public void uploadFile(By locator, String filePath){
        String absolutePath=System.getProperty("user.dir")+ File.separator +filePath;
        waitingManager.getFluentWait().until(webDriver -> {
            try {
                WebElement element=webDriver.findElement(locator);
                scrollToElement(locator);
                element.sendKeys(filePath);
                LogsManager.info("Uploaded file '",absolutePath,"' using element located by: ",locator.toString());
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    /// ///////// Alert Methods /////////

    public void acceptAlert(){
        waitingManager.getFluentWait().until(webDriver -> {
            try {
                webDriver.switchTo().alert().accept();
                LogsManager.info("Accepted alert");
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    public String getAlertText() {
        return waitingManager.getFluentWait().until(webDriver -> {
            try {
                String text = webDriver.switchTo().alert().getText();
                LogsManager.info("Retrieved alert text: ", text);
                return !text.isEmpty() ? text : null;
            } catch (Exception e) {
                return null;
            }
        });
    }

    public void sendTextToAlert(String text){
        waitingManager.getFluentWait().until(webDriver -> {
            try {
                webDriver.switchTo().alert().sendKeys(text);
                LogsManager.info("Sent text '",text,"' to alert");
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    public void dismissAlert(){
        waitingManager.getFluentWait().until(webDriver -> {
            try {
                webDriver.switchTo().alert().dismiss();
                LogsManager.info("Dismissed alert");
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    /// ///////// Frame Methods /////////

    public void switchToFrameByIndex(int index){
        waitingManager.getFluentWait().until(webDriver -> {
            try {
                webDriver.switchTo().frame(index);
                LogsManager.info("Switched to frame with index: ",String.valueOf(index));

                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    public void switchToFrameByNameOrId(String nameOrId){
        waitingManager.getFluentWait().until(webDriver -> {
            try {
                webDriver.switchTo().frame(nameOrId);
                LogsManager.info("Switched to frame with Name or ID: ",nameOrId);
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    public void switchToFrameByElement(By locator){
        waitingManager.getFluentWait().until(webDriver -> {
            try {
                WebElement element=webDriver.findElement(locator);
                webDriver.switchTo().frame(element);
                LogsManager.info("Switched to frame located by: ",locator.toString());
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    public void switchToDefaultContent(){
        waitingManager.getFluentWait().until(webDriver -> {
            try {
                webDriver.switchTo().defaultContent();
                LogsManager.info("Switched to default content");
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }


    public void scrollToElement(By locator){
        ((org.openqa.selenium.JavascriptExecutor)driver).executeScript("""
                        arguments[0].scrollIntoView({behaviour:"auto",block:"center",inline:"center"});""",findElement(locator));
    }

    public WebElement findElement(By locator){
        return driver.findElement(locator);
    }
}
