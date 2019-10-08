package rs.com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rs.com.test_utils.DriverManager;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import static java.lang.Double.parseDouble;
import static rs.com.test_utils.SeleniumDriverHelper.getTextByXpath;

public class ProductDetails {

    private WebDriver driver;

    @FindBy(xpath = "//*[contains(@class,'productDesc')]/a")
    private static HtmlElement productDesc;

    @FindBy(xpath = "//*[contains(@class,'at-table')][\"+productNum+\"]/div/div[2]")
    private static HtmlElement productDesc1;

    @FindBy(xpath = "//*[contains(@class,'at-table-column recRecItem')]/div/a")
    private static HtmlElement productLink;

    @FindBy(xpath = "//*[contains(@class,'recAddToBasket')]/span")
    private static HtmlElement addToBasketButton;

    @FindBy(xpath = ".//input[contains(@name,'txtQty')]")
    private static HtmlElement numberOfUnits;

    private static String resultsTableList = "//div[contains(@class,'resultsTable results-table-container')]/table/tbody";
    private static final String resultsTableProductAddBtn = "#atbBtn-1 > div";

    public ProductDetails() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
    }

    public static double getProductPrice(int productNum) {
        String productPriceXpath = "//*[contains(@class,'at-table')]["+productNum+"]/div[2]/div[2]";
        System.out.println("getTextByXpath(productPriceXpath)--"+getTextByXpath(productPriceXpath).split("£")[1]);
        //String number = getTextByXpath(productPriceXpath).split("£");
        return parseDouble((getTextByXpath(productPriceXpath).split("£")[1]));
    }

    public static String getProductDesc() {
        return productDesc.getText();
    }

    public static void clickAddToBasketButton() {
        addToBasketButton.click();
    }

    public static double getTotalPrice(int productNum){
        return getProductPrice(productNum) * getNumberOfUnits();
    }

    public static int getNumberOfUnits(){
        return Integer.parseInt(numberOfUnits.getText());
    }




}
