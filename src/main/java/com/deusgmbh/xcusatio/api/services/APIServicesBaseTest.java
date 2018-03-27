package com.deusgmbh.xcusatio.api.services;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class APIServicesBaseTest extends APIService {

    public static void main(String[] platinum) {
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
        apiService.setBaseUrlText("http://www.affebenz.com/content/home.html");
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
