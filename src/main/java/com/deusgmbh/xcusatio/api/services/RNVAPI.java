package com.deusgmbh.xcusatio.api.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    private final String JSONARR_STATIONS = "stations";
    private final String JSONSTR_STATION_ID = "hafasID";
    private final String JSONSTR_STATION_NAME = "longName";
    private final String JSONARR_LINE_IDS = "lineIDs";

    private String[] jsonResponses;

    private List<TramDetails> tramDetails;
    private List<TramNews> tramNews;
    private List<TramStatus> tramStatus;
    private String universityStationId;

    public RNVAPI() {
        super();
        this.jsonResponses = new String[2];
        this.universityStationId = DUALE_HOCHSCHULE_STATION_ID;
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
        getResponseFromSpecificWebsite(STATIONS_PACKAGE, 0);
        getResponseFromSpecificWebsite(LINES_PACKAGE, 1);

        System.out.println(this.jsonResponses[0] + "\n" + this.jsonResponses[1]);
        getLineLabel();

    }

    private void getLineLabel() throws JSONException {
        JSONObject jsonStationsTotal = new JSONObject(this.jsonResponses[0]);
        JSONArray jsonLinesTotal = new JSONArray(this.jsonResponses[1]);

        List<JSONObject> allLines = getJSONObjectsFromJSONArray(jsonLinesTotal);
        // allLines.forEach(s -> System.out.println(s));

        List<JSONArray> allLinesStations = getJSONArraysFromJSONObjects(allLines, this.JSONARR_LINE_IDS);
        allLinesStations.forEach(s -> System.out.println(s));
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
