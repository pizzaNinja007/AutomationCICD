package anaysahaiqa.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import anaysahaiqa.AbstractComponents.AbstractComponents;

/**
 * ProductCatalogue
 * -----------------
 * This class models the Product Catalogue page that displays
 * all available products after successful user login.
 *
 * It provides methods to:
 * - Fetch product list
 * - Identify products by name
 * - Add products to the shopping cart
 *
 * Extends AbstractComponents for common synchronization utilities.
 */
public class ProductCatalogue extends AbstractComponents {

    // WebDriver instance
    WebDriver driver;

    /**
     * Constructor
     * Initializes WebDriver, PageFactory elements,
     * and parent AbstractComponents.
     *
     * @param driver WebDriver instance from LandingPage
     */
    public ProductCatalogue(WebDriver driver) {
        super(driver);             // Initialize parent class
        this.driver = driver;      // Assign local driver
        PageFactory.initElements(driver, this);
    }

    // -------------------- PAGE ELEMENTS --------------------

    // List of all product cards displayed on the page
    @FindBy(css = ".mb-3")
    List<WebElement> products;

    // -------------------- LOCATORS FOR SYNCHRONIZATION --------------------

    // Locator for product list container
    By productsBy = By.cssSelector(".mb-3");

    // Locator for success toast message after adding product to cart
    By toastContainer = By.cssSelector("#toast-container");

    // Locator for loading animation displayed during cart updates
    By animation = By.cssSelector(".ng-animating");

    // -------------------- PAGE ACTIONS --------------------

    /**
     * Retrieves the list of products displayed on the catalogue page.
     *
     * @return List of product WebElements
     */
    public List<WebElement> getProductsList() {
        waitForElementToAppear(productsBy);
        return products;
    }

    /**
     * Finds and returns a product WebElement based on the product name.
     *
     * @param productName Name of the product to search for
     * @return WebElement representing the product, or null if not found
     */
    public WebElement getProductByName(String productName) {
        return products.stream()
                .filter(product ->
                        product.findElement(By.cssSelector("b"))
                                .getText().equals(productName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds the specified product to the cart.
     * Handles synchronization for toast message and loading animation.
     *
     * @param productName Name of the product to be added to the cart
     */
    public void addProductToCart(String productName) {
        WebElement product = getProductByName(productName);

        // Click Add to Cart button for the selected product
        product.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        // Wait for confirmation toast and animation to complete
        waitForElementToAppear(toastContainer);
        waitForElementToDisappear(animation);
    }
}
