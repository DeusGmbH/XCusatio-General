package com.deusgmbh.xcusatio.api.data;

import com.deusgmbh.xcusatio.data.usersettings.Address;

public class GeocodeData {

    private Address address;
    private double[] addressSpotCoordinates;
    private double[][] boundingBoxCoordinates;

    public GeocodeData(Address address, double[] addressSpotCoordinates, double[][] boundingBoxCoordinates) {
        super();
        this.address = address;
        this.addressSpotCoordinates = addressSpotCoordinates;
        this.boundingBoxCoordinates = boundingBoxCoordinates;
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

    public double[] getAddressSpotCoordinates() {
        return addressSpotCoordinates;
    }

    public void setAddressSpotCoordinates(double[] addressSpotCoordinates) {
        this.addressSpotCoordinates = addressSpotCoordinates;
    }

    public double[][] getBoundingBoxCoordinates() {
        return boundingBoxCoordinates;
    }

    public void setBoundingBoxCoordinates(double[][] boundingBoxCoordinates) {
        this.boundingBoxCoordinates = boundingBoxCoordinates;
    }

}
