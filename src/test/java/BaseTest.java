import drivers.GUIDriver;
import drivers.WebDriverProvider;
import org.openqa.selenium.WebDriver;

public class BaseTest implements WebDriverProvider {

    protected GUIDriver driver;

    public WebDriver getWebDriver() {
        return driver.getDriver();
    }
}
