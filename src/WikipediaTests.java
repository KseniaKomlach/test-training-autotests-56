package src;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import src.lib.CoreTestCase;

import java.time.Duration;
import java.util.List;

public class WikipediaTests extends CoreTestCase {

    @Test
    public void testCancelSearch() {
        waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                "Kingdom"
        );
        waitForElementPresentAndClick(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find title"
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find page title"
        );
        swipeUpToFindElementQuick(
                By.id("org.wikipedia:id/page_license_text"),
                "Cannot find element page_lecense_text",
                3

        );
    }

    @Test
    public void testSaveFirstArticleToMyList() {
        waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                "Kingdom"
        );
        waitForElementPresentAndClick(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find title"
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='Add to reading list']"),
                "Cannot find option to add article to reading list"
        );
        waitForElementPresentAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' button"
                //or org.wikipedia:id/create_button
        );
        waitForElementPresentAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder"
        );
        String name_of_folder = "Learning programming";
        waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "Cannot put text into articles folder input",
                name_of_folder
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.Button[@text='OK']"),
                "Cannot press OK button"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
                "Cannot find created folder"
        );
        swipeElementToLeft(
                By.xpath("//android.widget.TextView[@text='Kingdom']"),
                "Cannot find saved article"
        );
        waitForElementNotPresent(
                By.xpath("//android.widget.TextView[@text='Kingdom']"),
                "Cannot delete saved article",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        String search_line = "Linkin Park discography";
        waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                search_line
        );
        String search_result_locator = "//android.widget.ListView[@resource-id='org.wikipedia:id/search_results_list']/android.widget.LinearLayout[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_line,
                15
        );
        int amount_of_search_results = getAmountOfElements(
              By.xpath(search_result_locator)
        );
        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }
    @Test
    public void testAmountOfEmptySearch(){
        waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        String search_line = "hjghkjgk";
        waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                search_line
        );
        String search_result_locator = "//android.widget.ListView[@resource-id='org.wikipedia:id/search_results_list']/android.widget.LinearLayout[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "org.wikipedia:id/search_empty_view";
        waitForElementPresent(
                By.id(empty_result_label),
                "Cannot find empty result label by the request " + search_line,
                15
        );
        assertElementNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request " + search_line
        );
    }
    @Test
    public void testChangeScreenOrientationOnSearchResults(){
        waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        String search_line = "Java";
        waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                search_line
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
                15
        );
        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );
        driver.rotate(ScreenOrientation.LANDSCAPE);
        String title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );
        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );
        driver.rotate(ScreenOrientation.PORTRAIT);
        String title_after_second_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );
        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }
    @Test
    public void testCheckSearchArticleInBackground(){
        waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        String search_line = "Java";
        waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                search_line
        );
        waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
                15
        );
        driver.runAppInBackground(Duration.ofSeconds(2));
        waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='Object-oriented programming language']"),
                "Cannot find article after returning from background",
                15
        );

    }

    @Test
    public void testSavingTwoArticles(){
        String search_line = "Kingdom";
        String search_line_second = "Elizaveta";
        String name_of_folder = "Folder for test Ex5";
        waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                search_line
        );
        waitForElementPresentAndClick(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find title"
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='Add to reading list']"),
                "Cannot find option to add article to reading list"
        );
        waitForElementPresentAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' button"
                //or org.wikipedia:id/create_button
        );
        waitForElementPresentAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder"
        );
        waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "Cannot put text into articles folder input",
                name_of_folder
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.Button[@text='OK']"),
                "Cannot press OK button"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link"
        );
        waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                search_line_second
        );
        waitForElementPresentAndClick(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find title"
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='Add to reading list']"),
                "Cannot find option to add article to reading list"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
                "Cannot find option to add article to reading list"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists"
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
                "Cannot find created folder"
        );
        swipeElementToLeft(
                By.xpath("//android.widget.TextView[@text='" + search_line_second + "']"),
                "Cannot find article"
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Cannot find any article"
        );
        String title_of_article_before = waitForElementAndGetAttribute(
                By.xpath("//android.widget.TextView[@text='" + search_line + "']"),
                "text",
                "Cannot find title of article",
                10
        );
        waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='" + search_line + "']"),
                "Cannot find title of article"
        );
        String title_of_article_after = waitForElementAndGetAttribute(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/view_page_title_text']"),
                "text",
                "Cannot find title of article",
                10
        );
        Assert.assertEquals(
                "Article title have been changed",
                title_of_article_before,
                title_of_article_after
        );
    }

    @Test
    public void testAssertTitleOfArticlePresent(){
        String search_line = "Kingdom";
        waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                search_line
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find title"
        );
        waitForElementPresentAndClick(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find title"
        );
        assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Title of article not present now."
        );
    }




    private Boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private void assertElementHasText(By by, String expected_text, String error_message) {
        WebElement element = waitForElementPresent(by, "Cannot find element " + by);
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

    private void waitForElementPresentAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
    }

    private void waitForElementPresentAndClear(By by, String error_message) {
        waitForElementPresentAndClear(by, error_message, 5);
    }

    private void checkAllElementsByXpathContains(String xpath, String word) {
        List titles = driver.findElementsByXPath(xpath);
        titles.stream().forEach(
                (element) -> {
                    WebElement title = (WebElement) element;
                    Assert.assertTrue("At least one title does not contain '" + word + "'", title.getAttribute("text").contains(word));
                });
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action
                .press(PointOption.point(x, start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x, end_y))
                .release().perform();
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped >= max_swipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUp(2000);
            ++already_swiped;
        }
    }

    protected void swipeUpToFindElementQuick(By by, String error_message, int max_swipes) {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped >= max_swipes) {
                waitForElementPresent(
                        by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUp(200);
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int middle_y = element.getLocation().getY() + element.getSize().getHeight() / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(PointOption.point(right_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(150)))
                .moveTo(PointOption.point(left_x, middle_y))
                .release().perform();
    }
    private int getAmountOfElements(By by){
        List elements = driver.findElements(by);
        return elements.size();
    }
    private void assertElementNotPresent(By by, String error_message){
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements>0){
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + ". " + error_message);
        }
    }
    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
    private void assertElementPresent(By by, String error_message){
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements==0){
            String default_message = "An element '" + by.toString() + "' not present";
            throw new AssertionError(default_message + ". " + error_message);
        }
    }
}