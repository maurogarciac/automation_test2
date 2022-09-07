package com.mauroooo.tests;

import com.mauroooo.pages.GoogleHomePage;
import com.mauroooo.pages.GoogleSearchResultPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GoogleTest {
    protected WebDriver driver;

    protected GoogleHomePage homePage;
    protected GoogleSearchResultPage resultPage;

    //@Test
    public void testFindNLinks (){
        WebDriverManager manager = WebDriverManager.getInstance("chrome");
        manager.setup();
        driver = manager.create();

        homePage = new GoogleHomePage(driver);
        homePage.navigate();

        resultPage = new GoogleSearchResultPage(driver);
        homePage.search("Test");

        List<String> links = resultPage.findLinks(5);
        Assert.assertNotNull(links);
        Assert.assertTrue(driver.getCurrentUrl().contains("Test"));
    }
}
