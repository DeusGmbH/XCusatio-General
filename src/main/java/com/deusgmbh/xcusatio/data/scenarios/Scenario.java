package com.deusgmbh.xcusatio.data.scenarios;

public class Scenario {
    // following Section is only for UI-testing-purposes and can be deleted
    // afterwards
    private String UIName;

    public Scenario(String name) {
        this.UIName = name;
    }

    public void setUIName(String name) {
        this.UIName = name;
    }

    public String getUIName() {
        return this.UIName;
    }

}
