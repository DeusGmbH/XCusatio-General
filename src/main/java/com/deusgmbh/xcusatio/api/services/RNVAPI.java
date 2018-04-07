package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.http.impl.io.SocketOutputBuffer;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.TramDetails;
import com.deusgmbh.xcusatio.context.wildcard.RNVContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RNVAPI extends APIService {
    private final static Logger LOGGER = Logger.getLogger(RNVAPI.class.getName());

    private final static String DUALE_HOCHSCHULE_STATION_ID = "2521";
    private final static String PARADEPLATZ = "2451"; // for tests only
    private final static String BASE_URL = "http://rnv.the-agent-factory.de:8080/easygo2/api";
    private final static String RNV_API_TOKEN = "l1kjqp3r2m788o0oouolaeg8ui";

    private final static String STATIONS_PACKAGE = BASE_URL + "/regions/rnv/modules/stations/packages/1";
    private final static String LINES_PACKAGE = BASE_URL + "/regions/rnv/modules/lines/allJourney";
    private final static String STATIONS_MONITOR = BASE_URL + "/regions/rnv/modules/stationmonitor/element?hafasID=";

    private final static String JSONARR_STATIONS = "stations";
    private final static String JSONSTR_STATION_ID = "hafasID";
    private final static String JSONSTR_STATION_NAME = "longName";
    private final static String JSONARR_LINE_IDS = "lineIDs";
    private final static String JSON_LINE_LABEL = "lineId";
    private final static String JSONARR_DEPARTURES_LIST = "listOfDepartures";
    private final static String JSONSTR_PLANNED_DEPARTURE = "time";
    private final static String JSONSTR_DIFFERENCE_TIME = "differenceTime";

    private final static SimpleDateFormat RNV_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd+hh:mm:ss");

    private URL[] requestUrls;

    // private List<TramDetails> tramDetails;
    // private List<TramNews> tramNews;
    // private List<TramStatus> tramStatus;
    // private String universityStationId;
    // private List<String> universityLinesIds;

    public RNVAPI(UserSettings userSettings) {
        this.requestUrls = this.buildRequestUrls(userSettings);
    }

    @Override
    public RNVContext get(UserSettings usersettings) throws UnsupportedEncodingException, IOException {

        URL requestUrl = buildRequestUrl(usersettings);
        Gson gson = new Gson();
        JsonArray totalLines = getLinesPackage();
        JsonObject totalStations = getStationsMonitorPackage();

        TramDetails tramDetails = extractTramDetails(gson, totalLines, totalStations, DUALE_HOCHSCHULE_STATION_ID);
        return null;
    }

    private TramDetails extractTramDetails(Gson gson, JsonArray totalLines, JsonObject totalStations, String searchedStationId) {

        List<JsonObject> lineObjects = new LinkedList<>();
        for (int lineObjectIndex = 0; lineObjectIndex < totalLines.size(); ++lineObjectIndex) {
            lineObjects.add(totalLines.get(lineObjectIndex)
                    .getAsJsonObject());
        }
        // extract the line label(s) of trams stopping at the searched stations
        List<String> linesStoppingAtSearchedStation = new LinkedList<>();
        List<String> stopIdsOfLineText = new LinkedList<>();
        List<String> stopNamesOfLine = new LinkedList<>();
        Map<String, String> stationsMap = getStationIdNamesMap(gson, totalStations);
        lineObjects.forEach(lineObject -> {
            JsonArray stopIdsOfThisLine = gson.fromJson(lineObject.get(JSONARR_LINE_IDS), JsonArray.class);   
            for (int stopId = 0; stopId < stopIdsOfThisLine.size(); ++stopId) {
                String lineStopId = stopIdsOfThisLine.get(stopId)
                        .toString();
                lineStopId = lineStopId.substring(1, lineStopId.length() - 1);
                if (lineStopId.equals(searchedStationId)) {
                    String line = gson.fromJson(stopIdsOfThisLine.get(stopId), String.class);
                    line = line.substring(1,  line.length() - 1);
                    String foundLine = gson.fromJson(lineObject.get(JSON_LINE_LABEL), String.class);
                    linesStoppingAtSearchedStation.add(foundLine);
                    stopIdsOfThisLine.forEach(stop -> {
                    	String stopTrimmed = stop.toString().substring(1, stop.toString().length() - 1);
                    	stopIdsOfLineText.add(stopTrimmed.toString());
//                    	System.out.println("Added " + stopTrimmed.toString() + " to stopIdsOfLineText");
                    });
                }
//                stopIdsOfLineText.forEach(s-> System.out.println( "## " + s));
            }
            
            
            stopIdsOfLineText.forEach(id -> {       
            	System.out.println("Looking at " + id);
            	stationsMap.forEach((stationId, name) -> {
            		if (id.equals(stationId)) {
            			stopNamesOfLine.add(name);
            			System.out.println("Added: " + name);
            		}
            	});
        	});
//            stationsMap.forEach((k,v)->System.out.println("key: " + k + "value: " + v));
            int bound;
            for (int i = 0; i < (bound = stopNamesOfLine.size() < stopIdsOfLineText.size() ? stopNamesOfLine.size() : stopIdsOfLineText.size()); ++i) {
            	System.out.println("ID: " + stopIdsOfLineText.get(i) + " Name: " + stopNamesOfLine.get(i));
            }
        });        
        
		return null;
        
//        linesStoppingAtSearchedStation.forEach(s -> System.out.println("Found line: " + s));

        // extract the stops of these lines
        // TODO 1: get stopIds of the line
        
        
    }
    
        private Map<String, String> getStationIdNamesMap(Gson gson, JsonObject totalStations) {
        	JsonArray stationsArray = gson.fromJson(totalStations.get(JSONARR_STATIONS), JsonArray.class);
            List<JsonObject> stationObjects = new LinkedList<>();
            
            
            
            for (int stationObjectIndex = 0; stationObjectIndex < stationsArray.size(); ++stationObjectIndex) {
            	JsonObject currentObject = stationsArray.get(stationObjectIndex).getAsJsonObject();
            	stationObjects.add(currentObject);
            }
            
            Map<String, String> stationsMap = new HashMap<>();
            
            stationObjects.forEach(station -> {
            	String stationId = gson.fromJson(station.get(JSONSTR_STATION_ID), String.class);
            	String stationName = gson.fromJson(station.get(JSONSTR_STATION_NAME), String.class);
            	stationsMap.put(stationId, stationName);
            });
            
            
            return stationsMap;
        }
        
        
        
        // TODO 2: map stopIds to station names ectracting them for each station
        
        
        // id out of the stations package
        // TODO 3: fill a list of streings with these names


    private URL[] buildRequestUrls(UserSettings userSettings) {
        URL[] requestUrls = new URL[3];
        try {
            requestUrls[0] = new URL(STATIONS_PACKAGE);
            requestUrls[1] = new URL(LINES_PACKAGE);
            requestUrls[2] = new URL(
                    STATIONS_MONITOR + DUALE_HOCHSCHULE_STATION_ID + "&time=" + RNV_DATE_FORMAT.format(new Date()));
            return requestUrls;
        } catch (MalformedURLException e) {
            throw new RuntimeException("error building urls in rnvApi");
        }

    }

    // public void extractDesiredInfoFromResponse() throws JSONException {
    //
    // getRelevantRNVAPIPackages();
    //
    // System.out.println(this.jsonResponses[0] + "\n" + this.jsonResponses[1] +
    // "\n" + this.jsonResponses[2]);
    //
    // // TODO 1 get tram details: line label, start, end, diffTime
    // extractTramDetails();
    //
    // this.tramDetails.forEach(s -> {
    // System.out.println("Home: " + s.getHomeStation() + " Line: " +
    // s.getLineLabel());
    // System.out.println("Delay at home station: " +
    // s.getDifferenceTimeInMinutes());
    // System.out.println("Number of stops: " + s.getStops()
    // .size());
    // });
    //
    // }
    //
    // // add javadoc
    // private String[] getRelevantRNVAPIPackages() {
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd+hh:mm:ss");
    // Date now = new Date();
    // String dateText = sdf.format(now);
    // getResponseFromSpecificWebsite(STATIONS_PACKAGE, 0);
    // getResponseFromSpecificWebsite(LINES_PACKAGE, 1);
    // getResponseFromSpecificWebsite(STATIONS_MONITOR +
    // this.universityStationId + "&time=" + dateText, 2);
    // }
    //
    // // TODO refactor, add javadoc
    // private void extractTramDetails() throws JSONException {
    // List<String> lineLabels = new LinkedList<>();
    // List<Integer> delayTimesOfLine = new LinkedList<>();
    // List<String> stopNamesOfLine = new LinkedList<>();
    //
    // JSONObject jsonStationsTotal = new JSONObject(this.jsonResponses[0]);
    // JSONArray jsonLinesTotal = new JSONArray(this.jsonResponses[1]);
    // JSONObject jsonMonitorTotal = new JSONObject(this.jsonResponses[2]);
    //
    // List<JSONObject> allLines = getJSONObjectsFromJSONArray(jsonLinesTotal);
    // // List<JSONArray> allLinesStations =
    // // getJSONArraysFromJSONObjects(allLines, this.JSONARR_LINE_IDS);
    //
    // lineLabels = findLinesStoppingAt(allLines);
    //
    // delayTimesOfLine = getDelayTimes(jsonMonitorTotal);
    // // delayTimesOfLine.forEach(s -> System.out.println(s));
    //
    // List<JSONArray> stopsOfLineAsArrays = getStopsOfLine(jsonLinesTotal);
    //
    // stopNamesOfLine = mapLineIdsToStationNames(jsonStationsTotal,
    // stopsOfLineAsArrays);
    //
    // for (int i = 0; i < lineLabels.size(); ++i) {
    // this.tramDetails.add(new TramDetails(lineLabels.get(i),
    // this.universityStationId, stopNamesOfLine));
    // this.tramDetails.get(i)
    // .setDifferenceTimeInMinutes(delayTimesOfLine.get(0));
    // }
    //
    // }
    //
    // // TODO refactor, add javadoc
    // private List<String> mapLineIdsToStationNames(JSONObject
    // jsonStationsTotal, List<JSONArray> stopsOfLineAsArrays)
    // throws JSONException {
    // JSONArray allStations = jsonStationsTotal.getJSONArray(JSONARR_STATIONS);
    // List<JSONObject> allStationObjects =
    // getJSONObjectsFromJSONArray(allStations);
    // List<List<String>> idsOfLinesAsText =
    // getValuesFromJSONArrayList(stopsOfLineAsArrays);
    //
    // // idsOfLinesAsText.forEach(list -> list.forEach(str ->
    // // System.out.println(str)));
    //
    // List<String> allStationNames = new LinkedList<>();
    // List<String> relevantStationNames = new LinkedList<>();
    // try {
    // allStationNames = getValuesFromJSONObjects(allStationObjects,
    // JSONSTR_STATION_NAME);
    // } catch (JSONException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // allStationObjects.forEach(station -> {
    // for (int stopsArrayIndex = 0; stopsArrayIndex <
    // stopsOfLineAsArrays.size(); ++stopsArrayIndex) {
    // for (int stopIdIndex = 0; stopIdIndex <
    // stopsOfLineAsArrays.get(stopsArrayIndex)
    // .length(); ++stopIdIndex) {
    // try {
    // if (idsOfLinesAsText.get(stopsArrayIndex)
    // .get(stopIdIndex)
    // .equals(station.getString(JSONSTR_STATION_ID))) {
    // try {
    // relevantStationNames.add(station.getString(JSONSTR_STATION_NAME));
    // } catch (JSONException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    // } catch (JSONException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    // }
    // });
    // return relevantStationNames;
    // }
    //
    // // TODO refactor, add javadoc
    // private List<JSONArray> getStopsOfLine(JSONArray jsonLinesTotal) throws
    // JSONException {
    // List<JSONObject> stopsOfLine =
    // getJSONObjectsFromJSONArray(jsonLinesTotal);
    // List<JSONArray> stopsOfLineArrays = new LinkedList<>();
    // stopsOfLine.forEach(s -> {
    // try {
    // for (String lineId : this.universityLinesIds) {
    // if (s.get(JSON_LINE_LABEL)
    // .equals(lineId)) {
    // stopsOfLineArrays.add(s.getJSONArray(JSONARR_LINE_IDS));
    // }
    // }
    //
    // } catch (JSONException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // });
    // return stopsOfLineArrays;
    // }
    //
    // // TODO refactor, add javadoc
    // private List<Integer> getDelayTimes(JSONObject jsonMonitorTotal) throws
    // JSONException {
    // List<Integer> delayTimes = new LinkedList<>();
    // if (jsonMonitorTotal.has(JSONARR_DEPARTURES_LIST)) {
    // JSONArray listOfDepartures =
    // jsonMonitorTotal.getJSONArray(JSONARR_DEPARTURES_LIST);
    //
    // List<JSONObject> departureObjects =
    // getJSONObjectsFromJSONArray(listOfDepartures);
    //
    // SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
    // Date now = new Date();
    // String nowAsText = sdf.format(now);
    //
    // departureObjects.forEach(s -> {
    // try {
    // String timeCut = s.getString(JSONSTR_PLANNED_DEPARTURE)
    // .substring(0, 5);
    // Integer timeDiff =
    // Integer.parseInt(s.getString(JSONSTR_DIFFERENCE_TIME));
    // if (!timeCut.contains(".")) {
    // delayTimes.add(timeDiff - calculateDiffTimeInMinutes(nowAsText,
    // timeCut));
    // }
    // } catch (JSONException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // });
    //
    // } else {
    // LOGGER.warning(JSONARR_DEPARTURES_LIST + " not contained in " +
    // jsonMonitorTotal.toString());
    // }
    // return delayTimes;
    // }
    //
    // private List<String> findLinesStoppingAt(List<JSONObject> allLines) {
    // List<String> lineLabels = new LinkedList<>();
    // allLines.forEach(line -> {
    // try {
    // JSONArray tmpLineIds = line.getJSONArray(JSONARR_LINE_IDS);
    // for (int i = 0; i < tmpLineIds.length(); ++i) {
    // if (tmpLineIds.get(i)
    // .equals(this.universityStationId)) {
    // lineLabels.add(line.get(JSON_LINE_LABEL)
    // .toString());
    // LOGGER.info("Found common line: " + line.getString(JSON_LINE_LABEL));
    // }
    // }
    // } catch (JSONException e) {
    // LOGGER.warning("JSONException while processing json array: " +
    // e.getLocalizedMessage());
    // }
    //
    // });
    // return lineLabels;
    // }

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
        JsonObject stationsMonitorArray = element.getAsJsonObject();
        return stationsMonitorArray;
    }

    private JsonElement getJsonFromWebRequest(URL requestUrl) throws IOException {
        String jsonResponse = null;
        StringBuilder sb = new StringBuilder();

        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("RNV_API_TOKEN", RNV_API_TOKEN);
        connection.setRequestProperty("Content-Type", "application/json");
        // TODO handle encoding problems

        InputStream is = connection.getInputStream();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(IOUtils.toString(is));
        return element;

        // JsonParser parser = new JsonParser();
        // JsonElement element = parser.parse(jsonResponseString);
        // JsonObject total = gson.fromJson(element.getAsJsonObject(),
        // JsonObject.class);
        //
        // InputStreamReader reader = new
        // InputStreamReader(connection.getInputStream());
        // BufferedReader buffReader = new BufferedReader(reader);
        // String line;
        // while ((line = buffReader.readLine()) != null) {
        // sb.append(line);
        // }
        // byte[] bytes = sb.toString()
        // .getBytes();
        // jsonResponse = new String(bytes, "UTF-8");
        // buffReader.close();
        // reader.close();
        // connection.disconnect();
        // return jsonResponse;
    }

    public static void main(String[] aflok) throws IOException {
        UserSettings userSettings = null;
        RNVAPI rnvapi = new RNVAPI(userSettings);

        // System.out.println("Request URLs:");
        for (URL url : rnvapi.requestUrls) {
            System.out.println(url);
        }

        JsonObject stationsPackage = rnvapi.getStationsPackage();
        System.out.println(stationsPackage);
        JsonArray linesPackage = rnvapi.getLinesPackage();
        System.out.println(linesPackage);

        JsonObject stationsMonitorPackage = rnvapi.getStationsMonitorPackage();
        System.out.println(stationsMonitorPackage);

        System.out.println(
                "------------------------------3---------------------------------------------------------------------------------------------------------");

        Gson gson = new Gson();
        JsonArray totalLines = rnvapi.getLinesPackage();
        JsonObject totalStations = rnvapi.getStationsPackage();

        // TramDetails tramDetails = rnvapi.extractTramDetails(gson, totalLines,
        // PARADEPLATZ);
        TramDetails tramDetails = rnvapi.extractTramDetails(gson, totalLines, totalStations, DUALE_HOCHSCHULE_STATION_ID);

    }

    @Override
    public URL buildRequestUrl(UserSettings usersettings) throws UnsupportedEncodingException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
