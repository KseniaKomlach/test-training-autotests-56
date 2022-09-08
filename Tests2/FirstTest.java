package Tests2;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("systemPort", 8206);
        capabilities.setCapability("clearSystemFiles", "true");
        capabilities.setCapability("appium:deviceName", "Pixel 4");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:platformVersion", "13");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", "org.wikipedia.main.MainActivity");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appium:udid", "9A231FFAZ004BG");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void cancelSearch(){
        waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                "Kingdom"
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find title"
        );
        List titles = driver.findElementsByXPath("//*[@resource-id='org.wikipedia:id/page_list_item_title']");
        titles.stream().forEach(
                (element) -> {
                    WebElement title = (WebElement) element;
                    Assert.assertTrue("At least one title does not contain 'Kingdom'", title.getAttribute("text").contains("Kingdom"));
                });
    }
    //org.wikipedia:id/page_list_item_description

    @After
    public void tearDown() {
        driver.quit();
    }


    private Boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
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
