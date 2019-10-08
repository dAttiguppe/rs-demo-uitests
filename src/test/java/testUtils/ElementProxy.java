package testUtils;

import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ElementProxy implements InvocationHandler {
    private final WebElement element;
    private static  WebElement popup;

    public ElementProxy(WebElement element) {
        this.element = element;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        this.checkForPopupAndKill(popup);
        Object result = method.invoke(element, args);
        return result;
    }

    private void checkForPopupAndKill(WebElement popup) {
        if (popup.isDisplayed()) {
            popup.click();
        }
    }


}
