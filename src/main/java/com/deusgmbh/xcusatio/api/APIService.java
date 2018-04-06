package com.deusgmbh.xcusatio.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;

import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com, jan.leiblein@gmail.com
 *
 * 
 *
 */
public abstract class APIService {
    private static final Logger LOGGER = Logger.getLogger(APIService.class.getName());

    private URL requestUrl;

    public String getJsonStringFromInputStream(URL requestUrl) throws IOException {
        InputStream is = requestUrl.openStream();
        return IOUtils.toString(is, "UTF-8");
        // TODO close inputStream ?
    }

    public JsonObject getTotalJsonObject(URL requestUrl, Gson gson) throws IOException {
        String jsonResponseString = getResponseFromWebsite(requestUrl);
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonResponseString);
        JsonObject total = gson.fromJson(element.getAsJsonObject(), JsonObject.class);
        return total;
    }

    public String getResponseFromWebsite(URL requestUrl) throws IOException {
        return getJsonStringFromInputStream(requestUrl);
    };

    public URL getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(URL requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void printResponse(URL requestUrl) throws IOException {
        System.out.println(getJsonStringFromInputStream(requestUrl));

    }

    /**
     * 
     * @param usersettings
     *            contain settings entered by the user such as address, calendar
     *            link, etc.
     * @return an object of the requested context (traffic, weather, rnv,
     *         calendar)
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    public abstract <T extends Object> T get(UserSettings usersettings)
            throws IOException, JSONException, ParseException;

    /**
     * 
     * @param usersettings
     *            contain settings entered by the user such as address, calendar
     *            link, etc.
     * @return the URL which is used for the following web request
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public abstract URL buildRequestUrl(UserSettings usersettings) throws UnsupportedEncodingException, IOException;

}