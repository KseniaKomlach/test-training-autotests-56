package src.tests.iOS;

import org.junit.Test;
import src.lib.iOSTestCase;
import src.lib.ui.WelcomePageObject;

public class GetStartedTest extends iOSTestCase {
    @Test
    public void testPassThroughWelcome(){
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);

        WelcomePage.waitForLearnMoreLink();
        WelcomePage.clickNextButton();

        WelcomePage.waitForNewWayToExploreText();
        WelcomePage.clickNextButton();

        WelcomePage.waitForSearchInOver300LanguagesText();
        WelcomePage.clickNextButton();

        WelcomePage.waitForLearnMoreAboutDataCollectedText();
        WelcomePage.clickGetStartedButton();
    }
}
