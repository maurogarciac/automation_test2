package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features="src/test/resources/features/Google.feature", 
    glue="src/test/java/pages"
    )

public class Runner extends AbstractTestNGCucumberTests{
    
}
