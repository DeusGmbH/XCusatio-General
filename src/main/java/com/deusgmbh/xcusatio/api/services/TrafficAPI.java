package com.deusgmbh.xcusatio.api.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.GeocodeData;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentDetails;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentLocation;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentStatus;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentTimes;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentType;
import com.deusgmbh.xcusatio.context.wildcard.TrafficContext;
import com.deusgmbh.xcusatio.data.usersettings.Address;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class TrafficAPI extends APIService {

    private static final Logger LOGGER = Logger.getLogger(TrafficAPI.class.getName());

    public TrafficAPI() {

    }

    @Override
    public TrafficContext get(UserSettings usersettings) {
        /*
         * creating a dummy context
         * 
         * 
         */
        TrafficIncidentDetails trd = new TrafficIncidentDetails(TrafficIncidentType.CONSTRUCTION,
                TrafficIncidentStatus.ACTIVE, "Strasse wegen Baustelle gesperrt");
        TrafficIncidentLocation tri = new TrafficIncidentLocation(
                new GeocodeData(new Address("Coblitzallee", "6", "68163", "Mannheim")));
        String dateFormat = "MM/dd/yyyy hh:mm:ss";
        Date startTime = null, endTime = null;
        try {
            startTime = new SimpleDateFormat(dateFormat).parse("03/29/2018 06:05:11");
            endTime = new SimpleDateFormat(dateFormat).parse("03/29/2018 17:12:27");
        } catch (Exception e) {
            LOGGER.info("parsed wrong date format, " + e.getMessage());
        }
        TrafficIncidentTimes trt = new TrafficIncidentTimes(startTime, endTime);
        TrafficContext trafficContext = new TrafficContext(trd, tri, trt);
        return trafficContext;
        /*
         * end of dummy context
         * 
         * 
         */

        // TODO getTrafficIncidentType
        // TODO getTrafficIncidentShortDescription
        // TODO getTrafficIncidentDescription
        // TODO getTrafficIncidentLocationStreet
        // TODO setTrafficIncidentLocationRest --> obtained from userSettings
        // TODO getTrafficIncidentStartTime
        // TODO getTrafficIncidentEndTime
    }

    @Override
    public void transmitDataToWebsite() {

    }

    @Override
    public void extractDesiredInfoFromResponse() {

    }

    // public static void main(String[] selenium)
    // throws MalformedURLException, IOException, ParserConfigurationException,
    // SAXException {
    // TrafficAPI trafficAPI = new TrafficAPI();
    // TrafficContext trafficContext = trafficAPI.get(null);
    // trafficContext.logContextContent();
    //
    // }

}