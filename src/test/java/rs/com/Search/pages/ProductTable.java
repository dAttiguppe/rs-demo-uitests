package rs.com.Search.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import testUtils.DriverManager;
import testUtils.ElementHelper;
import testUtils.RandomUtil;
import testUtils.SeleniumDriverHelper;

import java.util.List;

import static testUtils.SeleniumDriverHelper.*;

public class ProductTable {


    private WebDriver driver;
    private FeedBackPopUp feedBackPopUp;
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

    public static String getItemAddedToBasketRsltTbl() {
        waitUntilPresenceOfElementLocatedByXpath("//*[contains(@onclick,'href')]/div[2]/div");
        return itemAddedToBasketRsltTbl.getText().toLowerCase();
    }

    @FindBy(xpath="//*[contains(@onclick,'href')]/div[2]/div")
    private static HtmlElement itemAddedToBasketRsltTbl;



    private static String resultsTableList = "//div[contains(@class,'resultsTable results-table-container')]/table/tbody";
    private static final String resultsTableProductAddBtn = "#atbBtn-1 > div";

    public ProductTable() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
    }

    public static int addToBasket() {
        if(isPopularProductSectionPresent()) {
            System.out.println("Inside if statement");
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

            System.out.println("checkPrdAddedToBskt----"+checkPrdAddedToBskt);

            System.out.println("Inside if statement---added tobasket");

            waitForAjax(5000);

            Assert.assertTrue(checkPrdAddedToBskt.contains(REC_ITEM_ADDED));

            //randomProduct = findElementByCss(randomProdLocator);
        }

        else{
            System.out.println("Inside else statement");

            ProductResultGrid productResultGrid = new ProductResultGrid();


//             productList = SeleniumDriverHelper.findElements(By.xpath(resultsTableList));
//             randomProductInt = productList.size();

             //DriverManager.getDriver().navigate().refresh();

//            randomProdLocator = "div.at-table > div > div:nth-child("+randomProductInt+") > div.recAboveFloatRight > div.recAddToBasket > span";
//            randomProduct = SeleniumDriverHelper.findElementByCss(randomProdLocator);
            waitForAjax(500);
            waitUntilPresenceOfElementLocatedByCss(resultsTableProductAddBtn);
            //scrollIntoElementUsingJs(By.cssSelector(resultsTableList));
            randomProduct = findElementByCss(resultsTableProductAddBtn);

            clickElementUsingJs(randomProduct);

            waitForAjax(5000);

            //price - .//td[contains(@class,'priceCol')]/div/span

            //button - .//button[contains(@class,'addToBasketBtn')]

            //name - .//a[contains(@class,'product-name')]

            //units - .//input[contains(@name,'txtQty')]

            String addedToBasketClass = "addToBasketMessage";

            String addedToBasketMsg = "//*[contains(@onclick,'href')]/div[2]/div";

            waitForAjax(5000);

            Assert.assertTrue(productResultGrid.getAddedToBsktTick().isDisplayed());

            waitForAjax(5000);

            //scrollIntoElementUsingJs(randomProduct);

            Assert.assertTrue(getItemAddedToBasketRsltTbl().contains("Added".toLowerCase()));
            waitForAjax(5000);

            productResultGrid.clickBasketFromResultGrid();
            logger.info("Product added to the basket");
        }

        return randomProductInt;

    }


    public static boolean isPopularProductSectionPresent() {
        return popularProductSection.isDisplayed();
    }


    public void checkBasket(int productSelected) {
        ProductDetails productDetails = new ProductDetails();
        Basket basket = new Basket();

        //scrollIntoElementUsingJs(SeleniumDriverHelper.findElementByXpath("//*[contains(@id,'atpRow-0')]"));
        String productDesc = productDetails.getProductDesc();
        double productPgPrdPrice = ElementHelper.round(productDetails.getProductPrice(productSelected),2);

        double basketAmt = basket.getBasketAmt();
        int basketQty = basket.getBasketQty();

        Assert.assertTrue(productPgPrdPrice==basketAmt);


    }
}
