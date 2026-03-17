package anaysahaiqa.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import anaysahaiqa.AbstractComponents.AbstractComponents;

/**
 * OrderPage
 * ----------
 * This class models the Orders Page of the application.
 * It provides functionality to:
 * - Retrieve product names from order history
 * - Validate whether a specific product was successfully ordered
 *
 * Extends AbstractComponents to inherit common waits and navigation utilities.
 */
public class OrderPage extends AbstractComponents {

    // WebDriver instance
    WebDriver driver;

    /**
     * Constructor
     * Initializes WebDriver, PageFactory elements,
     * and parent AbstractComponents.
     *
     * @param driver WebDriver instance from test or navigation
     */
    public OrderPage(WebDriver driver) {
        super(driver);             // Initialize parent class
        this.driver = driver;      // Assign local driver
        PageFactory.initElements(driver, this);
    }

    // -------------------- PAGE ELEMENTS --------------------

    // List of product names displayed in the orders table
    @FindBy(css = "tr td:nth-child(3)")
    List<WebElement> productNames;

    // (Optional) Checkout button – currently unused in this page
    @FindBy(css = ".totalRow button")
    WebElement checkoutButton;

    // -------------------- PAGE ACTIONS --------------------

    /**
     * Verifies whether the specified product is present
     * in the user's order history.
     *
     * @param productName Name of the product to be verified
     * @return true if product exists in orders, false otherwise
     */
    public Boolean verifyOrderDisplay(String productName) {
        return productNames.stream()
                .anyMatch(orderProduct ->
                        orderProduct.getText().equalsIgnoreCase(productName));
    }
}
