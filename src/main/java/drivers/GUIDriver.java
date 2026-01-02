package drivers;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import utils.actions.ActionsManager;
import utils.actions.BrowserActions;
import utils.dataManager.PropertyReader;
import utils.logs.LogsManager;
import utils.validations.BaseAssertion;
import utils.validations.HardAssertion;
import utils.validations.SoftAssertion;

import static java.sql.DriverManager.getDriver;

public class GUIDriver {
    private final static String BROWSER = PropertyReader.getProperty("browserType");

    private static ThreadLocal<WebDriver>driverThreadLocal=new ThreadLocal<>();

    public GUIDriver(){
        Browser browserType=Browser.valueOf(BROWSER.toUpperCase());
        LogsManager.info("Selected browser type: ",browserType.toString());
        AbstractDriver abstractDriver=browserType.getDriverFactory();
        WebDriver driver= ThreadGuard.protect(abstractDriver.createDriver());
        driverThreadLocal.set(driver);
    }

    public WebDriver getDriver(){
        return driverThreadLocal.get();
    }
    public void quitDriver(){
        if(driverThreadLocal.get()!=null){
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }


    public ActionsManager action(){
        return new ActionsManager(getDriver());
    }

    public BrowserActions browser(){
        return new BrowserActions(getDriver());
    }

    public SoftAssertion softAssert() {
        return new SoftAssertion(getDriver());
    }

    public HardAssertion hardAssert() {
        return new HardAssertion(getDriver());
    }



}
