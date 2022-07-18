package steps;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import javafx.beans.value.WeakChangeListener;
import pages.GoogleHomePage;

public class GoogleSteps {
    protected WebDriver driver;

    @Before()
    public void beforeScenario(){
        WebDriverManager manager = WebDriverManager.firefoxdriver();
        manager.setup();
        driver = manager.create();
    }
    @After
    public void afterScenario(){
        driver.quit();
    }
    
    @Given("^Google search is loaded$")
    public void googleInstance(){
        String currentPage = "www.google.com"; 
        GoogleHomePage.navigate();
        GoogleHomePage homePage = new GoogleHomePage(driver);
    }

    @When("^I search for '{value}'$")
    public void searchFor(String value){

    }
    
    @Then("^There are at least {amount:d} links that result from it are saved$")
    public void saveLinks(int amount){

    }


}
