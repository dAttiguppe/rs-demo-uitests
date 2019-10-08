package rs.com.test_utils;

import cucumber.api.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.github.bonigarcia.wdm.DriverManagerType.CHROME;
import static rs.com.test_data_constants.TestConstants.CHROME_DRIVER_PATH;
import static rs.com.test_utils.SeleniumDriverHelper.checkAlertPresentAndDismiss;

public class DriverManager {
    private static WebDriver driver;
    private static DesiredCapabilities caps;

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverManager.class);

    public static WebDriver getDriver() {
        return driver;
    }

    public static void init() throws Exception {
        String browser = System.getProperty("browser", "chrome");

        switch (browser) {

            case "chrome":
                driver = createChromeDriver();
                driver.manage().window().maximize();
                break;
            case "IE":
                //driver = initSelenoid();
                break;
            default:
                throw new AssertionError("Invalid browser: " + browser);
        }

    }

    private static void setImmutableData(DesiredCapabilities caps) {

    }

    private static ChromeDriver createChromeDriver() throws Exception {
        WebDriverManager.getInstance(CHROME).setup();
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        return new ChromeDriver();
    }

    public static ChromeOptions chromeDisablePopUps() {
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-popup-blocking");
        options.addArguments("--disable-notifications");

        return options;
    }

    public static void quit() {
        driver = null;
    }

    public static void embedScreenshot(Scenario scenario) {
        scenario.embed(((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BYTES), "image/png");
    }

    public static void embedScreenshotIfFailed(Scenario scenario) {
        if (scenario.isFailed()) {
            embedScreenshot(scenario);
        }
    }

    public WebElement findElement(By arg0) {
        checkAlertPresentAndDismiss();
        return driver.findElement(arg0);
    }

}
