package rs.com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rs.com.test_utils.DriverManager;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;

import static rs.com.test_utils.SeleniumDriverHelper.clickElementUsingJs;
import static rs.com.test_utils.SeleniumDriverHelper.findElementByXpath;

public class ProductResultGrid {
    private static WebDriver driver;

    @FindBy(xpath="//*[contains(@id,'atbBtn-1')]")
    private static HtmlElement addedToBsktTick;

    @FindBy(xpath="//*[contains(@class,'addToBasketMessageText')]")
    private static HtmlElement clickBasket;

    @FindBy(xpath="//*[contains(@class,'row margin-bottom')]")
    private static HtmlElement productDesc;

    @FindBy(xpath="//*[@id=\"results-table\"]/tbody/tr")
    private static List<WebElement> resultGrid;

    public static boolean checkResults;

    public ProductResultGrid() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
    }

    public static void clickBasketFromResultGrid() {
        clickElementUsingJs(clickBasket);
    }

    public static HtmlElement getAddedToBsktTick() {
        return addedToBsktTick;
    }

    public static List<WebElement> getResultGrid() {
        return resultGrid;
    }

    public static boolean checkResultsMatchFilter(String selectedFilter){
        List<WebElement> resultGridDetails = getResultGrid();
        String productName = findElementByXpath("//*[@id=\"results-table\"]/tbody/tr[1]//div/a").getText();
        String appliedFilter = findElementByXpath("//*[@id=\"bread-box\"]/div[2]/rs-bread-box/div[2]/div[2]/div/div").
                getText();
        return getResultsGridPrdDec(appliedFilter.toLowerCase());
    }

    private static boolean getResultsGridPrdDec(String appliedFilter) {
        for(int j=1;j<resultGrid.size();j++){
            if(resultGrid.get(j).findElement(By.xpath("//*[@id=\"results-table\"]/tbody/tr["+j+"]//div/a")).
                    getText().split(",")[0].toLowerCase().contains(appliedFilter.split(" ")[0])){
                checkResults= true;
            }
            else
                checkResults = false;
        }
        return checkResults;
    }


}
