package rs.com.Search.common;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import testUtils.DriverManager;
import testUtils.SeleniumDriverHelper;
import testUtils.StringUtil;

import static java.lang.Double.parseDouble;

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
        SeleniumDriverHelper.waitForAjax(60000, true, 4000);
    }

    public static double getGoodsTotalPrice() {
        return parseDouble(StringUtil.getDoubleFromString(goodsTotalPrice.getText()));
    }

    public static HtmlElement getDeliveryFee() {
        return deliveryFee;
    }

    public static double getVatPrice() {
        return parseDouble(StringUtil.getDoubleFromString(vatPrice.getText()));
    }

    public static double getTotalPrice() {
        return parseDouble(StringUtil.getDoubleFromString(totalPrice.getText()));
    }
}
