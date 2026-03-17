package anaysahaiqa;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import anaysahaiqa.pageObjects.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import anaysahaiqa.TestComponents.BaseTest;

/**
 * POMFrameworkDesign
 *
 * This test class demonstrates a data-driven approach for user registration,
 * login, product purchase, and order history verification using the Page Object Model.
 * Test data is provided via a JSON file and injected using TestNG's DataProvider.
 */
public class POMFrameworkDesign extends BaseTest {

	// Product name to be used for assertions in order history
	String productName = "ZARA COAT 3";

	/**
	 * submitOrder
	 *
	 * Registers a new user, logs in, adds a product to the cart, completes checkout,
	 * and verifies the confirmation message.
	 *
	 * @param input HashMap containing registration and purchase details (from DataProvider)
	 * @throws IOException if file operations fail
	 */
	@Test(dataProvider = "getData", groups = {"Purchase"})
	public void submitOrder(HashMap<String, String> input) throws IOException {
		// Registration step before login
		RegistrationPage registrationPage = new RegistrationPage(driver);
		registrationPage.goTo();
		registrationPage.completeRegistration(input.get("firstName"),
				input.get("lastName"),
				input.get("email"),
				input.get("phone"),
				input.get("occupation"),
				input.get("gender"),
				input.get("password"),
				input.get("confirmPassword")
		);

		// Login using registered credentials
		ob.loginPage(input.get("email"), input.get("password"));

		// Product selection and cart operations
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		List<WebElement> products = productCatalogue.getproductsList();
		productCatalogue.addProductToCart(input.get("product"));
		productCatalogue.goToCartPage();

		// Cart verification
		CartPage cart = new CartPage(driver);
		Boolean match = cart.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);

		// Checkout and order confirmation
		CheckoutPage checkoutPage = cart.goToCheckout();
		checkoutPage.selectCounry();
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.verifyConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}

	/**
	 * orderHistory
	 *
	 * Verifies that the purchased product appears in the user's order history.
	 * Depends on successful execution of submitOrder.
	 */
	@Test(dependsOnMethods = {"submitOrder"})
	public void orderHistory() {
		// Login with static credentials
		ob.loginPage("test@anay.com", "Abcd1234!");
		OrderPage orders = ob.goToOrdersPage();
		Assert.assertTrue(orders.verifyOrderDisplay(productName));
	}

	/**
	 * getData
	 *
	 * DataProvider method to supply test data from a JSON file.
	 *
	 * @return Object[][] containing HashMaps for each test iteration
	 * @throws IOException if file reading fails
	 */
	@DataProvider
	public Object[][] getData() throws IOException {
		// Read test data from JSON and return as Object[][]
		List<HashMap<String, String>> data = getJsonData(System.getProperty("user.dir") + "\\src\\test\\java\\anaysahaiqa\\data\\PurchaseOrder.json");
		return new Object[][]{{data.get(0)}, {data.get(1)}};
	}
}


