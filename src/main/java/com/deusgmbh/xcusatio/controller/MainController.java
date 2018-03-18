package com.deusgmbh.xcusatio.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.data.scenarios.Scenario;

/**
 * This class handles inputs of the userinterface via an event listener
 * interface and serves as mediator between the userinterface and the text
 * processing
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class MainController {
    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

    public MainController() {
    }

    public void generateExcuse(Scenario scenario) {
        // TODO: Write generateExcuse method
    }

    public ArrayList<Scenario> getScenarioList() {
        ArrayList<Scenario> scenarioList = new ArrayList<Scenario>();
        // TODO Get all scenarios
        return scenarioList;
    }
}
