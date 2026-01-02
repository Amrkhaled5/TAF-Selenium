package drivers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.dataManager.PropertyReader;
import utils.logs.LogsManager;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class ChromeFactory extends AbstractDriver{

    private ChromeOptions getOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--dsisable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--start-maximized");
        if(PropertyReader.getProperty("executionType").equalsIgnoreCase("localheadless")
        || PropertyReader.getProperty("executionType").equalsIgnoreCase("remote")){
            options.addArguments("--headless=new");
        }
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return options;
    }

    @Override
    public WebDriver createDriver() {

        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("local")
            || PropertyReader.getProperty("executionType").equalsIgnoreCase("localheadless")) {
            return new ChromeDriver(getOptions());
        } else if (PropertyReader.getProperty("executionType").equalsIgnoreCase("remote")) {
            try{
                return new RemoteWebDriver(new URI(PropertyReader.getProperty("remoteHost")+":"+PropertyReader.getProperty("remotePort")+"/wd/hub").toURL(),getOptions());
            } catch (Exception e) {
                LogsManager.error("Failed to create RemoteWebDriver: " + e.getMessage());
                throw new RuntimeException("Failed to create RemoteWebDriver", e);
            }
        }
        else
        {
            LogsManager.error("Invalid execution type specified: " + PropertyReader.getProperty("executionType"));
            throw new IllegalArgumentException("Invalid execution type specified: " + PropertyReader.getProperty("executionType"));
        }
    }
}
