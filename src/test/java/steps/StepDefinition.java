package steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import rs.com.search.common.FillSecureData;
import rs.com.search.common.PriceCalculations;
import rs.com.search.common.QuantityUpdate;
import rs.com.search.filter.FilterTypes;
import rs.com.search.menu.PrimaryNavContainer;
import rs.com.search.pages.*;
import rs.com.search.pojo.SecrurePageData;
import rs.com.search.searchBar.SecureCheckoutPage;
import rs.com.search.searchProduct.PopularProducts;
import rs.com.search.searchProduct.ProductDetailsPage;
import rs.com.search.searchProduct.SearchProduct;
import testDataConstants.TestConstants;
import testUtils.DriverManager;
import testUtils.JsonHelper;
import testUtils.MyPageFactory;
import testUtils.SeleniumDriverHelper;

import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static org.junit.Assume.assumeTrue;
import static rs.com.search.common.QuantityUpdate.getQtyUpdateLink;
import static rs.com.search.filter.FilterTypes.checkAppliedFilterListMatchResults;
import static rs.com.search.filter.FilterTypes.isFilterSectionPresent;
import static rs.com.search.pages.GuestLogin.clickGuestLoginBtn;
import static rs.com.search.searchBar.SecureCheckoutPage.isCheckPageHeaderPresent;
import static rs.com.search.searchProduct.PopularProducts.isProductAddedToBasket;
import static testDataConstants.TestConstants.*;
import static testUtils.DriverManager.embedScreenshot;
import static testUtils.DriverManager.embedScreenshotIfFailed;
import static testUtils.SeleniumDriverHelper.*;

public class StepDefinition {

    private WebDriver driver;
    private static PrimaryNavContainer primaryNavContainer = new PrimaryNavContainer();
    private static double basketAmt;
    private static double goodsTotalPrice;

    public StepDefinition() throws IllegalAccessException {
        this.driver = driver;
        MyPageFactory.initElements(DriverManager.getDriver(), this);
      }

    @Before
    public void initTest() throws Exception {
        initialize();
        DriverManager.getDriver().get(WEBSITE_URL);
        checkAlertPresentAndDismiss();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        embedScreenshot(scenario);
    }

    @After
    public void tearDownTest(Scenario scenario) {
        embedScreenshotIfFailed(scenario);
        tearDown();
    }

    @After
    public void clearCookies() {
        deleteAllCookies();
    }

    @Given("the user selects (.*) from (.*) section")
    public void theUserClicksOnProductsSection(String menuItem, String subMenuItem) throws Exception {
        initTest();
        primaryNavContainer.selectMenuOption(subMenuItem, menuItem);
    }

    @When("the user adds the product to the basket")
    public void theUserAddsTheProductToTheBasket() throws IllegalAccessException {
        if(isElementPresent(By.xpath("//div[contains(@class,'productTab')]"))) {
            PopularProducts popularProducts = new PopularProducts();
            Basket basket = new Basket();

            popularProducts.getProductDesc();
            basketAmt = popularProducts.getProductDblAmt();

            popularProducts.getAddToBasket().click();
            waitForAjax(5000);
            Assert.assertTrue(isProductAddedToBasket());
            Assert.assertTrue(basket.getBasketAmt()==basketAmt);
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

    private void navigateToProductPage() throws IllegalAccessException {
        CategoryResultsPage categoryResultsPage = new CategoryResultsPage();
        ProductTable productTable = new ProductTable();
        //navigateToProductPage(categoryResultsPage);
        addToBasket(productTable);
    }

    private void addToBasket(ProductTable productTable) throws IllegalAccessException{
        int productSelected = productTable.addToBasket();
        productTable.checkBasket(productSelected);
    }

    private static void navigateToProductPage(CategoryResultsPage categoryResultsPage) {
        categoryResultsPage.getCategoryList();
        waitForAjax(2000);
        categoryResultsPage.selectSubCategoryItem();
    }

    @And("clicks on Checkout securely")
    public void clicksOnCheckoutSecurely() throws IllegalAccessException {
        Basket basket = new Basket();
        basket.clickBasket();
        PriceCalculations productPrice = new PriceCalculations();
        goodsTotalPrice = productPrice.getGoodsTotalPrice();

        Assert.assertTrue(basket.getBasketPageTitle().contains(MY_BASKET.toLowerCase()));

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
               // updateQuantity();
                waitForAjax(5000);

                checkIfPopUp();

//                continueAsAGuest.getEmailIDField().click();
//                waitForAjax(1000);
//                continueAsAGuest.getEmailIDField().sendKeys("test@rscomponents.com");
                findElementByXpath("//*[@id=\"guestCheckoutForm:GuestWidgetAction_emailAddress_decorate:GuestWidgetAction_emailAddress\"]").
                        sendKeys(TEST_EMAILID);
                waitForAjax(1000);

                checkAlertPresentAndDismiss();
                waitForAjax(2000);
                clickGuestLoginBtn().click();
                waitForAjax(2000);
                SecureCheckoutPage secureCheckoutPage = new SecureCheckoutPage();
                Assert.assertTrue(isCheckPageHeaderPresent());
            }
        }
    }

    private void updateQuantity() {
        QuantityUpdate qtyUpdate = new QuantityUpdate();

                clickElementUsingJs(qtyUpdate.getQtyUpdateField());
                qtyUpdate.getQtyUpdateField().clear();
                qtyUpdate.getQtyUpdateField().sendKeys("10");
                waitForAjax(2000);
                getQtyUpdateLink().click();
                waitForAjax(2000);

        findElementByXpathAndClickUsingJs("//*[contains(@id,'checkoutSecurelyAndPuchBtn')]");
    }

    @Given("the user searches for (.*) product")
    public void searchProduct(String product) throws Exception {
        initTest();
        SearchProduct searchProduct = new SearchProduct();
        searchProduct.enterSearchTerm(product);
        searchProduct.getDropdownList(product);
    }

    @Then("the product should appear on the checkout page$")
    public void verifyCheckoutPage() {
        SecureCheckoutPage secureCheckoutPage = new SecureCheckoutPage();
        PayPage payPage = new PayPage();
        FillSecureData fillSecureData = new FillSecureData();
        fillSecureData.fillSecureCustomerData(JsonHelper.fromJson(SECUREPAGE_JSON_FILEPATH, SecrurePageData.class));

        Assert.assertTrue(payPage.checkPayPageHeader());
        Assert.assertTrue(payPage.checkTotalPrice()==goodsTotalPrice);
    }

    @When("the user selects (.*) filters")
    public void selectFilterCategory(String filterType, int numberOfFilters, int numberOfSubfilters) throws IllegalAccessException {
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
    public static void theUserSelectsSection(DataTable dataTable) throws IllegalAccessException {

        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        //TODO - Extend it more than 1 row of data table
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
        checkAlertPresentAndDismiss();
        waitForAjax(2000);
    }

}

