package anaysahaiqa.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import anaysahaiqa.AbstractComponents.AbstractComponents;

/**
 * LandingPage
 * ------------
 * This class models the Login (Landing) Page of the application.
 * It is responsible for user authentication and navigation
 * to the Product Catalogue page.
 */
public class LandingPage extends AbstractComponents {

    // WebDriver instance
    WebDriver driver;

    /**
     * Constructor
     * Initializes WebDriver, PageFactory elements,
     * and parent AbstractComponents.
     *
     * @param driver WebDriver instance from test class
     */
    public LandingPage(WebDriver driver) {
        super(driver);             // Initialize parent class
        this.driver = driver;      // Assign local driver
        PageFactory.initElements(driver, this);
    }

    // -------------------- PAGE ELEMENTS --------------------

    // Email input field
    @FindBy(id = "userEmail")
    WebElement userEmail;

    // Password input field
    @FindBy(id = "userPassword")
    WebElement password;

    // Login button
    @FindBy(id = "login")
    WebElement loginButton;

    // Error message displayed on invalid login
    @FindBy(css = "[class*='flyInOut']")
    WebElement errorMessage;

    // -------------------- PAGE ACTIONS --------------------

    /**
     * Performs login with provided credentials and
     * navigates to the Product Catalogue page.
     *
     * @param email        User email address
     * @param userPassword User password
     * @return ProductCatalogue instance
     */
    public ProductCatalogue loginPage(String email, String userPassword) {
        userEmail.sendKeys(email);
        password.sendKeys(userPassword);
        loginButton.click();
        return new ProductCatalogue(driver);
    }

    /**
     * Retrieves the error message displayed on failed login attempt.
     *
     * @return Login error message text
     */
    public String getErrorMessage() {
        waitForWebElementToAppear(errorMessage);
        return errorMessage.getText();
    }

    /**
     * Navigates directly to the application login page.
     */
    public void goTo() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");
    }
}
