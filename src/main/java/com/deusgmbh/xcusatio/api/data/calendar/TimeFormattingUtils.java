package com.deusgmbh.xcusatio.api.data.calendar;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class TimeFormattingUtils {

    public String formatMinutesAsText(long minutesLeft) {
        if (minutesLeft == 1) {
            return "1 Minute";
        }
        return (String.valueOf(minutesLeft) + " Minuten");
    }

    public String formatClocktime(String hh_colon_mm) {
        return hh_colon_mm + " Uhr";
    }
}
