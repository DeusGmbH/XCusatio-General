package com.deusgmbh.xcusatio.context.wildcard;

import java.util.List;

public class RNVContext {

    private TramDetails tram;
    private TramNews newsEntry; // can be empty if line not affected by any incidents
    private String tramStatus;
    private String differenceTimeInMinutes;

    class TramDetails {
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

    class TramNews {
        String title;
        String content;
        List<String> affectedLines;

        public TramNews(String title, String content, List<String> affectedLines) {
            super();
            this.title = title;
            this.content = content;
            this.affectedLines = affectedLines;
        }
    }

    public RNVContext(TramDetails tram, TramNews newsEntry, String tramStatus, String differenceTimeInMinutes) {
        super();
        this.tram = tram;
        this.newsEntry = newsEntry;
        this.tramStatus = tramStatus;
        this.differenceTimeInMinutes = differenceTimeInMinutes;
    }

    public TramDetails getTram() {
        return tram;
    }

    public void setTram(TramDetails tram) {
        this.tram = tram;
    }

    public TramNews getNewsEntry() {
        return newsEntry;
    }

    public void setNewsEntry(TramNews newsEntry) {
        this.newsEntry = newsEntry;
    }

    public String getTramStatus() {
        return tramStatus;
    }

    public void setTramStatus(String tramStatus) {
        this.tramStatus = tramStatus;
    }

    public String getDifferenceTimeInMinutes() {
        return differenceTimeInMinutes;
    }

    public void setDifferenceTimeInMinutes(String differenceTimeInMinutes) {
        this.differenceTimeInMinutes = differenceTimeInMinutes;
    }
}
