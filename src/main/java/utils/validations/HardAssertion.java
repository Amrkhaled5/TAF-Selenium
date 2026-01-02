package utils.validations;

import org.junit.Assert;
import org.testng.asserts.SoftAssert;
import utils.logs.LogsManager;

public class HardAssertion extends BaseAssertion {


    public HardAssertion(org.openqa.selenium.WebDriver driver) {
        super(driver);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        Assert.assertTrue(message, condition);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        Assert.assertFalse(message, condition);
    }

    @Override
    protected void assertEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(message, expected, actual);
    }
}
