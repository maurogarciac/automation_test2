package pages;

//import java.time.Duration;

import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GoogleHomePage {
    protected static WebDriver driver;
    //private static WebDriverWait wait;

    static{
        //FirefoxOptions firefoxOptions = new FirefoxOptions();
        //driver = new FirefoxDriver(firefoxOptions);
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    static void setupClass() {
        WebDriverManager.firefoxdriver().setup();
    }


    public GoogleHomePage(WebDriver driver){
        driver = new FirefoxDriver();
        GoogleHomePage.driver = driver;
    }

    public static void navigateTo(String url){
        driver.get(url);
    }

}
