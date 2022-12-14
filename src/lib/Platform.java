package src.lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.aspectj.weaver.ast.And;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {
    private static final String PLATFORM_IOS ="ios";
    private static final String PLATFORM_ANDROID ="android";
    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    private static Platform instance;
    private Platform(){

    }
    public static Platform getInstance(){
        if (instance == null){
            instance = new Platform();
        }
        return instance;
    }


    public AppiumDriver getDriver() throws Exception{
        URL URL = new URL(APPIUM_URL);
        if (this.isAndroid()){
            return new AndroidDriver(URL, this.getAndroidDesiredCapabilities());
        } else if (this.isIOS()){
            return new IOSDriver(URL, this.getIOSDesiredCapabilities());
        } else {
            throw new Exception("Cannot detect type of the Driver. Platform value: " + this.getPlatformVar());
        }
    }

    public boolean isAndroid(){
        return isPlatform(PLATFORM_ANDROID);
    }
    public boolean isIOS(){
        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("clearSystemFiles", "true");
        capabilities.setCapability("appium:deviceName", "Pixel 4");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:platformVersion", "13");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", "org.wikipedia.main.MainActivity");
        capabilities.setCapability("noReset", "false");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appium:udid", "9A231FFAZ004BG");

        return capabilities;
    }
    private DesiredCapabilities getIOSDesiredCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("appium:platformVersion", "15.5");
        capabilities.setCapability("appium:deviceName", "7Plus");
        capabilities.setCapability("appium:app", "/Users/macOS12/Downloads/Wikipedia.app");

        return capabilities;
    }

    private boolean isPlatform(String my_platform){
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }

    private String getPlatformVar(){
        return System.getenv("PLATFORM");
    }
}
