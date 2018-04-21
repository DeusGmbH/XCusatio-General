package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import org.json.JSONException;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.context.data.WeatherContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 
 * @author jan.leiblein@gmail.com
 *
 */

public class WeatherAPI extends APIService {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String CITY_NAME = "mannheim";
    private static final String COUNTRY_CODE = "de";
    private static final String UNITS_FORMAT = "metric";
    private static final String API_KEY = "6d2f9cc11f7325e34185f30bf4968e47";

    private static final String JSONARR_WEATHER = "weather";
    private static final String JSONOB_MAIN = "main";
    private static final String JSONOB_WIND = "wind";
    private static final String JSONOB_RAIN = "rain";
    private static final String JSONOB_SNOW = "snow";

    private static final String JSONSTR_WEATHER_DESCRIPTION = "description";

    private static final String JSONSTR_MAIN_TEMP = "temp";
    private static final String JSONSTR_WIND_SPEED = "speed";
    private static final String JSONSTR_WIND_DIRECTION = "deg";
    private static final String JSONSTR_RAIN_3_HOURS = "3h";
    private static final String JSONSTR_SNOW_3_HOURS = "3h";

    public URL buildRequestUrl(String city, String countryCode) {
        String urlText = BASE_URL + city + "," + countryCode + "&units=" + UNITS_FORMAT + "&appid=" + API_KEY;
        try {
            return new URL(urlText);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error building request url for openweathermap");
        }
    }

    @Override
    public WeatherContext get(UserSettings usersettings) throws IOException, JSONException, ParseException {
        Gson gson = new Gson();
        URL requestUrl = buildRequestUrl(CITY_NAME, COUNTRY_CODE);
        JsonObject totalJsonResponse = getTotalJsonObject(requestUrl, gson);

        int temperature = extractTemperature(totalJsonResponse);
        String description = extractWeatherDescription(totalJsonResponse);
        String windDirection = extractWindDirection(totalJsonResponse);
        int windSpeed = extractWindSpeed(totalJsonResponse);
        int snowHourly = extractSnowData(totalJsonResponse);
        int rainHourly = extractRainData(totalJsonResponse);

        WeatherContext weatherContext = new WeatherContext(temperature, description, windDirection, windSpeed,
                snowHourly, rainHourly);

        System.out.println("Temperature: " + weatherContext.getTemperature());
        System.out.println("Description: " + weatherContext.getDescription());
        System.out.println("Rain: " + weatherContext.getRainHourly());
        System.out.println("Snow: " + weatherContext.getSnowHourly());
        System.out.println("WindDir: " + weatherContext.getWindDirection());
        System.out.println("WindSpeed: " + weatherContext.getWindSpeed());

        return weatherContext;
    }

    private JsonObject extractWindData(JsonObject totalJsonResponse) {
        Gson gson = new Gson();
        JsonObject wind = gson.fromJson(totalJsonResponse.get(JSONOB_WIND), JsonObject.class);
        return wind;

    }

    private String extractWindDirection(JsonObject totalJsonResponse) {
        Gson gson = new Gson();
        JsonObject wind = extractWindData(totalJsonResponse);
        try {
            int windDirection = gson.fromJson(wind.get(JSONSTR_WIND_DIRECTION), int.class);
            String windDirectionText = convertWindDirectionToText(windDirection);
            return windDirectionText;
        } catch (NullPointerException e) {
            return "N/A"; // no wind blowing - no wind direction
        }
    }

    private String convertWindDirectionToText(int windDirection) {
        if (windDirection >= 0 && windDirection < 23) {
            return "N";
        }
        if (windDirection >= 23 && windDirection < 68) {
            return "NE";
        }
        if (windDirection >= 68 && windDirection < 113) {
            return "E";
        }
        if (windDirection >= 113 && windDirection < 158) {
            return "SE";
        }
        if (windDirection >= 158 && windDirection < 203) {
            return "S";
        }
        if (windDirection >= 203 && windDirection < 248) {
            return "SW";
        }
        if (windDirection >= 248 && windDirection < 293) {
            return "W";
        }
        return "NW";
    }

    private int extractWindSpeed(JsonObject totalJsonResponse) {
        Gson gson = new Gson();
        JsonObject wind = extractWindData(totalJsonResponse);
        try {
            double windSpeed = gson.fromJson(wind.get(JSONSTR_WIND_SPEED), double.class);
            int windSpeedRounded = (int) Math.ceil(windSpeed);
            return windSpeedRounded;
        } catch (NullPointerException e) {
            return -1; // why ever there is no wind speed available
        }

    }

    private int extractRainData(JsonObject totalJsonResponse) {
        Gson gson = new Gson();
        try {
            JsonObject rain = gson.fromJson(totalJsonResponse.get(JSONOB_RAIN), JsonObject.class);
            String rainMilliMeters = gson.fromJson(rain.get(JSONSTR_RAIN_3_HOURS), String.class);
            int rainMilliMetersInt = Integer.parseInt(rainMilliMeters);
            return rainMilliMetersInt;
        } catch (NullPointerException e) {
            return -1; // if it does not rain there won't be a response object
                       // "rain"
        }
    }

    private int extractSnowData(JsonObject totalJsonResponse) {
        Gson gson = new Gson();
        try {
            JsonObject snow = gson.fromJson(totalJsonResponse.get(JSONOB_SNOW), JsonObject.class);
            String snowMilliMeters = gson.fromJson(snow.get(JSONSTR_SNOW_3_HOURS), String.class);
            int snowMilliMetersInt = Integer.parseInt(snowMilliMeters);
            return snowMilliMetersInt;
        } catch (NullPointerException e) {
            return -1; // if it does not snow there won't be a response object
                       // "snow"
        }
    }

    private String extractWeatherDescription(JsonObject totalJsonResponse) {
        Gson gson = new Gson();
        JsonArray weather = gson.fromJson(totalJsonResponse.get(JSONARR_WEATHER), JsonArray.class);
        JsonObject weatherObj = weather.get(0)
                .getAsJsonObject();
        String description = gson.fromJson(weatherObj.get(JSONSTR_WEATHER_DESCRIPTION), String.class);
        return description;
    }

    private int extractTemperature(JsonObject totalJsonResponse) {
        Gson gson = new Gson();
        JsonObject main = gson.fromJson(totalJsonResponse.get(JSONOB_MAIN), JsonObject.class);
        String temperature = gson.fromJson(main.get(JSONSTR_MAIN_TEMP), String.class);
        double temperatureDouble = Double.parseDouble(temperature);
        int temperatureRounded = (int) Math.round(temperatureDouble);
        return temperatureRounded;
    }

}
