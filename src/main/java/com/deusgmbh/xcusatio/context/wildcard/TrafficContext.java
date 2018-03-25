package com.deusgmbh.xcusatio.context.wildcard;

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

    class IncidentDetails {
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

    class IncidentLocation {
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

    class IncidentTimes {
        private String startTimeOfTrafficIncident;
        private String endTimeOfTrafficIncident;

        public IncidentTimes(String startTimeOfTrafficIncident, String endTimeOfTrafficIncident) {
            super();
            this.startTimeOfTrafficIncident = startTimeOfTrafficIncident;
            this.endTimeOfTrafficIncident = endTimeOfTrafficIncident;
        }
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