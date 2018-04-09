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

    // TODO: @jan please remove all variables that are not going to be used.
    // There seems to be a lot of them in here ;)
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
    private static final String JSONSTR_DESCRIPTION_VALUE = "value";
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

    public TrafficAPI() {

    }

    @Override
    public TrafficContext get(UserSettings usersettings) throws IOException {

        // build the url for the request and build the total json response
        // string
        URL requestUrl = buildRequestUrl(usersettings);
        Gson gson = new Gson();
        JsonObject total = getTotalJsonObject(requestUrl, gson);

        // extract all required information from json
        List<TrafficIncidentDetails> trafficIncidentDetails = extractIncidentDetails(gson, total);
        List<TrafficIncidentLocation> trafficIncidentLocations = extractIncidentLocations(gson, total);
        List<TrafficIncidentTimes> trafficIncidentTimes = extractIncidentTimes(gson, total);

        // fill these information into the defined context object for traffic
        // incidents
        TrafficContext trafficContext = new TrafficContext(trafficIncidentDetails, trafficIncidentLocations,
                trafficIncidentTimes);

        return trafficContext;
    }

    /**
     * 
     * @param gson
     * @param total
     *            represents the full json response from the url request
     * @return a list of JsonObjects in the relevant depth level that is needed
     *         for all further extractions of information hence this method is
     *         class-specific
     */
    private List<JsonObject> getRelevantJsonObjects(Gson gson, JsonObject total) {
        JsonObject trafficItemObject = gson.fromJson(total.get(JSONOB_TRAFFIC_ITEMS), JsonObject.class);
        JsonArray trafficItemArray = gson.fromJson(trafficItemObject.get(JSONARR_TRAFFIC_ITEM), JsonArray.class);
        List<JsonObject> trafficItemsFromArray = new LinkedList<>();
        trafficItemArray.forEach(element -> {
            trafficItemsFromArray.add((JsonObject) element);
        });
        return trafficItemsFromArray;
    }

    /**
     * 
     * @param total
     *            contains the complete json response string from the web
     *            request
     * @return list containing TrafficIncidentDetails, that is the type of
     *         incident + its current status
     * @throws JSONException
     */
    private List<TrafficIncidentDetails> extractIncidentDetails(Gson gson, JsonObject total) {
        List<TrafficIncidentDetails> trafficIncidentDetails = new LinkedList<>();
        List<JsonObject> trafficItems = getRelevantJsonObjects(gson, total);
        trafficItems.forEach(trafficItem -> {
            String[] trafficItemTypes = new String[2];
            String itemTypeDescription = gson.fromJson(trafficItem.get(JSONSTR_INCIDENT_TYPE), String.class);
            String itemStatus = gson.fromJson(trafficItem.get(JSONSTR_INCIDENT_STATUS), String.class);
            trafficItemTypes[0] = itemTypeDescription;
            trafficItemTypes[1] = itemStatus;
            trafficIncidentDetails.add(new TrafficIncidentDetails(trafficItemTypes[0], trafficItemTypes[1]));
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
    private List<TrafficIncidentLocation> extractIncidentLocations(Gson gson, JsonObject total) throws JSONException {
        List<JsonObject> trafficItems = getRelevantJsonObjects(gson, total);
        List<JsonObject> locations = new LinkedList<>();
        trafficItems.forEach(trafficItem -> {
            locations.add(gson.fromJson(trafficItem.get(JSONOB_LOCATION), JsonObject.class));
        });
        List<TrafficIncidentLocation> trafficIncidentLocations = new LinkedList<>();
        locations.forEach(location -> {
            if (location.get(JSONOB_LOCATION_DEFINED) != null) {
                // path 1: defined origin roadway description[] value_STR
                JsonObject defined = gson.fromJson(location.get(JSONOB_LOCATION_DEFINED), JsonObject.class);
                JsonObject origin = gson.fromJson(defined.get(JSONOB_DEFINED_ORIGIN), JsonObject.class);
                JsonObject roadway = gson.fromJson(origin.get(JSONOB_ORIGIN_ROADWAY), JsonObject.class);
                JsonArray description = gson.fromJson(roadway.get(JSONARR_ROADWAY_DESCRIPTION), JsonArray.class);
                JsonObject descriptionObject = gson.fromJson(description.get(0), JsonObject.class);
                String streetOfIncident = gson.fromJson(descriptionObject.get(JSONSTR_DESCRIPTION_VALUE), String.class);
                trafficIncidentLocations.add(new TrafficIncidentLocation(streetOfIncident));
            } else {
                // path 2: intersection origin street address1_STR
                JsonObject intersection = gson.fromJson(location.get(JSONOB_LOCATION_INTERSECTION), JsonObject.class);
                JsonObject origin = gson.fromJson(intersection.get(JSONOB_INTERSECTION_ORIGIN), JsonObject.class);
                JsonObject street1 = gson.fromJson(origin.get(JSONOB_ORIGIN_STREET1), JsonObject.class);
                String streetOfIncident = gson.fromJson(street1.get(JSONSTR_STREET1_ADDRESS), String.class);
                trafficIncidentLocations.add(new TrafficIncidentLocation(streetOfIncident));
            }
        });
        return trafficIncidentLocations;
    }

    private List<TrafficIncidentTimes> extractIncidentTimes(Gson gson, JsonObject total) {
        List<JsonObject> trafficItems = getRelevantJsonObjects(gson, total);
        List<TrafficIncidentTimes> trafficIncidentTimes = new LinkedList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        trafficItems.forEach(trafficItem -> {
            String endTime = gson.fromJson(trafficItem.get(JSONSTR_INCIDENT_END_TIME), String.class);
            try {
                trafficIncidentTimes.add(new TrafficIncidentTimes(sdf.parse(endTime)));
            } catch (ParseException e) {
                throw new RuntimeException(
                        "date format parser specification error: \"MM/dd/yyyy hh:mm:ss\" should be response format. maybe api response has an error?");
            }
        });

        return trafficIncidentTimes;
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
        URL requestUrl = tApi.buildRequestUrl(usersettings);

        System.out.println(requestUrl);

        Gson gson = new Gson();
        JsonObject total = tApi.getTotalJsonObject(requestUrl, gson);

        List<TrafficIncidentDetails> trafficIncidentDetails = tApi.extractIncidentDetails(gson, total);
        List<TrafficIncidentLocation> trafficIncidentLocations = tApi.extractIncidentLocations(gson, total);
        List<TrafficIncidentTimes> trafficIncidentTimes = tApi.extractIncidentTimes(gson, total);

        System.out.println("Number of traffic incidents: " + trafficIncidentDetails.size());

        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------------------");

        trafficIncidentDetails.forEach(tid -> System.out
                .println("Type: " + tid.getTrafficIncidentType() + ", Status: " + tid.getTrafficIncidentStatus()));

        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------------------");

        trafficIncidentLocations.forEach(til -> System.out.println("Location: " + til.getStreetOfIncident()));

        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------------------");

        trafficIncidentTimes.forEach(tit -> System.out.println(tit.getEndTimeOfTrafficIncident()));

    }

}
