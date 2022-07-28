package com.mauroooo.scripts;

import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class TakeScreenshots {
    Date date = new Date();
    static final String SCREENSHOTS_DIR = "/C/Users/guy/Desktop/screenshots";
    protected WebDriver driver;

    public TakeScreenshots(WebDriver driver){
        this.driver = driver;
    }
    /*
    public String makeDirectory(){
        //variable that declares the name of the new dir
        String NewDirName = Calendar.DAY_OF_YEAR + "-" + Calendar.HOUR_OF_DAY + "hs-" + Calendar.MINUTE + "mins-" + this.feature.name;
        new File(SCREENSHOTS_DIR).mkdirs();
        String newPath = os.path.join(SCREENSHOTS_DIR, NewDirName); //
        //check if directory exists, and create it if it doesn't
        String candidate = newPath;
        boolean created = false;
        int i = 0;
        while(!created){
            try {
                new File(candidate).mkdirs();
                created = true;
            }
            catch(Exception alreadyCreated){
                i++;
                candidate = newPath + "_" + i;
            }
        }
        return candidate;
    }



    public void savePicture(String beforeAfter) {
        //now take the screenshot and save it in the new directory
        String stepName = "";
        if ("driver" in this && this.driver is not None){
            stepName = this.step.line;
        }
        if (this.step.table is not None){
            stepName = stepName + "_" + this.step.table.iteration;
            String screenshotFileNamePrefix = os.path.join(SCREENSHOTS_DIR, this.scenario.name + "-" + this.step.line + "-" + beforeAfter);
            this.driver.find_element_by_tag_name("body").screenshot(screenshotFileNamePrefix + "-full.png");
            this.driver.save_screenshot(screenshotFileNamePrefix + ".png");
        }
    }*/
}
