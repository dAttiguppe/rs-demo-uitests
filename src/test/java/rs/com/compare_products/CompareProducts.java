package rs.com.compare_products;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rs.com.test_utils.DriverManager;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assume.assumeTrue;
import static rs.com.test_utils.SeleniumDriverHelper.*;

public class CompareProducts {

    private WebDriver driver;

    @FindBy(xpath = "//*[contains(@class,'checkbox-icon')]//input[1]")
    private static CheckBox productOneCompareCheckBox;

    @FindBy(xpath = "//*[contains(@class,'content')]//tr[3]//div[contains(@class,'checkbox-icon')]//input")
    private static CheckBox productTwoCompareCheckBox;

    @FindBy(xpath = "//*[contains(@class,'content')]//tr[3]//td[2]//div/a")
    private static HtmlElement productTwoDesc;

    private static String compareButton ="//*[contains(@id,'compareSelectedBtnEnabled')]";

    public static CheckBox getProductOneCompareCheckBox() {
        return productOneCompareCheckBox;
    }

    public static CheckBox getProductTwoCompareCheckBox() {
        return productTwoCompareCheckBox;
    }

    private static int numberOfProducts= 2;

    public CompareProducts() {

        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
    }

    public static void addProductsToCompare(int numberOfProductsToCompare) {
        waitForAjax(1000);
        int numberOfProducts = findElements(By.xpath("//*[@id=\"results-table\"]/tbody/tr")).size();
        if (numberOfProducts > 1) {
            for(int i=1; i<=numberOfProductsToCompare;i++) {
                waitForAjax(1000);
                DriverManager.getDriver().navigate().refresh();
                clickElementUsingJs(findElementByXpath
                        ("//*[@id=\"results-table\"]/tbody/tr[" + i + "]/td[2]/div[6]/input"));
                waitForAjax(1000);
            }
        } else
            assumeTrue("Not enough products to compare", false);
    }

    public static List<String> getProductBrand(){
        List<WebElement> brandListProducts = findElementsByCss("#results-table > tbody > tr");
        List<String> productListBrand = new ArrayList<>();
        for(int i=2; i<=brandListProducts.size(); i++){
            String brandCssLocator = "//*[@id=\"results-table\"]/tbody/tr["+i+"]/td[2]/div[5]/a";
            productListBrand.add(findElementByCss(brandCssLocator).getText());
        }

        return productListBrand;
    }


    public static void clickButton(){
        waitForAjax(1000);
        clickElementUsingJs(findElementByXpath(compareButton));
    }

    public static void checkAddedProducts(){
        ComparePage comparePage = new ComparePage();
        comparePage.checkProductBrand(getProductBrand(), numberOfProducts);

    }








}
