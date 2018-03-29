package com.deusgmbh.xcusatio.api.data;

/**
 * 
 * @author jan.leiblein@gmail.com
 */

public class TrafficIncidentDetails {
    TrafficIncidentType incidentType;
    TrafficIncidentStatus incidentStatus;
    String incidentDescription;

    public TrafficIncidentDetails(TrafficIncidentType incidentType, TrafficIncidentStatus incidentStatus,
            String incidentDescription) {
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

    public TrafficIncidentStatus getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(TrafficIncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

}