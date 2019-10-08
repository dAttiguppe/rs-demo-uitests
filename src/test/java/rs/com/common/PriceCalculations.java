package rs.com.common;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rs.com.test_utils.DriverManager;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import static java.lang.Double.parseDouble;
import static rs.com.test_utils.SeleniumDriverHelper.*;
import static rs.com.test_utils.StringUtil.getDoubleFromString;

//Price calculation class
public class PriceCalculations {

    @FindBy(xpath = "//*[contains(@class,'orderTotal')]//../../tr[1]/td[2]")
    private static HtmlElement goodsTotalPrice;

    @FindBy(xpath = "//*[contains(@class,'orderTotal')]//../../tr[2]/td[2]")
    private static HtmlElement deliveryFee;

    @FindBy(xpath = "//*[contains(@class,'orderTotal')]//../../tr[3]/td[2]")
    private static HtmlElement vatPrice;

    @FindBy(xpath = "//*[contains(@class,'orderTotal')]//../../tr[4]/td[2]")
    private static HtmlElement totalPrice;

    public PriceCalculations() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
        waitForAjax(60000, true, 4000);
    }

    public static double getGoodsTotalPrice() {
        scrollIntoElementUsingJs(findElementByXpath("//*[contains(@class,'orderValueCell grandTotalCell')]"));
        waitForAjax(500);

        return parseDouble(findElementByXpath("//*[contains(@class,'orderValueCell grandTotalCell')]").
                getText().replaceAll("£",""));
    }

    public static HtmlElement getDeliveryFee() {
        return deliveryFee;
    }

    public static double getVatPrice() {
        return parseDouble(getDoubleFromString(vatPrice.getText()));
    }

    public static double getTotalPrice() {
        return parseDouble(getDoubleFromString(totalPrice.getText()));
    }
}
