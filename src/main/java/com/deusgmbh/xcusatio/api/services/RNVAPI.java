package com.deusgmbh.xcusatio.api.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
import com.deusgmbh.xcusatio.api.data.TramDetails;
import com.deusgmbh.xcusatio.api.data.TramNews;
import com.deusgmbh.xcusatio.api.data.TramStatus;
import com.deusgmbh.xcusatio.context.wildcard.RNVContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class RNVAPI extends APIService {
    private final static Logger LOGGER = Logger.getLogger(RNVAPI.class.getName());

    private final String DUALE_HOCHSCHULE_STATION_ID = "2521";
    private final String BASE_URL = "http://rnv.the-agent-factory.de:8080/easygo2/api";
    private final String RNV_API_TOKEN = "l1kjqp3r2m788o0oouolaeg8ui";

    private final String STATIONS_PACKAGE = BASE_URL + "/regions/rnv/modules/stations/packages/1";
    private final String LINES_PACKAGE = BASE_URL + "/regions/rnv/modules/lines/allJourney";
    private final String STATIONS_MONITOR = BASE_URL + "/regions/rnv/modules/stationmonitor/element?hafasID=";

    private final String JSONARR_STATIONS = "stations";
    private final String JSONSTR_STATION_ID = "hafasID";
    private final String JSONSTR_STATION_NAME = "longName";
    private final String JSONARR_LINE_IDS = "lineIDs";
    private final String JSON_LINE_LABEL = "lineId";
    private final String JSONARR_DEPARTURES_LIST = "listOfDepartures";
    private final String JSONSTR_PLANNED_DEPARTURE = "time";
    private final String JSONSTR_DIFFERENCE_TIME = "differenceTime";

    private String[] jsonResponses;

    private List<TramDetails> tramDetails;
    private List<TramNews> tramNews;
    private List<TramStatus> tramStatus;
    private String universityStationId;
    private List<String> universityLinesIds;

    public RNVAPI() {
        super();
        this.jsonResponses = new String[3];
        this.tramDetails = new LinkedList<>();
        this.tramNews = new LinkedList<>();
        this.tramStatus = new LinkedList<>();
        this.universityStationId = DUALE_HOCHSCHULE_STATION_ID;
        this.universityLinesIds = new LinkedList<>();
        universityLinesIds.add("5");
    }

    @Override
    public RNVContext get(UserSettings usersettings) {
        // TODO Auto-generated method stub

        // List<String> stops = new LinkedList<>();
        // stops.add("Mannheim Hbf");
        // stops.add("Duale Hochschule");
        // stops.add("Edingen");
        // stops.add("Heidelberg Hbf");
        //
        // TramDetails tram = new TramDetails("5", "Mannheim Hbf", "Heidelberg
        // Hbf", stops);
        //
        // List<String> affectedLines = new LinkedList<>();
        // affectedLines.add("3");
        // affectedLines.add("5");
        //
        // String dateFormat = "MM/dd/yyyy hh:mm:ss";
        // Date timeStamp = null;
        // try {
        // timeStamp = new SimpleDateFormat(dateFormat).parse("03/27/2018
        // 09:00:00");
        // } catch (Exception e) {
        // LOGGER.info("parsed wrong date format, " + e.getMessage());
        // }
        //
        // TramNews newsEntry = new TramNews(timeStamp, "Stoerung", "Oberleitung
        // beschaedigt", affectedLines);
        //
        // RNVContext rnvContext = new RNVContext(tram, newsEntry,
        // TramStatus.CANCELLED, 30);

        return null;
    }

    @Override
    public void transmitDataToWebsite() {
        // TODO Auto-generated method stub

    }

    @Override
    public void extractDesiredInfoFromResponse() throws JSONException {
        // TODO 1 get tram details: line label, start, end, diffTime
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd+hh:mm:ss");
        Date now = new Date();
        String dateText = sdf.format(now);
        getResponseFromSpecificWebsite(STATIONS_PACKAGE, 0);
        getResponseFromSpecificWebsite(LINES_PACKAGE, 1);
        getResponseFromSpecificWebsite(STATIONS_MONITOR + this.universityStationId + "&time=" + dateText, 2);

        System.out.println(this.jsonResponses[0] + "\n" + this.jsonResponses[1] + "\n" + this.jsonResponses[2]);
        extractTramDetails();

        this.tramDetails.forEach(s -> {
            System.out.println("Home: " + s.getHomeStation() + " Line: " + s.getLineLabel());
            System.out.println("Stops: ");
            s.getStops()
                    .forEach(stop -> System.out.println(stop));
        });

    }

    // TODO refactor, add javadoc
    private void extractTramDetails() throws JSONException {
        List<String> lineLabels = new LinkedList<>();
        List<Integer> delayTimesOfLine = new LinkedList<>();
        List<String> stopNamesOfLine = new LinkedList<>();

        JSONObject jsonStationsTotal = new JSONObject(this.jsonResponses[0]);
        JSONArray jsonLinesTotal = new JSONArray(this.jsonResponses[1]);
        JSONObject jsonMonitorTotal = new JSONObject(this.jsonResponses[2]);

        List<JSONObject> allLines = getJSONObjectsFromJSONArray(jsonLinesTotal);
        // List<JSONArray> allLinesStations =
        // getJSONArraysFromJSONObjects(allLines, this.JSONARR_LINE_IDS);

        lineLabels = findLinesStoppingAt(allLines);

        delayTimesOfLine = getDelayTimes(jsonMonitorTotal);
        // delayTimesOfLine.forEach(s -> System.out.println(s));

        List<JSONArray> stopsOfLineAsArrays = getStopsOfLine(jsonLinesTotal);

        stopNamesOfLine = mapLineIdsToStationNames(jsonStationsTotal, stopsOfLineAsArrays);

        for (int i = 0; i < lineLabels.size(); ++i) {
            this.tramDetails.add(new TramDetails(lineLabels.get(i), this.universityStationId, stopNamesOfLine));
        }

    }

    // TODO refactor, add javadoc
    private List<String> mapLineIdsToStationNames(JSONObject jsonStationsTotal, List<JSONArray> stopsOfLineAsArrays)
            throws JSONException {
        JSONArray allStations = jsonStationsTotal.getJSONArray(JSONARR_STATIONS);
        List<JSONObject> allStationObjects = getJSONObjectsFromJSONArray(allStations);
        List<List<String>> idsOfLinesAsText = getValuesFromJSONArrayList(stopsOfLineAsArrays);

        // idsOfLinesAsText.forEach(list -> list.forEach(str ->
        // System.out.println(str)));

        List<String> allStationNames = new LinkedList<>();
        List<String> relevantStationNames = new LinkedList<>();
        try {
            allStationNames = getValuesFromJSONObjects(allStationObjects, JSONSTR_STATION_NAME);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        allStationObjects.forEach(station -> {
            for (int stopsArrayIndex = 0; stopsArrayIndex < stopsOfLineAsArrays.size(); ++stopsArrayIndex) {
                for (int stopIdIndex = 0; stopIdIndex < stopsOfLineAsArrays.get(stopsArrayIndex)
                        .length(); ++stopIdIndex) {
                    try {
                        if (idsOfLinesAsText.get(stopsArrayIndex)
                                .get(stopIdIndex)
                                .equals(station.getString(JSONSTR_STATION_ID))) {
                            try {
                                relevantStationNames.add(station.getString(JSONSTR_STATION_NAME));
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        return relevantStationNames;
    }

    // TODO refactor, add javadoc
    private List<JSONArray> getStopsOfLine(JSONArray jsonLinesTotal) throws JSONException {
        List<JSONObject> stopsOfLine = getJSONObjectsFromJSONArray(jsonLinesTotal);
        List<JSONArray> stopsOfLineArrays = new LinkedList<>();
        stopsOfLine.forEach(s -> {
            try {
                for (String lineId : this.universityLinesIds) {
                    if (s.get(JSON_LINE_LABEL)
                            .equals(lineId)) {
                        stopsOfLineArrays.add(s.getJSONArray(JSONARR_LINE_IDS));
                    }
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        return stopsOfLineArrays;
    }

    // TODO refactor, add javadoc
    private List<Integer> getDelayTimes(JSONObject jsonMonitorTotal) throws JSONException {
        List<Integer> delayTimes = new LinkedList<>();
        if (jsonMonitorTotal.has(JSONARR_DEPARTURES_LIST)) {
            JSONArray listOfDepartures = jsonMonitorTotal.getJSONArray(JSONARR_DEPARTURES_LIST);

            List<JSONObject> departureObjects = getJSONObjectsFromJSONArray(listOfDepartures);

            SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
            Date now = new Date();
            String nowAsText = sdf.format(now);

            departureObjects.forEach(s -> {
                try {
                    String timeCut = s.getString(JSONSTR_PLANNED_DEPARTURE)
                            .substring(0, 5);
                    Integer timeDiff = Integer.parseInt(s.getString(JSONSTR_DIFFERENCE_TIME));
                    if (!timeCut.contains(".")) {
                        delayTimes.add(timeDiff - calculateDiffTimeInMinutes(nowAsText, timeCut));
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });

        } else {
            LOGGER.warning(JSONARR_DEPARTURES_LIST + " not contained in " + jsonMonitorTotal.toString());
        }
        return delayTimes;
    }

    private List<String> findLinesStoppingAt(List<JSONObject> allLines) {
        List<String> lineLabels = new LinkedList<>();
        allLines.forEach(line -> {
            try {
                JSONArray tmpLineIds = line.getJSONArray(JSONARR_LINE_IDS);
                for (int i = 0; i < tmpLineIds.length(); ++i) {
                    if (tmpLineIds.get(i)
                            .equals(this.universityStationId)) {
                        lineLabels.add(line.get(JSON_LINE_LABEL)
                                .toString());
                        LOGGER.info("Found common line: " + line.getString(JSON_LINE_LABEL));
                    }
                }
            } catch (JSONException e) {
                LOGGER.warning("JSONException while processing json array: " + e.getLocalizedMessage());
            }

        });
        return lineLabels;
    }

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

    @Override
    public void printResponse() {
        // TODO Auto-generated method stub

    }

    public void getResponseFromSpecificWebsite(String requestUrl, int responseIndex) {
        try {
            getXMLFromInputStream(requestUrl, responseIndex);
            // System.out.println(this.xmlResponse);
        } catch (IOException e) {
            LOGGER.warning("Error getting jsonString from input stream: " + e.getMessage());
        }
        // try {
        // JSONObject responseObject = XML.toJSONObject(this.jsonStations);
        // } catch (JSONException e) {
        // LOGGER.warning("JSONException during request: " +
        // e.getLocalizedMessage());
        // }

        // System.out.println(this.jsonResponse);

    }

    private void getXMLFromInputStream(String requestUrl, int responseIndex) throws IOException {
        URL url = new URL(requestUrl);
        super.setRequestUrl(url);
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

        // TODO do this in other api calls as well

        byte[] bytes = sb.toString()
                .getBytes();
        this.jsonResponses[responseIndex] = new String(bytes, "UTF-8");
        buffReader.close();
        reader.close();
        connection.disconnect();
    }

    @Override
    public void buildRequestUrl(UserSettings usersettings) {

    }

    public static void main(String[] aflok) {

        RNVAPI rnvapi = new RNVAPI();
        // rnvapi.getResponseFromSpecificWebsite(rnvapi.STATIONS_PACKAGE, 0);
        // rnvapi.getResponseFromSpecificWebsite(rnvapi.LINES_PACKAGE, 1);

        try {
            rnvapi.extractDesiredInfoFromResponse();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /* check if still necessary in inheriting class */
    @Override
    public void getResponseFromWebsite() {
        // TODO Auto-generated method stub

    }

}
