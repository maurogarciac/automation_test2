package com.mauroooo.scripts;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Calendar;


public class SaveScreenshots {
    private static final Logger logger = LoggerFactory.getLogger(SaveScreenshots.class);
    public static volatile SaveScreenshots instance;
    static Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(5);
    protected static File finalDirectory;

    protected static final File screenshotDirectory = new File("screenshots");

    protected static WebDriver driver;


    public SaveScreenshots(WebDriver driver) {
        SaveScreenshots.driver = driver;
    }

    public static SaveScreenshots getInstance(WebDriver driver){
        if (instance == null){
            synchronized (SaveScreenshots.class){
                if (instance == null) {
                    instance = new SaveScreenshots(driver);
                }
           }
        }
        return instance;
    }


    public static File makeDirectory(String scenarioName) {
        //variable that declares the name of the new dir
        String NewDirName = Calendar.DAY_OF_YEAR + "-" + Calendar.HOUR_OF_DAY + "hs-" + Calendar.MINUTE + "mins-" + scenarioName;
        if (screenshotDirectory.mkdirs()) {
            String text = "Created new Screenshots directory at:" + screenshotDirectory.getAbsolutePath();
            logger.info(text);
            System.out.println(text);
        } else {
            String text = "Screenshots will be saved in the existing Screenshots directory at:" + screenshotDirectory.getAbsolutePath();
            logger.info(text);
            System.out.println(text);
        }
        File newPath = new File(screenshotDirectory, NewDirName);
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


    public static void savePicture(String beforeAfter, String stepName, File finalDir) {
        //now take the screenshot and save it in the new directory
        finalDirectory = finalDir;
        try {
            int i = 1;
            File screenshotFileNamePrefix;
            screenshotFileNamePrefix = new File(String.valueOf(finalDirectory), stepName + "-" + beforeAfter + "-full-" + i + ".png");
            if (finalDirectory.isDirectory()) {
                while(screenshotFileNamePrefix.exists()) {
                    i++;
                    screenshotFileNamePrefix = new File(stepName + "-" + beforeAfter + "-full-" + i + ".png");
                }
            }
            WebElement element = new WebDriverWait(driver, PAGE_LOAD_TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));
            File screenshotFile = element.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, screenshotFileNamePrefix);
            //this.driver.save_screenshot(screenshotFileNamePrefix + ".png");
        } catch (IOException e) {
            logger.error("An error occurred");
            e.printStackTrace();
        }
    }
}
