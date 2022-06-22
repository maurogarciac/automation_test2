package steps;

import io.cucumber.java.en.*;
import pages.GoogleHomePage;

public class GoogleSteps {
    
    @Given("^Google search is loaded$")
    public void googleInstance(){
        String currentPage = "www.google.com"; 
        GoogleHomePage.navigateTo(currentPage);
        
    }

    @When("^I search for '{value}'$")
    public void searchFor(String value){

    }
    
    @Then("^There are at least {amount:d} links that result from it are saved$")
    public void saveLinks(int amount){

    }


}
