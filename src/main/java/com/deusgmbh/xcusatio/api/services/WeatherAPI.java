package com.deusgmbh.xcusatio.api.services;

import java.util.logging.Logger;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.context.wildcard.WeatherContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class WeatherAPI extends APIService {
    private static final Logger LOGGER = Logger.getLogger(WeatherAPI.class.getName());

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

    @Override
    public void printResponse() {
        // TODO Auto-generated method stub

    }

    @Override
    public void getResponseFromWebsite() {
        // TODO Auto-generated method stub

    }

    @Override
    public void buildRequestUrl(UserSettings usersettings) {
        // TODO Auto-generated method stub

    }

}