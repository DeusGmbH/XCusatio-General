package com.deusgmbh.xcusatio.api.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class TrafficIncidentTimes extends TimeFormattingUtils {
    private Date endTimeOfTrafficIncident;

    public TrafficIncidentTimes(Date startTimeOfTrafficIncident, Date endTimeOfTrafficIncident) {
        super();
        // TODO keep in mind that String of API call has to be converted to Date
        // somewhere in the TrafficAPI class
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        this.endTimeOfTrafficIncident = endTimeOfTrafficIncident;
    }

    public Date getEndTimeOfTrafficIncident() {
        return endTimeOfTrafficIncident;
    }

    public void setEndTimeOfTrafficIncident(Date endTimeOfTrafficIncident) {
        this.endTimeOfTrafficIncident = endTimeOfTrafficIncident;
    }

}