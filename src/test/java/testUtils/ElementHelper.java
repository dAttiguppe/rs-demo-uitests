package testUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.ArrayList;
import java.util.List;

public class ElementHelper {  public static String getHexColour(String dirtyDataWithColour) {
    String[] hexValue = dirtyDataWithColour.replace("rgb(", "").replace("rgba(", "").replaceAll("\\).*", "").split(",");

    int hexValue1 = Integer.parseInt(hexValue[0]);
    hexValue[1] = hexValue[1].trim();
    int hexValue2 = Integer.parseInt(hexValue[1]);
    hexValue[2] = hexValue[2].trim();
    int hexValue3 = Integer.parseInt(hexValue[2]);

    String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3).toUpperCase();
    return actualColor;
}

    public static String removeTag(String htmlTextElement, String nameOfTagToRemove) {
        String startElemet = nameOfTagToRemove.substring(0, nameOfTagToRemove.length() - 1);
        String endElemet = new StringBuffer(nameOfTagToRemove).insert(1, "/").toString();
        String rezult = htmlTextElement.replaceAll(startElemet + ".+?" + endElemet, "");

        return rezult;
    }

    public static String removeTagStartEnd(String htmlTextElement, String nameOfTagToRemove) {
        String startElemet = nameOfTagToRemove.substring(0, nameOfTagToRemove.length() - 1);
        String endElemet = new StringBuffer(nameOfTagToRemove).insert(1, "/").toString();
        int indexFirstElement = htmlTextElement.indexOf(startElemet);
        int indexLastElement = htmlTextElement.lastIndexOf(endElemet) + endElemet.length();
        String toRemove = htmlTextElement.substring(indexFirstElement, indexLastElement);
        String result = htmlTextElement.replaceAll(toRemove, "");
        return result;
    }

    public static String removeOneTagStartEndWithQuotes(String htmlTextElement, String nameOfTagToRemove) {
        String startElemet = nameOfTagToRemove.substring(0, nameOfTagToRemove.length() - 1);
        String endElemet = new StringBuffer(nameOfTagToRemove).insert(1, "/").toString();
        int indexFirstElement = htmlTextElement.indexOf(startElemet);
        int indexLastElement = htmlTextElement.lastIndexOf(endElemet) + endElemet.length();
        String toRemove = htmlTextElement.substring(indexFirstElement, indexLastElement);
        String result = htmlTextElement.replace(toRemove, "");
        return result;
    }

    public static WebElement getParent(WebElement element) {
        return element.findElement(By.xpath(".."));
    }

    public static WebElement findElementFromParentByXpath(WebElement element, String locator) {
        return getParent(element).findElement(By.xpath(locator));
    }

    public static String getTextContent(HtmlElement element) {
        return element.getAttribute("textContent");
    }

    public static String getTextContent(WebElement element) {
        return element.getAttribute("textContent");
    }

    public static String getInnerHtml(WebElement element) {
        return element.getAttribute("innerHTML");
    }

    public static String getInnerHtml(HtmlElement element) {
        return element.getAttribute("innerHTML");
    }

    public static boolean isClassActive(WebElement element) {
        return getClass(element).contains("active");
    }

    public static boolean isPriceFailed(WebElement element) {
        return getClass(element).contains("priceFailed");
    }

    public static boolean isClassSelected(WebElement element) {
        return getClass(element).contains("selected");
    }

    public static String getClass(WebElement element) {
        return element.getAttribute("class");
    }

    public static String getClass(HtmlElement element) {
        return element.getAttribute("class");
    }

    public static String getValue(WebElement element) {
        return element.getAttribute("value");
    }

//    public static boolean isElementWithTextAvailable(HtmlElement element, String text) {
//        return isElementExists(element) && getTextFromElement(element).equals(text);
//    }
//
//    public static boolean isElementWithTextAvailable(CheckBox element, String text) {
//        return isElementExists(element) && getTextFromElement(element).equals(text);
//    }

//    public static boolean isElementExists(HtmlElement element) {
//        return new DoesElementExistMatcher().matches(element);
//    }

//    public static boolean isElementClickable(HtmlElement element) {
//        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), 75);
//
//        try {
//            wait.until(ExpectedConditions.elementToBeClickable(element.getWrappedElement()));
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

//    public static boolean isElementExistsAndClickable(HtmlElement element) {
//        return isElementClickable(element) && isElementExists(element);
//    }

//    public static boolean isElementsExistsAndClickable(List<HtmlElement> elements) {
//        for (HtmlElement element : elements) {
//            if (!isElementExistsAndClickable(element)) {
//                return false;
//            }
//        }
//
//        return true;
//    }

//    public static boolean isElementsDoesNotExists(List<HtmlElement> elements) {
//        for (HtmlElement element : elements) {
//            if (isElementExists(element)) {
//                return false;
//            }
//        }
//
//        return true;
//    }

//    public static boolean isElementExists(CheckBox element) {
//        return new DoesElementExistMatcher().matches(element);
//    }

    public static String getTextFromElement(HtmlElement element) {
        return element.getText();
    }

//    public static String getTextFromElement(TypifiedElement element) {
//        return element.getText();
//    }

    public static String getTextFromElement(WebElement element) {
        return element.getText();
    }

    public static String getTitle(HtmlElement element) {
        return element.getAttribute("title");
    }

    public static boolean isElementDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    public static String getTrimmedText(HtmlElement element) {
        return element.getText().trim();
    }

    public static String getTrimmedText(WebElement element) {
        return element.getText().trim();
    }

//    public static boolean isTextEmpty(TypifiedElement element) {
//        return getTextFromElement(element).isEmpty();
//    }

    public static String getLowerCaseText(WebElement element) {
        return getTextFromElement(element).toLowerCase();
    }

    public static String getLowerCaseText(HtmlElement element) {
        return getTextFromElement(element).toLowerCase();
    }

    public static double getTextToDouble(HtmlElement element) {
        return Double.parseDouble(getTextFromElement(element));
    }

    public static double getValueToDouble(HtmlElement element) {
        return Double.parseDouble(getValue(element));
    }

    public static boolean isTextContentEqualsInLowCase(HtmlElement element, String text) {
        return getTextContent(element).toLowerCase().equals(text.toLowerCase());
    }

    public static boolean isTextContentEquals(HtmlElement element, String text) {
        return getTextContent(element).equals(text);
    }

    public static boolean isTextContains(HtmlElement element, String text) {
        return getTextFromElement(element).contains(text);
    }

//    public static void checkIfElementsListExists(List<? extends WrapsElement> elements) {
//        for (WrapsElement element : elements) {
//            MatcherAssert.assertThat(element, WrapsElementMatchers.exists());
//        }
//    }

    //TODO: refactor this method
    public static boolean checkItemSelected(List<CheckBox> listCheckBox, String checkedItem) {
        for (CheckBox checkBox : listCheckBox) {
            if (checkBox.getWrappedElement().getAttribute("value").contains(checkedItem)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getTextWebElementList(List<WebElement> elements) {
        List<String> elementsListText = new ArrayList<String>();
        for (WebElement element : elements) {
            elementsListText.add(element.getText());
        }
        return elementsListText;
    }

    public static boolean isClassContainsAttribute(WebElement element, String elementAttribute) {
        return getClass(element).contains(elementAttribute);
    }

    public static void clearSendKeys(HtmlElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}