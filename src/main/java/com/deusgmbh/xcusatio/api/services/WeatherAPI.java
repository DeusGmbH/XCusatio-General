package com.deusgmbh.xcusatio.api.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import java.io.FileReader;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;

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

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String readJsonFromUrl(String url) throws MalformedURLException, IOException {
        InputStream fis = new URL(url).openStream();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(fis, encoding))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            return sb.toString();
        }
        return null;
        
        URL url = new URL("https://graph.facebook.com/search?q=java&type=post");
        try (InputStream is = url.openStream();
            JsonReader rdr = Json.createReader(is)) {
                JsonObject obj = rdr.readObject();
                JsonArray results = obj.getJsonArray("data");
                for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                    System.out.print(result.getJsonObject("from").getString("name"));
                    System.out.print(": ");
                    System.out.println(result.getString("message", ""));
                    System.out.println("-----------");
                }
            }

    }

    // public static void main(String[] plutonium) {
    // UserSettings userSettings = new UserSettings(null, 0, null, null, null,
    // null, null);
    // WeatherAPI weatherAPI = new WeatherAPI();
    // WeatherContext weatherContext = weatherAPI.get(userSettings);
    // weatherContext.logContextContent();
    // }

}