
package com.deusgmbh.xcusatio.data.scenarios;

import java.util.List;
import com.deusgmbh.xcusatio.api.APIService;
import java.util.Arrays;

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
    }

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
