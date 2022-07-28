package com.mauroooo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//import java.time.Duration;


public class GoogleHomePage {
    public static final String URL= "https://www.google.com.ar";
    private static final int TIME_OUT_FOR_BUTTON = 15;
    //static Duration ELEMENT_LOAD_TIMEOUT = Duration.ofSeconds(5);
    protected WebDriver driver;

    @FindBy(name = "q") protected WebElement input; 
    @FindBy(name = "btnK") protected WebElement submit;
    @FindBy(name = "btnI") protected WebElement luckySubmit;

    public GoogleHomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigate(){
        driver.get(URL);
    }

    public void search(String text){
        search(text, false);
    }

    public GoogleSearchResultPage search(String text, boolean isLucky){
        this.input.sendKeys(text);
        //add catch for the list that appears suggesting test results
        int cnt = 1;
        do {
            //WebDriverWait(driver, ELEMENT_LOAD_TIMEOUT).until(visibilityOfElementLocated(submit));
            System.out.println( cnt + " Mississippi");
            cnt++;
        }
        while(!submit.isDisplayed() || cnt == TIME_OUT_FOR_BUTTON);
        if(isLucky){
            this.luckySubmit.click();
        } else{
            this.submit.click();
        }
        return new GoogleSearchResultPage(this.driver);
    }

}
