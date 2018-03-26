package com.deusgmbh.xcusatio.api.data;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class TrafficIncidentLocation {
    String countryOfIncident;
    String cityOfIncident;
    String streetOfIncident;

    public TrafficIncidentLocation(String countryOfIncident, String cityOfIncident, String streetOfIncident) {
        super();
        this.countryOfIncident = countryOfIncident;
        this.cityOfIncident = cityOfIncident;
        this.streetOfIncident = streetOfIncident;
    }

    public String getCountryOfIncident() {
        return countryOfIncident;
    }

    public void setCountryOfIncident(String countryOfIncident) {
        this.countryOfIncident = countryOfIncident;
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