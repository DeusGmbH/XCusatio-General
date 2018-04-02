package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.GeocodeData;
import com.deusgmbh.xcusatio.data.usersettings.Address;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.Sex;

public class GeocodeAPI extends APIService {
    private static final Logger LOGGER = Logger.getLogger(GeocodeAPI.class.getName());

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

    private String jsonResponse;
    private double[][] boundingBoxCoordinates;
    private double[] spotCoordinates;
    private String quadkey;
    private int[] mapTilesLowZoom;
    private int[] mapTilesMediumZoom;
    private int[] mapTilesHighZoom;

    public GeocodeAPI() {
        this.boundingBoxCoordinates = new double[2][2];
        this.spotCoordinates = new double[2];
        this.quadkey = "";
        this.mapTilesLowZoom = new int[2];
        this.mapTilesMediumZoom = new int[2];
        this.mapTilesHighZoom = new int[2];
    }

    @Override
    public void buildRequestUrl(UserSettings usersettings) {
        Address home = usersettings.getHome();
        String streetnum = home.getStreetnum();
        String streetname = home.getStreetName();
        String city = home.getCity();
        String zip = home.getZip();
        try {
            super.setRequestUrl(new URL(
                    BASE_URL + URLEncoder.encode(streetnum + "+" + streetname + "+" + city + "+" + zip, "UTF-8")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void transmitDataToWebsite() {
        // TODO Auto-generated method stub

    }

    @Override
    public void extractDesiredInfoFromResponse() throws JSONException {
        // TODO refactor this blown-up shit to some really smaller chunks of
        // code using a couple of private methods etc.
        getResponseFromWebsite();
        JSONObject jsonTotal = new JSONObject(this.jsonResponse);

        if (jsonTotal.has(JSON_RESPONSE)) {
            JSONObject jsonResponse = jsonTotal.getJSONObject(JSON_RESPONSE);
            if (jsonResponse.has(JSON_VIEW)) {
                JSONArray jsonViewArray = jsonResponse.getJSONArray(JSON_VIEW);
                JSONObject jsonView = jsonViewArray.getJSONObject(0);
                if (jsonView.has(JSON_RESULT)) {
                    JSONArray jsonResultArray = jsonView.getJSONArray(JSON_RESULT);
                    JSONObject jsonResult = jsonResultArray.getJSONObject(0);
                    if (jsonResult.has(JSON_LOCATION)) {
                        JSONObject jsonLocation = jsonResult.getJSONObject(JSON_LOCATION);
                        if (jsonLocation.has(JSON_MAPVIEW)) {
                            JSONObject jsonMapView = jsonLocation.getJSONObject(JSON_MAPVIEW);
                            if (jsonMapView.has(JSON_TOPLEFT) && jsonMapView.has(JSON_BOTTOMRIGHT)) {
                                JSONObject jsonTopLeft = jsonMapView.getJSONObject(JSON_TOPLEFT);
                                if (jsonTopLeft.has(JSON_LATITUDE) && jsonTopLeft.has(JSON_LONGITUDE)) {
                                    this.boundingBoxCoordinates[0][0] = Double
                                            .parseDouble(jsonTopLeft.get(JSON_LATITUDE)
                                                    .toString());
                                    this.boundingBoxCoordinates[0][1] = Double
                                            .parseDouble(jsonTopLeft.getString(JSON_LONGITUDE)
                                                    .toString());
                                }
                                JSONObject jsonBottomRight = jsonMapView.getJSONObject(JSON_BOTTOMRIGHT);
                                if (jsonBottomRight.has(JSON_LATITUDE) && jsonBottomRight.has(JSON_LONGITUDE)) {
                                    this.boundingBoxCoordinates[1][0] = Double
                                            .parseDouble(jsonBottomRight.get(JSON_LATITUDE)
                                                    .toString());
                                    this.boundingBoxCoordinates[1][1] = Double
                                            .parseDouble(jsonTopLeft.getString(JSON_LONGITUDE)
                                                    .toString());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void printResponse() {
        try {
            getJsonStringFromInputStream();
        } catch (IOException e) {
            LOGGER.warning("Error getting jsonString from input stream: " + e.getMessage());
        }
        String jsonResponse = getResponseAsJsonString();
        System.out.println(jsonResponse);
    }

    @Override
    public void getResponseFromWebsite() {
        try {
            getJsonStringFromInputStream();
        } catch (IOException e) {
            LOGGER.warning("Error getting jsonString from input stream: " + e.getMessage());
        }
        this.jsonResponse = getResponseAsJsonString();
    }

    private void calculateSpotCoordinates() {
        this.spotCoordinates[0] = (this.boundingBoxCoordinates[0][0] + this.boundingBoxCoordinates[1][0]) / 2;
        this.spotCoordinates[1] = (this.boundingBoxCoordinates[0][1] + this.boundingBoxCoordinates[1][1]) / 2;
    }

    /* currently not in use but might be used later */
    private void calculateQuadkey(int zoomLevel) {
        Double spotLatitudeDeg = this.spotCoordinates[0];
        Double spotLongitudeDeg = this.spotCoordinates[1];
        Double spotLatitudeRad = degToRad(spotLatitudeDeg);
        Double spotLongitudeRad = degToRad(spotLongitudeDeg);

        Double n = Math.pow(2, zoomLevel);
        Double xTile = n * ((spotLongitudeDeg + 180) / 360);
        Double yTile = n * (1 - (Math.log(Math.tan(spotLatitudeRad) + 1 / (Math.cos(spotLatitudeRad)) / Math.PI)) / 2);

        int xTileInt = (int) Math.round(xTile);
        int yTileInt = (int) Math.round(yTile);

        for (int i = zoomLevel; i > 0; i--) {
            int digit = 0;
            final int mask = 1 << (i - 1);
            if ((xTileInt & mask) != 0) {
                digit++;
            }
            if ((yTileInt & mask) != 0) {
                digit = digit + 2;
            }

            this.quadkey += String.valueOf(digit);
        }

    }

    private int[] calculateTiles(double zoomLevel) {
        double latRad = this.spotCoordinates[0] * Math.PI / 180;
        double n = Math.pow(2, zoomLevel);
        int[] mapTiles = new int[2];
        mapTiles[0] = (int) Math.round(n * ((this.spotCoordinates[1] + 180) / 360));
        mapTiles[1] = (int) Math.round(n * (1 - (Math.log(Math.tan(latRad) + 1 / Math.cos(latRad)) / Math.PI)) / 2);
        return mapTiles;
    }

    private Double degToRad(Double deg) {
        return (2 * Math.PI / 360) * deg;
    }

    @Override
    public GeocodeData get(UserSettings usersettings) {
        this.buildRequestUrl(usersettings);
        try {
            this.extractDesiredInfoFromResponse();
            LOGGER.info("Extracted required information from response");
        } catch (JSONException e) {
            LOGGER.warning("JSONException during extracting desired information: " + e.getMessage());
        }
        calculateSpotCoordinates();
        this.mapTilesHighZoom = calculateTiles(8);
        this.mapTilesMediumZoom = calculateTiles(12);
        this.mapTilesLowZoom = calculateTiles(16);
        return new GeocodeData(usersettings.getHome(), this.spotCoordinates, this.mapTilesLowZoom,
                this.mapTilesMediumZoom, this.mapTilesHighZoom);
    }

    // https://traffic.cit.api.here.com/traffic/6.0/incidents.json?bbox=52.5311%2C13.3644%3B52.5114%2C13.4035&criticality=minor&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg

    /*
     * 
     * test section
     * 
     * 
     * 
     */
    public static void main(String[] osmium) {

        UserSettings usersettings = new UserSettings(null, null, Sex.MALE,
                new Address("10", "Hanauer Landstraße", "60314", "Frankfurt am Main"));

        GeocodeAPI gApi = new GeocodeAPI();

        GeocodeData geocodeData = gApi.get(usersettings);

        System.out.println("Spot: " + gApi.spotCoordinates[0] + ", " + gApi.spotCoordinates[1]);

        System.out.println("High zoom: " + (int) Math.round(gApi.mapTilesHighZoom[0]) + "/"
                + (int) Math.round(gApi.mapTilesHighZoom[1]));
        System.out.println("High zoom: " + (int) Math.round(gApi.mapTilesMediumZoom[0]) + "/"
                + (int) Math.round(gApi.mapTilesMediumZoom[1]));
        System.out.println("High zoom: " + (int) Math.round(gApi.mapTilesLowZoom[0]) + "/"
                + (int) Math.round(gApi.mapTilesLowZoom[1]));

    }

}
