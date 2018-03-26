
package com.deusgmbh.xcusatio.data.scenarios;

import java.util.ArrayList;
import java.util.Arrays;
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

    public Scenario(ScenarioType type) {
        this.scenarioType = type;
        this.requiredAPIs = new ArrayList<>();
    }

    public Scenario setScenarioType(ScenarioType type) {
        this.scenarioType = type;
        return this;
    }

    public ScenarioType getScenarioType() {
        return scenarioType;
    }

    public List<Class<? extends APIService>> getRequiredAPIs() {
        return requiredAPIs;
    }

    public Scenario setRequiredAPIs(List<Class<? extends APIService>> requiredAPIs) {
        this.requiredAPIs = requiredAPIs;
        return this;
    }

    public Scenario addRequiredAPI(Class<? extends APIService> requiredAPI) {
        this.requiredAPIs.add(requiredAPI);
        return this;
    }

    /**
     * 
     * @returns true if the {@link ScenarioType} should be associated with a
     *          text based excuse
     */
    public boolean isExcuseType() {
        return Arrays.asList(ScenarioType.LATE_ARRIVAL, ScenarioType.DELAYED_SUBMISSION, ScenarioType.WHEELOFFORTUNE)
                .contains(this.getScenarioType());
    }
}
