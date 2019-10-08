package rs.com.search_product;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.com.test_utils.DriverManager;
import rs.com.test_utils.StringUtil;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import static rs.com.test_utils.SeleniumDriverHelper.findElementByXpath;

public class PopularProducts {

    private WebDriver driver;

    private static final Logger logger = LoggerFactory.getLogger(PopularProducts.class);

    @FindBy(xpath = "//*[contains(@class,'active')][1]//div[contains(@class,'productDesc')]")
    private static HtmlElement productDesc;

    @FindBy(xpath = "//*[contains(@class,'active')][1]//div[contains(@class,'linkedPrice')]")
    private static HtmlElement productPrice;

    @FindBy(xpath = "//*[contains(@class,'active')][1]//div[contains(@class,'recAddToBasket')]/span")
    private static HtmlElement addToBasket;

    public static HtmlElement getAddedToBasketState() {
        return addedToBasketState;
    }

    @FindBy(xpath="//*[contains(@class,'recItemAdded')]")
    private static HtmlElement addedToBasketState;

    public PopularProducts() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
    }

    public static HtmlElement getProductDesc() {
        return productDesc;
    }

    public static HtmlElement getProductPrice() {
        return productPrice;
    }

    public static HtmlElement getAddToBasket() {
        return addToBasket;
    }

    public static boolean isProductAddedToBasket() {
        return findElementByXpath("//*[contains(@class,'recItemAdded')]").
                getAttribute("class").toLowerCase().contains("added".toLowerCase());
    }

    public static double getProductDblAmt() {
        return StringUtil.getPriceFromString(productPrice.getText());
    }

}
