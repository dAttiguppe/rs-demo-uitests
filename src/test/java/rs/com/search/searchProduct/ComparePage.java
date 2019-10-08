package rs.com.search.searchProduct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import testUtils.DriverManager;

public class ComparePage {
    private WebDriver driver;

    @FindBy(xpath = "//*[contains(@class,'content')]//tr[2]//div[contains(@class,'checkbox-icon')]//input")
    private CheckBox comparedBrand;

    @FindBy(xpath = "//*[contains(@class,'content')]//tr[3]//div[contains(@class,'checkbox-icon')]//input")
    private CheckBox productTwoCompareCheckBox;

    public ComparePage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
    }


}
