package testUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;

public class MyPageFactory {
    public static <T> void initElements(WebDriver driver, T pageobject) throws IllegalAccessException {

        PageFactory.initElements(DriverManager.getDriver(), pageobject);

        for(Field f:pageobject.getClass().getDeclaredFields()){
            if(f.getType().equals(WebElement.class)){
                boolean accessible = f.isAccessible();
                f.setAccessible(true);
                f.set(pageobject, ElementGuard.guard((WebElement) f.get(pageobject)));
                f.setAccessible(accessible);
            }
        }

    }

}
