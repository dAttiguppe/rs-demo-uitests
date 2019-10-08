package rs.com.switch_view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rs.com.test_utils.DriverManager;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import static rs.com.test_utils.SeleniumDriverHelper.*;

public class SwitchView {

    private WebDriver driver;

    private static String switchViewLocator = "//*[contains(@ng-model,'showGridView')]";
    private static String gridViewIcon = "//*[contains(@class,'labelContainer toggleActive')]";

    @FindBy(xpath = "//*[@id=\"results-table\"][contains(@class,'grid')]")
    private static HtmlElement gridViewResultsDisplay;

    public SwitchView() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
        waitForAjax(60000, true, 4000);
    }

    public static void switchToGridView(){
        clickElementUsingJs(findElementByXpath(switchViewLocator));
        waitForAjax(1000);
    }

    public static WebElement getGridViewIcon() {
        return findElement(By.xpath(gridViewIcon));
    }

    public static boolean checkGridViewDisplayed(){
        scrollIntoElementUsingJs(getGridViewIcon());
        waitForAjax(1000);
         return getGridViewIcon().getAttribute("class").toLowerCase().contains("active") ? true : false;
    }

    public static boolean getGridViewResultsDisplay() {
        return gridViewResultsDisplay.getAttribute("class").toLowerCase().contains("grid".toLowerCase())
                ? true : false;
    }
}
