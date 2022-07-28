package com.mauroooo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;


public class GoogleHomePage {
    public static final String URL = "https://www.google.com.ar";
    static Duration ELEMENT_LOAD_TIMEOUT = Duration.ofSeconds(5);
    protected WebDriver driver;

    @FindBy(name = "q")
    protected WebElement input;
    @FindBy(name = "btnK")
    protected WebElement submit;
    @FindBy(name = "btnI")
    protected WebElement luckySubmit;

    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigate() {
        driver.get(URL);
    }

    public void search(String text) {
        search(text, false);
    }

    public GoogleSearchResultPage search(String text, boolean isLucky) {
        this.input.sendKeys(text);
        new WebDriverWait(driver, ELEMENT_LOAD_TIMEOUT).until(visibilityOf(isLucky ? luckySubmit : submit)).click();
        return new GoogleSearchResultPage(this.driver);
    }

}
