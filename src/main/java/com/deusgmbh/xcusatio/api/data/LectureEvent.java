package com.deusgmbh.xcusatio.api.data;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 * */

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;

public class LectureEvent extends TimeFormattingUtils {
    String lectureName;
    Lecturer lecturer;
    String startTimeAsClocktimeText;
    String endTimeAsClocktimeText;
    double startTimeAsClocktime;
    double endTimeAsClocktime;

    public LectureEvent(String lectureName, Lecturer lecturer, String startTimeAsClocktimeText,
            String endTimeAsClocktimeText) {
        this.lectureName = lectureName;
        this.lecturer = lecturer;
        this.startTimeAsClocktimeText = startTimeAsClocktimeText;
        this.endTimeAsClocktimeText = endTimeAsClocktimeText;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public String getStartTimeAsClocktimeText() {
        return startTimeAsClocktimeText;
    }

    public void setStartTimeAsClocktimeText(String startTimeAsClocktimeText) {
        this.startTimeAsClocktimeText = startTimeAsClocktimeText;
    }

    public String getEndTimeAsClocktimeText() {
        return endTimeAsClocktimeText;
    }

    public void setEndTimeAsClocktimeText(String endTimeAsClocktimeText) {
        this.endTimeAsClocktimeText = endTimeAsClocktimeText;
    }

    public double getStartTimeAsClocktime() {
        return startTimeAsClocktime;
    }

    public void setStartTimeAsClocktime(double startTimeAsClocktime) {
        this.startTimeAsClocktime = startTimeAsClocktime;
    }

    public double getEndTimeAsClocktime() {
        return endTimeAsClocktime;
    }

    public void setEndTimeAsClocktime(double endTimeAsClocktime) {
        this.endTimeAsClocktime = endTimeAsClocktime;
    }

}