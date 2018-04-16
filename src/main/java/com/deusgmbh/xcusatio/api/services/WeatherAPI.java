package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.json.JSONException;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.GeocodeData;
import com.deusgmbh.xcusatio.context.data.WeatherContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 
 * @author Lars.Dittert@de.ibm.com
 *
 */
public class WeatherAPI extends APIService {
    private static final Logger LOGGER = Logger.getLogger(WeatherAPI.class.getName());

    private final static String URL_START_SCHEME = "https://";
    private final static String BASE_URL = "@twcservice.mybluemix.net:443/api/weather/v1/geocode";

    private final static String USERNAME = "ac762809-4ed0-4539-b8ae-dc4d4e4024ac";
    private final static String PASSWORD = "V1FKa8Hcp1";
    private final static String CREDENTIALS = USERNAME + ":" + PASSWORD;

    private final static String CURRENT_WEATHER_PACKAGE = "/observations.json?units=m&language=de-DE";

    private static final String JSONOB_OBSERVATIONS = "observation";

    private static final String JSONOB_TEMPERATURE = "temp";
    private static final String JSONOB_RAIN_HOURLY = "precip_hrly";
    private static final String JSONOB_SNOW_HOURLY = "snow_hrly";
    private static final String JSONOB_WINDSPEED = "wspd";

    private GeocodeData geocodeData;

    @Override
    public WeatherContext get(UserSettings usersettings) throws IOException {
        URL requestUrl = buildRequestUrl(usersettings);
        Gson gson = new Gson();
        JsonObject total = getTotalJsonObject(requestUrl, gson);

        WeatherContext weatherContext = new WeatherContext().setWindSpeed(this.extractCurrentWindSpeed(gson, total))
                .setSnowHourly(this.extractCurrentSnowHourly(gson, total))
                .setRainHourly(this.extractCurrentRainHourly(gson, total))
                .setTemperature(this.extractCurrentTemperature(gson, total));
        return weatherContext;
    }

    public URL buildRequestUrl(UserSettings usersettings) throws IOException {
        GeocodeAPI gApi = new GeocodeAPI();
        GeocodeData gcd = gApi.get(usersettings);
        String mapTiles = prepareUrlBuild(gcd, usersettings);
        try {
            return new URL(URL_START_SCHEME + CREDENTIALS + BASE_URL + mapTiles + CURRENT_WEATHER_PACKAGE);
        } catch (MalformedURLException e) {
            throw new RuntimeException("error building url");
        }
    }

    private String prepareUrlBuild(GeocodeData gcd, UserSettings usersettings) {
        int[] mapTiles = gcd.getMapTiles();
        return "12/" + String.valueOf(mapTiles[0]) + "/" + String.valueOf(mapTiles[1]);
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
        JsonArray observationItemArray = gson.fromJson(total.get(JSONOB_OBSERVATIONS), JsonArray.class);
        List<JsonObject> observationItemsFromArray = new LinkedList<>();
        observationItemArray.forEach(element -> {
            observationItemsFromArray.add((JsonObject) element);
        });
        return observationItemsFromArray;
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
    private Integer extractCurrentTemperature(Gson gson, JsonObject total) {
        List<JsonObject> observationItems = getRelevantJsonObjects(gson, total);
        Optional<JsonObject> currentTemperature = observationItems.stream()
                .filter(observationItem -> {
                    return gson.fromJson(observationItem.get(JSONOB_TEMPERATURE), Integer.class) != null;
                })
                .findFirst();
        if (currentTemperature.isPresent()) {
            return gson.fromJson(currentTemperature.get(), Integer.class);
        }
        return null;
    }

    private Integer extractCurrentRainHourly(Gson gson, JsonObject total) {
        List<JsonObject> observationItems = getRelevantJsonObjects(gson, total);
        Optional<JsonObject> rainHourly = observationItems.stream()
                .filter(observationItem -> {
                    return gson.fromJson(observationItem.get(JSONOB_RAIN_HOURLY), Integer.class) != null;
                })
                .findFirst();
        if (rainHourly.isPresent()) {
            return gson.fromJson(rainHourly.get(), Integer.class);
        }
        return null;
    }

    private Integer extractCurrentSnowHourly(Gson gson, JsonObject total) {
        List<JsonObject> observationItems = getRelevantJsonObjects(gson, total);
        Optional<JsonObject> snowHourly = observationItems.stream()
                .filter(observationItem -> {
                    return gson.fromJson(observationItem.get(JSONOB_SNOW_HOURLY), Integer.class) != null;
                })
                .findFirst();
        if (snowHourly.isPresent()) {
            return gson.fromJson(snowHourly.get(), Integer.class);
        }
        return null;
    }

    private Integer extractCurrentWindSpeed(Gson gson, JsonObject total) {
        List<JsonObject> observationItems = getRelevantJsonObjects(gson, total);
        Optional<JsonObject> windSpeed = observationItems.stream()
                .filter(observationItem -> {
                    return gson.fromJson(observationItem.get(JSONOB_WINDSPEED), Integer.class) != null;
                })
                .findFirst();
        if (windSpeed.isPresent()) {
            return gson.fromJson(windSpeed.get(), Integer.class);
        }
        return null;
    }
}