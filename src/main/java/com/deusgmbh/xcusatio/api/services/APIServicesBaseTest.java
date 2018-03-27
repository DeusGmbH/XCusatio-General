package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.net.MalformedURLException;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class APIServicesBaseTest extends APIService {

    public static void main(String[] platinum) throws MalformedURLException, IOException {
        APIService apiService = new APIService() {

            @Override
            public void transmitDataToWebsite() {
                // TODO Auto-generated method stub

            }

            @Override
            public <T> T get(UserSettings usersettings) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void extractDesiredInfoFromResponse() {
                // TODO Auto-generated method stub

            }
        };
        apiService.setBaseUrlText(
                "https://traffic.cit.api.here.com/traffic/6.2/incidents/xml/8/134/86?app_id=ObXv79Ww3xdQ996uEDLw&app_code=74fsgcSubek54INvT13Rcg");
        apiService.requestWebsite();
        apiService.getResponseFromWebsite();
    }

    @Override
    public <T> T get(UserSettings usersettings) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void transmitDataToWebsite() {
        // TODO Auto-generated method stub

    }

    @Override
    public void extractDesiredInfoFromResponse() {
        // TODO Auto-generated method stub

    }

}
