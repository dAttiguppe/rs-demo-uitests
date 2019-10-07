package rs.com.Search.Menu;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import rs.com.Search.pages.BreadCrumb;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import testUtils.DriverManager;
import testUtils.SeleniumDriverHelper;

import static org.slf4j.LoggerFactory.getLogger;
import static testUtils.SeleniumDriverHelper.*;

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
        //return (openMenuState.getAttribute("class").contains("active"));
    }

//    private boolean isSubMenuOpen(WebElement element) {
//        return isClassContainsAttribute(element, "show");
//    }
//
//    public void openMenu() {
//        if (isElementDisplayed(menuItem)) {
//            if (!isMenuOpen("Test")) {
//                menuItem.click();
//                Assert.assertTrue(isMenuOpen("Test"));
//            }
//        }
//    }
//
//    public void closeMenu() {
//        if (isElementDisplayed(menuItem)) {
//            if (isMenuOpen("Test")) {
//                menuItem.click();
//                //Assert.assertFalse(isMenuOpen());
//            }
//        }
//    }
//
    private void selectSubMenu(String menuCategory) {
        WebElement menuItemCategory =
                findElementByXpath("//*/ul/li[contains(@class,'verticalMenuOption')]/a[contains(@href,'"+menuCategory.toLowerCase()+"')]");
        //String locator = ".//a[text()='" + text + "']";
        clickElementUsingJs(menuItemCategory);
        //menuItemCategory.click();
        SeleniumDriverHelper.waitForAjax(500);
       // if (isSubMenuOpen(parent)){
            //findElementByXpathAndClick("menuItem");
        //SeleniumDriverHelper.findElementByXpathAndClick("");
        BreadCrumb breadCrumb1 = new BreadCrumb();
        Assert.assertTrue(breadCrumb1.checkBreadCrumb(menuCategory));

    }

    //Example how to use: selectMenuItem("All Products"); selectMenuItem("Batteries", "Automotive");
    public void selectMenuOption(String mainMenuItem, String subMenuItem) throws Exception {


        switch (mainMenuItem) {
            case "AllProducts":
                //SeleniumDriverHelper.waitForAjax(500);
                waitUntilPresenceOfElementLocatedByXpath("//*[contains(@class,'menu product-menu')]");
                findElementByXpathAndClick("//*[contains(@class,'menu product-menu')]");
                //allProductsMenu.click();
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


