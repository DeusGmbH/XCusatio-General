package com.deusgmbh.xcusatio.api.data;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 * */

import java.util.List;

public class TramDetails {
    private String lineLabel;
    private String homeStation; /* the relevant station for the student */
    private int differenceTimeInMinutes; /*
                                          * in relation to normal time on
                                          * homeStation
                                          */
    private List<String> stops;

    public TramDetails(String lineNumber, String homeStation, List<String> stops) {
        super();
        this.lineLabel = lineNumber;
        this.homeStation = homeStation;
        this.stops = stops;
    }

    public String getLineLabel() {
        return lineLabel;
    }

    public void setLineLabel(String lineLabel) {
        this.lineLabel = lineLabel;
    }

    public List<String> getStops() {
        return stops;
    }

    public void setStops(List<String> stops) {
        this.stops = stops;
    }

    public String getHomeStation() {
        return homeStation;
    }

    public void setHomeStation(String homeStation) {
        this.homeStation = homeStation;
    }

    public int getDifferenceTimeInMinutes() {
        return differenceTimeInMinutes;
    }

    public void setDifferenceTimeInMinutes(int differenceTimeInMinutes) {
        this.differenceTimeInMinutes = differenceTimeInMinutes;
    }

}