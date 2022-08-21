package com.mauroooo.steps;

import com.mauroooo.pages.GoogleHomePage;
import com.mauroooo.pages.GoogleSearchResultPage;
import com.mauroooo.scripts.SaveScreenshots;
import com.mauroooo.scripts.SpreadsheetOutput;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class GoogleSteps {
    private static final Logger logger = LoggerFactory.getLogger(GoogleSteps.class);


    protected WebDriver driver;
    protected String valueName;
    protected int linkAmount;
    protected GoogleHomePage homePage;
    protected GoogleSearchResultPage resultPage;
    protected SpreadsheetOutput spreadsheet;
    protected ThreadLocal<SaveScreenshots> screenshots;


    @Before()
    public void beforeScenario() {
        WebDriverManager manager = WebDriverManager.getInstance(System.getProperty("browser"));
        manager.setup();
        driver = manager.create();


        //screenshots.set(new SaveScreenshots(driver)); // hacer un singleton de esto proximamente
    }

    @After
    public void afterScenario() {
        driver.quit();
    }


    @AfterStep
    public void afterStep() {
        //screenshots.get().savePicture("after", "step", "scenario", "StepID");
        //Se pueden conseguir estos atributos usando reflections?
        //crear un plugin que es un Listener y pasarselo a las cucumber options
        // screenshots.savePicture("after", scenario, step);
    }

    @Given("Google search is loaded")
    public void googleInstance() {
        homePage = new GoogleHomePage(driver);
        homePage.navigate();
    }

    @When("I search for {string}")
    public void searchFor(String value) {
        resultPage = new GoogleSearchResultPage(driver);
        this.valueName = value;
        homePage.search(this.valueName);
    }

    @When("I feel lucky about the term {string}")
    public void luckySearchFor(String value){

    }

    @Then("There are at least {int} links that result from it are saved")
    public void saveLinks(int amount) throws IOException {
        this.linkAmount = amount;
        List<String> links = resultPage.findLinks(amount);

        File textFile = new File("foundLinks.txt");
        try (FileWriter fw = new FileWriter(textFile.getAbsoluteFile(), true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter pr = new PrintWriter(bw);) {
            if (textFile.createNewFile()) {
                logger.info("File created under" + textFile.getPath());
            }
            int iter = 1;
            pr.println("\nResults for " + this.valueName + ":\n");
            for (String link : links) {
                pr.println("Link #" + (iter) + ": " + link + "\n");
                iter++;
            }
        } catch (IOException e) {
            logger.error("An error occurred");
            e.printStackTrace();
        }
        SpreadsheetOutput.writeExcelFile(valueName, links);
    }

    @Then("There is a resulting link for the term")
    public void saveLuckyLink(){

    }
}