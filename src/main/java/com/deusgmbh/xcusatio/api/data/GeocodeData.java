package com.deusgmbh.xcusatio.api.data;

import com.deusgmbh.xcusatio.data.usersettings.Address;

public class GeocodeData {

    private Address address;
    private double[] coordinates;

    public GeocodeData(Address address, double[] coordinates) {
        super();
        this.address = address;
        this.coordinates = coordinates;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

}
