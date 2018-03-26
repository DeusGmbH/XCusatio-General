package com.deusgmbh.xcusatio.context.wildcard;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */
public class CalendarContext extends TimeFormattingUtils {
    private LectureEvent lectureEvent;
    private int minutesLeft;
    private int minutesPassed;

    public CalendarContext(String lectureName, String startTimeAsClocktime, String endTimeAsClocktime, int minutesLeft,
            int minutesPassed) {
        this.lectureEvent = new LectureEvent(lectureName, startTimeAsClocktime, endTimeAsClocktime);
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
        return formatMinutesAsText(this.minutesLeft);
    }

    public String getMinutesPassedText() {
        return formatMinutesAsText(this.minutesPassed);
    }

}