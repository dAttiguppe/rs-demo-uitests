package rs.com.common;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rs.com.test_utils.DriverManager;
import rs.com.test_utils.SeleniumDriverHelper;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import static rs.com.test_data_constants.TestConstants.DELIVERY;

public class SecureCheckoutPage {

    @FindBy(xpath="//*[contains(@class,'progressStepDiv progressActive')]/span[2]")
    private static HtmlElement chkOutPgHeader;

    @FindBy(xpath="//*[contains(@class,'deliveryHeaderDiv')]")
    private static HtmlElement deliveryHeading;

    public SecureCheckoutPage() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
        SeleniumDriverHelper.waitForAjax(60000, true, 4000);
    }

    public static HtmlElement getChkOutPgHeader() {
        return chkOutPgHeader;
    }

    public static HtmlElement getDeliveryHeading() {
        return deliveryHeading;
    }

    public static boolean isCheckPageHeaderPresent(){
        return getChkOutPgHeader().getText().contains(DELIVERY);
    }

    public static boolean isDeliveryHeaderPresent(){
        return getDeliveryHeading().getText().contains(DELIVERY);
    }

}
