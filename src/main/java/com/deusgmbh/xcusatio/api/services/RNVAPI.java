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
import com.deusgmbh.xcusatio.api.data.TramNews;
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
    private final static String NEWS_ENTRIES = BASE_URL + "/regions/rnv/modules/news";

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
        JsonObject totalStations = getStationsPackage();
        JsonObject totalStationsMonitor = getStationsMonitorPackage();
        JsonArray totalNewsEntries = getNewsEntriesPackage();

        List<TramDetails> tramDetails = extractTramDetails(gson, totalLines, totalStations, totalStationsMonitor, DUALE_HOCHSCHULE_STATION_ID);
        return new RNVContext(tramDetails, null, null);
    }
    
    private List<TramNews> extractTramNews(JsonArray tramNewsEntries) {
    	return null;
    }

    

	private List<TramDetails> extractTramDetails(Gson gson, JsonArray totalLines, JsonObject totalStations, JsonObject totalStationsMonitor, String searchedStationId) {

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
        
        int[] round = {0};
        lineObjects.forEach(lineObject -> {
        	
            JsonArray stopIdsOfThisLine = gson.fromJson(lineObject.get(JSONARR_LINE_IDS), JsonArray.class);   
            for (int stopId = 0; stopId < stopIdsOfThisLine.size(); ++stopId) {
                String lineStopId = stopIdsOfThisLine.get(stopId)
                        .toString();
                lineStopId = removeQuotes(lineStopId);
                if (lineStopId.equals(searchedStationId)) {
                    String line = gson.fromJson(stopIdsOfThisLine.get(stopId), String.class);
                    line = removeQuotes(line);
                    String foundLine = gson.fromJson(lineObject.get(JSON_LINE_LABEL), String.class);
                    linesStoppingAtSearchedStation.add(foundLine);
                    stopIdsOfThisLine.forEach(stop -> {
                    	String stopTrimmed = removeQuotes(stop.toString());
                    	stopIdsOfLineText.add(stopTrimmed.toString());
                    });
                }
            }
        });    
        
        stopIdsOfLineText.forEach(id -> {       
        	stationsMap.forEach((stationId, name) -> {
        		if (id.equals(stationId) && (!stopNamesOfLine.contains(name))) {
        			stopNamesOfLine.add(name);
        		}
        	});
    	});
        
        System.out.println("Outest rounds: " + round[0]);
        
        //Determine delay times
        List<Integer> delayTimes = extractDelayTimes(gson, totalStationsMonitor);
        delayTimes.forEach(delay -> System.out.println(delay));
		
        List<TramDetails> tramDetails = new LinkedList<>();
        for (int tramIndex = 0; tramIndex < linesStoppingAtSearchedStation.size(); ++tramIndex) {
        	tramDetails.add(new TramDetails(linesStoppingAtSearchedStation.get(tramIndex), DUALE_HOCHSCHULE_STATION_ID, delayTimes, stopNamesOfLine));
        }
        
        for (int i = 0; i < tramDetails.size(); ++i) {
        	TramDetails currentDetail = tramDetails.get(i);
        	System.out.println("Line: " + currentDetail.getLineLabel() + "\nStation: " + currentDetail.getHomeStation() + "\nStops: " + currentDetail.getStops().size() + "\nDelay: " + currentDetail.getDifferenceTimesInMinutes().get(0));
        }
        
		return tramDetails;
        
    }
    
    private String removeQuotes(String stringWithinQuotes) {
    	return stringWithinQuotes.substring(1, stringWithinQuotes.length() - 1);
    }
   
    
    
        private List<Integer> extractDelayTimes(Gson gson, JsonObject totalStationsMonitor) {
        	List<Integer> delayTimes = new LinkedList<>();
        	JsonArray departuresArray = gson.fromJson(totalStationsMonitor.get(JSONARR_DEPARTURES_LIST), JsonArray.class);
            List<JsonObject> departureObjects = new LinkedList<>();
            for (int departureIndex = 0; departureIndex < departuresArray.size(); ++departureIndex) {
            	departureObjects.add(departuresArray.get(departureIndex).getAsJsonObject());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
            Date now = new Date();
            String nowAsText = sdf.format(now);
            departureObjects.forEach(departure -> {
    	    	String timeCut = gson.fromJson(departure.get(JSONSTR_PLANNED_DEPARTURE), String.class).substring(0, 5);
    		    String timeDiffText = gson.fromJson(departure.get(JSONSTR_DIFFERENCE_TIME), String.class);
    		    Integer timeDiff = Integer.parseInt(timeDiffText);
    		    if (!timeCut.contains(".")) {
    		    	delayTimes.add(timeDiff - calculateDiffTimeInMinutes(nowAsText, timeCut));
    		    }
            });
		return delayTimes;
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
        System.out.println(element);
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
    	
    	RNVAPI rnvapi = new RNVAPI(null);
    	RNVContext rnvContext = rnvapi.get(null);
    	
    	for (URL url : rnvapi.requestUrls) {
    		System.out.println(url);
    	}
    	
    	
//        UserSettings userSettings = null;
//        RNVAPI rnvapi = new RNVAPI(userSettings);
//
//        // System.out.println("Request URLs:");
//        for (URL url : rnvapi.requestUrls) {
//            System.out.println(url);
//        }
//
//        JsonObject stationsPackage = rnvapi.getStationsPackage();
//        System.out.println(stationsPackage);
//        JsonArray linesPackage = rnvapi.getLinesPackage();
//        System.out.println(linesPackage);
//
//        JsonObject stationsMonitorPackage = rnvapi.getStationsMonitorPackage();
//        System.out.println(stationsMonitorPackage);
//
//        System.out.println(
//                "------------------------------3---------------------------------------------------------------------------------------------------------");
//
//        Gson gson = new Gson();
//        JsonArray totalLines = rnvapi.getLinesPackage();
//        JsonObject totalStations = rnvapi.getStationsPackage();
//        JsonObject totalStationsMonitor = rnvapi.getStationsMonitorPackage();
//
//        // TramDetails tramDetails = rnvapi.extractTramDetails(gson, totalLines,
//        // PARADEPLATZ);
//        List<TramDetails> tramDetails = rnvapi.extractTramDetails(gson, totalLines, totalStations, totalStationsMonitor, DUALE_HOCHSCHULE_STATION_ID);

    }

    @Override
    public URL buildRequestUrl(UserSettings usersettings) throws UnsupportedEncodingException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
