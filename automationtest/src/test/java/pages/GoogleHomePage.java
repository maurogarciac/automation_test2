package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleHomePage {
    protected static WebDriver driver;
    private static WebDriverWait wait;

    static{
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        driver = new FirefoxDriver(firefoxOptions);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public GoogleHomePage(WebDriver driver){
        GoogleHomePage.driver = driver;
    }

    public static void navigateTo(String url){
        driver.get(url);
    }

}
