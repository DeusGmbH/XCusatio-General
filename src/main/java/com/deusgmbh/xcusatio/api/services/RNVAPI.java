package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.TramDetails;
import com.deusgmbh.xcusatio.api.data.TramNews;
import com.deusgmbh.xcusatio.api.data.TramStatus;
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

    private final static String JSONARR_NEWS_AFFECTED_LINES = "seperatedLines";
    private final static String JSONSTR_NEWS_TITLE = "title";
    private final static String JSONSTR_NEWS_CONTENT = "text";

    private final static String JSONSTR_NEWS_VALID_FROM = "validFrom";
    private final static String JSONSTR_NEWS_VALID_TO = "validTo";

    private final static String JSONSTR_STATUS = "status";
    private final static String JSONSTR_LINE_LABEL = "lineLabel";

    private final static SimpleDateFormat RNV_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd+hh:mm:ss");

    private URL[] requestUrls; // could also be stored static

    public RNVAPI(UserSettings userSettings) {
        this.requestUrls = this.buildRequestUrls(userSettings);
    }

    /**
     * 
     */
    @Override
    public RNVContext get(UserSettings usersettings) throws UnsupportedEncodingException, IOException {
        URL requestUrl = buildRequestUrl(usersettings);
        Gson gson = new Gson();
        JsonArray totalLines = getLinesPackage();
        JsonObject totalStations = getStationsPackage();
        JsonObject totalStationsMonitor = getStationsMonitorPackage();
        JsonArray totalNewsEntries = getNewsEntriesPackage();
        List<TramDetails> tramDetails = extractTramDetails(gson, totalLines, totalStations, totalStationsMonitor,
                DUALE_HOCHSCHULE_STATION_ID);
        List<TramNews> tramNews = extractTramNews(gson, totalNewsEntries, DUALE_HOCHSCHULE_STATION_ID, "5");
        List<TramStatus> tramStatus = extractTramStatus(gson, totalStationsMonitor, "5");
        return new RNVContext(tramDetails, tramNews, tramStatus);
    }

    private List<TramNews> extractTramNews(Gson gson, JsonArray tramNewsEntries, String searchedStationId,
            String lineLabelOfInterest) {
        List<JsonObject> tramNewsObjectsList = new LinkedList<>();
        for (int tramNewsObjectIndex = 0; tramNewsObjectIndex < tramNewsEntries.size(); ++tramNewsObjectIndex) {
            tramNewsObjectsList.add(tramNewsEntries.get(tramNewsObjectIndex)
                    .getAsJsonObject());
        }
        List<JsonObject> newsObjectsOfInterest = filterNewsObjectsByLineLabel(gson, tramNewsObjectsList,
                lineLabelOfInterest);
        List<String> newsTitlesList = extractNewsTitles(gson, newsObjectsOfInterest);
        List<String> newsContentsList = extractNewsContents(gson, newsObjectsOfInterest);
        List<LocalDate[]> timeStampsList = extractNewsTimeStamps(gson, newsObjectsOfInterest);
        List<List<String>> affectedLines = extractAffectedLines(gson, newsObjectsOfInterest);

        List<TramNews> tramNewsList = new LinkedList<>();
        for (int i = 0; i < newsObjectsOfInterest.size(); ++i) {
            TramNews tramNews = new TramNews(timeStampsList.get(i), newsTitlesList.get(i), newsContentsList.get(i),
                    affectedLines.get(i));
            tramNewsList.add(tramNews);
        }
        return tramNewsList;
    }

    private List<List<String>> extractAffectedLines(Gson gson, List<JsonObject> newsObjects) {
        List<List<String>> allLineListsOccuringInNews = new LinkedList<>();
        List<String> affectedLinesPerNewsEntry = new LinkedList<>();
        newsObjects.forEach(news -> {
            JsonArray affectedLines = gson.fromJson(news.get(JSONARR_NEWS_AFFECTED_LINES), JsonArray.class);
            for (int lineIndex = 0; lineIndex < affectedLines.size(); ++lineIndex) {
                String line = removeQuotes(affectedLines.get(lineIndex)
                        .toString());
                affectedLinesPerNewsEntry.add(line);
            }
            allLineListsOccuringInNews.add(affectedLinesPerNewsEntry);
        });
        return allLineListsOccuringInNews;
    }

    private List<String> extractNewsContents(Gson gson, List<JsonObject> newsObjects) {
        List<String> newsContents = new LinkedList<>();
        newsObjects.forEach(news -> {
            String newsTitle = gson.fromJson(news.get(JSONSTR_NEWS_CONTENT), String.class);
            newsContents.add(newsTitle);
        });
        return newsContents;
    }

    private List<LocalDate[]> extractNewsTimeStamps(Gson gson, List<JsonObject> newsObjects) {
        List<LocalDate[]> timeStampsList = new LinkedList<>();
        LocalDate[] startAndEndDate = new LocalDate[2];
        newsObjects.forEach(newsObject -> {
            String start = gson.fromJson(newsObject.get(JSONSTR_NEWS_VALID_FROM), String.class);
            String end = gson.fromJson(newsObject.get(JSONSTR_NEWS_VALID_TO), String.class);
            startAndEndDate[0] = convertMillisTextToLocalDate(start);
            startAndEndDate[1] = convertMillisTextToLocalDate(end);
            timeStampsList.add(startAndEndDate);
        });
        return timeStampsList;
    }

    private LocalDate convertMillisTextToLocalDate(String millisText) {
        Long millisLong = Long.parseLong(millisText);
        LocalDate localDate = Instant.ofEpochMilli(millisLong)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate;
    }

    private List<String> extractNewsTitles(Gson gson, List<JsonObject> newsObjects) {
        List<String> newsTitles = new LinkedList<>();
        newsObjects.forEach(news -> {
            String newsTitle = gson.fromJson(news.get(JSONSTR_NEWS_TITLE), String.class);
            newsTitles.add(newsTitle);
        });
        return newsTitles;
    }

    private List<JsonObject> filterNewsObjectsByLineLabel(Gson gson, List<JsonObject> tramNewsObjectsList,
            String lineLabelOfInterest) {
        List<JsonObject> newsObjects = new LinkedList<>();
        tramNewsObjectsList.forEach(newsObject -> {
            JsonArray affectedLines = gson.fromJson(newsObject.get(JSONARR_NEWS_AFFECTED_LINES), JsonArray.class);
            for (int lineIndex = 0; lineIndex < affectedLines.size(); ++lineIndex) {
                String currentLine = removeQuotes(affectedLines.get(lineIndex)
                        .toString());
                if ((currentLine.equals(lineLabelOfInterest))) {
                    newsObjects.add(newsObject);
                } else {
                    continue;
                }
            }
        });
        return newsObjects;
    }

    private List<TramDetails> extractTramDetails(Gson gson, JsonArray totalLines, JsonObject totalStations,
            JsonObject totalStationsMonitor, String searchedStationId) {

        // fill a list with json objects representing lines with their
        // associated station IDs
        List<JsonObject> lineObjects = new LinkedList<>();
        for (int lineObjectIndex = 0; lineObjectIndex < totalLines.size(); ++lineObjectIndex) {
            lineObjects.add(totalLines.get(lineObjectIndex)
                    .getAsJsonObject());
        }

        // extract the line label(s) of trams stopping at the searched station
        List<String> linesStoppingAtSearchedStation = new LinkedList<>();
        List<String> stopIdsOfLineAsText = new LinkedList<>();
        List<String> stopNamesOfLine = new LinkedList<>();

        // use a map which contains all stations with their IDs as keys and
        // their textual names as values
        Map<String, String> stationsMap = getStationIdNamesMap(gson, totalStations);

        lineObjects.forEach(lineObject -> {

            // get the stop IDs of the current lineObject iterated over...
            JsonArray stopIdsOfThisLine = gson.fromJson(lineObject.get(JSONARR_LINE_IDS), JsonArray.class);

            // ...and iterate over these IDs
            for (int stopId = 0; stopId < stopIdsOfThisLine.size(); ++stopId) {
                String lineStopId = stopIdsOfThisLine.get(stopId)
                        .toString();
                lineStopId = removeQuotes(lineStopId);

                // test if the station ID belonging to the current line object
                // matches the one searched for that is
                // a line has been found that stops at the desired station
                if (lineStopId.equals(searchedStationId)) {
                    String line = gson.fromJson(stopIdsOfThisLine.get(stopId), String.class);
                    line = removeQuotes(line);
                    String foundLine = gson.fromJson(lineObject.get(JSON_LINE_LABEL), String.class);
                    linesStoppingAtSearchedStation.add(foundLine);

                    // add all of the stops of the found line to a list
                    stopIdsOfThisLine.forEach(stop -> {
                        String stopTrimmed = removeQuotes(stop.toString());
                        stopIdsOfLineAsText.add(stopTrimmed.toString());
                    });
                }
            }
        });
        stopIdsOfLineAsText.forEach(id -> {
            stationsMap.forEach((stationId, name) -> {
                if (id.equals(stationId) && (!stopNamesOfLine.contains(name))) {
                    stopNamesOfLine.add(name);
                }
            });
        });
        // Determine delay times
        List<Integer> delayTimes = extractDelayTimes(gson, totalStationsMonitor);

        List<TramDetails> tramDetails = new LinkedList<>();
        for (int tramIndex = 0; tramIndex < linesStoppingAtSearchedStation.size(); ++tramIndex) {
            tramDetails.add(new TramDetails(linesStoppingAtSearchedStation.get(tramIndex), DUALE_HOCHSCHULE_STATION_ID,
                    delayTimes, stopNamesOfLine));
        }
        return tramDetails;

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

    private List<Integer> extractDelayTimes(Gson gson, JsonObject totalStationsMonitor) {
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
        return delayTimes;
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

    private List<TramStatus> extractTramStatus(Gson gson, JsonObject totalStationsMonitor, String lineLabelOfInterest) {
        List<String> tramStatusList = new LinkedList<>();
        JsonArray departures = gson.fromJson(totalStationsMonitor.get(JSONARR_DEPARTURES_LIST), JsonArray.class);

        // collect the departure objects in a list
        List<JsonObject> departureObjectsList = new LinkedList<>();
        for (int departureIndex = 0; departureIndex < departures.size(); ++departureIndex) {
            departureObjectsList.add(departures.get(departureIndex)
                    .getAsJsonObject());
        }

        // get just the status objects affecting the line searched for
        List<JsonObject> statusObjectsOfInterest = filterTramStatusByLineLabel(gson, departureObjectsList,
                lineLabelOfInterest);

        statusObjectsOfInterest.forEach(status -> {
            String line = removeQuotes(gson.fromJson(status.get(JSONSTR_LINE_LABEL), String.class));
            if (!(line.equals(lineLabelOfInterest))) {
                throw new RuntimeException("filtering lines of interest failed!");
            } else {
                String tramStatusText = gson.fromJson(status.get(JSONSTR_STATUS), String.class);
                tramStatusList.add(tramStatusText);
            }
        });

        // fill the TramStatus-list to be returned
        List<TramStatus> tramStatus = new LinkedList<>();
        tramStatusList.forEach(status -> {
            TramStatus tS = TramStatus.valueOf(status);
            tramStatus.add(tS);
        });
        return tramStatus;
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

    // if urls stored static this method becomes obsolete; currently
    // UserSettings is not used here due to the project's requirements
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

    // getters and setters
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
    }

    // testing
    public static void main(String[] aflok) throws IOException {

        RNVAPI rnvapi = new RNVAPI(null);
        RNVContext rnvContext = rnvapi.get(null);

        rnvContext.getTramDetails()
                .forEach(s -> {
                    System.out.println(s.getLineLabel());
                    System.out.println(s.getStops());
                    System.out.println(s.getDifferenceTimesInMinutes());
                });

        rnvContext.getTramStatus()
                .forEach(s -> {
                    System.out.println(s);
                });

        rnvContext.getNewsEntries()
                .forEach(s -> {
                    System.out.println(s.getTitle());
                    System.out.println(s.getTimestamps()[0] + " - " + s.getTimestamps()[1]);
                    System.out.println(s.getAffectedLines());
                    System.out.println(s.getContent());
                });
    }

    @Override
    public URL buildRequestUrl(UserSettings usersettings) throws UnsupportedEncodingException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
