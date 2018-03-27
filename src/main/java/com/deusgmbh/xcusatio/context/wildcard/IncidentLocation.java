package com.deusgmbh.xcusatio.context.wildcard;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class IncidentLocation {
    String countryOfIncident;
    String cityOfIncident;
    String streetOfIncident;

    public IncidentLocation(String countryOfIncident, String cityOfIncident, String streetOfIncident) {
        super();
        this.countryOfIncident = countryOfIncident;
        this.cityOfIncident = cityOfIncident;
        this.streetOfIncident = streetOfIncident;
    }
}