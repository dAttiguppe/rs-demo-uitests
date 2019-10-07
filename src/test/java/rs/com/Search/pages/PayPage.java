package rs.com.Search.pages;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import testUtils.DriverManager;

import static testUtils.SeleniumDriverHelper.waitForAjax;
import static testUtils.StringUtil.getNumbersFromString;

public class PayPage {

    @FindBy(xpath = "//*[contains(@class,'progressActive')]/span[2]")
    private HtmlElement payPageHeader;

    @FindBy(xpath = "//*[contains(@class,'grandTotalCost')]/span")
    private HtmlElement totalPrice;


    public PayPage() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
        waitForAjax(60000, true, 4000);
    }

    public HtmlElement getPayPageHeader() {
        return payPageHeader;
    }

    public HtmlElement getTotalPrice() {
        return totalPrice;
    }

    public boolean checkPayPageHeader() {
        return payPageHeader.getText().toLowerCase().contains("pay".toLowerCase()) ? true : false;
    }

    public int checkTotalPrice(){
        return getNumbersFromString(totalPrice.getText());
    }


}
