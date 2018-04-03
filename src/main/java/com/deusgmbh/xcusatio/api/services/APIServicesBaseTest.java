package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class APIServicesBaseTest extends APIService {

    public static void main(String[] platinum)
            throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
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

            @Override
            public void buildRequestUrl(UserSettings usersettings) {
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
        };

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

    @Override
    public void buildRequestUrl(UserSettings usersettings) {
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

}
