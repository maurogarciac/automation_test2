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
    protected int linkAmount;
    protected GoogleHomePage homePage;
    protected GoogleSearchResultPage resultPage;
    
    @Before()
    public void beforeScenario(){
        WebDriverManager manager = WebDriverManager.getInstance(System.getProperty("browser"));
        manager.setup();
        driver = manager.create();
    }
    @After
    public void afterScenario(){
        driver.quit();
    }
    
    @Given("Google search is loaded")
    public void googleInstance(){
        homePage = new GoogleHomePage(driver);
        homePage.navigate();

    }

    @When("I search for {string}")
    public void searchFor(String value){
        resultPage = new GoogleSearchResultPage(driver);
        this.valueName = value;
        homePage.search(this.valueName);


    }
    
    @Then("There are at least {int} links that result from it are saved")
    public void saveLinks(int amount){
        this.linkAmount = amount;
        List<String> links = resultPage.findLinks(amount);

        int iter = 1;
        System.out.println("Results for " + this.valueName + ":");
        for (String link : links){
            System.out.println( "Link #" +(iter) + ": " + link);
            iter++;
        }
    }


}
