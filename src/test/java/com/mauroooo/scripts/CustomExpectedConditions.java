package com.mauroooo.scripts;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomExpectedConditions {

    protected WebDriver driver;

    /**
    * An expectation for checking that the required number of elements are present on the DOM of a
    * page and visible. Visibility means that the elements are not only displayed
    * but also has a height and width that is greater than 0.
    *
    * @param locator used to find the elements
    * @param number used to determine the number of elements to find
    * @return the list of the first N visible WebElements in DOM order
    */
    public static ExpectedCondition<List<WebElement>> visibilityOfAtLeastNElementsLocated(By locator, int number){
        if(number <= 0){
            throw new IllegalArgumentException("The number of elements to find must be larger than 0");
        }
        
        return new ExpectedCondition<List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(locator);
                List<WebElement> results = new ArrayList<WebElement>(number);

                for (WebElement element : elements) {
                    if (results.size() >= number){
                        return results;
                    }
                    if (element.isDisplayed()) {
                        results.add(element);
                    }
                }
                return null;
            }
      
            @Override
            public String toString() {
                return "visibility of all elements located by " + locator;
            }
        };
    }

}