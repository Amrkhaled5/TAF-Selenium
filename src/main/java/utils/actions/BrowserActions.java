package utils.actions;

import org.openqa.selenium.WebDriver;
import utils.logs.LogsManager;

public class BrowserActions {
    private final WebDriver driver;

    public BrowserActions(WebDriver driver){
        this.driver=driver;
    }

    public void navigateTo(String url){
        driver.navigate().to(url);
        LogsManager.info("Navigated to URL: ",url);
    }

    public void maximizeWindow(){
        driver.manage().window().maximize();
    }

    public String getCurrentUrl(){
        String url=driver.getCurrentUrl();
        LogsManager.info("Current URL is: ",url);
        return url;
    }

    public void refreshPage(){
        driver.navigate().refresh();
    }

    public void closseCurrentTab(){
        driver.close();
    }

    public void openNewTab(String url){
        driver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        driver.navigate().to(url);
        LogsManager.info("Opened new tab with URL: ",url);
    }

}
