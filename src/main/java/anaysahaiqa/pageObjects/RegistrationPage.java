package anaysahaiqa.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import anaysahaiqa.AbstractComponents.AbstractComponents;

/**
 * RegistrationPage
 * -----------------
 * This class models the User Registration Page of the application.
 * It encapsulates all actions required to create a new user account.
 *
 * Extends AbstractComponents to reuse common synchronization utilities.
 */
public class RegistrationPage extends AbstractComponents {

    // WebDriver instance
    WebDriver driver;

    /**
     * Constructor
     * Initializes WebDriver, PageFactory elements,
     * and parent AbstractComponents.
     *
     * @param driver WebDriver instance from test class
     */
    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // -------------------- PAGE ELEMENTS --------------------

    // First name input field
    @FindBy(id = "firstName")
    WebElement firstNameField;

    // Last name input field
    @FindBy(id = "lastName")
    WebElement lastNameField;

    // Email input field
    @FindBy(id = "userEmail")
    WebElement emailField;

    // Mobile number input field
    @FindBy(id = "userMobile")
    WebElement phoneField;

    // Occupation dropdown
    @FindBy(css = "select[formcontrolname='occupation']")
    WebElement occupationDropdown;

    // Gender radio buttons
    @FindBy(css = "input[type='radio'][value='Male']")
    WebElement genderMaleRadio;

    @FindBy(css = "input[type='radio'][value='Female']")
    WebElement genderFemaleRadio;

    // Password input field
    @FindBy(id = "userPassword")
    WebElement passwordField;

    // Confirm password input field
    @FindBy(id = "confirmPassword")
    WebElement confirmPasswordField;

    // Age / terms confirmation checkbox
    @FindBy(css = "input[type='checkbox'][formcontrolname='required']")
    WebElement ageCheckbox;

    // Register button
    @FindBy(id = "login")
    WebElement registerButton;

    // Error message displayed on registration failure
    @FindBy(css = "[class*='flyInOut']")
    WebElement errorMessage;

    // -------------------- PAGE ACTIONS --------------------

    /**
     * Completes the user registration form and submits it.
     *
     * @param firstName       User first name
     * @param lastName        User last name
     * @param email           User email address
     * @param phone           User mobile number
     * @param occupation      User occupation (dropdown value)
     * @param gender          User gender (Male/Female)
     * @param password        User password
     * @param confirmPassword Confirmation of password
     */
    public void completeRegistration(
            String firstName,
            String lastName,
            String email,
            String phone,
            String occupation,
            String gender,
            String password,
            String confirmPassword) {

        // Populate personal details
        firstNameField.clear();
        firstNameField.sendKeys(firstName);

        lastNameField.clear();
        lastNameField.sendKeys(lastName);

        emailField.clear();
        emailField.sendKeys(email);

        phoneField.clear();
        phoneField.sendKeys(phone);

        // Select occupation from dropdown
        occupationDropdown.click();
        occupationDropdown.findElement(
                By.xpath("//option[contains(text(),'" + occupation + "')]"))
                .click();

        // Select gender based on input
        if (gender.equalsIgnoreCase("Male")) {
            genderMaleRadio.click();
        } else {
            genderFemaleRadio.click();
        }

        // Enter password details
        passwordField.clear();
        passwordField.sendKeys(password);

        confirmPasswordField.clear();
        confirmPasswordField.sendKeys(confirmPassword);

        // Accept age / terms if not already selected
        if (!ageCheckbox.isSelected()) {
            ageCheckbox.click();
        }

        // Submit registration form
        registerButton.click();
    }

    /**
     * Retrieves the error message displayed on registration failure.
     *
     * @return Registration error message text
     */
    public String getErrorMessage() {
        waitForWebElementToAppear(errorMessage);
        return errorMessage.getText();
    }

    /**
     * Navigates directly to the registration page URL.
     */
    public void goTo() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/register");
    }
}
