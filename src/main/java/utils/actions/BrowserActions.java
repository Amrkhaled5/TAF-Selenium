package utils;

import org.openqa.selenium.WebDriver;

public class BrowserActions {
    private final WebDriver driver;

    public BrowserActions(WebDriver driver){
        this.driver=driver;
    }

    public void navigateTo(String url){
        driver.navigate().to(url);
    }

    public void maximizeWindow(){
        driver.manage().window().maximize();
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
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
    }

}
