package com.deusgmbh.xcusatio.context.wildcard;

/**
 * 
 * @author jan.leiblein@gmail.com
 */

public class TrafficIncidentDetails {
    TrafficIncidentType incidentType;
    String incidentStatus;
    String incidentDescription;

    public TrafficIncidentDetails(TrafficIncidentType incidentType, String incidentStatus, String incidentDescription) {
        super();
        this.incidentType = incidentType;
        this.incidentStatus = incidentStatus;
        this.incidentDescription = incidentDescription;
    }
}