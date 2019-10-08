package rs.com.common;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rs.com.test_utils.DriverManager;
import rs.com.test_utils.SeleniumDriverHelper;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class QuantityUpdate {

    @FindBy(xpath = "//*[contains(@class,'quantityTd')]/input")
    private static HtmlElement qtyUpdateField;

    @FindBy(xpath = "//*[contains(@class,'floatLeft padding')]/a")
    private static HtmlElement qtyUpdateLink;

    public QuantityUpdate() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
        SeleniumDriverHelper.waitForAjax(60000, true, 4000);
    }

    public static HtmlElement getQtyUpdateField() {
        return qtyUpdateField;
    }

    public static HtmlElement getQtyUpdateLink() {
        return qtyUpdateLink;
    }

}
