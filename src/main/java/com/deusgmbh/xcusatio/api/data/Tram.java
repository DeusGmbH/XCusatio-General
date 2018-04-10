package com.deusgmbh.xcusatio.api.data;

import java.time.LocalDate;
import java.util.List;

public class Tram {

    // former TramDetails
    private String lineLabel;
    private String homeStation; /* the relevant station for the student */
    private List<Integer> differenceTimesInMinutes;
    private List<String> stops;

    // former TramNews
    private boolean hasNewsEntry;
    private LocalDate[] timestampsOfNews;
    private String titleOfNews;
    private String contentOfNews;
    private List<String> affectedLines; // empty if lineLabel not sublist

    private List<TramStatus> tramStatus;

    public Tram(String lineLabel, String homeStation, List<Integer> differenceTimesInMinutes, List<String> stops,
            boolean hasNewsEntry, LocalDate[] timestampsOfNews, String titleOfNews, String contentOfNews,
            List<String> affectedLines, List<TramStatus> tramStatus) {
        super();
        this.lineLabel = lineLabel;
        this.homeStation = homeStation;
        this.differenceTimesInMinutes = differenceTimesInMinutes;
        this.stops = stops;
        this.hasNewsEntry = hasNewsEntry;
        this.timestampsOfNews = timestampsOfNews;
        this.titleOfNews = titleOfNews;
        this.contentOfNews = contentOfNews;
        this.affectedLines = affectedLines;
        this.tramStatus = tramStatus;
    }

    public String getLineLabel() {
        return lineLabel;
    }

    public void setLineLabel(String lineLabel) {
        this.lineLabel = lineLabel;
    }

    public String getHomeStation() {
        return homeStation;
    }

    public void setHomeStation(String homeStation) {
        this.homeStation = homeStation;
    }

    public List<Integer> getDifferenceTimesInMinutes() {
        return differenceTimesInMinutes;
    }

    public void setDifferenceTimesInMinutes(List<Integer> differenceTimesInMinutes) {
        this.differenceTimesInMinutes = differenceTimesInMinutes;
    }

    public boolean isHasNewsEntry() {
        return hasNewsEntry;
    }

    public void setHasNewsEntry(boolean hasNewsEntry) {
        this.hasNewsEntry = hasNewsEntry;
    }

    public LocalDate[] getTimestampsOfNews() {
        return timestampsOfNews;
    }

    public void setTimestampsOfNews(LocalDate[] timestampsOfNews) {
        this.timestampsOfNews = timestampsOfNews;
    }

    public String getTitleOfNews() {
        return titleOfNews;
    }

    public void setTitleOfNews(String titleOfNews) {
        this.titleOfNews = titleOfNews;
    }

    public String getContentOfNews() {
        return contentOfNews;
    }

    public void setContentOfNews(String contentOfNews) {
        this.contentOfNews = contentOfNews;
    }

    public List<String> getAffectedLines() {
        return affectedLines;
    }

    public void setAffectedLines(List<String> affectedLines) {
        this.affectedLines = affectedLines;
    }

    public List<TramStatus> getTramStatus() {
        return tramStatus;
    }

    public void setTramStatus(List<TramStatus> tramStatus) {
        this.tramStatus = tramStatus;
    }

    public List<String> getStops() {
        return stops;
    }

    public void setStops(List<String> stops) {
        this.stops = stops;
    }

}
