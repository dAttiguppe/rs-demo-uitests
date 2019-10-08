package steps;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:reports", "json:reports/cucumber.json"},
        features = "src/test/resources/features/",
        glue = {"classpath:steps/"},
        tags = "@smokeTest")
public class Runner {
}