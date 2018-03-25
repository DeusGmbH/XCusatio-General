package com.deusgmbh.xcusatio.context.wildcard;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class TrafficContext {

    // incident location + street
    private IncidentDetails trafficIncident;
    private IncidentLocation incidentLocation;
    private IncidentTimes incidentTimes;

    public TrafficContext(IncidentDetails trafficIncident, IncidentLocation incidentLocation,
            IncidentTimes incidentTimes) {
        super();
        this.trafficIncident = trafficIncident;
        this.incidentLocation = incidentLocation;
        this.incidentTimes = incidentTimes;
    }

    enum TrafficIncidentType {
        ACCIDENT, CONGESTION, DISABLED_VEHICLE, ROAD_HAZARD, CONSTRUCTION, PLANNED_EVENT, MASS_TRANSIT, OTHER_NEWS, WEATHER, MISC, ROAD_CLOSURE, LANE_RESTRICTION
    }

    public IncidentDetails getTrafficIncident() {
        return trafficIncident;
    }

    public void setTrafficIncident(IncidentDetails trafficIncident) {
        this.trafficIncident = trafficIncident;
    }

    public IncidentLocation getIncidentLocation() {
        return incidentLocation;
    }

    public void setIncidentLocation(IncidentLocation incidentLocation) {
        this.incidentLocation = incidentLocation;
    }

    public IncidentTimes getIncidentTimes() {
        return incidentTimes;
    }

    public void setIncidentTimes(IncidentTimes incidentTimes) {
        this.incidentTimes = incidentTimes;
    }
}