package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class TrafficAPI extends APIService {
    private static final Logger LOGGER = Logger.getLogger(TrafficAPI.class.getName());

    private static final String BASE_URL = "https://traffic.cit.api.here.com/traffic/6.2/incidents/json/";
    private static final String APP_KEYS = "?app_id=ObXv79Ww3xdQ996uEDLw&app_code=74fsgcSubek54INvT13Rcg";
    private static final String JSONOB_TRAFFICML_INCIDENTS = "TRAFFICML_INCIDENTS";
    private static final String JSONOB_TRAFFIC_ITEMS = "TRAFFIC_ITEMS";
    private static final String JSONARR_TRAFFIC_ITEM = "TRAFFIC_ITEM";
    private static final String JSONOB_LOCATION = "LOCATION";
    private static final String JSONOB_LOCATION_DEFINED = "DEFINED";
    private static final String JSONOB_LOCATION_INTERSECTION = "INTERSECTION";
    private static final String JSONOB_INTERSECTION_ORIGIN = "ORIGIN";
    private static final String JSONOB_ORIGIN_STREET1 = "STREET1";
    private static final String JSONSTR_STREET1_ADDRESS = "ADDRESS1";
    private static final String JSONOB_DEFINED_ORIGIN = "ORIGIN";
    private static final String JSONOB_ORIGIN_DIRECTION = "DIRECTION";
    private static final String JSONARR_DIRECTION_DESCRIPTION = "DESCRIPTION";
    private static final String JSONSTR_DIRECTION_DESCRIPTION_FIRST = "value";
    private static final String JSONOB_ORIGIN_ROADWAY = "ROADWAY";
    private static final String JSONARR_ROADWAY_DESCRIPTION = "DESCRIPTION";
    private static final String JSONSTR_INCIDENT_TYPE = "TRAFFIC_ITEM_TYPE_DESC";
    private static final String JSONSTR_INCIDENT_STATUS = "TRAFFIC_ITEM_STATUS_SHORT_DESC";
    private static final String JSONSTR_INCIDENT_ENTRY_TIME = "ENTRY_TIME";
    private static final String JSONSTR_INCIDENT_END_TIME = "END_TIME";

    private String jsonResponse;
    private GeocodeData geocodeData;

    private String mapMediumText;

    // private List<TrafficIncidentDetails> trafficIncidentDetails;
    // private List<TrafficIncidentLocation> trafficIncidentLocations;
    //
    // private List<TrafficIncidentTimes> trafficIncidentTimes;
    public TrafficAPI() {

    }

    /**
     * @throws IOException
     * @throws ParseException
     * @throws JSONException
     * 
     */

    public TrafficContext get(UserSettings usersettings) throws IOException {

        URL requestUrl = buildRequestUrl(usersettings);

        Gson gson = new Gson();
        JsonObject total = getTotalJsonObject(requestUrl, gson);

        List<TrafficIncidentDetails> trafficIncidentDetails = extractIncidentDetails(gson, total);
        // List<TrafficIncidentTimes> trafficIncidentTimes =
        // extractIncidentTimes(trafficItemList);
        // List<TrafficIncidentLocation> trafficIncidentLocations =
        // extractIncidentLocations(trafficItemList);

        return null;
        // return new TrafficContext(trafficIncidentDetails,
        // trafficIncidentLocations, trafficIncidentTimes);
    }

    /**
     * 
     * @param total
     * @return
     * @throws JSONException
     */
    private List<TrafficIncidentDetails> extractIncidentDetails(Gson gson, JsonObject total) throws JSONException {
        List<TrafficIncidentDetails> trafficIncidentDetails = new LinkedList<>();

        System.out.println(total);

        JsonObject trafficItemObject = gson.fromJson(total.get(JSONOB_TRAFFIC_ITEMS), JsonObject.class);

        System.out.println("trafficItemObject: " + trafficItemObject);

        List<JsonArray> trafficItems = new LinkedList<>();
        for (int numOfItems = 0; numOfItems < trafficItemObject.size(); ++numOfItems) {
            trafficItems.add(gson.fromJson(trafficItemObject.get(JSONARR_TRAFFIC_ITEM), JsonArray.class));
        }

        Map<String, String> trafficItemTypes = new TreeMap<>();

        trafficItems.forEach(trafficItem -> {
            System.out.println(trafficItem);
            JsonObject firstEntry = gson.fromJson(trafficItem.get(0), JsonObject.class);
            String itemTypeDescription = gson.fromJson(firstEntry.get(JSONSTR_INCIDENT_TYPE), String.class);
            String itemStatus = gson.fromJson(firstEntry.get(JSONSTR_INCIDENT_STATUS), String.class);
            trafficItemTypes.put(itemTypeDescription, itemStatus);
        });

        trafficItemTypes.forEach((k, v) -> {
            trafficIncidentDetails.add(new TrafficIncidentDetails(k, v));
        });

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

    public void printResponse() {
        // TODO Auto-generated method stub

    }

    private String prepareUrlBuild(GeocodeData gcd, UserSettings usersettings) {
        int[] mapTiles = gcd.getMapTiles();
        return "12/" + String.valueOf(mapTiles[0]) + "/" + String.valueOf(mapTiles[1]);
    }

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
                            .getTrafficIncidentType()
                    + " (" + tContext.getTrafficIncident()
                            .get(i)
                            .getTrafficIncidentStatus()
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
                new Address("50", "Hanauer Landstrasse", "60314", "Frankfurt am Main"));
        URL requestUrl = tApi.buildRequestUrl(usersettings);

        Gson gson = new Gson();
        JsonObject total = tApi.getTotalJsonObject(requestUrl, gson);

        List<TrafficIncidentDetails> trafficIncidentDetails = tApi.extractIncidentDetails(gson, total);

        trafficIncidentDetails.forEach(tid -> System.out.println(tid.getTrafficIncidentType()
                .toString() + " "
                + tid.getTrafficIncidentStatus()
                        .toString()));

    }

    public void extractDesiredInfoFromResponse() throws JSONException, ParseException {
        // TODO Auto-generated method stub

    }
}
