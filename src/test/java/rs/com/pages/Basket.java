package rs.com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.com.test_utils.DriverManager;
import rs.com.test_utils.SeleniumDriverHelper;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static rs.com.test_utils.SeleniumDriverHelper.*;

//Verify Basket
//Verify the products added to the basket

public class Basket {

    @FindBy(xpath = "//*[contains(@class,'shBasket js-basket')]//div[contains(@id,'Qty')]")
    private HtmlElement basketQty;

    @FindBy(xpath = "//div[contains(@id,'Amt')]")
    private HtmlElement basketAmt;

    @FindBy(xpath = "//*[contains(@class,'js-basket')]/a")
    private HtmlElement clickBasket;

    @FindBy(xpath = "//*[contains(@class,'pageTitleBlack')]")
    private HtmlElement basketPageTitle;

    @FindBy(xpath = "//*[contains(@class,'red header')]/span[2]")
    private HtmlElement productNtAvlbl;

    @FindBy(xpath = "//*[contains(@id,'shoppingBasketForm')]//div[contains(@class,'errorMessageTitle')]")
    private HtmlElement errorMessage;

    @FindBy(xpath = "//*[contains(@id,'checkoutSecurelyAndPuchBtn')]")
    private HtmlElement checkoutSecurelyBtn;

    private static final Logger logger = LoggerFactory.getLogger(Basket.class);


    public Basket() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
        SeleniumDriverHelper.waitForAjax(60000, true, 4000);
    }

    public int getBasketQty() {
        return parseInt(basketQty.getText());
    }

    public double getBasketAmt() {
        waitUntilPresenceOfElementLocatedByXpath("//div[contains(@id,'Amt')]");
        scrollIntoElementUsingJs(basketAmt);
        logger.info("basketAmt.getText()---"+basketAmt.getText());
        return parseDouble(basketAmt.getText().split("£")[1]);
    }

    public void clickBasket() {
        clickElementUsingJs(clickBasket);
    }

    public String getBasketPageTitle() {
        return basketPageTitle.getText().toLowerCase();
    }

    public boolean getProductNtAvlbl() {
        return isElementPresent(By.xpath("//*[contains(@class,'red header')]/span[2]"));
    }

    public boolean isErrorMessagePresent() {
        return isElementPresent(By.xpath("//*[contains(@id,'shoppingBasketForm')]//div[contains(@class,'errorMessageTitle')]"));
    }

    public String getErrorMessage() {
        requiredWait(500);
        return errorMessage.getText().toLowerCase();
    }

    public void clickChkOutSecBtn() {
         waitForElementToBeClickable(checkoutSecurelyBtn);
         scrollIntoElementUsingJs(checkoutSecurelyBtn);
         waitForAjax(500);
         clickElementUsingJs(checkoutSecurelyBtn);
    }

}
