package com.deusgmbh.xcusatio.api.services;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.context.wildcard.TrafficContext;
import com.deusgmbh.xcusatio.context.wildcard.TrafficIncidentDetails;
import com.deusgmbh.xcusatio.context.wildcard.TrafficIncidentLocation;
import com.deusgmbh.xcusatio.context.wildcard.TrafficIncidentTimes;
import com.deusgmbh.xcusatio.context.wildcard.TrafficIncidentType;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class TrafficAPI extends APIService {

    @Override
    public TrafficContext get(UserSettings usersettings) {

        TrafficIncidentDetails trd = new TrafficIncidentDetails(TrafficIncidentType.CONSTRUCTION, "Baustelle",
                "Strasse wegen Baustelle gesperrt");
        TrafficIncidentLocation tri = new TrafficIncidentLocation("DE", "Mannheim", "Seckenheimer Landstr.");

        TrafficIncidentTimes trt = new TrafficIncidentTimes("6:00", "10:30");

        TrafficContext trafficContext = new TrafficContext(trd, tri, trt);

        return trafficContext;
    }

}
