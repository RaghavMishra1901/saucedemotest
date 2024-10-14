# saucedemotest
### Steps Automated:
1. Log into `https://www.saucedemo.com/` using the credentials provided.
2. Search for "Sauce Labs Fleece Jacket" and go to the product detail page.
3. Add the product to the cart.
4. Proceed to the cart, checkout, and enter user information.
5. Print the "Payment Information" and "Total price".
6. Complete the checkout process.

### Prerequisites
1. Install [Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
2. Install [Maven](https://maven.apache.org/)
3. Download the appropriate WebDriver (e.g., ChromeDriver for Chrome).

### How to Run
1. Clone the repository or download the source code.
2. Set the path of your WebDriver in the code (`System.setProperty("webdriver.chrome.driver", ".mavenproject\\null\\jars\\chromedriver.exe");`).
3. Run the test using Maven:
   ```bash
   mvn test
