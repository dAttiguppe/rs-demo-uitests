package rs.com.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.com.test_utils.DriverManager;
import rs.com.test_utils.ElementHelper;
import rs.com.test_utils.RandomUtil;
import rs.com.test_utils.SeleniumDriverHelper;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;

import static rs.com.test_utils.SeleniumDriverHelper.*;

public class ProductTable {

    private WebDriver driver;

    private static List<WebElement> productList;
    private static int randomProductInt;
    private static String randomProdLocator;
    private static WebElement randomProduct;
    private static double productPrice;
    private static String checkPrdAddedToBskt;

    private static String productTableLocator = "//*[contains(@class,'at-table-column recRecItem')]";
    public static final String REC_ITEM_ADDED = "recItemAdded";
    private static final Logger logger = LoggerFactory.getLogger(CategoryResultsPage.class);

    @FindBy(xpath = "//*[contains(@id,'ATPopularProduct')]")
    private static HtmlElement popularProductSection;

    @FindBy(xpath="//*[contains(@id,'resultsTable')]")
    private static HtmlElement resultsTable;

    @FindBy(xpath="//*[contains(@id,'resultsTable')]")
    private static HtmlElement itemAddedToBasket;

    @FindBy(xpath="//*[contains(@id,'atbBtn-1')]")
    private static HtmlElement itemAddedToBasketRsltTbl;

    public static String getItemAddedToBasketRsltTbl() {
        waitUntilPresenceOfElementLocatedByXpath("//*[contains(@id,'atbBtn-1')]");
        return itemAddedToBasketRsltTbl.getText().toLowerCase();
    }

    private static String resultsTableList = "//div[contains(@class,'resultsTable results-table-container')]/table/tbody";
    private static final String resultsTableProductAddBtn = "#atbBtn-1 > div";

    public ProductTable() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
    }

    public static int addToBasket() {
        if(isPopularProductSectionPresent()) {
            logger.info("Inside if statement");
            List<WebElement> productList = SeleniumDriverHelper.findElements(By.xpath(productTableLocator));
            randomProductInt = RandomUtil.getRandomNextInt(productList.size()-1);
            if(randomProductInt==0 || randomProductInt==1)
                randomProductInt=1;
            else
                randomProductInt=randomProductInt;

            SeleniumDriverHelper.waitForPageLoad();

            SeleniumDriverHelper.waitUntilPresenceOfElementLocated(By.xpath("//*[contains(@id,'ATPopularProduct')]"));

            //randomProdLocator = "div.at-table > div > div:nth-child("+randomProductInt+") > div.recAboveFloatRight > div.recAddToBasket > span";
            randomProdLocator = "//*[contains(@class,'at-table-column recRecItem')]["+randomProductInt+"]";
            WebElement addToBasketBtn = findElementByXpath("//*[contains(@class,'at-table-column recRecItem')]["+randomProductInt+"]//.//div[contains(@class,'recAddToBasket')]/span");

            waitForAjax(5000);
            clickElementUsingJs(addToBasketBtn);

            waitForAjax(5000);
            productPrice = Double.parseDouble(addToBasketBtn.getAttribute("data-price"));
            checkPrdAddedToBskt = addToBasketBtn.getAttribute("class");
            logger.info("checkPrdAddedToBskt----"+checkPrdAddedToBskt);
            logger.info("Inside if statement---added tobasket");
            waitForAjax(5000);
            Assert.assertTrue(checkPrdAddedToBskt.contains(REC_ITEM_ADDED));
        }

        else{
            logger.info("Inside else statement");
            ProductResultGrid productResultGrid = new ProductResultGrid();
            waitForAjax(500);
            waitUntilPresenceOfElementLocatedByCss(resultsTableProductAddBtn);
            randomProduct = findElementByCss(resultsTableProductAddBtn);
            clickElementUsingJs(randomProduct);
            waitForAjax(2000);

            Assert.assertTrue(productResultGrid.getAddedToBsktTick().isDisplayed());
            waitForAjax(2000);

            Assert.assertTrue(getItemAddedToBasketRsltTbl().contains("Added".toLowerCase()));
            waitForAjax(2000);

            productResultGrid.clickBasketFromResultGrid();
            logger.info("Product added to the basket");
        }

        return randomProductInt;

    }


    public static boolean isPopularProductSectionPresent() {
        return popularProductSection.isDisplayed();
    }


    public void checkBasket(int productSelected) throws  IllegalAccessException{
        ProductDetails productDetails = new ProductDetails();
        Basket basket = new Basket();

        //scrollIntoElementUsingJs(SeleniumDriverHelper.findElementByXpath("//*[contains(@id,'atpRow-0')]"));
        String productDesc = ProductDetails.getProductDesc();
        double productPgPrdPrice = ElementHelper.round(productDetails.getProductPrice(productSelected),2);

        double basketAmt = basket.getBasketAmt();
        int basketQty = basket.getBasketQty();

        Assert.assertTrue(productPgPrdPrice==basketAmt);


    }
}
