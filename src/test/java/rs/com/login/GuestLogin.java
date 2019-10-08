package rs.com.login;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rs.com.test_utils.DriverManager;
import rs.com.test_utils.SeleniumDriverHelper;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import static rs.com.test_utils.SeleniumDriverHelper.scrollIntoElementUsingJsAndClick;

public class GuestLogin {

    @FindBy(xpath="//*[contains(@id,'loginModelPanel_header')]")
    private static HtmlElement guestLoginModal;

    @FindBy(xpath="//*[contains(@id,'guestCheckoutForm')]")
    private static HtmlElement guestCheckoutSection;

    @FindBy(xpath="//*[contains(@id,'emailAddress')][2]")
    private static HtmlElement emailIDField;

    @FindBy(xpath="//*[@id=\"guestCheckoutForm:guestCheckout\"]")
    private static HtmlElement guestLoginBtn;

    public GuestLogin() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
        SeleniumDriverHelper.waitForAjax(60000, true, 4000);
    }


    public static boolean getGuestLoginModal() {
        return (guestLoginModal.isDisplayed()) ? true : false;
    }

    public static HtmlElement getGuestCheckoutSection() {
        return guestCheckoutSection;
    }

    public static HtmlElement getEmailIDField() {
        return emailIDField;
    }

    public static HtmlElement clickGuestLoginBtn() {
        return guestLoginBtn;
    }

    public static void enterEmailID(){
        scrollIntoElementUsingJsAndClick(emailIDField);
        getEmailIDField().sendKeys();

    }


}
