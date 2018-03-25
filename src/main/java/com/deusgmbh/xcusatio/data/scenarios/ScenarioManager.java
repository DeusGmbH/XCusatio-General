package com.deusgmbh.xcusatio.data.scenarios;

import com.deusgmbh.xcusatio.data.StorageUnit;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class ScenarioManager extends StorageUnit<Scenario> {

    public ScenarioManager() {
        super(Scenario.class);
    }

    @Override
    public StorageUnit<Scenario> addDefaultValues() {

        this.add(new Scenario(ScenarioType.WHEELOFFORTUNE));
        this.add(new Scenario(ScenarioType.DELAYED_SUBMISSION));
        this.add(new Scenario(ScenarioType.LATE_ARRIVAL));
        this.add(new Scenario(ScenarioType.THUMBGESTURE));
        return this;
    }

}
