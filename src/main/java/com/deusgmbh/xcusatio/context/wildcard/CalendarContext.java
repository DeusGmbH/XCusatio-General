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

    public CalendarContext(String lectureName, Lecturer lecturer, String startTimeInMinutes, String endTimeInMinutes,
            String startTimeAsClocktime, String endTimeAsClocktime, String minutesLeft, String minutesPassed) {
        super();
        this.lectureEvent = new LectureEvent(lectureName, lecturer, startTimeAsClocktime, endTimeAsClocktime);
        this.minutesLeft = minutesLeft;
        this.minutesPassed = minutesPassed;
    }

}