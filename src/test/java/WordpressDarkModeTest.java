import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class WordpressDarkModeTest {
    WebDriver driver;
    String baseUrl = "http://localhost/wordpress/wp-login.php";
    String adminUsername = "root";
    String adminPassword = "root";

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void loginToWordPress() {
        driver.get(baseUrl);
        driver.findElement(By.id("user_login")).sendKeys(adminUsername);  
        driver.findElement(By.id("user_pass")).sendKeys(adminPassword);
        driver.findElement(By.id("wp-submit")).click();
        assertTrue(driver.getTitle().contains("Dashboard"), "Login failed or incorrect page.");
    }

    @Test(priority = 2)
    public void checkAndInstallWPDarkMode() {
        // Navigate to Plugins
        driver.findElement(By.linkText("Plugins")).click();
        if (driver.getPageSource().contains("WP Dark Mode")) {
            WebElement pluginStatus = driver.findElement(By.xpath("//a[@id='activate-wp-dark-mode']"));
            if (pluginStatus != null) {
                pluginStatus.click();
            }
        } else {
            // Install WP Dark Mode if not found
            driver.findElement(By.linkText("Add New")).click();
            driver.findElement(By.id("plugin-search-input")).sendKeys("WP Dark Mode");
            driver.findElement(By.cssSelector(".search-submit")).click();
            driver.findElement(By.cssSelector("a[data-slug='wp-dark-mode']")).click(); // Install plugin
            driver.findElement(By.cssSelector(".activate-now")).click(); // Activate plugin
        }
    }

    @Test(priority = 3)
    public void enableAdminDashboardDarkMode() {
        // Navigate to WP Dark Mode
        driver.findElement(By.linkText("WP Dark Mode")).click();
        driver.findElement(By.linkText("Controls")).click();
        // Enable Admin Dashboard Dark Mode
        WebElement adminDarkModeSwitch = driver.findElement(By.id("wp_dark_mode_admin_darkmode"));
        if (!adminDarkModeSwitch.isSelected()) {
            adminDarkModeSwitch.click();
        }
        driver.findElement(By.id("save_changes")).click();
        // Validate dark mode in Admin Panel
        assertTrue(driver.findElement(By.tagName("body")).getAttribute("class").contains("dark-mode"), "Admin Dashboard Dark Mode not enabled.");
    }

    @Test(priority = 4)
    public void customizeDarkModeSwitch() {
        // Navigate to Customization
        driver.findElement(By.linkText("Customization")).click();
        driver.findElement(By.linkText("Switch Settings")).click();
        // Change Floating Switch Style
        Select switchStyle = new Select(driver.findElement(By.id("wp_dark_mode_switch_style")));
        switchStyle.selectByIndex(2); // Selecting an option that is not default
        // Customize Switch Size and Scale
        driver.findElement(By.id("custom_switch_size")).click();
        driver.findElement(By.id("custom_switch_scale")).clear();
        driver.findElement(By.id("custom_switch_scale")).sendKeys("220");
        // Change Floating Switch Position to Left
        Select switchPosition = new Select(driver.findElement(By.id("wp_dark_mode_switch_position")));
        switchPosition.selectByVisibleText("Left");
        driver.findElement(By.id("save_changes")).click();
    }

    @Test(priority = 5)
    public void disableKeyboardShortcuts() {
        // Navigate to Accessibility Settings
        driver.findElement(By.linkText("Accessibility Settings")).click();
        WebElement shortcutSwitch = driver.findElement(By.id("wp_dark_mode_keyboard_shortcut"));
        if (shortcutSwitch.isSelected()) {
            shortcutSwitch.click(); // Disable shortcut if enabled
        }
        driver.findElement(By.id("save_changes")).click();
    }

    @Test(priority = 6)
    public void enablePageTransitionAnimation() {
        // Navigate to Site Animation
        driver.findElement(By.linkText("Site Animation")).click();
        WebElement animationSwitch = driver.findElement(By.id("enable_page_transition"));
        if (!animationSwitch.isSelected()) {
            animationSwitch.click();
        }
        Select animationEffect = new Select(driver.findElement(By.id("page_transition_effect")));
        animationEffect.selectByIndex(2); // Selecting an animation effect that is not default
        driver.findElement(By.id("save_changes")).click();
    }

    @Test(priority = 7)
    public void validateFrontendDarkMode() {
        // Navigate to the front end
        driver.navigate().to("http://localhost/wordpress/");
        // Validate dark mode applied on the frontend
        WebElement bodyElement = driver.findElement(By.tagName("body"));
        assertTrue(bodyElement.getAttribute("class").contains("dark-mode"), "Dark Mode not applied on the frontend.");
    }

    @AfterClass
    public void tearDown() {
        //driver.quit();
    }
}
