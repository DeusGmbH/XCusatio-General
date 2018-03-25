package com.deusgmbh.xcusatio.context.wildcard;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 * */

import com.deusgmbh.xcusatio.context.wildcard.TrafficContext.TrafficIncidentType;

public class IncidentDetails {
    TrafficIncidentType incidentType;
    String incidentStatus;
    String incidentDescription;

    public IncidentDetails(TrafficIncidentType incidentType, String incidentStatus, String incidentDescription) {
        super();
        this.incidentType = incidentType;
        this.incidentStatus = incidentStatus;
        this.incidentDescription = incidentDescription;
    }
}