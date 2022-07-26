package com.mauroooo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class GoogleHomePage {
    public static final String URL= "https://www.google.com.ar";
    protected WebDriver driver;

    @FindBy(name = "q") protected WebElement input; 
    @FindBy(name = "btnK") protected WebElement submit;
    @FindBy(name = "btnI") protected WebElement luckySubmit;

    public GoogleHomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigate(WebDriver driver){
        driver.get(URL);
    }

    public void search(String text){
        search(text, false);
    }

    public GoogleSearchResultPage search(String text, boolean isLucky){
        this.input.sendKeys(text);

        if(isLucky){
            this.luckySubmit.click();
        } else{
            this.submit.click();
        }
        return new GoogleSearchResultPage(this.driver);
    }

}
