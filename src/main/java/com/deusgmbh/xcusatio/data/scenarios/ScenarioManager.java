package com.deusgmbh.xcusatio.data.scenarios;

import com.deusgmbh.xcusatio.api.services.CalendarAPI;
import com.deusgmbh.xcusatio.api.services.RNVAPI;
import com.deusgmbh.xcusatio.api.services.TrafficAPI;
import com.deusgmbh.xcusatio.api.services.WeatherAPI;
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
        this.add(new Scenario(ScenarioType.THUMBGESTURE))
                .add(new Scenario(ScenarioType.WHEELOFFORTUNE).addRequiredAPI(CalendarAPI.class)
                        .addRequiredAPI(RNVAPI.class)
                        .addRequiredAPI(TrafficAPI.class)
                        .addRequiredAPI(WeatherAPI.class))
                .add(new Scenario(ScenarioType.DELAYED_SUBMISSION).addRequiredAPI(CalendarAPI.class)
                        .addRequiredAPI(RNVAPI.class)
                        .addRequiredAPI(TrafficAPI.class)
                        .addRequiredAPI(WeatherAPI.class))
                .add(new Scenario(ScenarioType.LATE_ARRIVAL).addRequiredAPI(CalendarAPI.class)
                        .addRequiredAPI(RNVAPI.class)
                        .addRequiredAPI(TrafficAPI.class)
                        .addRequiredAPI(WeatherAPI.class));
        return this;
    }

}
