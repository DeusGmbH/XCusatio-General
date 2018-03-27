package com.deusgmbh.xcusatio.api.services;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentDetails;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentLocation;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentTimes;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentType;
import com.deusgmbh.xcusatio.context.wildcard.TrafficContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class TrafficAPI extends APIService {

    public static void main(String[] selenium) {
        TrafficAPI trafficAPI = new TrafficAPI();
        trafficAPI.setBaseUrlText("https://www.google.com");
        trafficAPI.requestWebsite();
        trafficAPI.getResponseFromWebsite();
    }

    @Override
    public TrafficContext get(UserSettings usersettings) {

        TrafficIncidentDetails trd = new TrafficIncidentDetails(TrafficIncidentType.CONSTRUCTION, "Baustelle",
                "Strasse wegen Baustelle gesperrt");
        TrafficIncidentLocation tri = new TrafficIncidentLocation("DE", "Mannheim", "Seckenheimer Landstr.");

        TrafficIncidentTimes trt = new TrafficIncidentTimes("6:00", "10:30");

        TrafficContext trafficContext = new TrafficContext(trd, tri, trt);

        return trafficContext;
    }

    @Override
    public void transmitDataToWebsite() {

    }

    @Override
    public void extractDesiredInfoFromResponse() {
        // TODO Auto-generated method stub

    }

    // public static void main(String[] uranium) {
    // UserSettings userSettings = new UserSettings(null, 0, null, null, null,
    // null, null);
    // TrafficAPI trafficAPI = new TrafficAPI();
    // TrafficContext trafficContext = trafficAPI.get(userSettings);
    // trafficContext.printContextContent();
    // }

}