package src.tests;

import org.junit.Test;
import src.lib.CoreTestCase;
import src.lib.Platform;
import src.lib.ui.WelcomePageObject;

public class GetStartedTest extends CoreTestCase {
    @Test
    public void testPassThroughWelcome(){
        if (Platform.getInstance().isAndroid()){
            return;
        }
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
