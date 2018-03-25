
package com.deusgmbh.xcusatio.data.scenarios;

import java.util.Arrays;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Scenario {
    // following Section is only for UI-testing-purposes and can be deleted
    // afterwards
    private ScenarioType scenarioType;

    public Scenario(ScenarioType type) {
        this.scenarioType = type;
    }

    public void setScenarioType(ScenarioType type) {
        this.scenarioType = type;
    }

    public ScenarioType getScenarioType() {
        return scenarioType;
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
