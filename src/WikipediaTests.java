package src;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import src.lib.CoreTestCase;
import src.lib.ui.ArticlePageObject;
import src.lib.ui.MainPageObject;
import src.lib.ui.SearchPageObject;

import java.time.Duration;

public class WikipediaTests extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Kingdom");
        SearchPageObject.waitForSearchResult("Wikimedia disambiguation page");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testSwipeArticle(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Kingdom");
        SearchPageObject.clickByArticleWithSubstring("Wikimedia disambiguation page");
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeQuickToFooter();
    }

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Kingdom");
        SearchPageObject.clickByArticleWithSubstring("Wikimedia disambiguation page");
        ArticlePageObject.waitForTitleElement();
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options"
        );
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='Add to reading list']"),
                "Cannot find option to add article to reading list"
        );
        MainPageObject.waitForElementPresentAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' button"
                //or org.wikipedia:id/create_button
        );
        MainPageObject.waitForElementPresentAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder"
        );
        String name_of_folder = "Learning programming";
        MainPageObject.waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "Cannot put text into articles folder input",
                name_of_folder
        );
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.Button[@text='OK']"),
                "Cannot press OK button"
        );
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link"
        );
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists"
        );
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
                "Cannot find created folder"
        );
        MainPageObject.swipeElementToLeft(
                By.xpath("//android.widget.TextView[@text='Kingdom']"),
                "Cannot find saved article"
        );
        MainPageObject.waitForElementNotPresent(
                By.xpath("//android.widget.TextView[@text='Kingdom']"),
                "Cannot delete saved article",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        String search_line = "Linkin Park discography";
        MainPageObject.waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                search_line
        );
        String search_result_locator = "//android.widget.ListView[@resource-id='org.wikipedia:id/search_results_list']/android.widget.LinearLayout[@resource-id='org.wikipedia:id/page_list_item_container']";
        MainPageObject.waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_line,
                15
        );
        int amount_of_search_results = MainPageObject.getAmountOfElements(
              By.xpath(search_result_locator)
        );
        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }
    @Test
    public void testAmountOfEmptySearch(){
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        String search_line = "hjghkjgk";
        MainPageObject.waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                search_line
        );
        String search_result_locator = "//android.widget.ListView[@resource-id='org.wikipedia:id/search_results_list']/android.widget.LinearLayout[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "org.wikipedia:id/search_empty_view";
        MainPageObject.waitForElementPresent(
                By.id(empty_result_label),
                "Cannot find empty result label by the request " + search_line,
                15
        );
        MainPageObject.assertElementNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request " + search_line
        );
    }
    @Test
    public void testChangeScreenOrientationOnSearchResults(){
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        String search_line = "Java";
        MainPageObject.waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                search_line
        );
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
                15
        );
        String title_before_rotation = MainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );
        driver.rotate(ScreenOrientation.LANDSCAPE);
        String title_after_rotation = MainPageObject.waitForElementAndGetAttribute(
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
        String title_after_second_rotation = MainPageObject.waitForElementAndGetAttribute(
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
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        String search_line = "Java";
        MainPageObject.waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                search_line
        );
        MainPageObject.waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
                15
        );
        driver.runAppInBackground(Duration.ofSeconds(2));
        MainPageObject.waitForElementPresent(
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
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Kingdom");
        SearchPageObject.clickByArticleWithSubstring("Wikimedia disambiguation page");

        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        MainPageObject.waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                search_line_second
        );
        MainPageObject.waitForElementPresentAndClick(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find title"
        );
        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title"
        );
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options"
        );
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='Add to reading list']"),
                "Cannot find option to add article to reading list"
        );
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
                "Cannot find option to add article to reading list"
        );
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link"
        );
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists"
        );
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
                "Cannot find created folder"
        );
        MainPageObject.swipeElementToLeft(
                By.xpath("//android.widget.TextView[@text='" + search_line_second + "']"),
                "Cannot find article"
        );
        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Cannot find any article"
        );
        String title_of_article_before = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//android.widget.TextView[@text='" + search_line + "']"),
                "text",
                "Cannot find title of article",
                10
        );
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//android.widget.TextView[@text='" + search_line + "']"),
                "Cannot find title of article"
        );
        String title_of_article_after = MainPageObject.waitForElementAndGetAttribute(
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
        MainPageObject.waitForElementPresentAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find 'Search Wikipedia'"
        );
        MainPageObject.waitForElementPresentAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field and send keys",
                search_line
        );
        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find title"
        );
        MainPageObject.waitForElementPresentAndClick(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find title"
        );
        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Title of article not present now."
        );
    }
}