package rs.com.compare_products;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import rs.com.test_utils.DriverManager;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.ArrayList;
import java.util.List;

import static rs.com.test_utils.SeleniumDriverHelper.*;

public class ComparePage {
    private WebDriver driver;

    public ComparePage() {
             PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
    }

    public static void checkProductBrand(List<String> productBrand, int numberOfProducts){
        List<String> compareTableProductBrand = new ArrayList<>();
        scrollIntoElementUsingJs(findElementByXpath("//*[contains(@id,'compareBrand')]/div/a"));
        waitForAjax(1000);
        for(int i=1; i<= numberOfProducts; i++){
            compareTableProductBrand.add(findElementByXpath("//*[contains(@id,'compareBrand')]["+i+"]/div/a")
            .getText());
        }
            Assert.assertTrue(compareTableProductBrand.containsAll(productBrand));
    }
}
