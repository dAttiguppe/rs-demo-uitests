package testUtils;

import com.google.common.base.Function;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static testUtils.ElementHelper.getInnerHtml;
import static testUtils.ElementHelper.getTextFromElement;

//import tc.elements.external.pages.ExternalPage;
//import test.java.testutils.DriverManager;

//import static tc.utils.ElementHelper.getInnerHtml;
//import static tc.utils.ElementHelper.getTextFromElement;
//import static tc.utils.SystemPropertyHelper.isTeardown;

    public class SeleniumDriverHelper {

        private static String mainWindowHandle;

        public static void setMainWindowHandle(String mainWindow) {
            mainWindowHandle = mainWindow;
        }

        public static void waitForAjax(long maximumWaitMiliseconds, boolean doubleCheckAjaxRequest) {
            WebDriver driver = DriverManager.getDriver();
            long endTime = System.currentTimeMillis() + maximumWaitMiliseconds;

            while (true) {
                long currentTime = System.currentTimeMillis();

                if (currentTime > endTime) {
                    break;
                }

                Boolean ajaxIsComplete = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0");

                if (ajaxIsComplete && doubleCheckAjaxRequest) {
                    requiredWait(100);
                    doubleCheckAjaxRequest = false;
                } else if (ajaxIsComplete) {
                    requiredWait(100);
                    break;
                }

                requiredWait(100);
            }
        }

        public static void waitForAjax(long maximumWaitMiliseconds) {
            waitForAjax(maximumWaitMiliseconds, false);
        }

        public static void requiredWait(long waitTimeOut) {
            try {
                Thread.sleep(waitTimeOut);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void implicitWait(long time) {
            WebDriver driver = DriverManager.getDriver();
            driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
        }

        public static void waitForPageLoad() {
            WebDriver driver = DriverManager.getDriver();
            WebDriverWait wait = new WebDriverWait(driver, 3000);

            Predicate<WebDriver> pageLoaded = input -> ((JavascriptExecutor) input).executeScript("return document.readyState").equals("complete");
            wait.until((Function<? super WebDriver, Boolean>) pageLoaded::test);
            requiredWait(5000);
        }

        public static void waitForAjax(long maximumWaitMiliseconds, boolean doubleCheckAjaxRequest, long timeoutForPrechekcingAjax) {
            requiredWait(timeoutForPrechekcingAjax);
            waitForAjax(maximumWaitMiliseconds, doubleCheckAjaxRequest);
        }

        public static void clickOnEmptySpace(WebDriver driver) {
            driver.findElement(By.cssSelector("#masthead-logo")).click();
            requiredWait(1000);
        }

        public static void scrollToWebElement(WebElement element) {
            if (!element.isDisplayed()) {
                scroll(element);
            }
        }

        public static void scrollToElementWithoutCheckingVisibility(WebElement element) {
            scroll(element);
        }

        private static void scroll(WebElement element) {
            Actions actions = new Actions(DriverManager.getDriver());
            actions.moveToElement(element);
            Action action = actions.build();
            action.perform();
        }

        public static void scrollToElement(WrapsElement element) {
            scrollToWebElement(element.getWrappedElement());
            requiredWait(500);
        }

        public static void switchWindow() {
            setMainWindowHandle(getWindowHandle());
            Set<String> windowHandles = getWindowHandles();

            for (String popupHandle : windowHandles) {
                if (!popupHandle.contains(mainWindowHandle)) {
                    switchToWindow(popupHandle);
                }
            }
        }

        public static void switchToMainWindow() {
            switchToWindow(mainWindowHandle);
        }

        public static void switchToWindow(String nameOrHandler) {
            DriverManager.getDriver().switchTo().window(nameOrHandler);
        }

//        public static ExternalPage swtichToExternalPage (HtmlElement element) {
//            element.click();
//            Pause.pause(1000);
//            SeleniumDriverHelper.switchWindow();
//            return new ExternalPage();
//        }

        public static String getWindowHandle() {
            return DriverManager.getDriver().getWindowHandle();
        }

        public static Set<String> getWindowHandles() {
            return DriverManager.getDriver().getWindowHandles();
        }

        public static void closeCurrentWindow() {
            DriverManager.getDriver().close();
        }

        public static void acceptAlert() {
            WebDriver driver = DriverManager.getDriver();
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }

        public static void dismissAlert() {
            WebDriver driver = DriverManager.getDriver();
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        }

        public static void checkAlertPresentAndAccept() {
            if (isAlertPresent()) {
                acceptAlert();
            }
        }

        public static void checkAlertPresentAndDismiss() {
            if (isAlertPresent()) {
                dismissAlert();
            }
        }

        public static boolean isAlertPresent() {
            return isAlertPresent(3);
        }

        public static boolean isAlertPresent(long seconds) {
            boolean isAlertPresent;

            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), seconds);

            try {
                wait.until(ExpectedConditions.alertIsPresent());
                isAlertPresent = true;
            } catch (TimeoutException e) {
                isAlertPresent = false;
            }

            return isAlertPresent;
        }

        public static boolean isElementPresent(By by) {
            boolean isElementPresent;
            WebDriver driver = DriverManager.getDriver();

            try {
                driver.findElement(by);
                isElementPresent = true;
            } catch (NoSuchElementException e) {
                isElementPresent = false;
            }

            return isElementPresent;
        }

        public static WebElement getElementFromListByText(By by, String text) {
            WebDriver driver = DriverManager.getDriver();
            List<WebElement> buttons = driver.findElements(by);

            WebElement button = null;

            for (WebElement b : buttons) {
                if (text.equals(b.getText())) {
                    button = b;
                    break;
                }
            }

            return button;
        }

        public static void waitUntilVisibilityOfElementLocatedByCss(String locator) {
            waitUntilVisibilityOfElementLocated(By.cssSelector(locator));
        }

        public static boolean isElementVisibleAfterWaitLocatedByCss(String locator) {
            boolean isElementVisible;

            try {
                waitUntilVisibilityOfElementLocated(By.cssSelector(locator));
                isElementVisible = true;
            } catch (TimeoutException e) {
                isElementVisible = false;
            }

            return isElementVisible;
        }

        public static void waitUntilVisibilityOfElementLocatedByXpath(String locator) {
            waitUntilVisibilityOfElementLocated(By.xpath(locator));
        }

        public static void waitUntilVisibilityOfElementLocatedByCss(String locator, long time) {
            waitUntilVisibilityOfElementLocated(By.cssSelector(locator), time);
        }

        public static void waitUntilVisibilityOfElementLocatedByCssAndClick(String locator, long time) {
            waitUntilVisibilityOfElementLocated(By.cssSelector(locator), time);
            findElement(By.cssSelector(locator)).click();
        }

        public static void waitUntilVisibilityOfElementLocated(By by) {
            waitUntilVisibilityOfElementLocated(by, 75);
        }

        public static void waitUntilVisibilityOfElementLocated(By by, long time) {
            WebDriver driver = DriverManager.getDriver();
            new WebDriverWait(driver, time).
                    until(ExpectedConditions.visibilityOfElementLocated(by));
        }

        public static WebElement waitForElementToBeClickable(By locator) {
            return waitForElementToBeClickable(locator, 20);
        }

        public static WebElement waitForElementToBeClickable(By locator, long time) {
            WebDriver driver = DriverManager.getDriver();
            return new WebDriverWait(driver, time).until(ExpectedConditions.elementToBeClickable(locator));
        }

        public static WebElement waitForElementToBeClickable(WebElement element, long time) {
            WebDriver driver = DriverManager.getDriver();
            return new WebDriverWait(driver, time).until(ExpectedConditions.elementToBeClickable(element));
        }

        public static WebElement waitForElementToBeClickable(HtmlElement element) {
            return waitForElementToBeClickable(element, 75);
        }

        public static void waitForElementToBeClickableAndClick(HtmlElement element) {
            waitForElementToBeClickable(element).click();
        }

        public static void switchToFrameWithNameOrId(String frameNameOrId) {
            WebDriver driver = DriverManager.getDriver();
            driver.switchTo().frame(frameNameOrId);
        }

        public static void switchToDefaultContent() {
            WebDriver driver = DriverManager.getDriver();
            driver.switchTo().defaultContent();
        }

        public static void scrollToElementUsingJs(WebElement element) {
            runScriptUsingJs("arguments[0].scrollIntoView();", element);
        }

        public static void scrollIntoElementUsingJs(WebElement element) {
            runScriptUsingJs("arguments[0].scrollIntoView(true);", element);
        }

        public static void scrollIntoElementUsingJsAndClick(WebElement element) {
            scrollIntoElementUsingJs(element);
            element.click();
            requiredWait(1000);
        }

        public static void implicitlyWaitInSeconds(long time) {
            DriverManager.getDriver().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
        }

        public static void waitUntilPresenceOfElementLocated(By by, long time) {
            WebDriver driver = DriverManager.getDriver();
            new WebDriverWait(driver, time).
                    until(ExpectedConditions.presenceOfElementLocated(by));
        }

        public static void waitUntilPresenceOfElementLocated(By by) {
            waitUntilPresenceOfElementLocated(by, 75);
        }

        public static void waitUntilPresenceOfElementLocatedByCss(String selector) {
            waitUntilPresenceOfElementLocatedByCss(selector, 75);
        }

        public static void waitUntilPresenceOfElementLocatedByXpath(String selector) {
            waitUntilPresenceOfElementLocated(By.xpath(selector));
        }

        public static void waitUntilPresenceOfElementLocatedByXpath(String selector, long time) {
            waitUntilPresenceOfElementLocated(By.xpath(selector), time);
        }

        public static void waitUntilPresenceOfElementLocatedByCss(String selector, long time) {
            waitUntilPresenceOfElementLocated(By.cssSelector(selector), time);
        }

        public static WebElement findElement(By by) {
            WebDriver driver = DriverManager.getDriver();
            return driver.findElement(by);
        }

        public static WebElement findElementByCss(String locator) {
            return findElement(By.cssSelector(locator));
        }

        public static WebElement findElementByXpath(String locator) {
            return findElement(By.xpath(locator));
        }

        public static List<WebElement> findElements(By by) {
            WebDriver driver = DriverManager.getDriver();
            return driver.findElements(by);
        }

        public static List<WebElement> findElementsByCss(String locator) {
            return findElements(By.cssSelector(locator));
        }

        public static void findElementByCssAndClick(String locator) {
            findElementByCss(locator).click();
        }

        public static void findElementByXpathAndClick(String locator) {
            findElementByXpath(locator).click();
        }

        public static void quit() {
            DriverManager.getDriver().quit();
            DriverManager.quit();
        }

//        public static void tearDown() {
//            if (isTeardown()) {
//                quit();
//            }
//        }

        public static void initialize() throws Exception {
            if (DriverManager.getDriver() == null) {
                DriverManager.init();


            }

//            WebDriverManager.getInstance(CHROME).setup();
//            WebDriver driver = new ChromeDriver();
//            System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\chromedriver.exe");
//            driver.get("https://uk.rs-online.com/web/");
        }

        public static void getUrl(String url) {
            DriverManager.getDriver().get(url);
        }

        public static void deleteAllCookies() {
            DriverManager.getDriver().manage().deleteAllCookies();
        }

        public static void deleteTempFolders() {
            File file = new File(System.getenv("LOCALAPPDATA")+"\\Temp\\");
            File[] paths = file.listFiles();

            for (File foundFile:paths) {
                if (foundFile.getName().contains("scoped_dir")) {
                    try {
                        FileUtils.deleteDirectory(foundFile);
                    }
                    catch (Exception e) {
                        System.out.println(foundFile+ " not deleted");
                    }
                }
            }
        }

        public static void clickElementUsingJs(WebElement element) {
            runScriptUsingJs("arguments[0].click();", element);
        }

        public static void findElementByCssAndClickUsingJs(String locator) {
            clickElementUsingJs(findElementByCss(locator));
        }

        public static void findElementByXpathAndClickUsingJs(String locator) {
            waitUntilPresenceOfElementLocatedByXpath(locator, 75);
            clickElementUsingJs(findElementByXpath(locator));
        }

        private static void runScriptUsingJs(String script, Object... args) {
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            js.executeScript(script, args);
        }

        public static void setValueUsingJs(WebElement element, String value) {
            runScriptUsingJs("arguments[0].value=arguments[1]", element, value);
        }

        public static String getTitle() {
            return DriverManager.getDriver().getTitle();
        }

        public static String getTextByCss(String locator) {
            waitUntilPresenceOfElementLocatedByCss(locator, 75);
            return getTextFromElement(findElementByCss(locator));
        }

        public static String getTextByXpath(String locator) {
            waitUntilPresenceOfElementLocatedByXpath(locator, 75);
            return getTextFromElement(findElementByXpath(locator));
        }

        public static String getInnerHtmlByCss(String locator) {
            return getInnerHtml(findElementByCss(locator));
        }

        public static List<WebElement> findElementsByTagName(String locator) {
            return findElements(By.tagName(locator));
        }

        private static void performAction(Action action) {
            SeleniumDriverHelper.requiredWait(500);
            action.perform();
            SeleniumDriverHelper.requiredWait(500);
        }

        private static Actions getActions() {
            return new Actions(DriverManager.getDriver());
        }

        public static void clickAndHoldSendKeys(String text) {
            performAction(getActions().clickAndHold().release().sendKeys(text).build());
        }

        public static void clickAndHoldSendKeysDelete() {
            performAction(getActions().clickAndHold().release().sendKeys(Keys.DELETE).build());
        }

        public static void clickAndHoldSendKeysEnter() {
            performAction(getActions().clickAndHold().release().sendKeys(Keys.ENTER).build());
        }

        public static void clickAndHoldShiftSendKeys(String text) {
            performAction(getActions().clickAndHold().release().keyDown(Keys.SHIFT).sendKeys(text).build());
        }

    }