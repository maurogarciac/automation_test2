package com.mauroooo.pages;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mauroooo.scripts.CustomExpectedConditions;


public class GoogleSearchResultPage {

    static Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(5);
    protected WebDriver driver;
    protected static By groupSelector = By.cssSelector("#rso .g [data-header-feature] > div:first-child > a");
    

    public GoogleSearchResultPage(WebDriver driver){
        this.driver = driver;
    }
        
    public List<String> findLinks(int amount){
        
        List<WebElement> resultAnchors = new WebDriverWait(driver, PAGE_LOAD_TIMEOUT).until(CustomExpectedConditions.visibilityOfAtLeastNElementsLocated(groupSelector, amount));
        List<String> fiveLinks = resultAnchors.stream().map(e -> e.getAttribute("href")).collect(Collectors.toList());

        return fiveLinks;

    }
        
        

}
