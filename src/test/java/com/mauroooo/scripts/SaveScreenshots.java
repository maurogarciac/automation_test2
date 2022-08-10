package com.mauroooo.scripts;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebElement;


import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class TakeScreenshots {
    Date date = new Date();
    protected File screenshotDirectory = new File("/C/Users/guy/Desktop/screenshots");
    protected WebDriver driver;
    protected Scenario scenario;

    public TakeScreenshots(WebDriver driver){
        this.driver = driver;
    }

    public File makeDirectory(){
        //variable that declares the name of the new dir
        String NewDirName = Calendar.DAY_OF_YEAR + "-" + Calendar.HOUR_OF_DAY + "hs-" + Calendar.MINUTE + "mins-" + scenario.getId();
        if (!screenshotDirectory.mkdirs()){
            System.out.println("Screenshots will be saved in the existing Screenshots directory at:" + screenshotDirectory.getPath());
        } else{
            System.out.println("Created new Screenshots directory at:" + screenshotDirectory.getPath());
        }
        File newPath = new File(screenshotDirectory, NewDirName); //
        //check if directory exists, and create it if it doesn't
        File candidate = newPath;
        int i = 0;
        while(true){
            if(candidate.mkdirs()) {
                return candidate;
            }else if(candidate.exists()){
                i++;
                candidate = new File(newPath + "_" + i);
            }
        }
    }



    public void savePicture(String beforeAfter) {
        //now take the screenshot and save it in the new directory
        String stepName = "";
        if (this.driver != null){
            stepName = scenario.getName(); //da elnombre del scenario no del step
        }
        if (this.step.table != null){
            stepName = stepName + "_" + this.step.table.iteration;
            File screenshotFileNamePrefix = new File(screenshotDirectory + this.scenario.name + "-" + this.step.line + "-" + beforeAfter + "-full.png");
            WebElement element = driver.findElement(By.cssSelector("body"));
            File screenshotFile = element.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, screenshotFileNamePrefix);
            //this.driver.save_screenshot(screenshotFileNamePrefix + ".png");
        }
    }
}
