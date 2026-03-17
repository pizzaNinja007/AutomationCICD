package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestNGTestRunner
 * ----------------
 * This class serves as the Cucumber TestNG runner.
 *
 * It allows execution of Cucumber feature files
 * using TestNG framework and enables reporting,
 * step definition mapping, and console readability.
 */
@CucumberOptions(
        // Path to all Cucumber feature files
        features = "src/test/java/cucumber",

        // Package containing step definition classes
        glue = "anaysahaiqa.stepDefinitions",

        // Improves console output readability
        monochrome = true,

        // Cucumber report configuration
        plugin = {
                "html:target/cucumber.html"
        }
)
public class TestNGTestRunner extends AbstractTestNGCucumberTests {

    // No implementation required
    // AbstractTestNGCucumberTests handles execution lifecycle

}
