package com.deusgmbh.xcusatio.api.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.context.wildcard.RNVContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RNVAPI extends APIService {
    private final static Logger LOGGER = Logger.getLogger(RNVAPI.class.getName());

    private final static String DUALE_HOCHSCHULE_STATION_ID = "2521";
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

    // private List<TramDetails> tramDetails;
    // private List<TramNews> tramNews;
    // private List<TramStatus> tramStatus;
    // private String universityStationId;
    // private List<String> universityLinesIds;

    public RNVAPI() {
        // super();
        // this.jsonResponses = new String[3];
        // this.tramDetails = new LinkedList<>();
        // this.tramNews = new LinkedList<>();
        // this.tramStatus = new LinkedList<>();
        // this.universityStationId = DUALE_HOCHSCHULE_STATION_ID;
        // this.universityLinesIds = new LinkedList<>();
        // universityLinesIds.add("5");
    }

    @Override
    public RNVContext get(UserSettings usersettings) throws UnsupportedEncodingException, IOException {

        URL requestUrl = buildRequestUrl(usersettings);
        Gson gson = new Gson();
        JsonObject total = getTotalJsonObject(requestUrl, gson);
        return null;
    }

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

    public JsonElement[] getResponsesFromSites(URL[] requestUrls) throws IOException {
        JsonElement[] jsonResponseElements = new JsonObject[3];
        String[] responseStrings = getJsonFromInputStream(requestUrls);
        if (jsonResponseElements.length != responseStrings.length) {
            throw new RuntimeException("bug in method getResponsesFromSites");
        }
        JsonParser parser = new JsonParser();
        int responseIndex = 0;
        for (String response : responseStrings) {
            JsonElement element = parser.parse(response);
            if (element.isJsonObject()) {
                jsonResponseElements[responseIndex] = element;
                System.out.println(element);
            } else {
                response = "{\"object\":" + response + "}";
                element = parser.parse(response);
                jsonResponseElements[responseIndex] = element;
            }

        }
        return jsonResponseElements;
    }

    private String[] getJsonFromInputStream(URL[] requestUrls) throws IOException {
        String[] jsonResponses = new String[3];
        int responseIndex = 0;
        for (URL url : requestUrls) {
            StringBuilder sb = new StringBuilder();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("RNV_API_TOKEN", RNV_API_TOKEN);
            connection.setRequestProperty("Content-Type", "application/json");

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            BufferedReader buffReader = new BufferedReader(reader);
            String line;
            while ((line = buffReader.readLine()) != null) {
                sb.append(line);
            }
            byte[] bytes = sb.toString()
                    .getBytes();
            jsonResponses[responseIndex] = new String(bytes, "UTF-8");
            buffReader.close();
            reader.close();
            connection.disconnect();
            ++responseIndex;
        }

        return jsonResponses;
    }

    public static void main(String[] aflok) throws IOException {
        UserSettings userSettings = null;
        RNVAPI rnvapi = new RNVAPI();

        URL[] requestUrls = rnvapi.buildRequestUrls(userSettings);
        // System.out.println("Request URLs:");
        for (URL url : requestUrls) {
            System.out.println(url);
        }
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------------------");

        System.out.println("Json Objects:");

        JsonElement[] jsonElements = rnvapi.getResponsesFromSites(requestUrls);
        for (JsonElement jsonObject : jsonElements) {
            System.out.println(jsonObject);
        }
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------------------");

    }

    @Override
    public URL buildRequestUrl(UserSettings usersettings) throws UnsupportedEncodingException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
