package rs.com.search_product;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rs.com.test_utils.DriverManager;
import rs.com.test_utils.SeleniumDriverHelper;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class ProductDetailsPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[contains(@class,'prodDescDivLL')]")
    private static HtmlElement productDetails;

    @FindBy(xpath = "//h1[contains(@class,'main')]")
    private static HtmlElement productName;

    @FindBy(xpath = "//*[contains(@class,'price txt-vat')]")
    private static HtmlElement productPriceIncVAT;

    @FindBy(xpath = "//*[contains(@class,'price')]/span[2]")
    private static HtmlElement productPriceExcVAT;

    @FindBy(xpath = "//*[@id=\"price-break-container\"]/div/div[2]/div/div[3]/div/button")
    private static HtmlElement addToBAsketBtn;

    public static HtmlElement getAddedToBasket() {
        return addedToBasket;
    }

    @FindBy(xpath = "//*[contains(@class,'cart-added')]/span")
    private static HtmlElement addedToBasket;

    public ProductDetailsPage() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
        SeleniumDriverHelper.waitForAjax(60000, true, 4000);
    }

    public static HtmlElement getProductDetails() {
        return productDetails;
    }

    public static HtmlElement getProductName() {
        return productName;
    }

    public static HtmlElement getProductPriceIncVAT() {
        return productPriceIncVAT;
    }

    public static HtmlElement getProductPriceExcVAT() {
        return productPriceExcVAT;
    }

    public static HtmlElement getAddToBAsketBtn() {
        return addToBAsketBtn;
    }

    public static void clickAddToBasket(){
        addToBAsketBtn.click();
    }

    public static String getProductNameSearchPg(){
        return productName.getText().toLowerCase();
    }

    public static String getAddedToBasketText(){
        return addedToBasket.getText().toLowerCase();
    }

}
