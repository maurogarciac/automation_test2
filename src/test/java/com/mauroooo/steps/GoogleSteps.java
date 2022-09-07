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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class GoogleSteps {
    //reificacion
    //parent class o multiton para pasar los parametros a estos objetos que llamo muchas veces como el Excel y el Txt quiza el Screenshots
    //private static final Logger logger = LoggerFactory.getLogger(GoogleSteps.class);
    //puede ser un multiton
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
    public void beforeScenario(Scenario scenario) throws MalformedURLException {
        String browserProperty = System.getProperty("browser");
        String remoteProperty = System.getProperty("remote");

        if(remoteProperty == null){
            WebDriverManager manager = WebDriverManager.getInstance(browserProperty);
            manager.setup();
            driver = manager.create();
        } else {
            String url = System.getProperty("hub_url");
            driver = new RemoteWebDriver(new URL(url), new DesiredCapabilities());
        }


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
}