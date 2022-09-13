package src.lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject{

    private static final String
            FOLDER_BY_NAME_TPL = "//android.widget.TextView[@text=\"{FOLDER_NAME}\"]",
            ARTICLE_BY_TITLE_TPL = "//*[@text=\"{TITLE}\"]";

    public MyListsPageObject(AppiumDriver driver){super(driver);}

    private static String getFolderXpathByName(String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }
    private static String getArticleXpathByTitle(String article_title){
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    public void openFolderByName(String name_of_folder){
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementPresentAndClick(
                By.xpath(folder_name_xpath),
                "Cannot find and click list with name " + name_of_folder,
                10
        );
    }
    public void swipeByArticleToDelete(String name_of_article){
        this.swipeElementToLeft(
                By.xpath("//android.widget.TextView[@text='" + name_of_article + "']"),
                "Cannot find and delete article"
        );
    }
    public void waitForArticleToDisappearByTitle(String article_title){
        String article_xpath = getArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(
                By.xpath(article_xpath),
                "Saved article still present with title " + article_title,
                10
        );
    }
    public void waitForArticleToAppearByTitle(String article_title){
        String article_xpath = getArticleXpathByTitle(article_title);
        this.waitForElementPresent(
                By.xpath(article_xpath),
                "Cannot find article by title " + article_title,
                10
        );
    }
}

