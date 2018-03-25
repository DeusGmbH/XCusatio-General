package com.deusgmbh.xcusatio.context.wildcard;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 * */

import java.util.List;

public class TramDetails {
    String lineLabel;
    String firstEndStation;
    String secondEndstation;
    List<String> stops;

    public TramDetails(String lineNumber, String firstEndStation, String secondEndstation, List<String> stops) {
        super();
        this.lineLabel = lineNumber;
        this.firstEndStation = firstEndStation;
        this.secondEndstation = secondEndstation;
        this.stops = stops;
    }
}