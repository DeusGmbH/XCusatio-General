package com.deusgmbh.xcusatio.context.wildcard;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class RNVContext extends TimeFormattingUtils {

    private TramDetails tram;
    private TramNews newsEntry; // can be empty if line not affected by any incidents
    private TramStatus tramStatus;
    private int differenceTimeInMinutes;
    private String differenceTimeInMinutesText;

    enum TramStatus {
        OK, CANCELLED
    }

    public RNVContext(TramDetails tram, TramNews newsEntry, TramStatus tramStatus, int differenceTimeInMinutes) {
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

    public TramStatus getTramStatus() {
        return tramStatus;
    }

    public void setTramStatus(TramStatus tramStatus) {
        this.tramStatus = tramStatus;
    }

    public int getDifferenceTimeInMinutes() {
        return differenceTimeInMinutes;
    }

    public void setDifferenceTimeInMinutes(int differenceTimeInMinutes) {
        this.differenceTimeInMinutes = differenceTimeInMinutes;
    }

    public String getDifferenceTimeInMinutesText() {
        return this.differenceTimeInMinutesText;
    }

    public void setDifferenceTimeInMinutesText() {
        this.differenceTimeInMinutesText = formatMinutesAsText(this.differenceTimeInMinutes);
    }

}
