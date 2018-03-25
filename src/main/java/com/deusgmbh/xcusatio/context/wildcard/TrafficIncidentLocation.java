package com.deusgmbh.xcusatio.context.wildcard;

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
}