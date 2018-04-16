package com.deusgmbh.xcusatio.api.data.traffic;

/**
 * 
 * @author jan.leiblein@gmail.com
 */

public class TrafficIncidentDetails {
    private TrafficIncidentType trafficIncidentType;
    private TrafficIncidentStatus trafficIncidentStatus;

    public TrafficIncidentDetails(String trafficIncidentType, String trafficIncidentStatus) {
        super();
        this.trafficIncidentType = TrafficIncidentType.valueOf(trafficIncidentType.replace(' ', '_'));
        this.trafficIncidentStatus = TrafficIncidentStatus.valueOf(trafficIncidentStatus);
    }

    public TrafficIncidentType getTrafficIncidentType() {
        return trafficIncidentType;
    }

    public void setTrafficIncidentType(TrafficIncidentType trafficIncidentType) {
        this.trafficIncidentType = trafficIncidentType;
    }

    public TrafficIncidentStatus getTrafficIncidentStatus() {
        return trafficIncidentStatus;
    }

    public void setTrafficIncidentStatus(TrafficIncidentStatus trafficIncidentStatus) {
        this.trafficIncidentStatus = trafficIncidentStatus;
    }

}