package com.deusgmbh.xcusatio.context.wildcard;

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
}
