package com.deusgmbh.xcusatio.context.wildcard;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class TrafficContext {

    // incident location + street
    private TrafficIncidentDetails trafficIncident;
    private TrafficIncidentLocation incidentLocation;
    private TrafficIncidentTimes incidentTimes;

    public TrafficContext(TrafficIncidentDetails trafficIncident, TrafficIncidentLocation incidentLocation,
            TrafficIncidentTimes incidentTimes) {
        super();
        this.trafficIncident = trafficIncident;
        this.incidentLocation = incidentLocation;
        this.incidentTimes = incidentTimes;
    }

    public TrafficIncidentDetails getTrafficIncident() {
        return trafficIncident;
    }

    public void setTrafficIncident(TrafficIncidentDetails trafficIncident) {
        this.trafficIncident = trafficIncident;
    }

    public TrafficIncidentLocation getIncidentLocation() {
        return incidentLocation;
    }

    public void setIncidentLocation(TrafficIncidentLocation incidentLocation) {
        this.incidentLocation = incidentLocation;
    }

    public TrafficIncidentTimes getIncidentTimes() {
        return incidentTimes;
    }

    public void setIncidentTimes(TrafficIncidentTimes incidentTimes) {
        this.incidentTimes = incidentTimes;
    }
}