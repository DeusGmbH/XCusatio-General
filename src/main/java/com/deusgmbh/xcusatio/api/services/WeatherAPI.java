package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class WeatherAPI extends APIService {

    private static final String API_KEY = "6d2f9cc11f7325e34185f30bf4968e47";
    private static final String CITY_NAME = "Mannheim";
    private static final String COUNTRY_CODE = "DE";

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

    @Override
    public <T> T get(UserSettings usersettings) throws IOException, JSONException, ParseException {
        // TODO Auto-generated method stub
        return null;
    }

}
