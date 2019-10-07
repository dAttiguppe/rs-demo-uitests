package rs.com.Search.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import testUtils.DriverManager;

public class BreadCrumb {
    private WebDriver driver;

    @FindBy(xpath = "//ol[contains(@class,'breadcrumb')]")
    public HtmlElement breadcrumb;

    public BreadCrumb() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
    }

    public boolean checkBreadCrumb(String menuItemCat) {
        System.out.println("checkBreadCrumb---"+breadcrumb.getText());
        return breadcrumb.getText().toLowerCase().contains(menuItemCat.toLowerCase()) ? true : false;
    }


    public String getBreadcrumbText() {
        return breadcrumb.getText();
    }

}
