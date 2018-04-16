package com.deusgmbh.xcusatio.api.data.traffic;

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

    public TrafficIncidentLocation(String streetOfIncident) {
        super();
        this.streetOfIncident = streetOfIncident;
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