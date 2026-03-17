package anaysahaiqa.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import anaysahaiqa.AbstractComponents.AbstractComponents;

/**
 * ConfirmationPage
 * -----------------
 * This class models the Order Confirmation Page that appears
 * after a successful order placement.
 *
 * It is responsible for retrieving confirmation-related details
 * to validate order completion.
 */
public class ConfirmationPage extends AbstractComponents {

    // WebDriver instance
    WebDriver driver;

    /**
     * Constructor
     * Initializes WebDriver, PageFactory elements,
     * and parent AbstractComponents.
     *
     * @param driver WebDriver instance from CheckoutPage
     */
    public ConfirmationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // -------------------- PAGE ELEMENTS --------------------

    // Confirmation message displayed after successful order submission
    @FindBy(css = ".hero-primary")
    WebElement confirmationMessage;

    // -------------------- PAGE ACTIONS --------------------

    /**
     * Retrieves the order confirmation message text.
     *
     * @return Confirmation message displayed on the page
     */
    public String verifyConfirmationMessage() {
        return confirmationMessage.getText();
    }
}
