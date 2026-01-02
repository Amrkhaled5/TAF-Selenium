package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.File;

public class ActionsManager {
    private final WebDriver driver;
    private WaitingManager waitingManager;

    public ActionsManager(WebDriver driver){
        this.driver=driver;
        this.waitingManager=new WaitingManager(driver);
    }


    public void click(By locator){
        waitingManager.getFluentWait(10,500).until(webDriver -> {
                    try {
                        WebElement element=webDriver.findElement(locator);
                        new Actions(webDriver).scrollToElement(element).click();
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
        });
    }

    public void type(By locator, String text){
        waitingManager.getFluentWait(10,500).until(webDriver -> {
            try {
                WebElement element=webDriver.findElement(locator);
                scrollToElement(locator);
                element.clear();
                element.sendKeys(text);
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    public String getText(By locator){
        return waitingManager.getFluentWait(10,500).until(webDriver -> {
            try {
                WebElement element=webDriver.findElement(locator);
                scrollToElement(locator);
                String text=element.getText();
                return !text.isEmpty() ? text : null;
            }
            catch (Exception e){
                return null;
            }
        });
    }

    public void uploadFile(By locator, String filePath){
        String absolutePath=System.getProperty("user.dir")+ File.separator +filePath;
        waitingManager.getFluentWait(10,500).until(webDriver -> {
            try {
                WebElement element=webDriver.findElement(locator);
                scrollToElement(locator);
                element.sendKeys(filePath);
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    /// ///////// Alert Methods /////////

    public void acceptAlert(){
        waitingManager.getFluentWait(10,500).until(webDriver -> {
            try {
                webDriver.switchTo().alert().accept();
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    public String getAlertText() {
        return waitingManager.getFluentWait(10, 500).until(webDriver -> {
            try {
                String text = webDriver.switchTo().alert().getText();
                return !text.isEmpty() ? text : null;
            } catch (Exception e) {
                return null;
            }
        });
    }

    public void sendTextToAlert(String text){
        waitingManager.getFluentWait(10,500).until(webDriver -> {
            try {
                webDriver.switchTo().alert().sendKeys(text);
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    public void dismissAlert(){
        waitingManager.getFluentWait(10,500).until(webDriver -> {
            try {
                webDriver.switchTo().alert().dismiss();
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    /// ///////// Frame Methods /////////

    public void switchToFrameByIndex(int index){
        waitingManager.getFluentWait(10,500).until(webDriver -> {
            try {
                webDriver.switchTo().frame(index);
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    public void switchToFrameByNameOrId(String nameOrId){
        waitingManager.getFluentWait(10,500).until(webDriver -> {
            try {
                webDriver.switchTo().frame(nameOrId);
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    public void switchToFrameByElement(By locator){
        waitingManager.getFluentWait(10,500).until(webDriver -> {
            try {
                WebElement element=webDriver.findElement(locator);
                webDriver.switchTo().frame(element);
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
    }

    public void switchToDefaultContent(){
        waitingManager.getFluentWait(10,500).until(webDriver -> {
            try {
                webDriver.switchTo().defaultContent();
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
