package rs.com.filter;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import rs.com.pages.ProductResultGrid;
import rs.com.test_utils.DriverManager;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static org.junit.Assume.assumeTrue;
import static org.slf4j.LoggerFactory.getLogger;
import static rs.com.test_utils.RandomUtil.generateRandomNumInSpecifiedRange;
import static rs.com.test_utils.SeleniumDriverHelper.*;

public class FilterTypes {

    @FindBy(xpath = "//*[contains(@id,'filters')]")
    public TextInput filterSection;

    @FindBy(xpath= "//*[contains(@class,'filters')]//rs-facets/a")
    public static List<WebElement> filterListSize;

    @FindBy(xpath= "//*[contains(@ng-click,'popoverCloseFunction()')]")
    public static HtmlElement closeSubFilterSection;

    public static String getChosenSubCatFilter() {
        return chosenSubCatFilter.getText().toLowerCase();
    }

    @FindBy(xpath= "//*[contains(@class,'appliedAttribute')]")
    public static HtmlElement chosenSubCatFilter;

    @FindBy(xpath= "//*[contains(@class,'popover-content')]//button")
    public static HtmlElement applyFilter;

    @FindBy(xpath= "//*[contains(@id,'bread')]//span[1]")
    public static HtmlElement numberOfProducts;

    @FindBy(xpath= "//*[contains(@class,'popover-content')]//button")
    public static HtmlElement applyFilterBtn;

    @FindBy(xpath= "//*[contains(@class,'popover-content')]//button")
    public static Button applyButton;


    @FindBy(xpath= "//*[@id=\"bread-box\"]/div[2]/rs-bread-box/div[2]/div[2]/div")
    public static List<WebElement> appliedFiltersList;

    private static WebElement filterCatXpath;
    private static int randomFilterInt;

//    public static int getNumberOFProducts() {
//        return numberOFProducts;
//    }

    //private static int numberOFProducts;

    private static String filterXpath ="//*[text()=\"+filterToBeSelected+\"][1]";
    private static final Logger logger = getLogger(FilterTypes.class);

    private static String subFilterSection;
    private static int subFilterSectionNumber;

    private static String selectedFilterName;

    public FilterTypes() throws IllegalAccessException {

        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
        waitForAjax(60000, true, 4000);
    }

    public static Button getApplyButton() {
        return applyButton;
    }

    public static void getFilterList(int numberOfSubFilters){
        if(isElementPresent(By.xpath("//*[contains(@class,'filters')]//rs-facets"))) {
            scrollIntoElementUsingJs(findElementByXpath("//*[contains(@class,'filters')]//rs-facets"));
            waitForAjax(5000);
            //getRandomFilterInt();

            for(int i=0; i<numberOfSubFilters;i++)
                selectRandomFilterCategory(getRandomFilterInt(),numberOfSubFilters);

            //filterList.forEach(s->s.getAttribute());
        }
        else
            assumeTrue("No filters to be selected",false);
    }

    private static int getRandomFilterInt() {
        List<WebElement> filterList = findElements(By.xpath("//*[contains(@class,'filters')]//rs-facets/a"));
        return generateRandomNumInSpecifiedRange(1,filterList.size() - 1);
    }

    private static void selectRandomFilterCategory(int numberOfFilters, int numberOfSubFilters) {
        int totalNumberOfFilters = getFilterListSize().size();

        isAlertPresent();

        if(totalNumberOfFilters > numberOfFilters) {
            for(int i= 1; i<=numberOfFilters; i++) {
                filterCatXpath = findElementByXpath("//*[contains(@class,'filters')]//rs-facets/a[" + i + "]");
                waitForAjax(1000);
                clickElementUsingJs(filterCatXpath);
                SubFilterCategory.selectSubFilterCategory(numberOfSubFilters);
            }
        }
        else if(totalNumberOfFilters==1){
            filterCatXpath = findElementByXpath("//*[contains(@class,'filters')]//rs-facets/a[1]");
            clickElementUsingJs(filterCatXpath);
        }
        else
            assumeTrue("Can't choose specified number of filters",false);

        //clickElementUsingJs(filterCatXpath);
        waitForAjax(2000);

    }

    //Method to choose selected filter from the filter section
    public static void selectChosenFilter(String filterCategory, int numberOfFilters,int numberOfSubFilters){

        if(numberOfFilters > 0) {

            WebElement chosenFilter = findElementByXpath("//a[text()='"+filterCategory+"']");
            SubFilterCategory subFilterCategory = new SubFilterCategory();

               if (isElementPresent(By.xpath("//a[text()='" + filterCategory + "']"))) {
                    //scrollIntoElementUsingJs(chosenFilter);
                    waitForAjax(8000);

                    clickElementUsingJs(chosenFilter);
                    waitForAjax(1000);

                    int numberOfProductsPreFiltering = getNumberOfProducts();

                    HashMap<String, Object> subFilter = SubFilterCategory.selectSubFilterCategory(numberOfSubFilters);

                    waitForAjax(1000);
                    int numberOfProductsPostFiltering = getNumberOfProducts();

                    logger.info("numberOfProductsPostFiltering--" + numberOfProductsPostFiltering);
                    logger.info("subFilterSectionNumber---" + subFilterSectionNumber);

                    Assert.assertTrue((chosenSubCatFilter.getWrappedElement().getText().toLowerCase().
                            contains(getChosenSubCatFilter())));

                    Assert.assertTrue(parseInt(valueOf(subFilter.get("subCatNum"))) == numberOfProductsPostFiltering);

                    checkResults(subFilter);
                } else
                    assumeTrue("No filters to choose", false);

        }
        else
            assumeTrue("Filter not available to select",false);
    }

    private static void checkResults(HashMap<String, Object> subFilter) {
        ProductResultGrid productResultGrid = new ProductResultGrid();
        productResultGrid.checkResultsMatchFilter(String.valueOf(subFilter.get("subCatName")).toLowerCase());
    }

    public static boolean isFilterSectionPresent(){
        return isElementPresent(By.xpath("//*[contains(@class,'filters')]//rs-facets")) ? true :false;
    }

    public static void clickApplyFilter(){
        getApplyFilter().click();
    }

    public static int getNumberOfProducts() {
        return parseInt(numberOfProducts.getText());
    }

    public static HtmlElement getApplyFilter() {
        return applyFilter;
    }

    public static List<WebElement> getFilterListSize() {
        return filterListSize;
    }

    public static void chooseMultipleFilters(){
        int randomFilter = getRandomFilterNumber();
        WebElement randomFilterXpath = findElementByXpath("//*[contains(@class,'filter')]//rs-facets/a["+randomFilter+"]");
    }

    private static int getRandomFilterNumber() {
        List<WebElement> filterFacets = getFilterListSize();
        return generateRandomNumInSpecifiedRange(1,filterFacets.size()-1);
    }

    //TODO - check subfilternumber
    public static void chooseFilter(WebElement randomFilterXpath, int numberOfSubFilters){
        SubFilterCategory subFilterCategory = new SubFilterCategory();
        waitForAjax(8000);
        clickElementUsingJs(randomFilterXpath);
        waitForAjax(1000);
        int numberOfProductsPreFiltering = getNumberOfProducts();
        //TODO - check the subfilternumber
        HashMap<String,Object> subFilter = SubFilterCategory.selectSubFilterCategory(numberOfSubFilters);
        waitForAjax(1000);
        String subFilterSection = subFilterCategory.getSubCategoryName().toLowerCase();
        int subFilterSectionNumber = parseInt(valueOf(subFilter.get("subCatNum")));

        clickElementUsingJs(applyFilter);
        waitForAjax(1000);

        //clickElementUsingJs(closeSubFilterSection);
        int numberOfProductsPostFiltering = getNumberOfProducts();

        logger.info("numberOfProductsPostFiltering--"+numberOfProductsPostFiltering);
        logger.info("subFilterSectionNumber---"+subFilterSectionNumber);

        Assert.assertTrue((chosenSubCatFilter.getWrappedElement().getText().toLowerCase().
                contains(getChosenSubCatFilter())));

        Assert.assertTrue(parseInt(valueOf(subFilter.get("subCatNum")))==numberOfProductsPostFiltering);

    }


    public static void selectFilter(String filterType, int numberOfFilters, int numberOfSubFilters) {

        switch (filterType) {
            case "Random":
                logger.info("Inside random");
                selectRandomFilterCategory(numberOfFilters, numberOfSubFilters);
                break;
            default:
                    selectChosenFilter(filterType, numberOfFilters, numberOfSubFilters);
                    selectedFilterName = filterType;
                break;

        }

    }

    public static List<WebElement> getAppliedFiltersList() {
        return appliedFiltersList;
    }

    public static void checkAppliedFilterListMatchResults(){
        List<String> appliedFiltersListSection = new ArrayList<>();
        ProductResultGrid productResultGrid = new ProductResultGrid();
        for(int i=0;i< getAppliedFiltersList().size()-1;i++){
            appliedFiltersListSection.add(getAppliedFiltersList().get(i).getText());
        }

        Assert.assertTrue("Filtered results dont match the selected filter",productResultGrid.checkResultsMatchFilter(selectedFilterName));


    }
}
