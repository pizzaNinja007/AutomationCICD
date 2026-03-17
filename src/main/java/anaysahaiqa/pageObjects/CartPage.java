package anaysahaiqa.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import anaysahaiqa.AbstractComponents.AbstractComponents;

/**
 * CartPage
 * ---------
 * This class models the Cart Page of the application.
 * It provides methods to:
 * - Validate products added to the cart
 * - Proceed to checkout
 *
 * Extends AbstractComponents to inherit common utilities
 * such as waits and global navigation actions.
 */
public class CartPage extends AbstractComponents {

    // WebDriver instance
    WebDriver driver;

    /**
     * Constructor
     * Initializes WebDriver, PageFactory elements,
     * and parent AbstractComponents.
     *
     * @param driver WebDriver instance from test class
     */
    public CartPage(WebDriver driver) {
        super(driver);              // Initialize parent class
        this.driver = driver;       // Assign local driver
        PageFactory.initElements(driver, this);
    }

    // -------------------- PAGE ELEMENTS --------------------

    // List of product titles displayed in the cart
    @FindBy(css = ".infoWrap h3")
    List<WebElement> productTitles;

    // Checkout button at the bottom of the cart page
    @FindBy(css = ".totalRow button")
    WebElement checkoutButton;

    // -------------------- PAGE ACTIONS --------------------

    /**
     * Verifies whether the specified product is displayed in the cart.
     *
     * @param productName Name of the product to be verified
     * @return true if product exists in cart, false otherwise
     */
    public Boolean verifyProductDisplay(String productName) {
        return productTitles.stream()
                .anyMatch(cartProduct ->
                        cartProduct.getText().equalsIgnoreCase(productName));
    }

    /**
     * Clicks on the Checkout button and navigates to CheckoutPage.
     *
     * @return CheckoutPage instance
     */
    public CheckoutPage goToCheckout() {
        checkoutButton.click();
        return new CheckoutPage(driver);
    }
}
