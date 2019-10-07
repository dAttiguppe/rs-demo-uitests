package Steps;

import TestConstants.TestConstants;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import rs.com.Search.Filter.FilterTypes;
import rs.com.Search.Menu.PrimaryNavContainer;
import rs.com.Search.SearchBar.SecureCheckoutPage;
import rs.com.Search.common.FillSecureData;
import rs.com.Search.common.PriceCalculations;
import rs.com.Search.common.QuantityUpdate;
import rs.com.Search.pages.*;
import rs.com.Search.pojo.SecrurePageData;
import rs.com.Search.searchProduct.PopularProducts;
import rs.com.Search.searchProduct.ProductDetailsPage;
import rs.com.Search.searchProduct.SearchProduct;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import testUtils.DriverManager;
import testUtils.JsonHelper;
import testUtils.SeleniumDriverHelper;

import java.util.List;
import java.util.Map;

import static TestConstants.TestConstants.MY_BASKET;
import static TestConstants.TestConstants.SECUREPAGE_JSON_FILEPATH;
import static java.lang.Integer.parseInt;
import static org.junit.Assume.assumeTrue;
import static rs.com.Search.Filter.FilterTypes.checkAppliedFilterListMatchResults;
import static rs.com.Search.Filter.FilterTypes.isFilterSectionPresent;
import static rs.com.Search.SearchBar.SecureCheckoutPage.isCheckPageHeaderPresent;
import static rs.com.Search.pages.Basket.clickBasket;
import static rs.com.Search.pages.Basket.getBasketPageTitle;
import static rs.com.Search.pages.GuestLogin.clickGuestLoginBtn;
import static rs.com.Search.searchProduct.PopularProducts.isProductAddedToBasket;
import static testUtils.SeleniumDriverHelper.*;

public class StepDefinition {

    private WebDriver driver;
    private static PrimaryNavContainer primaryNavContainer = new PrimaryNavContainer();
    private static double basketAmt;
    private static double goodsTotalPrice;





    public StepDefinition() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
        //SeleniumDriverHelper.waitForAjax(60000, true, 4000);
    }


    @Before
    public void initTest() throws Exception {
        initialize();
        DriverManager.getDriver().get("https://uk.rs-online.com/web/");

    }

    public boolean checkIfPopUp(){
        try
        {
            DriverManager.getDriver().switchTo().alert().dismiss();
            return true;
        }   // try
        catch (NoAlertPresentException Ex)
        {
            return false;
        }   // catch
    }

    @Given("^sample feature file is ready$")
        public void givenStatment() throws Exception {

        initTest();

            System.out.println("Given statement executed successfully");
        }

    @Given("the user selects (.*) from (.*) section")
    public void theUserClicksOnProductsSection(String menuItem, String subMenuItem) throws Exception {
        initTest();
        primaryNavContainer.selectMenuOption(subMenuItem, menuItem);
    }

    @When("the user adds the product to the basket")
    public void theUserAddsTheProductToTheBasket() {
        if(isElementPresent(By.xpath("//div[contains(@class,'productTab')]"))) {
            PopularProducts popularProducts = new PopularProducts();
            Basket basket = new Basket();

            popularProducts.getProductDesc();
            basketAmt = popularProducts.getProductDblAmt();

            popularProducts.getAddToBasket().click();
            waitForAjax(5000);
            Assert.assertTrue(isProductAddedToBasket());
            //Assert.assertTrue(basket.getBasketAmt()==basketAmt);
        }

        else if(isElementPresent(By.xpath("//*[contains(@class,'key')]//span"))){
        if(getTextByXpath("//*[contains(@class,'key')]//span").toLowerCase().contains("stock")){
            ProductDetailsPage productDetailsPage = new ProductDetailsPage();
            productDetailsPage.getProductNameSearchPg();

            productDetailsPage.clickAddToBasket();

            Assert.assertTrue(productDetailsPage.getAddedToBasketText().contains("Added".toLowerCase()));
        }
        }
        //Navigates into final category page to add the product to basket
        else {
            navigateToProductPage();
        }
    }

    private void navigateToProductPage() {
        CategoryResultsPage categoryResultsPage = new CategoryResultsPage();
        ProductTable productTable = new ProductTable();

        navigateToProductPage(categoryResultsPage);

        addToBasket(productTable);
    }

    private void addToBasket(ProductTable productTable) {
        int productSelected = productTable.addToBasket();
        productTable.checkBasket(productSelected);
    }

    private static void navigateToProductPage(CategoryResultsPage categoryResultsPage) {
        categoryResultsPage.getCategoryList();
        categoryResultsPage.selectSubCategoryItem();
    }

    @And("clicks on Checkout securely")
    public void clicksOnCheckoutSecurely() {

        Basket basket = new Basket();
        //scrollIntoElementUsingJs(basket);
        clickBasket();
        PriceCalculations productPrice = new PriceCalculations();
        goodsTotalPrice = productPrice.getGoodsTotalPrice();

        Assert.assertTrue(getBasketPageTitle().contains(MY_BASKET.toLowerCase()));

        if(basket.isErrorMessagePresent())
            assumeTrue(TestConstants.AVAILABLE_TO_BACK_ORDER_WHEN_STOCK_IS_AVAILABLE, false);
        else if(basket.getProductNtAvlbl()){
            assumeTrue(TestConstants.ERROR_MESSAGE,false);
        }
        else
        {

            basket.clickChkOutSecBtn();
            GuestLogin continueAsAGuest = new GuestLogin();
            if(continueAsAGuest.getGuestLoginModal())
            {
                SeleniumDriverHelper.checkAlertPresentAndAccept();
                QuantityUpdate qtyUpdate = new QuantityUpdate();

//                clickElementUsingJs(qtyUpdate.getQtyUpdateField());
//                qtyUpdate.getQtyUpdateField().clear();
//                qtyUpdate.getQtyUpdateField().sendKeys("10");
//                waitForAjax(5000);
//                getQtyUpdateLink().click();
//                waitForAjax(5000);

                //findElementByXpathAndClickUsingJs("//*[contains(@id,'checkoutSecurelyAndPuchBtn')]");
                waitForAjax(5000);

                checkIfPopUp();

//                continueAsAGuest.getEmailIDField().click();
//                waitForAjax(1000);
//                continueAsAGuest.getEmailIDField().sendKeys("test@rscomponents.com");
                findElementByXpath("//*[@id=\"guestCheckoutForm:GuestWidgetAction_emailAddress_decorate:GuestWidgetAction_emailAddress\"]").sendKeys("te@te.com");
                waitForAjax(1000);
                //clickAndHoldSendKeys(GUESTEMAILID);

                checkAlertPresentAndDismiss();
                //continueAsAGuest.getEmailIDField().sendKeys("test@rscomponents.com");
                waitForAjax(5000);
                //actions.click(clickGuestLoginBtn());

                clickGuestLoginBtn().click();

                waitForAjax(5000);
                SecureCheckoutPage secureCheckoutPage = new SecureCheckoutPage();
                Assert.assertTrue(isCheckPageHeaderPresent());
            }
        }

    }

    @Given("the user searches for (.*) product")
    public void searchProduct(String product) throws Exception {
        initTest();
        SearchProduct searchProduct = new SearchProduct();
        searchProduct.enterSearchTerm(product);
        searchProduct.getDropdownList(product);
    }

    @Then("the product should appear on the checkout page")
    public void verifyCheckoutPage() {
        SecureCheckoutPage secureCheckoutPage = new SecureCheckoutPage();
        PayPage payPage = new PayPage();

        FillSecureData fillSecureData = new FillSecureData();
        fillSecureData.fillSecureCustomerData(JsonHelper.fromJson(SECUREPAGE_JSON_FILEPATH, SecrurePageData.class));

        Assert.assertTrue(payPage.checkPayPageHeader());
        Assert.assertTrue(payPage.checkTotalPrice()==goodsTotalPrice);

    }

    @When("the user selects (.*) filters")
    public void selectFilterCategory(String filterType, int numberOfFilters, int numberOfSubfilters) {
        navigateToAddToBasketPage();

        if (isFilterSectionPresent()){
            FilterTypes filterTypes = new FilterTypes();
            if(filterType.equalsIgnoreCase("multiple")){
                filterTypes.chooseMultipleFilters();
            }
            else
                filterTypes.selectChosenFilter(filterType,numberOfFilters,numberOfSubfilters);
            //filterTypes.getFilterList();

        }//selectFilterType(filterType);

        else
            assumeTrue("No filters available to select",false);
    }

    @And("the filtered results should display")
    public void checkResultsPage() {
       ProductResultGrid productResultGrid = new ProductResultGrid();
        checkAppliedFilterListMatchResults();
    }

    @When("the user selects filters section")
    public static void theUserSelectsSection(DataTable dataTable) {
//        FilterTypes filterTypes = new FilterTypes();
//        FilterTransformer filterTransformer = new FilterTransformer();
//        //filterTransformer.configureTypeRegistry(dataTable.);

//        String filterSelection;
//        int numberOfFilters;
//        int numberOfSubFilters;


        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
//        list.size();
//        for(int i=0; i<list.size();i++) {
           String filterSelection = list.get(0).get("filterType");
           int numberOfFilters = parseInt(list.get(0).get("numberOfFilters"));
           int numberOfSubFilters = parseInt(list.get(0).get("subFilter"));

        navigateToAddToBasketPage();

        if (isFilterSectionPresent()) {
            FilterTypes filterTypes = new FilterTypes();
            filterTypes.selectFilter(filterSelection, numberOfFilters, numberOfSubFilters);
        }


    }

    private static void navigateToAddToBasketPage() {
        CategoryResultsPage categoryResultsPage = new CategoryResultsPage();
        navigateToProductPage(categoryResultsPage);
        waitForAjax(5000);
    }



}

