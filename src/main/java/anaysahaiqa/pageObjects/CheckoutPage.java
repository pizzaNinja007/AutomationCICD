package anaysahaiqa.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import anaysahaiqa.AbstractComponents.AbstractComponents;

/**
 * CheckoutPage
 * -------------
 * This class models the Checkout Page of the application.
 * It provides functionality to:
 * - Select delivery country using auto-suggestion
 * - Submit the order
 *
 * Extends AbstractComponents to reuse wait and synchronization utilities.
 */
public class CheckoutPage extends AbstractComponents {

    // WebDriver instance
    WebDriver driver;

    /**
     * Constructor
     * Initializes WebDriver, PageFactory elements,
     * and parent AbstractComponents.
     *
     * @param driver WebDriver instance from test or previous page
     */
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // -------------------- PAGE ELEMENTS --------------------

    // Country input field used for auto-suggestion search
    @FindBy(css = "[placeholder='Select Country']")
    WebElement countryInput;

    // Submit / Place Order button
    @FindBy(css = ".action__submit")
    WebElement submitButton;

    // Second country option from the auto-suggestion list
    @FindBy(css = ".ta-item:nth-of-type(2)")
    WebElement selectCountryOption;

    // Locator for auto-suggestion results container
    By resultsContainer = By.cssSelector(".ta-results");

    // -------------------- PAGE ACTIONS --------------------

    /**
     * Selects the delivery country using the auto-suggestion dropdown.
     * Currently selects "India" from the list.
     */
    public void selectCountry() {
        Actions actions = new Actions(driver);

        // Type country name to trigger auto-suggestions
        actions.sendKeys(countryInput, "india").build().perform();

        // Wait for suggestion results to appear
        waitForElementToAppear(resultsContainer);

        // Select the desired country from the suggestions
        selectCountryOption.click();
    }

    /**
     * Submits the order and navigates to ConfirmationPage.
     *
     * @return ConfirmationPage instance
     */
    public ConfirmationPage submitOrder() {
        submitButton.click();
        return new ConfirmationPage(driver);
    }
}
