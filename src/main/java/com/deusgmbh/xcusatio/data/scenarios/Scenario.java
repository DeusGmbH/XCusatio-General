
package com.deusgmbh.xcusatio.data.scenarios;

import java.util.List;

import com.deusgmbh.xcusatio.api.APIService;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Scenario {

    private ScenarioType scenarioType;
    List<Class<? extends APIService>> requiredAPIs;

    public void setScenarioType(ScenarioType type) {
        this.scenarioType = type;
    }

    public ScenarioType getScenarioType() {
        return scenarioType;
    }

    public List<Class<? extends APIService>> getRequiredAPIs() {
        return requiredAPIs;
    }

    public void setRequiredAPIs(List<Class<? extends APIService>> requiredAPIs) {
        this.requiredAPIs = requiredAPIs;
    }

    public Scenario addRequiredAPIs(Class<? extends APIService> requiredAPI) {
        this.requiredAPIs.add(requiredAPI);
        return this;
    }

}
