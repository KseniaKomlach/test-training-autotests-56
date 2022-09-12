package src.lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

    private static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            MORE_OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            FOOTER_ELEMENT = "org.wikipedia:id/page_license_text",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_LIST_BUTTON = "//android.widget.TextView[@text='Add to reading list']",
            ADD_TO_LIST_OVERLAY = "org.wikipedia:id/onboarding_button", //or org.wikipedia:id/create_button
            LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            LIST_OK_BUTTON = "//android.widget.Button[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(By.id(TITLE), "Cannot find article title on page");
    }
    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }
    public void swipeToFooter(){
        this.swipeUpToFindElement(By.id(FOOTER_ELEMENT), "Cannot find the end of article", 20);
    }
    public void swipeQuickToFooter(){
        this.swipeUpToFindElementQuick(By.id(FOOTER_ELEMENT), "Cannot find the end of article", 20);
    }
    public void addArticleToMyList(String name_of_folder){
        this.waitForElementPresentAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options"
        );
        this.waitForElementPresentAndClick(
                By.xpath(OPTIONS_ADD_TO_LIST_BUTTON),
                "Cannot find option to add article to reading list"
        );
        this.waitForElementPresentAndClick(
                By.id(ADD_TO_LIST_OVERLAY),
                "Cannot find 'Got it' button"
        );
        this.waitForElementPresentAndClear(
                By.id(LIST_NAME_INPUT),
                "Cannot find input to set name of articles folder"
        );
        this.waitForElementPresentAndSendKeys(
                By.id(LIST_NAME_INPUT),
                "Cannot put text into articles folder input",
                name_of_folder
        );
        this.waitForElementPresentAndClick(
                By.xpath(LIST_OK_BUTTON),
                "Cannot press OK button"
        );
    }
    public void closeArticle(){
        this.waitForElementPresentAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find X link"
        );
    }
}
