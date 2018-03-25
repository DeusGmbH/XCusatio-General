package com.deusgmbh.xcusatio.context.wildcard;

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;

public class CalendarContext {
    private LectureEvent lectureEvent;
    private String minutesLeft;
    private String minutesPassed;

    class LectureEvent {
        String lectureName;
        Lecturer lecturer;
        String startTimeAsClocktime;
        String endTimeAsClocktime;

        public LectureEvent(String lectureName, Lecturer lecturer, String startTimeAsClocktime,
                String endTimeAsClocktime) {
            this.lectureName = lectureName;
            this.lecturer = lecturer;
            this.startTimeAsClocktime = startTimeAsClocktime;
            this.endTimeAsClocktime = endTimeAsClocktime;
        }
    }

    public CalendarContext(String lectureName, Lecturer lecturer, String startTimeAsClocktime,
            String endTimeAsClocktime, String minutesLeft, String minutesPassed) {
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

    public String getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(String minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    public String getMinutesPassed() {
        return minutesPassed;
    }

    public void setMinutesPassed(String minutesPassed) {
        this.minutesPassed = minutesPassed;
    }

}