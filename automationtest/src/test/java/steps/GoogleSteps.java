package steps;

import io.cucumber.java.en.*;

public class GoogleSteps {
    
    @Given("^Google search is loaded$")
    public void googleInstance(){

    }

    @When("^I search for '{value}'$")
    public void searchFor(String value){

    }
    
    @Then("^There are at least {amount:d} links that result from it are saved$")
    public void saveLinks(int amount){

    }


}
