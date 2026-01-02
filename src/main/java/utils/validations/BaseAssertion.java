package utils.validations;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.actions.WaitingManager;
import utils.actions.ActionsManager;

public abstract class BaseAssertion {
    protected final WebDriver driver;
    protected final WaitingManager waitingManager;
    protected ActionsManager elementActions;

    public BaseAssertion(WebDriver driver){
        this.driver=driver;
        this.waitingManager=new WaitingManager(driver);
        this.elementActions=new ActionsManager(driver);
    }

    protected abstract void assertTrue(boolean condition, String message);
    protected abstract void assertFalse(boolean condition, String message);
    protected abstract void assertEquals(Object actual, Object expected, String message);

    protected void True(boolean condition, String message){
        assertTrue(condition, message);
    }
    protected void False(boolean condition, String message){
        assertFalse(condition, message);
    }
    protected void Equals(Object actual, Object expected, String message) {
        assertEquals(actual, expected, message);
    }
    protected void isElementVisible(By locator, String elementName){
        boolean flag= waitingManager.getFluentWait().until(webDriver -> {
            try {
                webDriver.findElement(locator).isDisplayed();
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
        assertTrue(flag,"Element '"+elementName+"' is not visible.");
    }
    protected void assertPageUrl(String expectedUrl){
        String actualUrl=driver.getCurrentUrl();
        assertEquals(actualUrl,expectedUrl,"Current page URL '"+actualUrl+"' does not match expected URL '"+expectedUrl+"'.");
    }
    protected void assertPageTitle(String expectedTitle){
        String actualTitle=driver.getTitle();
        assertEquals(actualTitle,expectedTitle,"Current page title '"+actualTitle+"' does not match expected title '"+expectedTitle+"'.");
    }


}
