package com.deusgmbh.xcusatio.context.wildcard;

import java.util.logging.Logger;

import com.deusgmbh.xcusatio.api.data.TrafficIncidentDetails;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentLocation;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentTimes;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class TrafficContext {

    private static final Logger LOGGER = Logger.getLogger(TrafficContext.class.getName());

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

    public void logContextContent() {
        LOGGER.info("TrafficContext:\nType of incident: " + this.getTrafficIncident().getIncidentType()
                + "\nDescription: " + this.getTrafficIncident().getIncidentDescription() + "\nStatus: "
                + this.getTrafficIncident().getIncidentStatus() + "\noccured in: "
                + this.getIncidentLocation().getCountryOfIncident() + ", "
                + this.getIncidentLocation().getCityOfIncident() + ", "
                + this.getIncidentLocation().getStreetOfIncident() + "\nStarted: "
                + this.getIncidentTimes().getStartTimeOfTrafficIncident() + "\nEnd: "
                + this.getIncidentTimes().getEndTimeOfTrafficIncident());
    }
}