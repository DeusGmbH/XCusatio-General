package com.deusgmbh.xcusatio.api.data.traffic;

public class TrafficIncident {
    private TrafficIncidentDetails trafficIncidentDetails;
    private TrafficIncidentLocation trafficIncidentLocation;
    private TrafficIncidentTimes trafficIncidentTimes;

    public TrafficIncident(TrafficIncidentDetails trafficIncidentDetails,
            TrafficIncidentLocation trafficIncidentLocation, TrafficIncidentTimes trafficIncidentTimes) {
        super();
        this.trafficIncidentDetails = trafficIncidentDetails;
        this.trafficIncidentLocation = trafficIncidentLocation;
        this.trafficIncidentTimes = trafficIncidentTimes;
    }

    public TrafficIncidentDetails getTrafficIncidentDetails() {
        return trafficIncidentDetails;
    }

    public void setTrafficIncidentDetails(TrafficIncidentDetails trafficIncidentDetails) {
        this.trafficIncidentDetails = trafficIncidentDetails;
    }

    public TrafficIncidentLocation getTrafficIncidentLocation() {
        return trafficIncidentLocation;
    }

    public void setTrafficIncidentLocation(TrafficIncidentLocation trafficIncidentLocation) {
        this.trafficIncidentLocation = trafficIncidentLocation;
    }

    public TrafficIncidentTimes getTrafficIncidentTimes() {
        return trafficIncidentTimes;
    }

    public void setTrafficIncidentTimes(TrafficIncidentTimes trafficIncidentTimes) {
        this.trafficIncidentTimes = trafficIncidentTimes;
    }

}
