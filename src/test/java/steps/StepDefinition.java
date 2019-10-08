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
import org.openqa.selenium.support.PageFactory;
import rs.com.common.FillSecureData;
import rs.com.common.PriceCalculations;
import rs.com.common.QuantityUpdate;
import rs.com.common.SecureCheckoutPage;
import rs.com.filter.FilterTypes;
import rs.com.login.GuestLogin;
import rs.com.menu.PrimaryNavContainer;
import rs.com.pages.*;
import rs.com.pojo.SecrurePageData;
import rs.com.search_product.PopularProducts;
import rs.com.search_product.ProductDetailsPage;
import rs.com.search_product.SearchProduct;
import rs.com.switch_view.SwitchView;
import rs.com.test_data_constants.TestConstants;
import rs.com.test_utils.DriverManager;
import rs.com.test_utils.JsonHelper;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static org.junit.Assume.assumeTrue;
import static rs.com.common.QuantityUpdate.getQtyUpdateLink;
import static rs.com.common.SecureCheckoutPage.isCheckPageHeaderPresent;
import static rs.com.compare_products.CompareProducts.*;
import static rs.com.filter.FilterTypes.checkAppliedFilterListMatchResults;
import static rs.com.filter.FilterTypes.isFilterSectionPresent;
import static rs.com.login.GuestLogin.clickGuestLoginBtn;
import static rs.com.search_product.PopularProducts.isProductAddedToBasket;
import static rs.com.switch_view.SwitchView.*;
import static rs.com.test_data_constants.TestConstants.*;
import static rs.com.test_utils.DriverManager.embedScreenshot;
import static rs.com.test_utils.DriverManager.embedScreenshotIfFailed;
import static rs.com.test_utils.SeleniumDriverHelper.*;

public class StepDefinition {

    private WebDriver driver;
    private static PrimaryNavContainer primaryNavContainer = new PrimaryNavContainer();
    private static double basketAmt;
    private static double goodsTotalPrice;

    public StepDefinition(){
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
      }

    @Before
    public void initTest() throws Exception {
        initialize();
        DriverManager.getDriver().get(WEBSITE_URL);
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
                waitForAjax(5000);
                findElementByXpath("//*[@id=\"guestCheckoutForm:GuestWidgetAction_emailAddress_decorate:GuestWidgetAction_emailAddress\"]").
                        sendKeys(TEST_EMAILID);
                waitForAjax(1000);

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
        if(!(findElementByXpath("//*[contains(@class,'disabled')]")
                .getAttribute("class").contains("disabled"))) {
            if (isFilterSectionPresent()) {
                FilterTypes filterTypes = new FilterTypes();
                if (filterType.equalsIgnoreCase("multiple")) {
                    filterTypes.chooseMultipleFilters();
                } else
                    filterTypes.selectChosenFilter(filterType, numberOfFilters, numberOfSubfilters);

            }
        }
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

        waitForAjax(1000);
        navigateToAddToBasketPage();
        if (isFilterSectionPresent()) {
            FilterTypes filterTypes = new FilterTypes();
            filterTypes.selectFilter(filterSelection, numberOfFilters, numberOfSubFilters);
        }
    }

    private static void navigateToAddToBasketPage() {
        CategoryResultsPage categoryResultsPage = new CategoryResultsPage();
        navigateToProductPage(categoryResultsPage);
        waitForAjax(2000);
    }

    @And("the user adds the product to the compare section")
    public void addToCompareSection() {
        waitForAjax(1000);
        addProductsToCompare(2);
        clickButton();
        waitForAjax(1000);
        checkAddedProducts();
    }

    @And("the user switched to grid view")
    public void checkSwitchView() {
        switchToGridView();
    }

    @Then("the results should be displayed in a grid")
    public void checkGridViewResults() {
        SwitchView switchView = new SwitchView();
        Assert.assertTrue(checkGridViewDisplayed());
        Assert.assertTrue(getGridViewResultsDisplay());
    }
}

