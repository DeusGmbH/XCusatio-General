package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
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

    private double[][] boundingBoxCoordinates;
    private double[] spotCoordinates;
    private int[] mapTiles;

    public GeocodeAPI() {
    }

    /**
     * 
     */
    @Override
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

    @Override
    public void transmitDataToWebsite() {
        // TODO Auto-generated method stub

    }

    public double[][] extractBoundingBoxCoordinates(URL requestUrl) throws JSONException, IOException {
        double[][] boundingBoxCoordinates = new double[2][2];

        String jsonResponseString = getResponseFromWebsite(requestUrl);
        JSONObject jsonTotal = new JSONObject(jsonResponseString);
        System.out.println(jsonResponseString);

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
                                    boundingBoxCoordinates[0][0] = Double.parseDouble(jsonTopLeft.get(JSON_LATITUDE)
                                            .toString());
                                    boundingBoxCoordinates[0][1] = Double.parseDouble(jsonTopLeft.get(JSON_LONGITUDE)
                                            .toString());
                                }

                                JSONObject jsonBottomRight = jsonMapView.getJSONObject(JSON_BOTTOMRIGHT);
                                if (jsonBottomRight.has(JSON_LATITUDE) && jsonBottomRight.has(JSON_LONGITUDE)) {
                                    boundingBoxCoordinates[1][0] = Double.parseDouble(jsonBottomRight.get(JSON_LATITUDE)
                                            .toString());
                                    boundingBoxCoordinates[1][1] = Double.parseDouble(jsonTopLeft.get(JSON_LONGITUDE)
                                            .toString());
                                }
                            }
                        }
                    }
                }
            }
        }
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

    @Override
    public GeocodeData get(UserSettings usersettings) throws IOException {
        URL requestUrl = buildRequestUrl(usersettings);
        String jsonResponse = getResponseFromWebsite(requestUrl);

        this.boundingBoxCoordinates = extractBoundingBoxCoordinates(requestUrl);

        this.spotCoordinates = calculateSpotCoordinates(boundingBoxCoordinates);

        this.mapTiles = calculateTiles(spotCoordinates, 12); // 12 represents a
                                                             // medium level of
                                                             // zoom. in areas
                                                             // of high traffic
                                                             // a zoom of 16
                                                             // still yields
                                                             // good results.
                                                             // if the area has
                                                             // almost no
                                                             // traffic choose
                                                             // 8 to get a
                                                             // wider area
        return new GeocodeData(usersettings.getHome(), this.spotCoordinates, this.mapTiles);
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

        try {
            GeocodeData geocodeData = gApi.get(usersettings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gApi.printResponse();

    }

    @Override
    public void printResponse() {
        // TODO Auto-generated method stub

    }

    @Override
    public void extractDesiredInfoFromResponse() throws JSONException, ParseException {
        // TODO Auto-generated method stub

    }

}
