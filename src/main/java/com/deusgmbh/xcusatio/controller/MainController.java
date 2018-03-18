package com.deusgmbh.xcusatio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.context.wildcard.Wildcards;

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
    private Wildcards wildcards;

    public MainController() {
        wildcards = new Wildcards();
    }

    public List<String> getWildcardNames() {
        return wildcards.getNames();
    }
    
    public void generateExcuse(String excuseType) {
        // TODO: Write generateExcuse method
    }

    public List<Scenario> getScenarioList() {
        List<Scenario> scenarioList = new ArrayList<Scenario>();
        // TODO Get all scenarios
        return scenarioList;
    }
}
