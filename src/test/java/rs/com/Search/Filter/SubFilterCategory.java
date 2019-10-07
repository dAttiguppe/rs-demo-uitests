package rs.com.Search.Filter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import testUtils.DriverManager;
import testUtils.SeleniumDriverHelper;

import java.util.HashMap;
import java.util.List;

import static rs.com.Search.Filter.FilterTypes.applyFilter;
import static testUtils.SeleniumDriverHelper.*;
import static testUtils.StringUtil.getNumbersFromString;
import static testUtils.StringUtil.getStringOutsideBrackets;

public class SubFilterCategory {

    private WebDriver driver;

    @FindBy(xpath = "//*[contains(@class,'filterCheckboxLabelText')]//span[1]/span[1]")
    private static HtmlElement numberOfCategories;

    private static String subCategoryName =  "//*[contains(@id,'label')]";
    //private static HtmlElement subCategoryName;

    @FindBy(xpath = "//*[contains(@class,'attributesContainer')]/span/div/label")
    private static List<WebElement> subCategoryList;

    private static int randomFilterInt;
    private static WebElement filterCatXpath;
    private static HashMap<String,Object> subFilter = new HashMap<String,Object>();


    public SubFilterCategory() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
        waitForAjax(60000, true, 4000);
    }

    public static HashMap<String,Object> selectSubFilterCategory(int numberOfSubFilters) {
        isAlertPresent();
//        List<WebElement> subFilterCategories = findElements(By.xpath("//div[contains(@ng-repeat,'filterRecord')]"));
//
//        randomFilterInt = getRandomNextInt(subFilterCategories.size()-1);
//
//        if(randomFilterInt==0 || randomFilterInt==1)
//            filterCatXpath = findElementByXpath("//*[contains(@ng-repeat,'filterRecord')][1]");
//        else
//            filterCatXpath = findElementByXpath("//*[contains(@ng-repeat,'filterRecord')]["+randomFilterInt+"]");

        int subFiltersNumber = (getNumbersFromString(findElementByXpath("//div[contains(@class,'popoverMatchesCount')]").getText()));

        for(int i=1;i<=numberOfSubFilters;i++) {
            filterCatXpath = findElementByXpath("//*[contains(@ng-repeat,'filterRecord')]["+numberOfSubFilters+"]");
            subFilter.put("subCatName",getSubCategoryName());
            subFilter.put("subCatNum",getNumberOfCategories());

            SeleniumDriverHelper.waitForPageLoad();

            clickElementUsingJs(filterCatXpath);
        }


        waitForAjax(1000);

        clickElementUsingJs(applyFilter);
        waitForAjax(1000);

        return subFilter;

    }

    public static String getNumberOfCategories() {
         return (getTextByXpath("//*[contains(@id,'label')]").split("[\\(\\)]")[1]);
    }

    public static String getSubCategoryName() {
         return getStringOutsideBrackets(getTextByXpath("//*[contains(@id,'label')]")).trim();
    }


}
