package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.GeocodeData;
import com.deusgmbh.xcusatio.data.usersettings.Address;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 
 * @author jan.leiblein@gmail.com
 *
 */

public class GeocodeAPI extends APIService {
    private static final Logger LOGGER = Logger.getLogger(GeocodeAPI.class.getName());
    /**
     * 12 represents a medium level of zoom. in areas of high traffic a zoom of
     * 16 still yields good results. if the area has almost no traffic choose 8
     * to get a wider area
     */
    private static final int ZOOM_LEVEL = 12;

    private final String JSON_RESPONSE = "Response";
    private final String JSON_VIEW = "View";
    private final String JSON_RESULT = "Result";
    private final String JSON_LOCATION = "Location";
    private final String JSON_MAPVIEW = "MapView";
    private final String JSON_TOPLEFT = "TopLeft";
    private final String JSON_BOTTOMRIGHT = "BottomRight";
    private final String JSON_LATITUDE = "Latitude";
    private final String JSON_LONGITUDE = "Longitude";
    private final String BASE_URL = "https://geocoder.api.here.com/6.2/geocode.json?app_id=ObXv79Ww3xdQ996uEDLw&app_code=74fsgcSubek54INvT13Rcg&searchtext=";

    public GeocodeAPI() {
    }

    public URL buildRequestUrl(UserSettings usersettings) throws UnsupportedEncodingException {
        Address address = usersettings.getHome();
        String streetnum = address.getStreetnum();
        String streetname = address.getStreetName();
        String city = address.getCity();
        String zip = address.getZip();
        try {
            return new URL(
                    BASE_URL + URLEncoder.encode(streetnum + "+" + streetname + "+" + city + "+" + zip, "UTF-8"));
        } catch (MalformedURLException e) {
            throw new RuntimeException("error building geocode request url");
        }
    }

    public double[][] extractBoundingBoxCoordinates(URL requestUrl) throws IOException {
        double[][] boundingBoxCoordinates = new double[2][2];
        Gson gson = new Gson();
        JsonObject total = getTotalJsonObject(requestUrl, gson);
        JsonObject response = gson.fromJson(total.get(JSON_RESPONSE), JsonObject.class);
        JsonArray view = gson.fromJson(response.get(JSON_VIEW), JsonArray.class);
        JsonObject viewObject = gson.fromJson(view.get(0), JsonObject.class);
        JsonArray result = gson.fromJson(viewObject.get(JSON_RESULT), JsonArray.class);
        JsonObject resultObject = gson.fromJson(result.get(0), JsonObject.class);
        JsonObject location = gson.fromJson(resultObject.get(JSON_LOCATION), JsonObject.class);
        JsonObject mapView = gson.fromJson(location.get(JSON_MAPVIEW), JsonObject.class);
        JsonObject[] boundingBox = new JsonObject[] { gson.fromJson(mapView.get(JSON_TOPLEFT), JsonObject.class),
                gson.fromJson(mapView.get(JSON_BOTTOMRIGHT), JsonObject.class) };
        double[] topLeft = new double[] { gson.fromJson(boundingBox[1].get(JSON_LATITUDE), double.class),
                gson.fromJson(boundingBox[0].get(JSON_LONGITUDE), double.class) };
        double[] bottomRight = new double[] { gson.fromJson(boundingBox[0].get(JSON_LATITUDE), double.class),
                gson.fromJson(boundingBox[1].get(JSON_LONGITUDE), double.class) };
        boundingBoxCoordinates[0] = topLeft;
        boundingBoxCoordinates[1] = bottomRight;
        return boundingBoxCoordinates;
    }

    private double[] calculateSpotCoordinates(double[][] boundingBoxCoordinates) {
        double[] spotCoordinates = new double[2];
        spotCoordinates[0] = (boundingBoxCoordinates[0][0] + boundingBoxCoordinates[1][0]) / 2;
        spotCoordinates[1] = (boundingBoxCoordinates[0][1] + boundingBoxCoordinates[1][1]) / 2;
        return spotCoordinates;
    }

    /* currently not in use but might be used later */
    private String calculateQuadkey(double[] spotCoordinates, int zoomLevel) {
        Double spotLatitudeDeg = spotCoordinates[0];
        Double spotLongitudeDeg = spotCoordinates[1];
        Double spotLatitudeRad = degToRad(spotLatitudeDeg);
        Double n = Math.pow(2, zoomLevel);
        Double xTile = n * ((spotLongitudeDeg + 180) / 360);
        Double yTile = n * (1 - (Math.log(Math.tan(spotLatitudeRad) + 1 / (Math.cos(spotLatitudeRad)) / Math.PI)) / 2);
        int xTileInt = (int) Math.round(xTile);
        int yTileInt = (int) Math.round(yTile);
        String quadkey = "";
        for (int i = zoomLevel; i > 0; i--) {
            int digit = 0;
            final int mask = 1 << (i - 1);
            if ((xTileInt & mask) != 0) {
                digit++;
            }
            if ((yTileInt & mask) != 0) {
                digit = digit + 2;
            }

            quadkey += String.valueOf(digit);
        }
        return quadkey;

    }

    private int[] calculateTiles(double[] spotCoordinates, double zoomLevel) {
        double latRad = spotCoordinates[0] * Math.PI / 180;
        double n = Math.pow(2, zoomLevel);
        int[] mapTiles = new int[2];
        mapTiles[0] = (int) Math.round(n * ((spotCoordinates[1] + 180) / 360));
        mapTiles[1] = (int) Math.round(n * (1 - (Math.log(Math.tan(latRad) + 1 / Math.cos(latRad)) / Math.PI)) / 2);
        return mapTiles;
    }

    private Double degToRad(Double deg) {
        return (2 * Math.PI / 360) * deg;
    }

    public GeocodeData get(UserSettings usersettings) throws IOException {
        URL requestUrl = buildRequestUrl(usersettings);
        String jsonResponse = getResponseFromWebsite(requestUrl);

        double[][] boundingBoxCoordinates = extractBoundingBoxCoordinates(requestUrl);

        double[] spotCoordinates = calculateSpotCoordinates(boundingBoxCoordinates);

        int[] mapTiles = calculateTiles(spotCoordinates, ZOOM_LEVEL);
        return new GeocodeData(usersettings.getHome(), spotCoordinates, mapTiles);
    }

}
