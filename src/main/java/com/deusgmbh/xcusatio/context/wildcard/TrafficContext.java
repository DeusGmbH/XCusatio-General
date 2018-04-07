package com.deusgmbh.xcusatio.context.wildcard;

import java.util.List;
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

    private List<TrafficIncidentDetails> incidentDetails;
    private List<TrafficIncidentLocation> incidentLocation;
    private List<TrafficIncidentTimes> incidentTimes;

    public TrafficContext(List<TrafficIncidentDetails> incidentDetails, List<TrafficIncidentLocation> incidentLocations,
            List<TrafficIncidentTimes> incidentTimes) {
        super();
        this.incidentDetails = incidentDetails;
        this.incidentLocation = incidentLocations;
        this.incidentTimes = incidentTimes;
    }

    public List<TrafficIncidentDetails> getTrafficIncident() {
        return incidentDetails;
    }

    public void setTrafficIncident(List<TrafficIncidentDetails> trafficIncident) {
        this.incidentDetails = trafficIncident;
    }

    public List<TrafficIncidentLocation> getIncidentLocation() {
        return incidentLocation;
    }

    public void setIncidentLocation(List<TrafficIncidentLocation> incidentLocation) {
        this.incidentLocation = incidentLocation;
    }

    public List<TrafficIncidentTimes> getIncidentTimes() {
        return incidentTimes;
    }

    public void setIncidentTimes(List<TrafficIncidentTimes> incidentTimes) {
        this.incidentTimes = incidentTimes;
    }

}