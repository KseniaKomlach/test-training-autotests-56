package src.tests;

import org.testng.annotations.Test;
import src.lib.CoreTestCase;
import src.lib.Platform;
import src.lib.ui.ArticlePageObject;
import src.lib.ui.MyListsPageObject;
import src.lib.ui.NavigationUI;
import src.lib.ui.SearchPageObject;
import src.lib.ui.factories.ArticlePageObjectFactory;
import src.lib.ui.factories.MyListsPageObjectFactory;
import src.lib.ui.factories.NavigationUIFactory;
import src.lib.ui.factories.SearchPageObjectFactory;

public class MyListsTests extends CoreTestCase {
    //@Test
    public void testSaveFirstArticleToMyList() {
        String search_line = "Kingdom";
        String substring = "Video game series";
        String name_of_folder = "Folder";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(substring);

        ArticlePageObject.waitForTitleElement();

        if (Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyNewList(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI.clickMyLists();

        if (Platform.getInstance().isAndroid()){
            MyListPageObject.openFolderByName(name_of_folder);
        } else {
            MyListPageObject.closePopUpWindow();
        }
        MyListPageObject.waitForArticleToAppearByTitle(search_line);
        MyListPageObject.swipeByArticleToDelete(search_line);
        MyListPageObject.waitForArticleToDisappearByTitle(search_line);
    }
    @Test
    public void testSavingTwoArticles(){
        String search_line = "Kingdom";
        String substring = "Video game series";

        String search_line_second = "Elizabeth II";
        String substring_second = "Queen of the United Kingdom";

        String name_of_folder = "Folder";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(substring);

        ArticlePageObject.waitForTitleElement();

        if (Platform.getInstance().isAndroid()){
            substring = ArticlePageObject.getArticleTitle();
            ArticlePageObject.addArticleToMyNewList(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line_second);
        SearchPageObject.clickByArticleWithSubstring(substring_second);

        ArticlePageObject.waitForTitleElement();

        if (Platform.getInstance().isAndroid()){
            substring_second = ArticlePageObject.getArticleTitle();
            ArticlePageObject.addArticleToMyNewList(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI.clickMyLists();

        if (Platform.getInstance().isAndroid()){
            MyListPageObject.openFolderByName(name_of_folder);
        } else {
            MyListPageObject.closePopUpWindow();
        }
        MyListPageObject.swipeByArticleToDelete(substring_second);
        MyListPageObject.waitForArticleToDisappearByTitle(substring_second);
        MyListPageObject.waitForArticleToAppearByTitle(substring);
    }
}
