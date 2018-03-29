package com.deusgmbh.xcusatio.api.services;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.context.wildcard.WeatherContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class WeatherAPI extends APIService {

    @Override
    public WeatherContext get(UserSettings usersettings) {
        // TODO: implement API call and result processing
        WeatherContext weatherContext = new WeatherContext(11, "Sturmböen", "SE", 15, 8, 34, 0, 2);
        return weatherContext;
    }

    @Override
    public void transmitDataToWebsite() {
        // TODO Auto-generated method stub

    }

    @Override
    public void extractDesiredInfoFromResponse() {
        // TODO Auto-generated method stub

    }

    // public static void main(String[] plutonium) {
    // UserSettings userSettings = new UserSettings(null, 0, null, null, null,
    // null, null);
    // WeatherAPI weatherAPI = new WeatherAPI();
    // WeatherContext weatherContext = weatherAPI.get(userSettings);
    // weatherContext.logContextContent();
    // }

}