package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.rnv.Tram;
import com.deusgmbh.xcusatio.api.data.rnv.TramStatus;
import com.deusgmbh.xcusatio.context.data.RNVContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RNVAPI extends APIService {
    private final static Logger LOGGER = Logger.getLogger(RNVAPI.class.getName());

    private final static String DUALE_HOCHSCHULE_STATION_ID = "2521";
    private final static String LINE_LABEL = "5";
    private final static String PARADEPLATZ = "2451"; // for tests only
    private final static String BASE_URL = "http://rnv.the-agent-factory.de:8080/easygo2/api";
    private final static String RNV_API_TOKEN = "l1kjqp3r2m788o0oouolaeg8ui";

    private final static String STATIONS_PACKAGE = BASE_URL + "/regions/rnv/modules/stations/packages/1";
    private final static String LINES_PACKAGE = BASE_URL + "/regions/rnv/modules/lines/allJourney";
    private final static String STATIONS_MONITOR = BASE_URL + "/regions/rnv/modules/stationmonitor/element?hafasID=";
    private final static String NEWS_ENTRIES = BASE_URL + "/regions/rnv/modules/news";

    private final static String JSONARR_STATIONS = "stations";
    private final static String JSONSTR_STATION_ID = "hafasID";
    private final static String JSONSTR_STATION_NAME = "longName";
    private final static String JSONARR_LINE_IDS = "lineIDs";
    private final static String JSON_LINE_LABEL = "lineId";
    private final static String JSONARR_DEPARTURES_LIST = "listOfDepartures";
    private final static String JSONSTR_PLANNED_DEPARTURE = "time";
    private final static String JSONSTR_DIFFERENCE_TIME = "differenceTime";

    private final static String JSONARR_NEWS_AFFECTED_LINES = "seperatedLines";
    private final static String JSONSTR_NEWS_TITLE = "title";
    private final static String JSONSTR_NEWS_CONTENT = "text";

    private final static String JSONSTR_NEWS_VALID_FROM = "validFrom";
    private final static String JSONSTR_NEWS_VALID_TO = "validTo";

    private final static String JSONSTR_STATUS = "status";
    private final static String JSONSTR_LINE_LABEL = "lineLabel";

    private final static SimpleDateFormat RNV_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd+hh:mm:ss");

    private URL[] requestUrls;
    private Map<String, List<String>> stopsOfLines;

    public RNVAPI() {
        this.requestUrls = this.buildRequestUrls();
        this.stopsOfLines = new LinkedHashMap<>();
        List<String> stopsOfLine5;
        try {
            stopsOfLine5 = getLineStops(LINE_LABEL);
        } catch (IOException e) {
            stopsOfLine5 = new ArrayList<>();
        }
        this.stopsOfLines.put(LINE_LABEL, stopsOfLine5);
    }

    /**
     * 
     */
    @Override
    public RNVContext get(UserSettings usersettings) throws UnsupportedEncodingException, IOException {

        Tram tram = extractTram(DUALE_HOCHSCHULE_STATION_ID);

        RNVContext rnvContext = new RNVContext(tram);
        return rnvContext;
    }

    private Tram extractTram(String searchedStationId) throws IOException {

        Gson gson = new Gson();
        JsonObject totalStationsMonitor = getStationsMonitorPackage();

        // Determine delay times
        int delayTime = extractDelayTime(gson, totalStationsMonitor);

        TramStatus tramStatus = extractTramStatus(gson, totalStationsMonitor, DUALE_HOCHSCHULE_STATION_ID);

        Tram tram = new Tram(LINE_LABEL, DUALE_HOCHSCHULE_STATION_ID, delayTime, this.stopsOfLines, tramStatus);

        return tram;

    }

    private int extractDelayTime(Gson gson, JsonObject totalStationsMonitor) {
        List<Integer> delayTimes = new LinkedList<>();
        JsonArray departuresArray = gson.fromJson(totalStationsMonitor.get(JSONARR_DEPARTURES_LIST), JsonArray.class);
        List<JsonObject> departureObjects = new LinkedList<>();
        for (int departureIndex = 0; departureIndex < departuresArray.size(); ++departureIndex) {
            departureObjects.add(departuresArray.get(departureIndex)
                    .getAsJsonObject());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
        Date now = new Date();
        String nowAsText = sdf.format(now);
        departureObjects.forEach(departure -> {

            // cut time string response due to inconsistencies in api responses
            String timeCut = gson.fromJson(departure.get(JSONSTR_PLANNED_DEPARTURE), String.class)
                    .substring(0, 5);
            String timeDiffText = gson.fromJson(departure.get(JSONSTR_DIFFERENCE_TIME), String.class);
            Integer timeDiff = Integer.parseInt(timeDiffText);

            // wipe out the times from the next day represented by a date dd.MM
            // (contains the '.')
            if (!timeCut.contains(".")) {
                delayTimes.add(timeDiff - calculateDiffTimeInMinutes(nowAsText, timeCut));
            }
        });
        int delayTime = filterDelaysByRelevance(delayTimes);
        return delayTime;
    }

    private List<String> getLineStops(String lineLabel) throws IOException {
        Gson gson = new Gson();
        JsonArray totalLines = getLinesPackage();
        JsonObject totalStations = getStationsPackage();
        JsonObject lineObject5 = null;
        for (int lineObjectIndex = 0; lineObjectIndex < totalLines.size(); ++lineObjectIndex) {
            JsonObject currentObject = totalLines.get(lineObjectIndex)
                    .getAsJsonObject();
            String currentLineLabel = removeQuotes(currentObject.get(JSON_LINE_LABEL)
                    .toString());
            if (currentLineLabel.equals(LINE_LABEL)) {
                lineObject5 = currentObject;
                break;
            }
        }

        JsonArray stopIdsOfThisLine = gson.fromJson(lineObject5.get(JSONARR_LINE_IDS), JsonArray.class);
        List<String> stopIdsOfLineAsText = new LinkedList<>();
        stopIdsOfThisLine.forEach(stop -> {
            String stopTrimmed = removeQuotes(stop.toString());
            stopIdsOfLineAsText.add(stopTrimmed.toString());
        });
        Map<String, String> stationsMap = getStationIdNamesMap(gson, totalStations);
        List<String> stopNamesOfLine = new LinkedList<>();
        stopIdsOfLineAsText.forEach(id -> {
            stationsMap.forEach((stationId, name) -> {
                if (id.equals(stationId) && (!stopNamesOfLine.contains(name))) {
                    stopNamesOfLine.add(name);
                }
            });
        });
        return stopNamesOfLine;
    }

    private Map<String, String> getStationIdNamesMap(Gson gson, JsonObject totalStations) {
        JsonArray stationsArray = gson.fromJson(totalStations.get(JSONARR_STATIONS), JsonArray.class);
        List<JsonObject> stationObjects = new LinkedList<>();

        // get all the single station objects in one list
        for (int stationObjectIndex = 0; stationObjectIndex < stationsArray.size(); ++stationObjectIndex) {
            JsonObject currentObject = stationsArray.get(stationObjectIndex)
                    .getAsJsonObject();
            stationObjects.add(currentObject);
        }

        // for each station put its Id as key and its textual name as value in
        // the map
        Map<String, String> stationsMap = new HashMap<>();
        stationObjects.forEach(station -> {
            String stationId = gson.fromJson(station.get(JSONSTR_STATION_ID), String.class);
            String stationName = gson.fromJson(station.get(JSONSTR_STATION_NAME), String.class);
            stationsMap.put(stationId, stationName);
        });

        return stationsMap;
    }

    private TramStatus extractTramStatus(Gson gson, JsonObject totalStationsMonitor, String lineLabelOfInterest) {
        JsonArray departures = gson.fromJson(totalStationsMonitor.get(JSONARR_DEPARTURES_LIST), JsonArray.class);
        String tramStatusText = null;

        // collect the departure objects in a list
        List<JsonObject> departureObjectsList = new LinkedList<>();
        for (int departureIndex = 0; departureIndex < departures.size(); ++departureIndex) {
            departureObjectsList.add(departures.get(departureIndex)
                    .getAsJsonObject());
        }

        // get just the status objects affecting the line searched for
        List<JsonObject> statusObjectsOfInterest = filterTramStatusByLineLabel(gson, departureObjectsList,
                lineLabelOfInterest);

        for (JsonObject status : statusObjectsOfInterest) {
            String line = removeQuotes(gson.fromJson(status.get(JSONSTR_LINE_LABEL), String.class));
            if (!(line.equals(lineLabelOfInterest))) {
                throw new RuntimeException("filtering lines of interest failed!");
            } else {
                tramStatusText = gson.fromJson(status.get(JSONSTR_STATUS), String.class);
                return TramStatus.valueOf(tramStatusText);
            }
        }
        return TramStatus.valueOf("OK"); // probably not occuring in an excuse
                                         // then
    }

    private List<JsonObject> filterTramStatusByLineLabel(Gson gson, List<JsonObject> tramStatusObjectsList,
            String lineLabelOfInterest) {
        List<JsonObject> statusObjects = new LinkedList<>();
        tramStatusObjectsList.forEach(statusObject -> {

            // Look at line labels array first and stop if line of interest is
            // not there
            for (int statusIndex = 0; statusIndex < tramStatusObjectsList.size(); ++statusIndex) {
                String line = gson.fromJson(tramStatusObjectsList.get(statusIndex)
                        .get(JSONSTR_LINE_LABEL), String.class);
                // do not add duplicates
                if ((line.equals(lineLabelOfInterest) && (!statusObjects.contains(statusObject)))) {
                    statusObjects.add(statusObject);
                } else {
                    continue;
                }
            }
        });
        return statusObjects;
    }

    // getters and setters

    /*
     * Methods for doing web requests and receiving the necessary json objects
     * as response
     * 
     */

    private URL[] buildRequestUrls() {
        URL[] requestUrls = new URL[4];
        try {
            requestUrls[0] = new URL(STATIONS_PACKAGE);
            requestUrls[1] = new URL(LINES_PACKAGE);
            requestUrls[2] = new URL(
                    STATIONS_MONITOR + DUALE_HOCHSCHULE_STATION_ID + "&time=" + RNV_DATE_FORMAT.format(new Date()));
            requestUrls[3] = new URL(NEWS_ENTRIES);
            return requestUrls;
        } catch (MalformedURLException e) {
            throw new RuntimeException("error building urls in rnvApi");
        }

    }

    private JsonObject getStationsPackage() throws IOException {
        URL requestUrl = this.requestUrls[0];
        JsonElement element = getJsonFromWebRequest(requestUrl);
        JsonObject stationsObject = element.getAsJsonObject();
        return stationsObject;

    }

    private JsonArray getLinesPackage() throws IOException {
        URL requestUrl = this.requestUrls[1];
        JsonElement element = getJsonFromWebRequest(requestUrl);
        JsonArray linesObject = element.getAsJsonArray();
        return linesObject;
    }

    private JsonObject getStationsMonitorPackage() throws IOException {
        URL requestUrl = this.requestUrls[2];
        JsonElement element = getJsonFromWebRequest(requestUrl);
        JsonObject stationsMonitorObject = element.getAsJsonObject();
        return stationsMonitorObject;
    }

    private JsonArray getNewsEntriesPackage() throws IOException {
        URL requestUrl = this.requestUrls[3];
        JsonElement element = getJsonFromWebRequest(requestUrl);
        JsonArray newsEntryObject = element.getAsJsonArray();
        return newsEntryObject;
    }

    private JsonElement getJsonFromWebRequest(URL requestUrl) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("RNV_API_TOKEN", RNV_API_TOKEN);
        connection.setRequestProperty("Content-Type", "application/json");
        // TODO handle encoding problems

        InputStream is = connection.getInputStream();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(IOUtils.toString(is));
        return element;
    }

    /*
     * 
     * Helper methods
     * 
     * 
     */
    private Integer calculateDiffTimeInMinutes(String startTime, String endTime) {
        Integer endH = getHours(endTime);
        Integer endM = getMinutes(endTime);
        Integer startH = getHours(startTime);
        Integer startM = getMinutes(startTime);

        if (endH == startH) {
            return endM - startM;
        }
        if (endH - startH < 0) {
            Integer deltaH = 24 + (endH - startH);
            return 60 * deltaH + (endM - startM);
        }
        Integer deltaH = endH - startH;
        if (endM - startM < 0) {
            return 60 * deltaH + (endM - startM);
        }
        return 60 * deltaH + (endM - startM);
    }

    private Integer getHours(String clockTime) {
        return Integer.parseInt(clockTime.substring(0, 2));
    }

    private Integer getMinutes(String clockTime) {
        return Integer.parseInt(clockTime.substring(3, 5));
    }

    private String removeQuotes(String stringWithinQuotes) {
        try {
            if (!(stringWithinQuotes.matches("\".*\""))) {
                return stringWithinQuotes;
            }
            return stringWithinQuotes.substring(1, stringWithinQuotes.length() - 1);
        } catch (Exception e) {
            throw new RuntimeException("Error transforming quoted string, got: " + stringWithinQuotes + " expected: "
                    + "\"" + stringWithinQuotes + "\"");
        }

    }

    private int filterDelaysByRelevance(List<Integer> delayTimes) {
        double sumOfDelayTimes = 0.0;
        int numberOfPositiveDelayTimes = 0;
        for (Integer i : delayTimes) {
            if (i > -1) {
                ++numberOfPositiveDelayTimes;
                sumOfDelayTimes += i;
            }
        }
        int ceiledAverage = (int) Math.ceil(sumOfDelayTimes / numberOfPositiveDelayTimes);
        return ceiledAverage;
    }

    // testing
    public static void main(String[] aflok) throws IOException {

        RNVAPI rnvapi = new RNVAPI();
        RNVContext rnvContext = rnvapi.get(null);
        System.out.println(rnvContext.getTram()
                .getDifferenceTimeInMinutes());
        System.out.println(rnvContext.getTram()
                .getLineLabel());
        System.out.println(rnvContext.getTram()
                .getStopsOfLines()
                .get(LINE_LABEL));

    }

}
