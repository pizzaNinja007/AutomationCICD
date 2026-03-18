package anaysahaiqa.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import anaysahaiqa.pageObjects.LandingPage;

/**
 * BaseTest
 * --------
 * Central test configuration class responsible for:
 * - WebDriver initialization
 * - Browser selection
 * - Application launch
 * - Test teardown
 * - Utility methods (JSON reader, screenshots)
 */
public class BaseTest {

    public WebDriver driver;
    public LandingPage ob;

    /**
     * Initializes WebDriver based on configuration
     */
    public WebDriver initializeDriver() throws IOException {

        
            driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * Reads JSON test data and converts it into List of HashMaps
     */
    public List<HashMap<String, String>> getJsonData(String filePath) throws IOException {

        String jsonContent = FileUtils.readFileToString(
                new File(filePath), StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(
                jsonContent,
                new TypeReference<List<HashMap<String, String>>>() {}
        );
    }

    /**
     * Captures screenshot and returns file path
     */
    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        File destination = new File(
                System.getProperty("user.dir") +
                "//reports//" + testCaseName + ".png"
        );

        FileUtils.copyFile(source, destination);
        return destination.getAbsolutePath();
    }

    /**
     * Launches application before each test
     */
    @BeforeMethod(alwaysRun = true)
    public LandingPage LaunchApplication() throws IOException {
        driver = initializeDriver();
        ob = new LandingPage(driver);
        ob.goTo();
        return ob;
    }

    /**
     * Closes browser after each test
     */
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
