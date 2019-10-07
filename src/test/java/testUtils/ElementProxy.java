package testUtils;

import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

public class ElementProxy {
    private final WebElement element;
    private static  WebElement popup;

    public ElementProxy(WebElement element) {
        this.element = element;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //before invoking actual method check for the popup
        this.checkForPopupAndKill(popup);
        //at this point, popup would have been closed if it had appeared. element action can be called safely now.
        Object result = method.invoke(element, args);
        return result;
    }

    private void checkForPopupAndKill(WebElement popup) {
        if (popup.isDisplayed()) {
            System.out.println("You damn popup, you appearded again!!?? I am gonna kill you now!!");
            popup.click();
        }
    }


}
