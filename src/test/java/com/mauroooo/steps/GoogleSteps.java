package com.mauroooo.steps;

import com.mauroooo.pages.GoogleHomePage;
import com.mauroooo.pages.GoogleSearchResultPage;
import com.mauroooo.scripts.SaveScreenshots;
import com.mauroooo.scripts.SpreadsheetOutput;
import com.mauroooo.scripts.TxtWriteOutput;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class GoogleSteps {
    private static final Logger logger = LoggerFactory.getLogger(GoogleSteps.class);

    protected Scenario scenario;
    protected WebDriver driver;
    protected String valueName;
    protected int linkAmount;
    protected GoogleHomePage homePage;
    protected GoogleSearchResultPage resultPage;
    protected SaveScreenshots screenshots;
    protected File screenshots_directory;
    protected File textFile;


    @Before()
    public void beforeScenario(Scenario scenario) throws IOException {
        WebDriverManager manager = WebDriverManager.getInstance(System.getProperty("browser"));
        manager.setup();
        driver = manager.create();

        this.scenario = scenario;
        String scenarioName = scenario.getName().replaceAll(" ", "_");
        Path excelFile = Paths.get(scenarioName + ".xlsx").toAbsolutePath();
        SpreadsheetOutput.open(excelFile);

        TxtWriteOutput.getInstance();
        this.textFile = TxtWriteOutput.makeTextFile(scenarioName);
        //SaveScreenshots.getInstance(driver);
        //screenshots_directory = SaveScreenshots.makeDirectory(scenarioName);
    }

    @After
    public void afterScenario() throws IOException {
        SpreadsheetOutput.closeExcelFile();
        driver.quit();
    }

    @AfterTest
    public void afterTest() throws IOException {

    }
    @AfterStep
    public void afterStep() {
        //SaveScreenshots.savePicture("after", valueName , screenshots_directory);

        //Se pueden conseguir estos atributos usando reflections?
        //crear un plugin que es un Listener y pasarselo a las cucumber options
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
        resultPage = new GoogleSearchResultPage(driver);
        this.valueName = value;
        homePage.search(this.valueName, true);

    }

    @Then("There are at least {int} links that result from it are saved")
    public void saveLinks(int amount) throws IOException {
        this.linkAmount = amount;
        List<String> links = resultPage.findLinks(amount);

        TxtWriteOutput.writeTextFile(valueName, links, textFile);
        /*
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

         */
        SpreadsheetOutput.writeExcelFile(valueName, links);
    }

    @Then("There is a resulting link for the term")
    public void saveLuckyLink() throws IOException {
        String link = resultPage.returnLink(valueName);
        List<String> linkList = new ArrayList<>();
        linkList.add(link);

        TxtWriteOutput.writeTextFile(valueName, linkList, textFile);
        SpreadsheetOutput.writeExcelFile(valueName, linkList);
    }
}