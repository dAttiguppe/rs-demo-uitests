package rs.com.Search.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import testUtils.DriverManager;
import testUtils.SeleniumDriverHelper;

import static java.lang.Double.parseDouble;
import static testUtils.SeleniumDriverHelper.*;

//Verify Basket
//Verify the products added to the basket

public class Basket {

    @FindBy(xpath = "//*[contains(@class,'shBasket js-basket')]//div[contains(@id,'Qty')]")
    private static HtmlElement basketQty;

    @FindBy(xpath = "//*[contains(@class,'shBasket js-basket')]//div[contains(@id,'Amt')]")
    private static HtmlElement basketAmt;

    //TO:DO - check if other scenarios work
    @FindBy(xpath = "//*[contains(@class,'js-basket')]/a")
    private static HtmlElement clickBasket;

    @FindBy(xpath = "//*[contains(@class,'pageTitleBlack')]")
    private static HtmlElement basketPageTitle;

    @FindBy(xpath = "//*[contains(@class,'red header')]/span[2]")
    private static HtmlElement productNtAvlbl;

    @FindBy(xpath = "//*[contains(@id,'shoppingBasketForm')]//div[contains(@class,'errorMessageTitle')]")
    private static HtmlElement errorMessage;

    @FindBy(xpath = "//*[contains(@id,'checkoutSecurelyAndPuchBtn')]")
    private static HtmlElement checkoutSecurelyBtn;

    private static final Logger logger = LoggerFactory.getLogger(Basket.class);


    public Basket() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
        SeleniumDriverHelper.waitForAjax(60000, true, 4000);
    }

    public static int getBasketQty() {
        return Integer.parseInt(basketQty.getText());
    }

    public static double getBasketAmt() {
        logger.info("basketAmt.getText()---"+basketAmt.getText());
        return parseDouble(basketAmt.getText().split("£")[1]);
    }

    public static void clickBasket() {
        clickElementUsingJs(clickBasket);
    }

    public static String getBasketPageTitle() {
        return basketPageTitle.getText().toLowerCase();
    }

    public static boolean getProductNtAvlbl() {
        return isElementPresent(By.xpath("//*[contains(@class,'red header')]/span[2]"));
        //return productNtAvlbl.isEnabled();
    }

    public static boolean isErrorMessagePresent() {
        return isElementPresent(By.xpath("//*[contains(@id,'shoppingBasketForm')]//div[contains(@class,'errorMessageTitle')]"));
        //return errorMessage.getText().toLowerCase();
    }

    public static String getErrorMessage() {
        //return isElementPresent(By.xpath("//*[contains(@id,'shoppingBasketForm')]//div[contains(@class,'errorMessageTitle')]"));
        requiredWait(500);
        return errorMessage.getText().toLowerCase();
    }

    public static void clickChkOutSecBtn() {
        waitForElementToBeClickable(checkoutSecurelyBtn);
         scrollIntoElementUsingJs(checkoutSecurelyBtn);
         waitForAjax(500);
         clickElementUsingJs(checkoutSecurelyBtn);
    }

}
