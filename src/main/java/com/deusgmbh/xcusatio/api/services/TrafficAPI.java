package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.GeocodeData;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentDetails;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentLocation;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentTimes;
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
    private final String JSONOB_LOCATION_INTERSECTION = "INTERSECTION";
    private final String JSONOB_INTERSECTION_ORIGIN = "ORIGIN";
    private final String JSONOB_ORIGIN_STREET1 = "STREET1";
    private final String JSONSTR_STREET1_ADDRESS = "ADDRESS1";
    private final String JSONOB_DEFINED_ORIGIN = "ORIGIN";
    private final String JSONOB_ORIGIN_DIRECTION = "DIRECTION";
    private final String JSONARR_DIRECTION_DESCRIPTION = "DESCRIPTION";
    private final String JSONSTR_DIRECTION_DESCRIPTION_FIRST = "value";
    private final String JSONOB_ORIGIN_ROADWAY = "ROADWAY";
    private final String JSONARR_ROADWAY_DESCRIPTION = "DESCRIPTION";
    private final String JSONSTR_INCIDENT_TYPE = "TRAFFIC_ITEM_TYPE_DESC";
    private final String JSONSTR_INCIDENT_STATUS = "TRAFFIC_ITEM_STATUS_SHORT_DESC";
    private final String JSONSTR_INCIDENT_ENTRY_TIME = "ENTRY_TIME";
    private final String JSONSTR_INCIDENT_END_TIME = "END_TIME";

    private String jsonResponse;
    private GeocodeData geocodeData;

    private String mapMediumText;

    private List<TrafficIncidentDetails> trafficIncidentDetails;
    private List<TrafficIncidentLocation> trafficIncidentLocations;
    private List<TrafficIncidentTimes> trafficIncidentTimes;

    public TrafficAPI() {

    }

    /**
     * @throws ParseException
     * @throws JSONException
     * 
     */
    @Override
    public TrafficContext get(UserSettings usersettings) throws IOException, JSONException, ParseException {

        URL requestUrl = buildRequestUrl(usersettings);

        String jsonResponse = getResponseFromWebsite(requestUrl);

        JSONObject totalJsonObject = new JSONObject(jsonResponse);

        if (totalJsonObject.has(JSONOB_TRAFFIC_ITEMS)) {
            JSONObject trafficItems = totalJsonObject.getJSONObject(JSONOB_TRAFFIC_ITEMS);
            List<JSONObject> trafficItemList = getJSONObjectsFromJSONArray(trafficItems, JSONARR_TRAFFIC_ITEM);
            this.trafficIncidentDetails = extractIncidentDetails(trafficItemList);
            this.trafficIncidentTimes = extractIncidentTimes(trafficItemList);
            this.trafficIncidentLocations = extractIncidentLocations(trafficItemList);
        } else {

        }

        return new TrafficContext(this.trafficIncidentDetails, this.trafficIncidentLocations,
                this.trafficIncidentTimes);

    }

    @Override
    public void transmitDataToWebsite() {

    }

    /**
     * 
     * @param trafficItemList
     * @return
     * @throws JSONException
     */
    private List<TrafficIncidentDetails> extractIncidentDetails(List<JSONObject> trafficItemList) throws JSONException {
        List<TrafficIncidentDetails> trafficIncidentDetails = new LinkedList<>();
        List<String> incidentTypes = getValuesFromJSONObjects(trafficItemList, JSONSTR_INCIDENT_TYPE);
        List<String> incidentStatus = getValuesFromJSONObjects(trafficItemList, JSONSTR_INCIDENT_STATUS);
        int typesIndex = 0;
        while (typesIndex < incidentTypes.size()) {
            trafficIncidentDetails
                    .add(new TrafficIncidentDetails(incidentTypes.get(typesIndex), incidentStatus.get(typesIndex)));
            ++typesIndex;
        }
        return trafficIncidentDetails;
    }

    /**
     * 
     * @param trafficItemList
     *            contains all of the traffic items received through api call
     * @return list of traffic incident locations
     * @throws JSONException
     */
    private List<TrafficIncidentLocation> extractIncidentLocations(List<JSONObject> trafficItemList)
            throws JSONException {
        List<TrafficIncidentLocation> trafficIncidentLocations = new LinkedList<>();
        List<JSONObject> locationList = goInside(trafficItemList, JSONOB_LOCATION);
        List<JSONObject> definedLocations = goInside(locationList, JSONOB_LOCATION_DEFINED);
        List<JSONObject> locOrigins = goInside(definedLocations, JSONOB_DEFINED_ORIGIN);
        List<String> cityNamesOfIncidents = getValuesFromNestedJSONArray(locOrigins, JSONOB_ORIGIN_DIRECTION,
                JSONARR_DIRECTION_DESCRIPTION, JSONSTR_DIRECTION_DESCRIPTION_FIRST);
        if (cityNamesOfIncidents.isEmpty()) {
            List<JSONObject> intersections = goInside(locationList, JSONOB_LOCATION_INTERSECTION);
            List<JSONObject> intersectionsOrigins = goInside(intersections, JSONOB_INTERSECTION_ORIGIN);
            List<JSONObject> originsStreets = goInside(intersectionsOrigins, JSONOB_ORIGIN_STREET1);
            cityNamesOfIncidents = getValuesFromJSONObjects(originsStreets, JSONSTR_STREET1_ADDRESS);
        }
        List<String> streetNamesOfIncidents = getValuesFromNestedJSONArray(locOrigins, JSONOB_ORIGIN_ROADWAY,
                JSONARR_ROADWAY_DESCRIPTION, JSONSTR_DIRECTION_DESCRIPTION_FIRST);

        for (int i = 0; i < cityNamesOfIncidents.size(); ++i) {
            trafficIncidentLocations
                    .add(new TrafficIncidentLocation(cityNamesOfIncidents.get(i), streetNamesOfIncidents.get(i)));
        }
        return trafficIncidentLocations;
    }

    private List<TrafficIncidentTimes> extractIncidentTimes(List<JSONObject> trafficItemList)
            throws JSONException, ParseException {
        List<TrafficIncidentTimes> trafficIncidentTimes = new LinkedList<>();
        List<String> entryTimes = getValuesFromJSONObjects(trafficItemList, JSONSTR_INCIDENT_ENTRY_TIME);
        List<String> endTimes = getValuesFromJSONObjects(trafficItemList, JSONSTR_INCIDENT_END_TIME);
        int timesIndex = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        while (timesIndex < entryTimes.size()) {
            trafficIncidentTimes.add(new TrafficIncidentTimes(sdf.parse(entryTimes.get(timesIndex)),
                    sdf.parse(endTimes.get(timesIndex))));
            ++timesIndex;
        }
        return trafficIncidentTimes;
    }

    @Override
    public void printResponse() {
        // TODO Auto-generated method stub

    }

    private String prepareUrlBuild(GeocodeData gcd, UserSettings usersettings) {
        int[] mapTiles = gcd.getMapTiles();
        return "12/" + String.valueOf(mapTiles[0]) + "/" + String.valueOf(mapTiles[1]);
    }

    @Override
    public URL buildRequestUrl(UserSettings usersettings) throws IOException {
        GeocodeAPI gApi = new GeocodeAPI();
        GeocodeData gcd = gApi.get(usersettings);
        String mapTiles = prepareUrlBuild(gcd, usersettings);
        try {
            return new URL(BASE_URL + mapTiles + APP_KEYS);
        } catch (MalformedURLException e) {
            throw new RuntimeException("error building url");
        }
    }

    protected void printTrafficIncidentSummary(TrafficContext tContext) {
        int index = tContext.getIncidentLocation()
                .size() > tContext.getIncidentTimes()
                        .size() ? (index = tContext.getIncidentTimes()
                                .size() > tContext.getTrafficIncident()
                                        .size() ? tContext.getTrafficIncident()
                                                .size()
                                                : tContext.getIncidentTimes()
                                                        .size())
                                : tContext.getIncidentLocation()
                                        .size();
        for (int i = 0; i < index; ++i) {
            System.out.println(tContext.getIncidentLocation()
                    .get(i)
                    .getCityOfIncident() + ", "
                    + tContext.getIncidentLocation()
                            .get(i)
                            .getStreetOfIncident()
                    + ": " + tContext.getTrafficIncident()
                            .get(i)
                            .getIncidentType()
                    + " (" + tContext.getTrafficIncident()
                            .get(i)
                            .getIncidentStatus()
                    + ") seit " + tContext.getIncidentTimes()
                            .get(i)
                            .getStartTimeOfTrafficIncident()
                    + "; bis: " + tContext.getIncidentTimes()
                            .get(i)
                            .getEndTimeOfTrafficIncident());
        }
    }

    /*
     * 
     * test section
     * 
     * 
     * 
     */
    public static void main(String[] uranium) throws JSONException, IOException, ParseException {
        TrafficAPI tApi = new TrafficAPI();
        UserSettings usersettings = new UserSettings(null, null, Sex.MALE,
                new Address("10", "Dalbergstrasse", "68159", "Mannheim"));
        TrafficContext tContext = tApi.get(usersettings);
        tApi.printTrafficIncidentSummary(tContext);
    }

    @Override
    public void extractDesiredInfoFromResponse() throws JSONException, ParseException {
        // TODO Auto-generated method stub

    }
}
