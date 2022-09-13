package src.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import src.lib.CoreTestCase;
import src.lib.ui.ArticlePageObject;
import src.lib.ui.MainPageObject;
import src.lib.ui.SearchPageObject;

public class SearchTests extends CoreTestCase {
    //Ex3
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
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Linkin Park discography");
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }
    @Test
    public void testAmountOfEmptySearch(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("hjghkjgk");
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.asserThereIsNoResultOfSearch();
    }
    @Test
    public void testCheckResultsOfSearch(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Kingdom");
        SearchPageObject.waitForSearchResult("Wikimedia disambiguation page");
        SearchPageObject.checkAllResultsOfSearchContains("Kingdom");
    }
    //Ex9
    @Test
    public void testFind3ArticlesByTitleAndDescription(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Kingdom");
        SearchPageObject.waitForElementByTitleAndDescription("Kingdom", "Wikimedia disambiguation page");
        SearchPageObject.waitForElementByTitleAndDescription("Kingdom of Great Britain", "Constitutional monarchy in Western Europe (1707–1800)");
        SearchPageObject.waitForElementByTitleAndDescription("Kingdom Hearts", "Video game series");
    }
}
