package SauceDemoTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class SauceDemoTest {
    public WebDriver driver;
    private WebDriverWait wait;

    // Locators for the elements
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By fleeceJacketItem = By.xpath("//div[text()='Sauce Labs Fleece Jacket']");
    private final By addToCartButton = By.id("add-to-cart");
    private final By cartLink = By.className("shopping_cart_link");
    private final By checkoutButton = By.id("checkout");
    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By paymentInfo = By.className("summary_info");
    private final By totalPrice = By.className("summary_total_label");
    private final By finishButton = By.id("finish");

    @BeforeMethod
    public void setUp() {
        // Set up the ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\raghav.mishra\\eclipse-workspace\\mavenproject\\null\\jars\\chromedriver.exe");

        // ChromeOptions to handle remote origin policy issues
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Initialize the driver
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Initialize WebDriverWait for explicit waits
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open the website
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void completeorder_flow() throws InterruptedException{
        // Step 1: Login
        login("standard_user", "secret_sauce");

        // Step 2: Search and select "Sauce Labs Fleece Jacket"
        selectProduct("Sauce Labs Fleece Jacket");

        // Step 3: Add the product to the cart
        addToCart();

        // Step 4: Proceed to checkout and enter checkout information
        proceedToCheckout("Raghav", "Mishra", "12345");

        // Step 5: Print Payment Information and Total Price
        printPaymentInformation();

        // Step 6: Complete the order
        completeOrder();
    }

    
    
    
    
    
    
    private void login(String username, String password) {
        // Enter username
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).sendKeys(username);
        // Enter password
        driver.findElement(passwordInput).sendKeys(password);
        // Click login button
        driver.findElement(loginButton).click();

        // Verify that we are on the inventory page by checking the presence of the shopping cart
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(cartLink)).isDisplayed(), "Login failed, cart link not visible.");
    }

    private void selectProduct(String productName) {
        // Find and click on the specific product
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='" + productName + "']"))).click();
    }

    private void addToCart() {
        // Click on "ADD TO CART"
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        // Verify that the cart count has updated
        Assert.assertTrue(driver.findElement(cartLink).getText().contains("1"), "Product not added to the cart.");
    }

    private void proceedToCheckout(String firstName, String lastName, String postalCode) {
        // Click on the cart link
        driver.findElement(cartLink).click();
        // Click on "Checkout"
        driver.findElement(checkoutButton).click();
        // Enter first name, last name, and postal code
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(postalCodeInput).sendKeys(postalCode);
        // Click on continue
        driver.findElement(continueButton).click();
    }

    private void printPaymentInformation() {
        // Fetch and print payment information and total price
        WebElement paymentElement = driver.findElement(paymentInfo);
        WebElement totalPriceElement = driver.findElement(totalPrice);
        System.out.println("Payment Information: " + paymentElement.getText());
        System.out.println("Total Price: " + totalPriceElement.getText());

        // Assert that payment info and total price are displayed
        Assert.assertTrue(paymentElement.isDisplayed(), "Payment information not found.");
        Assert.assertTrue(totalPriceElement.isDisplayed(), "Total price not found.");
    }

    private void completeOrder() {
        // Click on "Finish"
        driver.findElement(finishButton).click();
        // Verify that the order is complete
        WebElement completeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        Assert.assertEquals(completeMessage.getText(), "Thank you for your order!", "Order completion failed.");
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
