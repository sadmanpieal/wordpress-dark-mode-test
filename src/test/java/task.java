import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class task {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Set the path to your ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        driver = new ChromeDriver();
    }

    @Test
    public void qaTestFormSubmission() {
        // Step 1: Login to WordPress Admin
        driver.get("http://yourwordpresssite.com/wp-login.php");

        // Login with valid credentials
        driver.findElement(By.id("user_login")).sendKeys("your-username");
        driver.findElement(By.id("user_pass")).sendKeys("your-password");
        driver.findElement(By.id("wp-submit")).click();

        // Step 2: Navigate to the QA Test settings page
        driver.get("http://yourwordpresssite.com/wp-admin/options-general.php?page=qa-test-settings");

        // Step 3: Fill out the form fields
        driver.findElement(By.id("qa_test_fullname")).sendKeys("John Doe");
        driver.findElement(By.id("qa_test_nickname")).sendKeys("Johnny");
        driver.findElement(By.id("qa_test_address")).sendKeys("123 Test Street");
        driver.findElement(By.id("qa_test_dob_d")).sendKeys("15");

        // Date of Birth - Month (Dropdown)
        Select dobMonthDropdown = new Select(driver.findElement(By.id("qa_test_dob_m")));
        dobMonthDropdown.selectByValue("5"); // May

        driver.findElement(By.id("qa_test_dob_y")).sendKeys("1990");
        driver.findElement(By.id("qa_test_email")).sendKeys("john.doe@example.com");
        driver.findElement(By.id("qa_test_web")).sendKeys("http://johndoe.com");

        // Step 4: Submit the form
        driver.findElement(By.name("Submit")).click();

        // Optionally, verify the success message
        String successMessage = driver.findElement(By.className("updated")).getText();
        Assert.assertTrue(successMessage.contains("Settings saved"), "Form submission failed!");
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
