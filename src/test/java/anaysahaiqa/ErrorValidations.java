package anaysahaiqa;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import anaysahaiqa.TestComponents.BaseTest;
import anaysahaiqa.TestComponents.Retry;
import anaysahaiqa.pageObjects.CartPage;
import anaysahaiqa.pageObjects.ProductCatalogue;

/**
 * ErrorValidations
 * -----------------
 * This test class contains negative test scenarios to validate
 * application error handling and incorrect user actions.
 *
 * It focuses on:
 * - Login failure validation
 * - Product mismatch validation in cart
 *
 * Extends BaseTest to reuse driver setup and common test utilities.
 */
public class ErrorValidations extends BaseTest {

    /**
     * Validates error message displayed on invalid login attempt.
     *
     * Scenario:
     * - User enters incorrect password
     * - Application should display proper error message
     *
     * Retry logic is enabled to handle flaky UI failures.
     */
    @Test(groups = { "ErrorHandling" }, retryAnalyzer = Retry.class)
    public void loginErrorValidation() throws IOException {

        // Attempt login with invalid credentials
        ob.loginPage("test@anay.com", "Abcd12");

        // Validate incorrect login error message
        Assert.assertEquals(
                ob.getErrorMessage(),
                "Incorrect email or password.",
                "Login error message mismatch"
        );
    }

    /**
     * Validates that an incorrect product is NOT displayed in the cart.
     *
     * Scenario:
     * - User logs in successfully
     * - Adds a valid product to cart
     * - Verifies cart does not contain an incorrect product name
     */
    @Test
    public void productErrorValidation() throws IOException {

        String productName = "ZARA COAT 3";

        // Perform valid login
        ob.loginPage("anay@test.com", "Abcd@1234");

        // Navigate to product catalogue
        ProductCatalogue productCatalogue = new ProductCatalogue(driver);

        // Load products and add selected product to cart
        productCatalogue.getProductsList();
        productCatalogue.addProductToCart(productName);

        // Navigate to cart page using abstract component method
        productCatalogue.goToCartPage();

        // Initialize CartPage and validate incorrect product presence
        CartPage cart = new CartPage(driver);
        Boolean match = cart.verifyProductDisplay("ZARA COAT 33");

        // Assert that incorrect product is not present
        Assert.assertFalse(
                match,
                "Incorrect product was unexpectedly found in the cart"
        );
    }
}
