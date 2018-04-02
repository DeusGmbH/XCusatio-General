package com.deusgmbh.xcusatio.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;

import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com, 
 * jan.leiblein@gmail.com
 *
 * 
 *
 */
public abstract class APIService {
    private static final Logger LOGGER = Logger.getLogger(APIService.class.getName());

    private URL requestUrl;
    private String responseAsJsonString;

    public void getJsonStringFromInputStream() throws IOException {
        InputStream is = this.requestUrl.openStream();
        this.responseAsJsonString = IOUtils.toString(is, "UTF-8");
        is.close();
    }

    public abstract <T extends Object> T get(UserSettings usersettings);

    public abstract void buildRequestUrl(UserSettings usersettings);

    public abstract void transmitDataToWebsite();

    /* just for tests */
    public abstract void printResponse();

    /* differs in terms of placement of desired info inside XML/JSON */
    public abstract void extractDesiredInfoFromResponse() throws JSONException;

    /* differs in terms of data format returned by the call */
    public abstract void getResponseFromWebsite();

    public URL getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(URL requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getResponseAsJsonString() {
        return responseAsJsonString;
    }

    public void setResponseAsJsonString(String responseAsJsonString) {
        this.responseAsJsonString = responseAsJsonString;
    }

}