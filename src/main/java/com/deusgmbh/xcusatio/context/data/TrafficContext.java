package com.deusgmbh.xcusatio.context.data;

import java.util.List;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.api.data.traffic.TrafficIncident;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class TrafficContext {

    private static final Logger LOGGER = Logger.getLogger(TrafficContext.class.getName());

    private List<TrafficIncident> trafficIncidents;

    public TrafficContext(List<TrafficIncident> trafficIncidents) {
        super();
        this.trafficIncidents = trafficIncidents;
    }

    public List<TrafficIncident> getTrafficIncidents() {
        return trafficIncidents;
    }

    public void setTrafficIncidents(List<TrafficIncident> trafficIncidents) {
        this.trafficIncidents = trafficIncidents;
    }

}