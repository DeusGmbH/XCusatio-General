package com.deusgmbh.xcusatio.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public abstract class APIService {

    private String baseUrlText;
    private URL baseUrl;
    private HttpURLConnection connection;
    private String responseAsText;

    public abstract <T extends Object> T get(UserSettings usersettings);

    public void requestWebsite() {
        buildURLFromString();
        openHttpConnection();
    }

    /*
     * must be abstract because special implementations are necessary: e.g. rnv
     * api needs transmitted header data and the others don't
     */
    public abstract void transmitDataToWebsite();

    public void getResponseFromWebsite() {
        if (statusCodeOkay()) {
            readResponseStream();
            connection.disconnect();
        } else {
            System.out.println("StatusCode not OK ");
        }
    }

    public abstract void extractDesiredInfoFromResponse();

    /* exception handling */
    private void buildURLFromString() {
        try {
            this.baseUrl = new URL(this.baseUrlText);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("URL was: " + baseUrl.toString() + "\n" + e.getMessage());
        }
    }

    private void openHttpConnection() {
        try {
            this.connection = (HttpURLConnection) this.baseUrl.openConnection();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean statusCodeOkay() {
        try {
            System.out.println(this.connection.getResponseCode());
            return (this.connection.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    private void readResponseStream() {
        try {
            InputStream inputStream = new BufferedInputStream(this.connection.getInputStream());
            printStream(inputStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void printStream(InputStream inputStream) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
            in.lines().forEach(System.out::println);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setBaseUrlText(String urlText) {
        this.baseUrlText = urlText;
    }

    public String getResponseAsText() {
        return this.responseAsText;
    }

}