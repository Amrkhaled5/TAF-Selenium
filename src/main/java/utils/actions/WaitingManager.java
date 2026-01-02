package utils;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class WaitingManager {
    private WebDriver driver;

    public WaitingManager(WebDriver driver) {
        this.driver = driver;
    }

    public FluentWait<WebDriver> getFluentWait(long timeoutInSeconds, long pollingInMillis) {
        return new FluentWait<>(driver)
                .withTimeout(java.time.Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(java.time.Duration.ofMillis(pollingInMillis))
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
