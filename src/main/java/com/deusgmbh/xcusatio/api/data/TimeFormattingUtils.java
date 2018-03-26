package com.deusgmbh.xcusatio.api.data;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class TimeFormattingUtils {

    public String formatMinutesAsText(int minutes) {
        if (minutes == 1) {
            return "1 Minute";
        }
        return (String.valueOf(minutes) + " Minuten");
    }
    
    public String formatClocktime(String hh_colon_mm) {
        return hh_colon_mm + " Uhr";
    }
}
