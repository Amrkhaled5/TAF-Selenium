package utils.validations;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import utils.logs.LogsManager;

public class SoftAssertion extends BaseAssertion {
    private static SoftAssert softAssert=new SoftAssert();
    private static boolean isInitialized=false;

    public SoftAssertion(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        isInitialized=true;
        softAssert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        isInitialized=true;
        softAssert.assertFalse(condition, message);
    }

    @Override
    protected void assertEquals(Object actual, Object expected, String message) {
        isInitialized=true;
        softAssert.assertEquals(actual, expected, message);
    }

    public static void assertAll(){
        if (!isInitialized)return;
        try {
            softAssert.assertAll();
        }
        catch (AssertionError e){
            LogsManager.error("Hard assertion failed: ",e.getMessage());
            throw e;
        }
        finally {
            softAssert=new SoftAssert();
        }
    }
}
