package com.deusgmbh.xcusatio.context.wildcard;

import com.deusgmbh.xcusatio.api.data.LectureEvent;
import com.deusgmbh.xcusatio.api.data.TimeFormattingUtils;

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

    public CalendarContext(String lectureName, Lecturer lecturer, String startTimeAsClocktimeText,
            String endTimeAsClocktimeText, int minutesLeft, int minutesPassed) {
        this.lectureEvent = new LectureEvent(lectureName, lecturer, startTimeAsClocktimeText, endTimeAsClocktimeText);
        this.minutesLeft = minutesLeft;
        this.minutesPassed = minutesPassed;
        this.minutesPassedText = formatMinutesAsText(this.minutesPassed);
        this.minutesLeftText = formatMinutesAsText(this.minutesLeft);
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

    /* formatted printing of context contents for testing purposes only */
    public void printContextContent() {
        System.out.println("CalendarContext:\n" + this.lectureEvent.getLectureName() + "\nRead by: "
                + this.lectureEvent.getLecturer().getName() + ",\n" + "starts at: "
                + this.lectureEvent.getStartTimeAsClocktimeText() + "\nends at: "
                + this.lectureEvent.getEndTimeAsClocktimeText() + "\nPassed already: " + this.minutesPassedText
                + "\nStill to go: " + this.minutesLeftText + "\n");

    }
}