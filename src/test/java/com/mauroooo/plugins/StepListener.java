package com.mauroooo.plugins;

import io.cucumber.messages.types.PickleStep;
import io.cucumber.messages.types.PickleTable;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestStepFinished;


//correr todo con un singleton con un threadLocal que le decis getCurrentStep(currentStep)
//se podria sacar la screenshot desde este listenener
//crear un handler para el Scenario tambien
public class StepListener implements ConcurrentEventListener {
    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestStepFinished.class, this::handleStepFinished);
        eventPublisher.registerHandlerFor(PickleTable.class, this::handlePickleTable);
        eventPublisher.registerHandlerFor(PickleStep.class, this::handlePickleStep);
    }

    public void handleStepFinished(TestStepFinished event){
        System.out.println(event.getTestStep());
    }
    public void handlePickleStep(PickleStep event){
        System.out.println(event.getText());
    }
    public void handlePickleTable(PickleTable event){
        System.out.println(event.getRows());
    }
}
