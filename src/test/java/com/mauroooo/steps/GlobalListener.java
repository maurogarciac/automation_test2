package com.mauroooo.steps;

import com.mauroooo.scripts.SaveScreenshots;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class GlobalListener {

    @BeforeAll()
    public static void beforeAll(){
        System.setProperty("webdriver.chrome.silentOutput", "true");
        System.setProperty("webdriver.edge.silentOutput", "true");
        System.setProperty("webdriver.firefox.logfile", "/dev/null");
        System.setProperty("webdriver.ie.driver.silent", "true");
        System.setProperty("webdriver.opera.silentOutput", "true");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @AfterAll
    public static void afterAll(){

    }
}
