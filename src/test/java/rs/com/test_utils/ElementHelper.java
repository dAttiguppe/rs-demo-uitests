package rs.com.test_utils;

import org.openqa.selenium.WebElement;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

public class ElementHelper {

    public static String getTextFromElement(HtmlElement element) {
        return element.getText();
    }

    public static String getTextFromElement(WebElement element) {
        return element.getText();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}