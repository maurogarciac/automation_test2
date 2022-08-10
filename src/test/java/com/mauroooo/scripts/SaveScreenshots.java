package com.mauroooo.scripts;


import io.cucumber.core.gherkin.Step;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;


public class SaveScreenshots {
    private static final Logger logger = LoggerFactory.getLogger(SaveScreenshots.class);

    protected File screenshotDirectory = new File("/screenshots");
    protected WebDriver driver;
    // sanitizar el scenario

    public SaveScreenshots(WebDriver driver) {
        this.driver = driver;
    }

    private File makeDirectory(Scenario scenario) {
        //variable that declares the name of the new dir
        String NewDirName = Calendar.DAY_OF_YEAR + "-" + Calendar.HOUR_OF_DAY + "hs-" + Calendar.MINUTE + "mins-" + scenario.getName();
        if (screenshotDirectory.mkdirs()) {
            logger.info("Created new Screenshots directory at:" + screenshotDirectory.getPath());
        } else {
            logger.info("Screenshots will be saved in the existing Screenshots directory at:" + screenshotDirectory.getPath());
        }
        File newPath = new File(screenshotDirectory, NewDirName); //
        //check if directory exists, and create it if it doesn't
        File candidate = newPath;
        int i = 0;
        while (true) {
            if (candidate.mkdirs()) {
                return candidate;
            } else if (candidate.exists()) {
                i++;
                candidate = new File(newPath + "_" + i);
            }
        }
    }


    public void savePicture(String beforeAfter, Scenario scenario, Step step) {
        //now take the screenshot and save it in the new directory

        String stepName = "";
        if (this.driver != null) {
            stepName = step.getText();
        }
        try {
            if (step.getId() != null) { // get table actually
                stepName = stepName + "_" + step.getId();
            }
            File screenshotFileNamePrefix = new File(makeDirectory(scenario), stepName + "-" + beforeAfter + "-full.png");
            WebElement element = driver.findElement(By.cssSelector("body"));
            File screenshotFile = element.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, screenshotFileNamePrefix);
            //this.driver.save_screenshot(screenshotFileNamePrefix + ".png");
        } catch (IOException e) {
            logger.error("An error occurred");
            e.printStackTrace();
        }
    }
}
