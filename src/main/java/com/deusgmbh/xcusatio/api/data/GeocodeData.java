package com.deusgmbh.xcusatio.api.data;

import com.deusgmbh.xcusatio.data.usersettings.Address;

/**
 * 
 * @author jan.leiblein@gmail.com
 *
 */

public class GeocodeData {

    private Address address;
    private double[] spotCoordinates;
    private double[][] boundingBoxCoordinates;
    private int[] mapTiles;
    private String quadkey;

    public GeocodeData(Address address, double[] spotCoordinates, int[] mapTiles) {
        super();
        this.address = address;
        this.spotCoordinates = spotCoordinates;
        this.mapTiles = mapTiles;
    }

    public GeocodeData(Address address) {
        super();
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double[] getSpotCoordinates() {
        return spotCoordinates;
    }

    public void setSpotCoordinates(double[] spotCoordinates) {
        this.spotCoordinates = spotCoordinates;
    }

    public double[][] getBoundingBoxCoordinates() {
        return boundingBoxCoordinates;
    }

    public void setBoundingBoxCoordinates(double[][] boundingBoxCoordinates) {
        this.boundingBoxCoordinates = boundingBoxCoordinates;
    }

    public int[] getMapTiles() {
        return mapTiles;
    }

    public void setMapTiles(int[] mapTiles) {
        this.mapTiles = mapTiles;
    }

    public String getQuadkey() {
        return quadkey;
    }

    public void setQuadkey(String quadkey) {
        this.quadkey = quadkey;
    }

}
