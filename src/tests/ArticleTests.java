package src.tests;

import org.testng.annotations.Test;
import src.lib.CoreTestCase;
import src.lib.ui.ArticlePageObject;
import src.lib.ui.MyListsPageObject;
import src.lib.ui.NavigationUI;
import src.lib.ui.SearchPageObject;

public class ArticleTests extends CoreTestCase {
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
    public void testAssertTitleOfArticlePresent(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Java";
        String substring = "Object-oriented programming language";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(substring);
        ArticlePageObject.waitForTitleElement();
    }
}
