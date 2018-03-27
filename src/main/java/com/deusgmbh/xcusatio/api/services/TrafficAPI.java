package com.deusgmbh.xcusatio.api.services;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentDetails;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentLocation;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentTimes;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentType;
import com.deusgmbh.xcusatio.context.wildcard.TrafficContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class TrafficAPI extends APIService {

    public TrafficAPI(String baseUrlText) {
        setBaseUrlText(baseUrlText);
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

    // public static void main(String[] selenium) throws MalformedURLException,
    // IOException {
    // TrafficAPI trafficAPI = new TrafficAPI(
    // "https://traffic.cit.api.here.com/traffic/6.2/incidents/xml/8/134/86?app_id=ObXv79Ww3xdQ996uEDLw&app_code=74fsgcSubek54INvT13Rcg");
    // trafficAPI.requestWebsite();
    // trafficAPI.getResponseFromWebsite();
    // }

}