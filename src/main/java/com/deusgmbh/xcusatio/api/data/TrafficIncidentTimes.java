package com.deusgmbh.xcusatio.api.data;

import java.util.Date;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class TrafficIncidentTimes extends TimeFormattingUtils {
    private Date startTimeOfTrafficIncident;
    private Date endTimeOfTrafficIncident;

    public TrafficIncidentTimes(Date startTimeOfTrafficIncident, Date endTimeOfTrafficIncident) {
        super();
        // TODO keep in mind that String of API call has to be converted to Date
        // somewhere in the TrafficAPI class
        this.startTimeOfTrafficIncident = startTimeOfTrafficIncident;
        this.endTimeOfTrafficIncident = endTimeOfTrafficIncident;
    }

    public Date getStartTimeOfTrafficIncident() {
        return startTimeOfTrafficIncident;
    }

    public void setStartTimeOfTrafficIncident(Date startTimeOfTrafficIncident) {
        this.startTimeOfTrafficIncident = startTimeOfTrafficIncident;
    }

    public Date getEndTimeOfTrafficIncident() {
        return endTimeOfTrafficIncident;
    }

    public void setEndTimeOfTrafficIncident(Date endTimeOfTrafficIncident) {
        this.endTimeOfTrafficIncident = endTimeOfTrafficIncident;
    }

}