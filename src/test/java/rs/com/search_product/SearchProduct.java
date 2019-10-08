package rs.com.search_product;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.com.test_utils.DriverManager;
import rs.com.test_utils.SeleniumDriverHelper;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;

public class SearchProduct {

    private WebDriver driver;

    private static final Logger logger = LoggerFactory.getLogger(SearchProduct.class);

    @FindBy(xpath = "//*[contains(@id,'searchForm')]/input[3]")
    private static HtmlElement searchField;

    @FindBy(xpath = "//*[contains(@id,'btnSearch')]")
    private static HtmlElement searchBtn;

    @FindBy(xpath = "//*[contains(@id,'js-predictive-search')]/ul/li[2]")
    private static HtmlElement searchList;

    @FindBy(xpath="//*[contains(@id,'js-search-hint')]/li[2]/a")
    private static List<WebElement> dropdownList;

    public SearchProduct() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
    }
    public static HtmlElement getSearchField() {
        return searchField;
    }

    public static HtmlElement getSearchBtn() {
        return searchBtn;
    }

    public void clickSearchBtn(){
        getSearchBtn().click();
    }

    public void enterSearchTerm(String product){
        getSearchField().sendKeys(product);
    }

    public static void getDropdownList(String product) {
        SeleniumDriverHelper.waitForAjax(5000);
        //WebElement searchElem =findElementByXpath("//*[contains(@class,'advItemContainer')]");
        //waitForElementToBeClickable(searchElem,1000);
        SeleniumDriverHelper.findElementByXpathAndClickUsingJs("//*[contains(@data-category,'"+product+"')]");
    }




}
