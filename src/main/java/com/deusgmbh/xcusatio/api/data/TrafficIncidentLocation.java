package com.deusgmbh.xcusatio.api.data;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class TrafficIncidentLocation {

    private String cityOfIncident;
    private String streetOfIncident;

    public TrafficIncidentLocation(String city, String street) {
        super();
        this.cityOfIncident = city;
        this.streetOfIncident = street;
    }

    public String getCityOfIncident() {
        return cityOfIncident;
    }

    public void setCityOfIncident(String cityOfIncident) {
        this.cityOfIncident = cityOfIncident;
    }

    public String getStreetOfIncident() {
        return streetOfIncident;
    }

    public void setStreetOfIncident(String streetOfIncident) {
        this.streetOfIncident = streetOfIncident;
    }

}