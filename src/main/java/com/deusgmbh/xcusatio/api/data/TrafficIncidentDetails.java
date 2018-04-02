package com.deusgmbh.xcusatio.api.data;

/**
 * 
 * @author jan.leiblein@gmail.com
 */

public class TrafficIncidentDetails {
    TrafficIncidentType incidentType;
    TrafficIncidentStatus incidentStatus;

    public TrafficIncidentDetails(String incidentType, String incidentStatus) {
        super();
        this.incidentType = TrafficIncidentType.valueOf(incidentType.replace(' ', '_'));
        this.incidentStatus = TrafficIncidentStatus.valueOf(incidentStatus);
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

}