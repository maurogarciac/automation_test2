package com.mauroooo;
//para vscode necesito que el classpath este integrado

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(plugin = "com.mauroooo.plugins.StepListener")
public class RunCucumberTestNGTest extends AbstractTestNGCucumberTests {


}