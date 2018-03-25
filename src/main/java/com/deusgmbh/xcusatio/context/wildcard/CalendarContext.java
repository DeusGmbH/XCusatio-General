package com.deusgmbh.xcusatio.context.wildcard;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 * */

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;

public class CalendarContext extends TimeFormattingUtils {
    private LectureEvent lectureEvent;
    private String minutesLeftText;
    private int minutesLeft;
    private String minutesPassedText;
    private int minutesPassed;

    public CalendarContext(String lectureName, Lecturer lecturer, String startTimeAsClocktime,
            String endTimeAsClocktime, int minutesLeft, int minutesPassed) {
        this.lectureEvent = new LectureEvent(lectureName, lecturer, startTimeAsClocktime, endTimeAsClocktime);
        this.minutesLeft = minutesLeft;
        this.minutesPassed = minutesPassed;
    }

    public LectureEvent getLectureEvent() {
        return lectureEvent;
    }

    public void setLectureEvent(LectureEvent lectureEvent) {
        this.lectureEvent = lectureEvent;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    public int getMinutesPassed() {
        return minutesPassed;
    }

    public void setMinutesPassed(int minutesPassed) {
        this.minutesPassed = minutesPassed;
    }

    public String getMinutesLeftText() {
        return minutesLeftText;
    }

    public void setMinutesLeftText() {
        this.minutesLeftText = formatMinutesAsText(this.minutesLeft);
    }

    public String getMinutesPassedText() {
        return minutesPassedText;
    }

    public void setMinutesPassedText() {
        this.minutesPassedText = formatMinutesAsText(this.minutesPassed);
    }
}