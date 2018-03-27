package com.deusgmbh.xcusatio.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public abstract class APIService {

    private static final Logger LOGGER = Logger.getLogger(APIService.class.getName());

    private String baseUrlText;
    private URL baseUrl;
    private HttpURLConnection connection;
    private String responseAsText;

    public abstract <T extends Object> T get(UserSettings usersettings);

    public void requestWebsite() throws IOException {
        try {
            this.baseUrl = new URL(this.baseUrlText);
        } catch (MalformedURLException e) {
            LOGGER.warning("Malformed URL, got " + this.baseUrlText + " as input string.");
        }
        this.connection = (HttpURLConnection) this.baseUrl.openConnection();
    }

    /*
     * must be abstract because special implementations are necessary: e.g. rnv
     * api needs transmitted header data and the others don't
     */
    public abstract void transmitDataToWebsite();

    public void getResponseFromWebsite() throws IOException {
        if (statusCodeOkay()) {
            readResponseStream();
            connection.disconnect();
            LOGGER.info("Closed connection.");
        } else {
            LOGGER.warning("Bad http status: " + connection.getResponseCode());
        }
    }

    public abstract void extractDesiredInfoFromResponse();

    private boolean statusCodeOkay() throws IOException {
        return (this.connection.getResponseCode() == HttpURLConnection.HTTP_OK);
    }

    private void readResponseStream() throws IOException {
        InputStream inputStream = new BufferedInputStream(this.connection.getInputStream());
        writeToString(inputStream);
    }

    private void writeToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        this.responseAsText = stringBuilder.toString();
        System.out.println(this.responseAsText);
    }

    public void setBaseUrlText(String urlText) {
        this.baseUrlText = urlText;
    }

    public String getResponseAsText() {
        return this.responseAsText;
    }

}