import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SeleniumTesting {

    WebDriver driver;

    @BeforeEach
    void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions);
    }

    // Gets the application webpage loaded into the driver with a timeout limit of 2 seconds.
    void openPage() {
        driver.get("http://localhost:8080/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    // Login method.
    void login(String username, String password) {
        openPage();
        WebElement usernameElement = driver.findElement(By.name("username"));
        WebElement passwordElement = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type=submit]"));

        usernameElement.sendKeys(username);
        passwordElement.sendKeys(password);
        loginButton.click();
    }

    @Test
    @Order(1)
    void test_page_load() {
        openPage();
        WebElement title = driver.findElement(By.cssSelector("h2"));
        String expectedTitle = "Login";
        assertEquals(expectedTitle, title.getText());
    }

    @Test
    @Order(2)
    void test_login_user() {
        login("testuser", "password");

        WebElement title = driver.findElement(By.cssSelector("h2"));
        String expectedTitle = "User Home Page";
        assertEquals(expectedTitle, title.getText());
    }

    @Test
    @Order(3)
    void test_login_admin() {
        login("testadmin", "password");

        WebElement title = driver.findElement(By.cssSelector("h2"));
        String expectedTitle = "Admin Home Page";
        assertEquals(expectedTitle, title.getText());
    }

    @Test
    @Order(4)
    void test_login_invalid_password() {
        login("testuser", "InvalidPassword");

        WebElement errorMessage = driver.findElement(By.cssSelector("p[style='color:red']"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Invalid username or password", errorMessage.getText());
    }

    @Test
    @Order(5)
    void test_view_notifications() {
        login("testuser", "password");

        WebElement notificationsLink = driver.findElement(By.cssSelector("a[href*='/notifications']"));

        notificationsLink.click();
        WebElement title = driver.findElement(By.cssSelector("h2"));
        String expectedTitle = "Notification Inbox";
        assertEquals(expectedTitle, title.getText());
    }

    @Test
    @Order(6)
    void test_user_logout() {
        login("testuser", "password");
        WebElement logoutButton = driver.findElement(By.linkText("Logout"));
        logoutButton.click();

        WebElement loginTitle = driver.findElement(By.cssSelector("h2"));
        assertEquals("Login", loginTitle.getText());
    }

    @Test
    @Order(7)
    void test_admin_logout() {
        login("testadmin", "password");
        WebElement logoutButton = driver.findElement(By.linkText("Logout"));
        logoutButton.click();

        WebElement loginTitle = driver.findElement(By.cssSelector("h2"));
        assertEquals("Login", loginTitle.getText());
    }

    @Test
    @Order(8)
    void test_view_accounts() {
        login("testadmin", "password");

        WebElement userRow = driver.findElement(By.xpath("//td[text()='TestUser']/.."));
        assertNotNull(userRow);

        WebElement viewAccountsLink = driver.findElement(By.cssSelector("a[href*='/accounts']"));
        viewAccountsLink.click();

        WebElement title = driver.findElement(By.cssSelector("h2"));
        assertEquals("Accounts for User: TestUser", title.getText());
    }

    @Test
    @Order(9)
    void test_freeze_and_unfreeze_account() {
        login("testadmin", "password");

        WebElement userRow = driver.findElement(By.xpath("//td[text()='TestUser']/.."));
        WebElement viewAccountsLink = userRow.findElement(By.cssSelector("a[href*='/accounts']"));
        viewAccountsLink.click();

        List<WebElement> activeAccountRows = driver.findElements(By.xpath("//td[text()='Active']/../td/a[text()='Freeze']"));

        if (activeAccountRows.isEmpty()) {
            fail("No active accounts to freeze.");
        }
        else {
            WebElement freezeLink = activeAccountRows.get(0);
            freezeLink.click();
        }

        WebElement frozenAccountRow = driver.findElement(By.xpath("//td[text()='Frozen']/.."));
        assertNotNull(frozenAccountRow);

        List<WebElement> frozenAccountRows = driver.findElements(By.xpath("//td[text()='Frozen']/../td/a[text()='Unfreeze']"));

        if (frozenAccountRows.isEmpty()) {
            fail("No frozen accounts to unfreeze.");
        }
        else {
            WebElement unfreezeLink = frozenAccountRows.get(0);
            unfreezeLink.click();
        }

        WebElement activeAccountRow = driver.findElement(By.xpath("//td[text()='Active']/.."));
        assertNotNull(activeAccountRow);
    }

    @Test
    @Order(10)
    void test_create_transaction() {
        login("testuser", "password");

        WebElement amount = driver.findElement(By.id("amount"));
        WebElement senderAccount = driver.findElement(By.id("senderAccount"));
        WebElement recipientAccount = driver.findElement(By.id("recipientAccount"));
        WebElement submitButton = driver.findElement(By.cssSelector("form button[type='submit']"));

        amount.sendKeys("50.00");
        senderAccount.sendKeys("1");
        recipientAccount.sendKeys("2");
        submitButton.click();

        WebElement successMessage = driver.findElement(By.cssSelector("p[style='color:green']"));
        assertTrue(successMessage.isDisplayed());
        assertEquals("Transaction successful.", successMessage.getText());
    }

    @Test
    @Order(11)
    void test_invalid_transaction_missing_account() {
        login("testuser", "password");

        WebElement amount = driver.findElement(By.id("amount"));
        WebElement senderAccount = driver.findElement(By.id("senderAccount"));
        WebElement recipientAccount = driver.findElement(By.id("recipientAccount"));
        WebElement submitButton = driver.findElement(By.cssSelector("form button[type='submit']"));

        amount.sendKeys("50.00");
        senderAccount.sendKeys("1");
        recipientAccount.sendKeys("50");
        submitButton.click();

        WebElement errorMessage = driver.findElement(By.cssSelector("p[style='color:red']"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Transaction failed: sender or recipient account is missing.", errorMessage.getText());
    }

    @Test
    @Order(12)
    void test_invalid_transaction_insufficient_balance() {
        login("testuser2", "password");

        WebElement amount = driver.findElement(By.id("amount"));
        WebElement senderAccount = driver.findElement(By.id("senderAccount"));
        WebElement recipientAccount = driver.findElement(By.id("recipientAccount"));
        WebElement submitButton = driver.findElement(By.cssSelector("form button[type='submit']"));

        amount.sendKeys("1500.00");
        senderAccount.sendKeys("3");
        recipientAccount.sendKeys("4");
        submitButton.click();

        WebElement errorMessage = driver.findElement(By.cssSelector("p[style='color:red']"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Transaction failed: Insufficient funds, account overdraft is not enabled.", errorMessage.getText());
    }

    @Test
    @Order(13)
    void test_invalid_transaction_exceeds_minimum_balance() {
        login("testuser2", "password");

        WebElement amount = driver.findElement(By.id("amount"));
        WebElement senderAccount = driver.findElement(By.id("senderAccount"));
        WebElement recipientAccount = driver.findElement(By.id("recipientAccount"));
        WebElement submitButton = driver.findElement(By.cssSelector("form button[type='submit']"));

        amount.sendKeys("950.00");
        senderAccount.sendKeys("3");
        recipientAccount.sendKeys("4");
        submitButton.click();

        WebElement errorMessage = driver.findElement(By.cssSelector("p[style='color:red']"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Transaction failed: Transaction will cause account to go below minimum balance.", errorMessage.getText());
    }

    @Test
    @Order(14)
    void test_invalid_transaction_frozen_account() {
        login("testadmin", "password");
        WebElement userRow = driver.findElement(By.xpath("//td[text()='TestUser']/.."));
        WebElement viewAccountsLink = userRow.findElement(By.cssSelector("a[href*='/accounts']"));
        viewAccountsLink.click();

        WebElement freezeLink = driver.findElement(By.xpath("//td[text()='Active']/../td/a[text()='Freeze']"));
        freezeLink.click();

        WebElement logoutButton = driver.findElement(By.linkText("Logout"));
        logoutButton.click();

        login("testuser", "password");
        WebElement amount = driver.findElement(By.id("amount"));
        WebElement senderAccount = driver.findElement(By.id("senderAccount"));
        WebElement recipientAccount = driver.findElement(By.id("recipientAccount"));
        WebElement submitButton = driver.findElement(By.cssSelector("form button[type='submit']"));

        amount.sendKeys("50.00");
        senderAccount.sendKeys("1");
        recipientAccount.sendKeys("2");
        submitButton.click();

        WebElement errorMessage = driver.findElement(By.cssSelector("p[style='color:red']"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Transaction failed: one or both accounts are frozen.", errorMessage.getText());

        logoutButton = driver.findElement(By.linkText("Logout"));
        logoutButton.click();

        login("testadmin", "password");
        viewAccountsLink = driver.findElement(By.cssSelector("a[href*='/accounts']"));
        viewAccountsLink.click();

        WebElement unfreezeLink = driver.findElement(By.xpath("//td[text()='Frozen']/../td/a[text()='Unfreeze']"));
        unfreezeLink.click();

        WebElement activeAccountRow = driver.findElement(By.xpath("//td[text()='Active']/.."));
        assertNotNull(activeAccountRow);
    }

    @Test
    @Order(20)
    void test_invalid_transaction_exceed_daily_limit() {
        login("testuser", "password");

        // Loop to force user to reach maximum daily transaction limit (default 5).
        for (int i = 0; i < 6; i++) {
            WebElement amount = driver.findElement(By.id("amount"));
            WebElement senderAccount = driver.findElement(By.id("senderAccount"));
            WebElement recipientAccount = driver.findElement(By.id("recipientAccount"));
            WebElement submitButton = driver.findElement(By.cssSelector("form button[type='submit']"));

            amount.clear();
            senderAccount.clear();
            recipientAccount.clear();

            amount.sendKeys("50.00");
            senderAccount.sendKeys("1");
            recipientAccount.sendKeys("2");

            submitButton.click();
        }

        WebElement errorMessage = driver.findElement(By.cssSelector("p[style='color:red']"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Transaction failed: Account has reached the maximum allowed amount of daily transactions.", errorMessage.getText());

    }
}
