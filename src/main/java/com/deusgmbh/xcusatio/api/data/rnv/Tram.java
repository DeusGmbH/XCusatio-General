package com.deusgmbh.xcusatio.api.data.rnv;

import java.util.List;
import java.util.Map;

public class Tram {

    // former TramDetails
    private String lineLabel;
    private String universityStation; /* the relevant station for the student */
    private int differenceTimeInMinutes;
    private Map<String, List<String>> stopsOfLines;
    private TramStatus tramStatus;

    public Tram(String lineLabel, String universityStation, int differenceTimeInMinutes,
            Map<String, List<String>> stopsOfLines, TramStatus tramStatus) {
        super();
        this.lineLabel = lineLabel;
        this.universityStation = universityStation;
        this.differenceTimeInMinutes = differenceTimeInMinutes;
        this.stopsOfLines = stopsOfLines;
        this.tramStatus = tramStatus;
    }

    public String getLineLabel() {
        return lineLabel;
    }

    public void setLineLabel(String lineLabel) {
        this.lineLabel = lineLabel;
    }

    public String getUniversityStation() {
        return universityStation;
    }

    public void setUniversityStation(String universityStation) {
        this.universityStation = universityStation;
    }

    public int getDifferenceTimeInMinutes() {
        return differenceTimeInMinutes;
    }

    public void setDifferenceTimeInMinutes(int differenceTimeInMinutes) {
        this.differenceTimeInMinutes = differenceTimeInMinutes;
    }

    public Map<String, List<String>> getStopsOfLines() {
        return stopsOfLines;
    }

    public void setStopsOfLines(Map<String, List<String>> stopsOfLines) {
        this.stopsOfLines = stopsOfLines;
    }

    public TramStatus getTramStatus() {
        return tramStatus;
    }

    public void setTramStatus(TramStatus tramStatus) {
        this.tramStatus = tramStatus;
    }

}
