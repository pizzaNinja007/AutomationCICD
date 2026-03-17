package anaysahaiqa;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import anaysahaiqa.pageObjects.LandingPage;

/**
 * StandAloneTest
 * ----------------
 * This class validates the complete purchase flow of an e-commerce application:
 * Login → Product Selection → Add to Cart → Checkout → Order Confirmation
 */
public class StandAloneTest {

    public static void main(String[] args) throws InterruptedException {

        // Test data: Product to be purchased
        String productName = "ZARA COAT 3";

        // Initialize Chrome browser
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Apply implicit wait for element synchronization
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Launch application URL
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        // Initialize Landing Page object (for future extensibility)
        LandingPage landingPage = new LandingPage(driver);

        // -------------------- LOGIN --------------------

        // Enter valid user credentials
        driver.findElement(By.id("userEmail")).sendKeys("test@anay.com");
        driver.findElement(By.id("userPassword")).sendKeys("Abcd1234!");

        // Click Login button
        driver.findElement(By.id("login")).click();

        // -------------------- PRODUCT SELECTION --------------------

        // Fetch all products displayed on the landing page
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        // Filter product list using Java Streams to match the required product
        WebElement selectedProduct = products.stream()
                .filter(product -> product.findElement(By.cssSelector("b"))
                .getText().equals(productName))
                .findFirst()
                .orElse(null);

        // Add the selected product to cart
        selectedProduct.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        // Explicit wait for toast message confirmation
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

        // Wait for loading animation to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));

        // -------------------- CART VALIDATION --------------------

        // Navigate to cart page
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        // Fetch product names present in the cart
        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".infoWrap h3"));

        // Validate that selected product exists in the cart
        Boolean match = cartProducts.stream()
                .anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));

        // Assert cart validation
        Assert.assertTrue(match, "Selected product is not present in the cart");

        // -------------------- CHECKOUT --------------------

        // Proceed to checkout
        driver.findElement(By.cssSelector(".totalRow button")).click();

        // Initialize Actions class for keyboard interactions
        Actions actions = new Actions(driver);

        // Enter country name dynamically
        actions.sendKeys(
                driver.findElement(By.cssSelector("[placeholder='Select Country']")),
                "india")
                .build()
                .perform();

        // Wait for country suggestions dropdown
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

        // Select second country option from suggestions
        driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();

        // Submit the order
        driver.findElement(By.cssSelector(".action__submit")).click();

        // -------------------- ORDER CONFIRMATION --------------------

        // Capture confirmation message
        String confirmationMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();

        // Validate order success message
        Assert.assertTrue(
                confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."),
                "Order confirmation message mismatch"
        );

        // Close browser
        driver.close();
    }
}
