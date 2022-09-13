package src.lib.ui;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPageObject extends MainPageObject{

    private static final String
            SEARCH_INIT_ELEMENT = "//*[@text='Search Wikipedia']",
            SEARCH_INPUT = "org.wikipedia:id/search_src_text",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text=\"{SUBSTRING}\"]",
            EMPTY_RESULTS_LABEL = "org.wikipedia:id/search_empty_view",
            TITLE_OF_SEARCH_RESULT = "//*[@resource-id='org.wikipedia:id/page_list_item_title']",
            SEARCH_RESULT_ELEMENT = "//android.widget.ListView[@resource-id='org.wikipedia:id/search_results_list']/android.widget.LinearLayout[@resource-id='org.wikipedia:id/page_list_item_container']";


    public SearchPageObject(AppiumDriver driver){
        super(driver);
    }

    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public void initSearchInput(){
        this.waitForElementPresentAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element");
        this.waitForElementPresent(By.id(SEARCH_INPUT), "Cannot find search input after clicking search init element");
    }
    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button");
    }
    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present", 5);
    }
    public void clickCancelSearch(){
        this.waitForElementPresentAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button");
    }
    public void typeSearchLine(String search_line){
        this.waitForElementPresentAndSendKeys(By.id(SEARCH_INPUT), "Cannot find and type into search input", search_line);
    }
    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with substring " + substring, 15);
    }
    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresentAndClick(By.xpath(search_result_xpath), "Cannot find and click search result with substring " + substring, 15);
    }
    public int getAmountOfFoundArticles(){
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request",
                15
        );
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }
    public void waitForEmptyResultLabel(){
        this.waitForElementPresent(
                By.id(EMPTY_RESULTS_LABEL),
                "Cannot find empty result label by the request ",
                15
        );
    }
    public void asserThereIsNoResultOfSearch(){
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We supposed not to find any results");
    }
    public void checkAllResultsOfSearchContains(String word) {
        List titles = driver.findElementsByXPath(TITLE_OF_SEARCH_RESULT);
        titles.stream().forEach(
                (element) -> {
                    WebElement title = (WebElement) element;
                    Assert.assertTrue("At least one title does not contain '" + word + "'", title.getAttribute("text").contains(word));
                });
    }
}
