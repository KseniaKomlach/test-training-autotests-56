package Tests2;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("systemPort", 8206);
        capabilities.setCapability("clearSystemFiles", "true");
        capabilities.setCapability("appium:deviceName", "Redmi Note 8 Pro");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:platformVersion", "10");
        capabilities.setCapability("appPackage", "ru.vipnet.connect");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", "org.wikipedia.main.MainActivity");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appium:udid", "y5njdmemqcivuorw");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void testNameOfSearchField(){
        assertElementHasText(
                By.xpath("//android.widget.TextView[@text='Search Wikipedia']"),
                "Search Wikipedia",
                "Actual text of element not equal to 'Search Wikipedia'"
        );
    }

    @After
    public void tearDown() {
        driver.quit();
    }



    private void assertElementHasText(By by, String expected_text, String error_message){
        WebElement element = waitForElementPresent(by, "Cannot find element "+by);
        String actual_text = element.getAttribute("text");
        Assert.assertEquals(
                error_message,
                expected_text,
                actual_text
        );
    }
    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    private WebElement waitForElementPresent(By by, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }
    private void waitForElementPresentAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
    }
    private void waitForElementPresentAndClick(By by, String error_message) {
        waitForElementPresentAndClick(by, error_message, 5);
    }
    private void waitForElementPresentAndSendKeys(By by, String error_message, String keys, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(keys);
    }
    private void waitForElementPresentAndSendKeys(By by, String error_message, String keys) {
        waitForElementPresentAndSendKeys(by, error_message, keys, 5);
    }
}
