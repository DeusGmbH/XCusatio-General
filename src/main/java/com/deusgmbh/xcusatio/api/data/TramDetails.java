package com.deusgmbh.xcusatio.api.data;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 * */

import java.util.List;

public class TramDetails {
    private String lineLabel;
    private String firstEndStation;
    private String secondEndstation;
    private List<String> stops;

    public TramDetails(String lineNumber, String firstEndStation, String secondEndstation, List<String> stops) {
        super();
        this.lineLabel = lineNumber;
        this.firstEndStation = firstEndStation;
        this.secondEndstation = secondEndstation;
        this.stops = stops;
    }

    public String getLineLabel() {
        return lineLabel;
    }

    public void setLineLabel(String lineLabel) {
        this.lineLabel = lineLabel;
    }

    public String getFirstEndStation() {
        return firstEndStation;
    }

    public void setFirstEndStation(String firstEndStation) {
        this.firstEndStation = firstEndStation;
    }

    public String getSecondEndstation() {
        return secondEndstation;
    }

    public void setSecondEndstation(String secondEndstation) {
        this.secondEndstation = secondEndstation;
    }

    public List<String> getStops() {
        return stops;
    }

    public void setStops(List<String> stops) {
        this.stops = stops;
    }

}