package com.mauroooo.steps;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.mauroooo.pages.GoogleHomePage;

public class GoogleSteps {
    protected WebDriver driver;
    
    GoogleHomePage homePage = new GoogleHomePage(driver);
    
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
        homePage.navigate(driver);
        
    }

    @When("^I search for '{value}'$")
    public void searchFor(String value){
        homePage.search(value);


    }
    
    @Then("^There are at least {amount:d} links that result from it are saved$")
    public void saveLinks(int amount){

    }


}
