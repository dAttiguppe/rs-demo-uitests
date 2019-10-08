package testUtils;

import org.openqa.selenium.WebElement;

import java.lang.reflect.Proxy;

public class ElementGuard {

    public static WebElement guard(WebElement element) {
        ElementProxy proxy = new ElementProxy(element);
        WebElement wrappdElement = (WebElement) Proxy.newProxyInstance(ElementProxy.class.getClassLoader(),
                new Class[] { WebElement.class },
                proxy);
        return wrappdElement;
    }

}
