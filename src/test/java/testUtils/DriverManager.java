package testUtils;

import cucumber.api.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.github.bonigarcia.wdm.DriverManagerType.CHROME;

public class DriverManager {
    private static WebDriver driver;
    private static String username = System.getenv("SAUCE_USERNAME");
    private static String accessKey = System.getenv("SAUCE_ACCESS_KEY");
    private static String name = System.getenv("JOB_NAME");
    private static String owner = System.getenv("BUILD_USER");
    private static String build = System.getenv("BUILD_NUMBER");
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
        caps.setCapability("username", username);
        caps.setCapability("accessKey", accessKey);
        caps.setCapability("name", name);
        caps.setCapability("build", build);
        caps.setCapability("owner", owner);

    }

    private static ChromeDriver createChromeDriver() throws Exception {
//        WebDriverManager.getInstance(CHROME).setup();
//        System.setProperty("chrome","C://Program Files (x86)//chromDriver.exe");

        WebDriverManager.getInstance(CHROME).setup();
        //WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver","C:\\Program Files\\chromeDriver\\chromedriver.exe");
//        driver.get("https://uk.rs-online.com/web/");
        return new ChromeDriver();
    }

    public static ChromeOptions chromeDisablePopUps() {
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        //prefs = {"profile.default_content_setting_values.notifications" : 2}
        //options.setUnhandledPromptBehaviour("dom.webnotifications.enabled",false);
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


}
