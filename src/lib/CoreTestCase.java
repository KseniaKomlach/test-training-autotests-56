package src.lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {
    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    public void setUp() throws Exception {

        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("systemPort", 8206);
        capabilities.setCapability("clearSystemFiles", "true");
        capabilities.setCapability("appium:deviceName", "Pixel 4");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:platformVersion", "13");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", "org.wikipedia.main.MainActivity");
        capabilities.setCapability("noReset", "false");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appium:udid", "9A231FFAZ004BG");

        driver = new AndroidDriver(new URL(AppiumURL), capabilities);
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @Override
    public void tearDown() throws Exception {
        driver.quit();

        super.tearDown();
    }
}
