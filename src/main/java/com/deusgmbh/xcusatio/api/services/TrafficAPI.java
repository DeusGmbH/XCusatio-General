package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.Sex;

public class TrafficAPI extends APIService {
    private static final Logger LOGGER = Logger.getLogger(TrafficAPI.class.getName());

    private final String BASE_URL = "https://traffic.cit.api.here.com/traffic/6.2/incidents/json/";
    private final String APP_KEYS = "?app_id=ObXv79Ww3xdQ996uEDLw&app_code=74fsgcSubek54INvT13Rcg";
    private final String JSONOB_TRAFFIC_ITEMS = "TRAFFIC_ITEMS";
    private final String JSONARR_TRAFFIC_ITEM = "TRAFFIC_ITEM";
    private final String JSONOB_LOCATION = "LOCATION";
    private final String JSONOB_LOCATION_DEFINED = "DEFINED";
    private final String JSONOB_DEFINED_ORIGIN = "ORIGIN";
    private final String JSONOB_ORIGIN_ROADWAY = "ROADWAY";
    private final String JSONARR_ROADWAY_DESCRIPTION = "DESCRIPTION";
    private final String JSONSTR_DESCRIPTION_VALUE = "VALUE";
    private final String JSONSTR_INCIDENT_TYPE = "TRAFFIC_ITEM_TYPE_DESC";
    private final String JSONSTR_INCIDENT_STATUS = "TRAFFIC_ITEM_STATUS_SHORT_DESC";
    private final String JSONARR_INCIDENT_DESCRIPTION = "TRAFFIC_ITEM_DESCRIPTION";
    private final String JSONSTR_INCIDENT_DESCRIPTION_FIRST = "value";
    private final String JSONSTR_INCIDENT_ENTRY_TIME = "ENTRY_TIME";
    private final String JSONSTR_INCIDENT_END_TIME = "END_TIME";
    private final String JSONOB_INCIDENT_CRITICALITY = "CRITICALITY";
    private final String JSONSTR_CRITICALITY_DESCRIPTION = "DESCRIPTION";
    private final String JSONOB_TRAFFIC_ITEM_DETAIL = "TRAFFIC_ITEM_DETAIL";
    private final String JSONSTR_DETAIL_ROAD_CLOSED = "ROAD_CLOSED";

    // https://traffic.cit.api.here.com/traffic/6.0/incidents.json

    private String jsonResponse;
    private GeocodeData geocodeData;
    private String mapLowText;
    private String mapMediumText;
    private String mapHighText;
    private URL requestUrlLow, requestUrlMedium, requestUrlHigh;
    private List<TrafficIncidentDetails> incidentDetails;
    private List<TrafficIncidentLocation> incidentLocation;
    private List<TrafficIncidentTimes> incidentTimes;

    public TrafficAPI() {

    }

    @Override
    public TrafficContext get(UserSettings usersettings) {
        /*
         * creating a dummy context
         * 
         * 
         */
        List<TrafficIncidentDetails> trdList = new LinkedList<>();
        trdList.add(new TrafficIncidentDetails(TrafficIncidentType.CONSTRUCTION, TrafficIncidentStatus.ACTIVE,
                "Strasse wegen Baustelle gesperrt"));

        List<TrafficIncidentLocation> triList = new LinkedList<>();
        triList.add(
                new TrafficIncidentLocation(new GeocodeData(new Address("Coblitzallee", "6", "68163", "Mannheim"))));

        String dateFormat = "MM/dd/yyyy hh:mm:ss";
        Date startTime = null, endTime = null;
        try {
            startTime = new SimpleDateFormat(dateFormat).parse("03/29/2018 06:05:11");
            endTime = new SimpleDateFormat(dateFormat).parse("03/29/2018 17:12:27");
        } catch (Exception e) {
            LOGGER.info("parsed wrong date format, " + e.getMessage());
        }

        List<TrafficIncidentTimes> trtList = new LinkedList<>();
        trtList.add(new TrafficIncidentTimes(startTime, endTime));

        TrafficContext trafficContext = new TrafficContext(trdList, triList, trtList);
        return trafficContext;

    }

    @Override
    public void transmitDataToWebsite() {

    }

    @Override
    public void extractDesiredInfoFromResponse() throws JSONException {
        getResponseFromWebsite();
        JSONObject jsonTotal = new JSONObject(this.jsonResponse);

        if (jsonTotal.has(JSONOB_TRAFFIC_ITEMS)) {
            JSONObject trafficItems = jsonTotal.getJSONObject(JSONOB_TRAFFIC_ITEMS);
            System.out.println("* " + trafficItems.toString());

            /* get the list of all traffic items */
            List<JSONObject> trafficItemList = getJSONObjectsFromJSONArray(trafficItems, JSONARR_TRAFFIC_ITEM);

            /*
             * get 1. item status short description 2. item type description 3.
             * entry time 4. end time 5. location
             * 
             */

            // TODO 1
            List<String> shortDescriptions = getValuesFromJSONObjects(trafficItemList, JSONSTR_INCIDENT_STATUS);
            shortDescriptions.forEach(s -> System.out.println(s));

            // TODO 2
            List<String> incidentTypes = getValuesFromJSONObjects(trafficItemList, JSONSTR_INCIDENT_TYPE);
            incidentTypes.forEach(s -> System.out.println(s));

            // TODO 3 convert to Date later
            List<String> entryTimes = getValuesFromJSONObjects(trafficItemList, JSONSTR_INCIDENT_ENTRY_TIME);
            entryTimes.forEach(s -> System.out.println(s));

            // TODO 4 convert to Date later
            List<String> endTimes = getValuesFromJSONObjects(trafficItemList, JSONSTR_INCIDENT_END_TIME);
            endTimes.forEach(s -> System.out.println(s));

            /* get incident location */
            List<JSONObject> locationList = goInside(trafficItemList, JSONOB_LOCATION);
            List<JSONObject> definedLocations = goInside(locationList, JSONOB_LOCATION_DEFINED);
            List<JSONObject> locOrigins = goInside(definedLocations, JSONOB_DEFINED_ORIGIN);
            List<JSONObject> roadways = goInside(locOrigins, JSONOB_ORIGIN_ROADWAY);
            List<JSONObject> rdWaysDescriptions = new LinkedList<>();
            for (int i = 0; i < roadways.size(); ++i) {
                rdWaysDescriptions.addAll(getJSONObjectsFromJSONArray(roadways.get(i), JSONARR_ROADWAY_DESCRIPTION));
            }
            List<String> streetNamesOfIncidents = getValuesFromJSONObjects(rdWaysDescriptions,
                    JSONSTR_INCIDENT_DESCRIPTION_FIRST);
            streetNamesOfIncidents.forEach(s -> System.out.println(s));

            /* get incident type */

        } else {
            LOGGER.warning("json response does not contain values for KEY " + JSONOB_TRAFFIC_ITEMS);
        }
    }

    /**
     * 
     * @param into
     *            provides the list to move into in order to get the target list
     *            of json objects out of it
     * @param KEY
     *            represents the key referring to this target list of json
     *            objects
     * @return the target list
     * @throws JSONException
     */
    private List<JSONObject> goInside(List<JSONObject> into, String KEY) throws JSONException {
        List<JSONObject> jL = new LinkedList<>();
        for (JSONObject jO : into) {
            if (jO.has(KEY)) {
                jL.add(jO.getJSONObject(KEY));
                System.out.println(KEY + ": " + jO.toString());
            }
        }
        return jL;
    }

    /**
     * 
     * @param jO
     *            provides the json object that should contain a json array
     * @param KEY
     *            is the key referring to this json array
     * @return a list of all json objects contained in the json array
     * @throws JSONException
     */
    private List<JSONObject> getJSONObjectsFromJSONArray(JSONObject jO, String KEY) throws JSONException {
        if (jO.has(KEY)) {
            JSONArray jA = jO.getJSONArray(KEY);

            List<JSONObject> jL = new LinkedList<>();
            for (int i = 0; i < jA.length(); ++i) {
                jL.add(jA.getJSONObject(i));
                System.out.println(KEY + jA.get(i));
            }
            return jL;
        }
        LOGGER.warning(KEY + " not contained in JSONObject " + jO.toString());
        return null;
    }

    /**
     * 
     * @param jL
     *            list of json objects to retrieve string values from
     * @param KEY
     *            of the desired string value
     * @return list of desired string values
     * @throws JSONException
     */
    private List<String> getValuesFromJSONObjects(List<JSONObject> jL, String KEY) throws JSONException {
        List<String> sL = new LinkedList<>();
        for (JSONObject jO : jL) {
            if (jO.has(KEY)) {
                sL.add(jO.getString(KEY));
            } else {
                LOGGER.warning(KEY + " not contained in " + jO);
                return null;
            }
        }
        return sL;
    }

    @Override
    public void printResponse() {
        // TODO Auto-generated method stub

    }

    @Override
    public void getResponseFromWebsite() {
        try {
            getJsonStringFromInputStream();
        } catch (IOException e) {
            LOGGER.warning("Error getting jsonString from input stream: " + e.getMessage());
        }
        this.jsonResponse = getResponseAsJsonString();
        System.out.println(this.jsonResponse);
    }

    private void prepareUrlBuild(GeocodeData gcd, UserSettings usersettings) {
        int[] mapLow = gcd.getMapTilesLowZoom();
        int[] mapMedium = gcd.getMapTilesMediumZoom();
        int[] mapHigh = gcd.getMapTilesHighZoom();
        this.mapLowText = "16/" + String.valueOf(mapLow[0]) + "/" + String.valueOf(mapLow[1]);
        this.mapMediumText = "12/" + String.valueOf(mapMedium[0]) + "/" + String.valueOf(mapMedium[1]);
        this.mapHighText = "8/" + String.valueOf(mapHigh[0]) + "/" + String.valueOf(mapHigh[1]);
    }

    @Override
    public void buildRequestUrl(UserSettings usersettings) {
        GeocodeAPI gApi = new GeocodeAPI();
        GeocodeData gcd = gApi.get(usersettings);
        prepareUrlBuild(gcd, usersettings);

        System.out.println(BASE_URL + mapMediumText + APP_KEYS);

        try {
            super.setRequestUrl(new URL(BASE_URL + mapMediumText + APP_KEYS));
        } catch (MalformedURLException e) {
            // TODO: handle exception
        }
        System.out.println(getRequestUrl());

    }

    /*
     * 
     * test section
     * 
     * 
     * 
     */
    public static void main(String[] uranium) {
        TrafficAPI tApi = new TrafficAPI();
        UserSettings usersettings = new UserSettings(null, null, Sex.MALE,
                new Address("6", "Augartenstraﬂe", "68165", "Mannheim"));

        tApi.buildRequestUrl(usersettings);
        try {
            tApi.extractDesiredInfoFromResponse();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // TrafficContext tContext = tApi.get(usersettings);
        // tContext.logContextContent();
    }
}
