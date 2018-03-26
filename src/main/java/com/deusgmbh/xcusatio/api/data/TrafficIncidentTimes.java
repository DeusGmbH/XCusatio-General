package com.deusgmbh.xcusatio.api.data;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class TrafficIncidentTimes extends TimeFormattingUtils {
    private String startTimeOfTrafficIncident;
    private String endTimeOfTrafficIncident;

    public TrafficIncidentTimes(String startTimeOfTrafficIncident, String endTimeOfTrafficIncident) {
        super();
        this.startTimeOfTrafficIncident = formatClocktime(startTimeOfTrafficIncident);
        this.endTimeOfTrafficIncident = formatClocktime(endTimeOfTrafficIncident);
    }

    public String getStartTimeOfTrafficIncident() {
        return startTimeOfTrafficIncident;
    }

    public void setStartTimeOfTrafficIncident(String startTimeOfTrafficIncident) {
        this.startTimeOfTrafficIncident = formatClocktime(startTimeOfTrafficIncident);
    }

    public String getEndTimeOfTrafficIncident() {
        return endTimeOfTrafficIncident;
    }

    public void setEndTimeOfTrafficIncident(String endTimeOfTrafficIncident) {
        this.endTimeOfTrafficIncident = formatClocktime(endTimeOfTrafficIncident);
    }

}