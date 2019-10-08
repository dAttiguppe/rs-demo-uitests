package rs.com.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rs.com.pojo.SecrurePageData;
import rs.com.test_utils.DriverManager;
import rs.com.test_utils.SeleniumDriverHelper;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import static rs.com.test_utils.SeleniumDriverHelper.*;

public class FillSecureData {
    @FindBy(xpath = "//*[contains(@class,'title')]")
    public Select title;

    @FindBy(xpath = "//input[contains(@class,'first')]")
    public TextInput firstName;

    @FindBy(xpath = "//input[contains(@class,'surName')]")
    public TextInput surName;

    @FindBy(xpath = "//input[contains(@class,'company')]")
    public TextInput companyName;

    @FindBy(xpath = "//input[contains(@class,'addressLine')][1]")
    public TextInput addressLine1;

    @FindBy(xpath = "//input[contains(@placeholder,'Town')]")
    public TextInput town;

    @FindBy(xpath = "//input[contains(@name,'post')]")
    public TextInput postCode;

    @FindBy(css = "//*[contains(@class,'helpInputDiv')]/input")
    public TextInput contactTelephoneNumber;

    public FillSecureData() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
        waitForAjax(60000, true, 4000);
    }


    public void fillSecureCustomerData(SecrurePageData securePageData) {
        SeleniumDriverHelper.findElementByXpath("//*[contains(@class,'title')]").click();
        waitForAjax(500);
        title.selectByValue(securePageData.getTitle());
        firstName.sendKeys(securePageData.getFirstName());
        surName.sendKeys(securePageData.getSurName());
        SeleniumDriverHelper.clickAndHoldSendKeysEnter();
        waitForAjax(500);

        //contactTelephoneNumber.sendKeys("34521312311");
        waitForAjax(500);
        SeleniumDriverHelper.waitUntilPresenceOfElementLocatedByXpath("//*[contains(@class,'helpInputDiv')]/input",500);
        findElementByXpath("//*[contains(@class,'helpInputDiv')]/input").sendKeys(securePageData.getContactTelephoneNumber());

        waitForAjax(500);

        SeleniumDriverHelper.scrollToElement(companyName);
        clickElementUsingJs(companyName);
        companyName.sendKeys(securePageData.getCompanyName());

        clickElementUsingJs(addressLine1);
        addressLine1.sendKeys(securePageData.getAddressLine1());

        clickElementUsingJs(town);
        SeleniumDriverHelper.scrollToElement(town);
        town.sendKeys(securePageData.getTown());

        waitForAjax(500);

        clickElementUsingJs(postCode);
        findElementByXpath("//*[contains(@name,'post')]").sendKeys(securePageData.getPostCode());
        SeleniumDriverHelper.clickAndHoldSendKeysEnter();

        clickContinueToPayPage();
    }

    public static void clickContinueToPayPage(){
        WebElement continueToPayBtn = findElement(By.xpath("//*[contains(@id,'checkoutSecurelyBtn')]"));
        scrollIntoElementUsingJs(continueToPayBtn);
        waitForAjax(500);
        clickElementUsingJs(continueToPayBtn);


    }
}
