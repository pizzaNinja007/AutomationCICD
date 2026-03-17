package anaysahaiqa.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import anaysahaiqa.TestComponents.BaseTest;
import anaysahaiqa.pageObjects.CartPage;
import anaysahaiqa.pageObjects.CheckoutPage;
import anaysahaiqa.pageObjects.ConfirmationPage;
import anaysahaiqa.pageObjects.LandingPage;
import anaysahaiqa.pageObjects.ProductCatalogue;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * StepDefinitionImpl
 * ------------------
 * This class contains Cucumber step definitions
 * that map Gherkin steps to Selenium automation logic.
 *
 * It extends BaseTest to leverage WebDriver setup
 * and common reusable utilities.
 */
public class StepDefinitionimpl extends BaseTest {

    public LandingPage landingPage;
    public ProductCatalogue productCatalogue;
    public ConfirmationPage confirmationPage;

    /**
     * Launches the Ecommerce application
     */
    @Given("I landed on Ecommerce Page")
    public void I_landed_on_Ecommerce_Page() throws IOException {
        landingPage = LaunchApplication();
    }

    /**
     * Logs into the application using dynamic credentials
     *
     * @param username application username
     * @param password application password
     */
    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_with_username_and_password(String username, String password) {
        productCatalogue = landingPage.loginPage(username, password);
    }

    /**
     * Adds a specific product to the cart
     *
     * @param productName name of the product to add
     */
    @When("^I add product (.+) to cart$")
    public void i_add_product_to_cart(String productName) {
        List<WebElement> products = productCatalogue.getproductsList();
        productCatalogue.addProductToCart(productName);
    }

    /**
     * Performs checkout and submits the order
     *
     * @param productName product expected in cart
     */
    @When("^Checkout (.+) and submit the order$")
    public void checkout_submit_order(String productName) {

        // Navigate to cart page
        productCatalogue.goToCartPage();

        // Validate product presence in cart
        CartPage cart = new CartPage(driver);
        Boolean match = cart.verifyProductDisplay(productName);
        Assert.assertTrue(match);

        // Proceed to checkout and submit order
        CheckoutPage checkoutPage = cart.goToCheckout();
        checkoutPage.selectCounry();
        confirmationPage = checkoutPage.submitOrder();
    }

    /**
     * Validates confirmation message on order success page
     *
     * @param expectedMessage expected confirmation text
     */
    @Then("{string} message is displayed on ConfirmationPage")
    public void confirmation_message_is_displayed(String expectedMessage) {
        String confirmMessage = confirmationPage.verifyConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase(expectedMessage));
    }
}
