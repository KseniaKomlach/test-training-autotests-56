package src.lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

    private static final String
            TITLE = "id:org.wikipedia:id/view_page_title_text",
            MORE_OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
            FOOTER_ELEMENT = "id:org.wikipedia:id/page_license_text",
            OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_LIST_BUTTON = "xpath://android.widget.TextView[@text='Add to reading list']",
            ADD_TO_NEW_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
            LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
            LIST_OK_BUTTON = "xpath://android.widget.Button[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
            FOLDER_BY_NAME_TPL = "xpath://android.widget.TextView[@text=\"{FOLDER_NAME}\"]";

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(TITLE, "Cannot find article title on page");
    }
    private static String getFolderXpathByName(String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }
    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }
    public void swipeToFooter(){
        this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 20);
    }
    public void swipeQuickToFooter(){
        this.swipeUpToFindElementQuick(FOOTER_ELEMENT, "Cannot find the end of article", 20);
    }
    public void addArticleToMyNewList(String name_of_folder){
        this.waitForElementPresentAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options"
        );
        this.waitForElementPresentAndClick(
                OPTIONS_ADD_TO_LIST_BUTTON,
                "Cannot find option to add article to reading list"
        );
        this.waitForElementPresentAndClick(
                ADD_TO_NEW_LIST_OVERLAY,
                "Cannot find 'Got it' button"
        );
        this.waitForElementPresentAndClear(
                LIST_NAME_INPUT,
                "Cannot find input to set name of articles folder"
        );
        this.waitForElementPresentAndSendKeys(
                LIST_NAME_INPUT,
                "Cannot put text into articles folder input",
                name_of_folder
        );
        this.waitForElementPresentAndClick(
                LIST_OK_BUTTON,
                "Cannot press OK button"
        );
    }

    public void addArticleToMyList(String name_of_folder){
        this.waitForElementPresentAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options"
        );
        this.waitForElementPresentAndClick(
                OPTIONS_ADD_TO_LIST_BUTTON,
                "Cannot find option to add article to reading list"
        );
        String folder_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementPresentAndClick(
                folder_xpath,
                "Cannot find and click folder with name " + name_of_folder,
                10
        );
    }

    public void closeArticle(){
        this.waitForElementPresentAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link"
        );
    }
}
