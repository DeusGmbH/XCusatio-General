package com.deusgmbh.xcusatio.context.wildcard;

import java.util.logging.Logger;

import com.deusgmbh.xcusatio.api.data.TimeFormattingUtils;
import com.deusgmbh.xcusatio.api.data.TramDetails;
import com.deusgmbh.xcusatio.api.data.TramNews;
import com.deusgmbh.xcusatio.api.data.TramStatus;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class RNVContext extends TimeFormattingUtils {

    private static final Logger LOGGER = Logger.getLogger(RNVContext.class.getName());

    private TramDetails tram;
    private TramNews newsEntry; // can be empty if line not affected by any
                                // incidents
    private TramStatus tramStatus;

    public RNVContext(TramDetails tram, TramNews newsEntry, TramStatus tramStatus, int differenceTimeInMinutes) {
        super();
        this.tram = tram;
        this.newsEntry = newsEntry;
        this.tramStatus = tramStatus;
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

    public void logContextContent() {
        LOGGER.info("RNVContext:\nTram No. " + this.tram.getLineLabel() + "\nFrom: " + "\n(" + this.tram.getStops()
                .size() + " stops)\n" + this.newsEntry.getTitle() + ": " + this.newsEntry.getContent() + ", affecting "
                + this.newsEntry.getAffectedLines()
                        .size()
                + "lines.\nStatus of tram: " + this.tramStatus + "\nTram delay: ");
    }

}
