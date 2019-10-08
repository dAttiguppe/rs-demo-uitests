package rs.com.menu;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import rs.com.common.BreadCrumb;
import rs.com.test_utils.DriverManager;
import rs.com.test_utils.SeleniumDriverHelper;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import static org.slf4j.LoggerFactory.getLogger;
import static rs.com.test_utils.SeleniumDriverHelper.*;

public class PrimaryNavContainer {
    private WebDriver driver;
    private BreadCrumb breadCrumb;

    @FindBy(xpath = "//*[contains(@class,'product-menu')]")
    public HtmlElement allProductsMenu;

    @FindBy(xpath = "//*[contains(@class,'brands-menu')]")
    public HtmlElement ourBrandsMenu;

    @FindBy(xpath = "//*[contains(@title,'New')]")
    public HtmlElement newProductsMenu;

    @FindBy(xpath = "//*[contains(@title,'Account')]")
    public HtmlElement myAccount;

    @FindBy(xpath = "//*[contains(@title,'Services')]")
    public HtmlElement ourServices;

    @FindBy(xpath = "//ul[contains(@class,'verticalMenu showVerticalMenu')]")
    public HtmlElement openMenuState;

    @FindBy(xpath = "//li[contains(@class,'verticalMenuOption')]/a[contains(@href,'semiconductors')]")
    public HtmlElement menuItem;

    private static final Logger logger = getLogger(PrimaryNavContainer.class);
    private static String menuItemLocator;


    public PrimaryNavContainer() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);        //SeleniumDriverHelper.waitForAjax(60000, true, 4000);
    }

    private boolean isMenuOpen(String menuCategory, String menuItemLocator) {
        return findElementByXpath(menuItemLocator).getAttribute("class").contains("active");
    }

    private void selectSubMenu(String menuCategory) {

        WebElement menuItemCategory =
        findElementByXpath("//*/ul/li[contains(@class,'verticalMenuOption')]/a[contains(@href,'"+menuCategory.toLowerCase()+"')]");
        clickElementUsingJs(menuItemCategory);
        SeleniumDriverHelper.waitForAjax(1000);
        BreadCrumb breadCrumb1 = new BreadCrumb();
        Assert.assertTrue(breadCrumb1.checkBreadCrumb(menuCategory));
    }

    //Example how to use: selectMenuItem("All Products"); selectMenuItem("Batteries", "Automotive");
    public void selectMenuOption(String mainMenuItem, String subMenuItem) throws Exception {

        switch (mainMenuItem) {
            case "AllProducts":
                SeleniumDriverHelper.waitForAjax(1000);
                waitUntilPresenceOfElementLocatedByXpath("//*[contains(@class,'menu product-menu')]");
                findElementByXpathAndClick("//*[contains(@class,'menu product-menu')]");

                menuItemLocator = "//*[contains(@class,'menu product-menu')]";
                isMenuOpen(mainMenuItem,menuItemLocator);
                selectSubMenu(subMenuItem);
                break;
            case "Brands":
                ourBrandsMenu.click();
                menuItemLocator = "//*[contains(@class,"+mainMenuItem+")]";
                isMenuOpen(mainMenuItem,menuItemLocator);
                //selectSubMenu(subMenuItem);
                break;
            case "NewProducts":
                newProductsMenu.click();
                break;
            case "MyAccount":
                myAccount.click();
                //selectSubMenu(subMenuItem);
                break;
            case "OurServices":
                ourServices.click();
                break;
            default:
                throw new Exception("Please select a valid category from the item");

        }

        //closeMenu();
    }
}


