package com.deusgmbh.xcusatio.context.wildcard;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 * */

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;

public class LectureEvent {
    String lectureName;
    Lecturer lecturer;
    String startTimeAsClocktime;
    String endTimeAsClocktime;

    public LectureEvent(String lectureName, Lecturer lecturer, String startTimeAsClocktime, String endTimeAsClocktime) {
        this.lectureName = lectureName;
        this.lecturer = lecturer;
        this.startTimeAsClocktime = startTimeAsClocktime;
        this.endTimeAsClocktime = endTimeAsClocktime;
    }
}