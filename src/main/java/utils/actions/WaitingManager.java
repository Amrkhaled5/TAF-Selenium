package utils.actions;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import utils.dataManager.PropertyReader;

import java.time.Duration;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class WaitingManager {
    private WebDriver driver;

    public WaitingManager(WebDriver driver) {
        this.driver = driver;
    }

    public FluentWait<WebDriver> getFluentWait() {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Long.parseLong(PropertyReader.getProperty("DEFAULT_WAIT"))))
                .pollingEvery(Duration.ofMillis(Long.parseLong(PropertyReader.getProperty("polling_interval"))))
                .ignoreAll(getExceptionsToIgnore());
    }

    private ArrayList<Class<?extends Exception>> getExceptionsToIgnore(){
        ArrayList<Class<?extends Exception>> exceptionsToIgnore=new ArrayList<>();
        exceptionsToIgnore.add(NoSuchElementException.class);
        exceptionsToIgnore.add(StaleElementReferenceException.class);
        exceptionsToIgnore.add(StaleElementReferenceException.class);
        exceptionsToIgnore.add(ElementClickInterceptedException.class);
        return exceptionsToIgnore;
    }
}
