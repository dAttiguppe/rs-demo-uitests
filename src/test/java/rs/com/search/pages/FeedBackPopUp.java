package rs.com.search.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import testUtils.DriverManager;
import testUtils.SeleniumDriverHelper;

public class FeedBackPopUp {

    @FindBy(xpath = "//*[contains(@id,'fsrInvite')]")
    private static HtmlElement feedBackPopUp;

    @FindBy(xpath = "//*[contains(@id,'fsrFocusFirst')]")
    private static HtmlElement closeFeedBackPopUp;

    public static boolean getFeedBackPopUp() {
        return SeleniumDriverHelper.isAlertPresent();
    }

    public static void closeFeedBackPopUp() {
        closeFeedBackPopUp.click();
    }

    public boolean isAlertPresent() {

        boolean presentFlag = false;

        try {
            // Check the presence of alert
            Alert alert = DriverManager.getDriver().switchTo().alert();
            // Alert present; set the flag
            presentFlag = true;
            // if present consume the alert
            alert.accept();
        } catch (NoAlertPresentException ex) {
            // Alert not present
            ex.printStackTrace();
        }

        return presentFlag;

    }


}
