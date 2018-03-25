package com.deusgmbh.xcusatio.context.wildcard;

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;

public class CalendarContext {
    private LectureEvent lectureEvent;
    private String minutesLeft;
    private String minutesPassed;

    class LectureEvent {
        String lectureName;
        Lecturer lecturer;
        String startTimeInMinutes;
        String endTimeinMinutes;
    }

}
