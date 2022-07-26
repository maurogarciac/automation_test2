package com.mauroooo.steps;

import java.util.List;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.mauroooo.pages.GoogleHomePage;
import com.mauroooo.pages.GoogleSearchResultPage;

public class GoogleSteps {
    protected WebDriver driver;
    protected String valueName;

    GoogleHomePage homePage = new GoogleHomePage(driver);
    GoogleSearchResultPage resultPage = new GoogleSearchResultPage(driver);
    
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
        this.valueName = value;
        homePage.search(this.valueName);


    }
    
    @Then("^There are at least {amount:d} links that result from it are saved$")
    public void saveLinks(int amount){
        
        List<String> links = resultPage.findLinks(amount);

        int iter = amount + 1;
        System.out.println("Results for " + this.valueName + ":");
        for (String link : links){
            System.out.println( "Link #" +(amount % iter) + ": " + link);
            iter--;
        }
    }


}
