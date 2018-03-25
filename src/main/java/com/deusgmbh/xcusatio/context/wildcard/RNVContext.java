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
}
