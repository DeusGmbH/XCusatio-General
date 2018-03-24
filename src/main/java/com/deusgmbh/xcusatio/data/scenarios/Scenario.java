
package com.deusgmbh.xcusatio.data.scenarios;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Scenario {
    // following Section is only for UI-testing-purposes and can be deleted
    // afterwards
    private String UIName;
    private ScenarioType scenarioType;

    public Scenario(String name) {
        this.UIName = name;
    }

    public void setUIName(String name) {
        this.UIName = name;
    }

    public String getUIName() {
        return this.UIName;
    }

    public void setScenarioType(ScenarioType type) {
        this.scenarioType = type;
    }

    public ScenarioType getScenarioType() {
        return scenarioType;
    }

}
