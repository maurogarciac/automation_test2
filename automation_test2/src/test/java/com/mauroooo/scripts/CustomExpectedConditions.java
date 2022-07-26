package com.mauroooo.scripts;


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
    * @return the list of WebElements once they are located
    */
    public static ExpectedCondition<List<WebElement>> visibilityOfNElementsLocated(By locator, int number){
        return new ExpectedCondition<List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement element : elements) {
                    if (!element.isDisplayed()) {
                    return null;
                    }
                }
                List<WebElement> result = new List<WebElement>();
                for (WebElement element : elements){
                    if (elements.size() >= number){
                        int iter = number;
                        while (iter >= 0){
                            iter--;
                            result.add(element);
                        }
                    }else{result = null;}
                }
                return result;
            }
      
            @Override
            public String toString() {
                return "visibility of all elements located by " + locator;
            }
        };
    }
}