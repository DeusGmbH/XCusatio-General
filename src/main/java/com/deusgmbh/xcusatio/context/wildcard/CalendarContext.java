package com.deusgmbh.xcusatio.context.wildcard;

import java.util.Date;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.api.data.LectureEvent;
import com.deusgmbh.xcusatio.api.data.TimeFormattingUtils;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 * */

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;

public class CalendarContext extends TimeFormattingUtils {
    private static final Logger LOGGER = Logger.getLogger(CalendarContext.class.getName());

    private LectureEvent lectureEvent;
    private long minutesLeft;
    private long minutesPassed;

    public CalendarContext(String lectureName, Lecturer lecturer, Date startTime, Date endTime, long minutesPassed,
            long minutesLeft) {
        this.lectureEvent = new LectureEvent(lectureName, lecturer, startTime, endTime);
        this.minutesLeft = minutesLeft;
        this.minutesPassed = minutesPassed;
    }

    public CalendarContext() {
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

    /* formatted printing of context contents for testing purposes only */
    public void logContextContent() {
        LOGGER.info("CalendarContext:\n" + this.lectureEvent.getLectureName() + "\nRead by: "
                + this.lectureEvent.getLecturer().getName() + ",\n" + "starts at: " + this.lectureEvent.getStartTime()
                + "\nends at: " + this.lectureEvent.getEndTime() + "\nPassed already: " + this.minutesPassed
                + "\nStill to go: " + this.minutesLeft + "\n");

    }

}