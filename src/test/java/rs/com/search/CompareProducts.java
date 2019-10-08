package rs.com.search;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import testUtils.DriverManager;

public class CompareProducts {

    private WebDriver driver;

    @FindBy(xpath = "//*[contains(@class,'content')]//tr[2]//div[contains(@class,'checkbox-icon')]//input")
    private static CheckBox productOneCompareCheckBox;

    @FindBy(xpath = "//*[contains(@class,'content')]//tr[3]//div[contains(@class,'checkbox-icon')]//input")
    private static CheckBox productTwoCompareCheckBox;

    @FindBy(xpath = "//*[contains(@class,'content')]//tr[2]//td[2]//div/a")
    private static HtmlElement productOneDesc;

    @FindBy(xpath = "//*[contains(@class,'content')]//tr[3]//td[2]//div/a")
    private static HtmlElement productTwoDesc;

    @FindBy(css = "#compareSelectedBtnEnabled")
    private static Button compareButton;



    public static CheckBox getProductOneCompareCheckBox() {
        return productOneCompareCheckBox;
    }

    public static CheckBox getProductTwoCompareCheckBox() {
        return productTwoCompareCheckBox;
    }

    public CompareProducts() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
    }

    public static void addProductsToCompare(){
        productOneCompareCheckBox.select();
        productTwoCompareCheckBox.select();
    }

    public static void clickButton(){
        compareButton.click();
    }

    public static void checkAddedProducts(){

    }








}
