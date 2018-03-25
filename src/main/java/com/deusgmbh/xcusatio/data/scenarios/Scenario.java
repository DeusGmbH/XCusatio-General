
package com.deusgmbh.xcusatio.data.scenarios;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Scenario {
    // following Section is only for UI-testing-purposes and can be deleted
    // afterwards
    private ScenarioType scenarioType;

    public void setScenarioType(ScenarioType type) {
        this.scenarioType = type;
    }

    public ScenarioType getScenarioType() {
        return scenarioType;
    }
}
