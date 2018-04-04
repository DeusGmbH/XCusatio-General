package com.deusgmbh.xcusatio.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

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
    // private String responseAsJsonString;

    public String getJsonStringFromInputStream(URL requestUrl) throws IOException {
        InputStream is = requestUrl.openStream();
        return IOUtils.toString(is, "UTF-8");
        // TODO close inputStream ?
    }

    public abstract <T extends Object> T get(UserSettings usersettings)
            throws IOException, JSONException, ParseException;

    public abstract URL buildRequestUrl(UserSettings usersettings) throws UnsupportedEncodingException, IOException;

    public abstract void transmitDataToWebsite();

    /* just for tests */
    public abstract void printResponse();

    /* differs in terms of placement of desired info inside XML/JSON */
    public abstract void extractDesiredInfoFromResponse() throws JSONException, ParseException;

    /* differs in terms of data format returned by the call */
    public String getResponseFromWebsite(URL requestUrl) throws IOException {
        return getJsonStringFromInputStream(requestUrl);
    };

    /**
     * 
     * @param into
     *            provides the list to move into in order to get the target list
     *            of json objects out of it
     * @param KEY
     *            represents the key referring to this target list of json
     *            objects
     * @return the target list
     * @throws JSONException
     */
    protected List<JSONObject> goInside(List<JSONObject> into, String KEY) throws JSONException {
        List<JSONObject> jL = new LinkedList<>();
        for (JSONObject jO : into) {
            if (jO.has(KEY)) {
                jL.add(jO.getJSONObject(KEY));
                // System.out.println(KEY + ": " + jO.toString());
            }
        }
        return jL;
    }

    /**
     * 
     * @param jO
     *            provides the json object that should contain a json array
     * @param KEY
     *            is the key referring to this json array
     * @return a list of all json objects contained in the json array
     * @throws JSONException
     */
    protected List<JSONObject> getJSONObjectsFromJSONArray(JSONObject jO, String KEY) throws JSONException {
        if (jO.has(KEY)) {
            JSONArray jA = jO.getJSONArray(KEY);

            List<JSONObject> jL = new LinkedList<>();
            for (int i = 0; i < jA.length(); ++i) {
                jL.add(jA.getJSONObject(i));
                // System.out.println(KEY + jA.get(i));
            }
            return jL;
        }
        LOGGER.warning(KEY + " not contained in JSONObject " + jO.toString());
        return null;
    }

    /**
     * 
     * @param jL
     *            list of json objects to retrieve string values from
     * @param KEY
     *            of the desired string value
     * @return list of desired string values
     * @throws JSONException
     */
    protected List<String> getValuesFromJSONObjects(List<JSONObject> jL, String KEY) throws JSONException {
        List<String> sL = new LinkedList<>();
        for (JSONObject jO : jL) {
            if (jO.has(KEY)) {
                sL.add(jO.getString(KEY));
            } else {
                LOGGER.warning(KEY + " not contained in " + jO);
                return null;
            }
        }
        return sL;
    }

    /**
     * 
     * @param outerJL
     *            is the list of json objects that contains a json array
     * @param KEY
     *            referring to the desired value
     * @return list of desired string-values with KEY
     * @throws JSONException
     */
    protected List<String> getValuesFromNestedJSONArray(List<JSONObject> outerJL, String KEY, String arrayKEY,
            String strKEY) throws JSONException {
        List<JSONObject> outerTmp = goInside(outerJL, KEY);
        List<JSONObject> innerTmp = new LinkedList<>();
        for (int i = 0; i < outerTmp.size(); ++i) {
            innerTmp.addAll(getJSONObjectsFromJSONArray(outerTmp.get(i), arrayKEY));
        }
        List<String> result = getValuesFromJSONObjects(innerTmp, strKEY);
        // testing purposes
        // result.forEach(s -> System.out.println("Found: " + s));
        return result;
    }

    /**
     * 
     * @param jA
     *            json array to get json objects from
     * @return list of obtained json objects
     * @throws JSONException
     */
    protected List<JSONObject> getJSONObjectsFromJSONArray(JSONArray jA) throws JSONException {
        List<JSONObject> jL = new LinkedList<>();
        for (int i = 0; i < jA.length(); ++i) {
            jL.add(jA.getJSONObject(i));
        }
        return jL;
    }

    /**
     * 
     * @param jL
     *            list with json objects to retrieve json arrays from
     * @param KEY
     *            reffering to the target json array
     * @return list of these json arrays
     */
    protected List<JSONArray> getJSONArraysFromJSONObjects(List<JSONObject> jL, String KEY) {
        List<JSONArray> jA = new LinkedList<>();
        jL.forEach(jO -> {
            if (jO.has(KEY)) {
                try {
                    jA.add(jO.getJSONArray(KEY));
                } catch (JSONException e) {
                    LOGGER.warning("JSONException while trying to obtain json array from a json object: "
                            + e.getLocalizedMessage());
                }
            }
        });
        return jA;
    }

    /**
     * 
     * @param jAL
     *            list of json arrays
     * @return list of lists containing strings
     * @throws JSONException
     */
    protected List<List<String>> getValuesFromJSONArrayList(List<JSONArray> jAL) throws JSONException {
        List<List<String>> valsL = new LinkedList<>();
        List<String> vals = new LinkedList<>();
        for (int i = 0; i < jAL.size(); ++i) {
            for (int j = 0; j < jAL.get(i)
                    .length(); ++j) {
                vals.add(jAL.get(i)
                        .getString(j));
            }
            valsL.add(vals);
        }
        return valsL;
    }

    public URL getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(URL requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void printResponse(URL requestUrl) throws IOException {
        System.out.println(getJsonStringFromInputStream(requestUrl));

    }

}