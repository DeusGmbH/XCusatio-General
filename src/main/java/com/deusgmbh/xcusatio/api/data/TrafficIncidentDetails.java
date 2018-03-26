package com.deusgmbh.xcusatio.api.data;

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

    public TrafficIncidentType getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(TrafficIncidentType incidentType) {
        this.incidentType = incidentType;
    }

    public String getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(String incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

}