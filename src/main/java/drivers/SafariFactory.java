package drivers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import utils.dataManager.PropertyReader;
import utils.logs.LogsManager;

import java.net.URI;

public class SafariFactory extends AbstractDriver {

    public SafariOptions getOptions() {
        SafariOptions options = new SafariOptions();

        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return options;
    }

    @Override
    public WebDriver createDriver() {

        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("local")
                || PropertyReader.getProperty("executionType").equalsIgnoreCase("localheadless")) {
            return new SafariDriver(getOptions());
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
