package src.lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject{

    private static final String
            SEARCH_INIT_ELEMENT = "//*[@text='Search Wikipedia']",
            SEARCH_INPUT = "org.wikipedia:id/search_src_text",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text=\"{SUBSTRING}\"]";

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
}
