package com.mauroooo.scripts;

import java.util.Calendar;
import java.util.Date;

public class TakeScreenshots {
    Date date = new Date();


    public void makeDirectory(){
        /*
        //variable that declares the name of the new dir
        String NewDirName = Calendar.DAY_OF_YEAR + "-" + Calendar.HOUR_OF_DAY + "hs-" + Calendar.MINUTE + "mins-" + this.feature.name;
        String cwd = System.getProperty("user.dir");
        String screenshots_directory = os.path.join(cwd, "screenshots");
        os.makedirs(screenshots_directory, exist_ok = True);
        String new_path = os.path.join(screenshots_directory, NewDirName);
        //check if directory exists, and create it if it doesn't
        String candidate = new_path;
        boolean created = false;
        int i = 0;
        while(!created){
            try {
                os.mkdir(candidate);
                created = True;
            }
            catch(Exception alreadyCreated){
                i++;
                candidate = new_path + "_" + i;
            }
        }
        return candidate;

         */
    }



    public void savePicture(){
        
    }
}
