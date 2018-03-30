package com.deusgmbh.xcusatio.api.data;

import java.util.Date;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 * */

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;

public class LectureEvent extends TimeFormattingUtils {
    String lectureName;
    Lecturer lecturer;
    Date startTime;
    Date endTime;
    String startTimeAsClocktimeText;
    String endTimeAsClocktimeText;
    double startTimeAsClocktime;
    double endTimeAsClocktime;

    public LectureEvent(String lectureName, Lecturer lecturer, Date startTime, Date endTime) {
        this.lectureName = lectureName;
        this.lecturer = lecturer;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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