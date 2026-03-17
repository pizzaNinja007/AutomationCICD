package anaysahaiqa.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import anaysahaiqa.pageObjects.OrderPage;

/**
 * AbstractComponents
 * -------------------
 * This class contains all common reusable methods and web elements
 * that are shared across multiple Page Object classes.
 *
 * Responsibilities:
 * - Explicit wait utilities
 * - Global navigation actions (Cart, Orders)
 * - Synchronization handling
 */
public class AbstractComponents {

    // WebDriver instance used across page objects
    WebDriver driver;

    /**
     * Constructor
     * Initializes driver and PageFactory elements.
     *
     * @param driver WebDriver instance passed from test classes
     */
    public AbstractComponents(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // -------------------- GLOBAL HEADER ELEMENTS --------------------

    // Cart icon/link available in application header
    @FindBy(css = "[routerlink*='cart']")
    WebElement cartReader;

    // Orders link available in application header
    @FindBy(css = "[routerlink*='myorders']")
    WebElement orderHeader;

    // -------------------- WAIT UTILITIES --------------------

    /**
     * Waits until an element located by the given locator becomes visible.
     *
     * @param findBy Locator of the target element
     */
    public void waitForElementToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    /**
     * Waits until the given WebElement becomes visible.
     *
     * @param element WebElement to wait for
     */
    public void waitForWebElementToAppear(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits until an element located by the given locator disappears from the DOM
     * or becomes invisible.
     *
     * Commonly used for loaders, spinners, and animations.
     *
     * @param findBy Locator of the target element
     */
    public void waitForElementToDisappear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
    }

    // -------------------- NAVIGATION METHODS --------------------

    /**
     * Navigates to the Cart page by clicking on the cart icon.
     */
    public void goToCartPage() {
        cartReader.click();
    }

    /**
     * Navigates to the Orders page and returns the OrderPage object.
     *
     * @return OrderPage instance
     */
    public OrderPage goToOrdersPage() {
        orderHeader.click();
        return new OrderPage(driver);
    }
}
