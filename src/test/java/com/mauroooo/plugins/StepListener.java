package com.mauroooo.plugins;

import io.cucumber.plugin.event.Node.Scenario;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestStep;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;


//correr todo con ThreadLocal<StepListener> y le decis get().CurrentStep(currentStep)
//vos vas a tener un mapa al que le decis get() y ese thread te devuelve un mapa de acuerdo a ese thread.
//como un multiton pero de threads
//el esquema de singleton en TestNG es estatico?
//se podria sacar la screenshot desde este listenener
//crear un handler para el Scenario tambien
public class StepListener implements ConcurrentEventListener {

    //protected SaveScreenshots screenshots;

    //protected WebDriver driver;
    //protected String stepName;
    //String scenarioName;
    //protected String stepId;

    //private StepListener(){
        //screenshots = new SaveScreenshots(driver);
    //}
    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestStepFinished.class, this::handleStepFinished);
        eventPublisher.registerHandlerFor(TestStep.class, this::handleStep);
        eventPublisher.registerHandlerFor(TestStepStarted.class, this::handleStepStarted);
        eventPublisher.registerHandlerFor(TestCase.class, this::handleTestCase);
        //eventPublisher.registerHandlerFor(WebDriver.class, this::handleWebDriver);
    }
    //public void handleWebDriver(Before event){
    //    driver = event.getClass().getDeclaredConstructor(Class<WebDriver>);
    //    screenshots = new SaveScreenshots(driver);
    //}
    public void handleStep(TestStep event){
        System.out.println("TestStep === " + event.getId());
        //seguramente tengo acceso al map de cosas del Test y dado ese mapa puedo pedir el dato con el UUID
        //un listener del Scenario y el le pido el step que tenga ese id
    }
    public void handleStepFinished(TestStepFinished event){
        System.out.println("TestStepFinished === " + event.getTestStep());
        //stepName = event.getTestStep().toString();
        //stepId = event.getTestStep().getId().toString();
    }
    public void handleStepStarted(TestStepStarted event){
        System.out.println("TestStepStarted === " + event.getTestStep());
        //stepName = event.getTestStep().toString();
        //stepId = event.getTestStep().getId().toString();
    }
    public void handleTestCase(TestCase event){
        System.out.println("TestCase:" + event.getTestSteps());
    }
    //Se pueden conseguir estos atributos usando reflections?
    //crear un plugin que es un Listener y pasarselo a las cucumber options
    //screenshots.savePicture("after", stepName, scenarioName, stepId);
}
