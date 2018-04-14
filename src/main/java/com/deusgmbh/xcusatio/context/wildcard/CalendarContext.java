package com.deusgmbh.xcusatio.context.wildcard;

import java.util.logging.Logger;

import com.deusgmbh.xcusatio.api.data.LectureEvent;
import com.deusgmbh.xcusatio.api.data.TimeFormattingUtils;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */
public class CalendarContext extends TimeFormattingUtils {
    private static final Logger LOGGER = Logger.getLogger(CalendarContext.class.getName());

    private LectureEvent lectureEvent;
    private long minutesLeft;
    private long minutesPassed;

    public CalendarContext(LectureEvent lectureEvent, long minutesLeft, long minutesPassed) {
        super();
        this.lectureEvent = lectureEvent;
        this.minutesLeft = minutesLeft;
        this.minutesPassed = minutesPassed;
    }

    public LectureEvent getLectureEvent() {
        return lectureEvent;
    }

    public void setLectureEvent(LectureEvent lectureEvent) {
        this.lectureEvent = lectureEvent;
    }

    public long getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(long minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    public long getMinutesPassed() {
        return minutesPassed;
    }

    public void setMinutesPassed(long minutesPassed) {
        this.minutesPassed = minutesPassed;
    }

}