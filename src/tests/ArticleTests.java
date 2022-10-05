package src.tests;

import org.testng.annotations.Test;
import src.lib.CoreTestCase;
import src.lib.ui.ArticlePageObject;
import src.lib.ui.MyListsPageObject;
import src.lib.ui.NavigationUI;
import src.lib.ui.SearchPageObject;
import src.lib.ui.factories.ArticlePageObjectFactory;
import src.lib.ui.factories.SearchPageObjectFactory;

public class ArticleTests extends CoreTestCase {
    @Test
    public void testSwipeArticle(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Automation for Apps");
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }
    @Test
    public void testAssertTitleOfArticlePresent(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Appium";
        String substring = "Automation for Apps";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(substring);
        ArticlePageObject.waitForTitleElement();
    }
}
