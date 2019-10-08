package rs.com.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rs.com.test_utils.DriverManager;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class BreadCrumb {
    private WebDriver driver;

    @FindBy(xpath = "//ol[contains(@class,'breadcrumb')]")
    public HtmlElement breadcrumb;

    public BreadCrumb() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
    }

    public boolean checkBreadCrumb(String menuItemCat) {
        return breadcrumb.getText().toLowerCase().contains(menuItemCat.toLowerCase()) ? true : false;
    }

    public String getBreadcrumbText() {
        return breadcrumb.getText();
    }

}
