package com.mauroooo.steps;

import com.mauroooo.pages.GoogleHomePage;
import com.mauroooo.pages.GoogleSearchResultPage;
import com.mauroooo.scripts.SaveScreenshots;
import com.mauroooo.scripts.SpreadsheetOutput;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class GoogleSteps {
    //private static final Logger logger = LoggerFactory.getLogger(GoogleSteps.class);

    protected WebDriver driver;
    protected String valueName;
    protected int linkAmount;
    protected GoogleHomePage homePage;
    protected GoogleSearchResultPage resultPage;
    protected SaveScreenshots screenshots;
    protected File screenshots_directory;
    protected File textFile;
    protected ThreadLocal<SpreadsheetOutput> spreadsheetOutput = new ThreadLocal<>();
    //protected ThreadLocal<TxtWriteOutput> writingTxtFile = new ThreadLocal<>();


    @Before()
    public void beforeScenario(Scenario scenario){
        WebDriverManager manager = WebDriverManager.getInstance(System.getProperty("browser"));
        manager.setup();
        driver = manager.create();

        String scenarioName = scenario.getName().replaceAll("[ .\"']", "_").replaceAll("_for__.*", "");
        Path excelFile = Paths.get(scenarioName + ".xlsx").toAbsolutePath();

        spreadsheetOutput.set(SpreadsheetOutput.getInstance(excelFile));

        //TxtWriteOutput.getInstance();
        //this.textFile = TxtWriteOutput.makeTextFile(scenarioName);
        //TxtWriteOutput.writeFileHeader(textFile, scenarioName);

        //SaveScreenshots.getInstance(driver);
        //screenshots_directory = SaveScreenshots.makeDirectory(scenarioName);
    }

    @After
    public void afterScenario() throws IOException {
        System.out.println("afterScenario ran");
        spreadsheetOutput.get().writeFile();
        driver.quit();
    }

    @AfterStep
    public void afterStep() {
        //SaveScreenshots.savePicture("after", valueName , screenshots_directory);
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
    public void saveLinks(int amount) {
        this.linkAmount = amount;
        List<String> links = resultPage.findLinks(amount);

        //TxtWriteOutput.writeTextFile(valueName, links, textFile);
        spreadsheetOutput.get().addSheet(valueName, links);
    }

    @Then("There is a resulting link for the term")
    public void saveLuckyLink() {
        String link = resultPage.returnLink(valueName);
        List<String> linkList = new ArrayList<>();
        linkList.add(link);

        //TxtWriteOutput.writeTextFile(valueName, linkList, textFile);
        spreadsheetOutput.get().addSheet(valueName, linkList);
    }

    public boolean isPalindrome(String string){
        StringBuilder sb = new StringBuilder(string);
        String reversed = sb.reverse().toString();
        return string.equals(reversed);
    }
    public boolean isPalindromeAgain(String string){
        for (int iter = 0; iter < string.length()/2; iter++){
            if (string.charAt(iter) != string.charAt(string.length()-1-iter)){
                return false;
            }
        }
        return true;
    }
}