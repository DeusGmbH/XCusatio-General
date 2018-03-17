package com.deusgmbh.xcusatio.data.scenarios;

public class Scenario {
    // following Section is only for UI-testing-purposes and can be deleted
    // afterwards
    private String UIName;
    private String scenarioType;

    public Scenario(String name) {
        this.UIName = name;
    }

    public void setUIName(String name) {
        this.UIName = name;
    }

    public String getUIName() {
        return this.UIName;
    }

    public void setScenarioType(String type) {
        this.scenarioType = type;
    }

    public String getScenarioType() {
        return scenarioType;
    }

}
