package com.deusgmbh.xcusatio.api.data;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class TrafficIncidentLocation {

    private GeocodeData geocodeDataOfIncident;
    private String countryOfIncident;
    private String cityOfIncident;
    private String streetOfIncident;

    public TrafficIncidentLocation(GeocodeData geocodeDataOfIncident) {
        super();
        this.countryOfIncident = geocodeDataOfIncident.getAddress().getCountry();
        this.cityOfIncident = geocodeDataOfIncident.getAddress().getCity();
        this.streetOfIncident = geocodeDataOfIncident.getAddress().getStreetname();
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

    public GeocodeData getGeocodeDataOfIncident() {
        return geocodeDataOfIncident;
    }

    public void setGeocodeDataOfIncident(GeocodeData geocodeDataOfIncident) {
        this.geocodeDataOfIncident = geocodeDataOfIncident;
    }

}