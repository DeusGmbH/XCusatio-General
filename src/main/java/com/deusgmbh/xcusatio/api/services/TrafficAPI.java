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
        this.incidentDetails = new LinkedList<>();
        this.incidentLocation = new LinkedList<>();
        this.incidentTimes = new LinkedList<>();
    }

    @Override
    public TrafficContext get(UserSettings usersettings) {
        /*
         * creating a dummy context
         * 
         * 
         */
        // List<TrafficIncidentDetails> trdList = new LinkedList<>();
        // trdList.add(new TrafficIncidentDetails("CONSTRUCTION", "ACTIVE"));
        //
        // List<TrafficIncidentLocation> triList = new LinkedList<>();
        // Address address = new Address("Coblitzallee", "6", "68163",
        // "Mannheim");
        // triList.add(new TrafficIncidentLocation(address.getCity(),
        // address.getStreetName()));
        //
        // String dateFormat = "MM/dd/yyyy hh:mm:ss";
        // Date startTime = null, endTime = null;
        // try {
        // startTime = new SimpleDateFormat(dateFormat).parse("03/29/2018
        // 06:05:11");
        // endTime = new SimpleDateFormat(dateFormat).parse("03/29/2018
        // 17:12:27");
        // } catch (Exception e) {
        // LOGGER.info("parsed wrong date format, " + e.getMessage());
        // }
        //
        // List<TrafficIncidentTimes> trtList = new LinkedList<>();
        // trtList.add(new TrafficIncidentTimes(startTime, endTime));
        //
        // TrafficContext trafficContext = new TrafficContext(trdList, triList,
        // trtList);
        this.buildRequestUrl(usersettings);
        try {
            this.extractDesiredInfoFromResponse();
        } catch (JSONException e) {
            LOGGER.warning("JSONException during processing of api response: " + e.getLocalizedMessage());
        } catch (ParseException e) {
            LOGGER.warning("Wrong time format detected while processing entry and end times of incidents: "
                    + e.getLocalizedMessage());
        }

        return new TrafficContext(this.incidentDetails, this.incidentLocation, this.incidentTimes);

    }

    @Override
    public void transmitDataToWebsite() {

    }

    @Override
    public void extractDesiredInfoFromResponse() throws JSONException, ParseException {

        /* get json response through api call */
        getResponseFromWebsite();
        JSONObject jsonTotal = new JSONObject(this.jsonResponse);

        /* check for traffic items */
        if (jsonTotal.has(JSONOB_TRAFFIC_ITEMS)) {
            JSONObject trafficItems = jsonTotal.getJSONObject(JSONOB_TRAFFIC_ITEMS);
            // System.out.println("* " + trafficItems.toString());

            /* get the list of all traffic items */
            List<JSONObject> trafficItemList = getJSONObjectsFromJSONArray(trafficItems, JSONARR_TRAFFIC_ITEM);

            /* fill context relevant lists */
            extractDetailsInformation(trafficItemList);
            extractTimesInformation(trafficItemList);
            extractIncidentLocation(trafficItemList);

        } else {
            LOGGER.warning("json response does not contain values for KEY " + JSONOB_TRAFFIC_ITEMS);
        }
    }

    private void extractDetailsInformation(List<JSONObject> trafficItemList) throws JSONException {
        // TODO 1 TYPE
        List<String> incidentTypes = getValuesFromJSONObjects(trafficItemList, JSONSTR_INCIDENT_TYPE);
        // incidentTypes.forEach(s -> System.out.println(s));

        // TODO 2 STATUS
        List<String> incidentStatus = getValuesFromJSONObjects(trafficItemList, JSONSTR_INCIDENT_STATUS);
        // incidentStatus.forEach(s -> System.out.println(s));

        int typesIndex = 0;
        while (typesIndex < incidentTypes.size()) {
            this.incidentDetails
                    .add(new TrafficIncidentDetails(incidentTypes.get(typesIndex), incidentStatus.get(typesIndex)));
            ++typesIndex;
        }

    }

    /**
     * 
     * @param trafficItemList
     *            contains all of the traffic items received through api call
     * @throws JSONException
     */
    private void extractIncidentLocation(List<JSONObject> trafficItemList) throws JSONException {
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

        System.out.println(cityNamesOfIncidents.size());
        for (int i = 0; i < cityNamesOfIncidents.size(); ++i) {
            this.incidentLocation
                    .add(new TrafficIncidentLocation(cityNamesOfIncidents.get(i), streetNamesOfIncidents.get(i)));
            System.out.println("added " + cityNamesOfIncidents.get(i) + " and " + streetNamesOfIncidents.get(i));
        }
        //
        // int locationIndex = 0;
        // while (locationIndex < cityNamesOfIncidents.size()) {
        // this.incidentLocation.add(new
        // TrafficIncidentLocation(cityNamesOfIncidents.get(locationIndex),
        // streetNamesOfIncidents.get(locationIndex)));
        // ++locationIndex;
        // }
        //
        // this.incidentLocation
        // .forEach(loc -> System.out.println(loc.getCityOfIncident() + " : " +
        // loc.getStreetOfIncident()));
    }

    private void extractTimesInformation(List<JSONObject> trafficItemList) throws JSONException, ParseException {
        // TODO 4 convert to Date later
        List<String> entryTimes = getValuesFromJSONObjects(trafficItemList, JSONSTR_INCIDENT_ENTRY_TIME);
        // entryTimes.forEach(s -> System.out.println(s));

        // TODO 5 convert to Date later
        List<String> endTimes = getValuesFromJSONObjects(trafficItemList, JSONSTR_INCIDENT_END_TIME);
        // endTimes.forEach(s -> System.out.println(s));

        int timesIndex = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        while (timesIndex < entryTimes.size()) {
            this.incidentTimes.add(new TrafficIncidentTimes(sdf.parse(entryTimes.get(timesIndex)),
                    sdf.parse(endTimes.get(timesIndex))));
            ++timesIndex;
        }
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
        // System.out.println(this.jsonResponse);
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

        // System.out.println(BASE_URL + mapMediumText + APP_KEYS);

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
                new Address("6", "Dornheimer Ring", "68309", "Mannheim"));

        System.out.println("#####################################");
        TrafficContext tContext = tApi.get(usersettings);
        System.out.println("#####################################");

        tContext.logContextContent();

        tContext.getIncidentLocation()
                .forEach(s -> System.out
                        .println("City: " + s.getCityOfIncident() + " Street: " + s.getStreetOfIncident()));

        tContext.getTrafficIncident()
                .forEach(s -> System.out.println("Type: " + s.getIncidentType() + " Status: " + s.getIncidentStatus()));

        // TrafficContext tContext = tApi.get(usersettings);
        // tContext.logContextContent();
    }
}
