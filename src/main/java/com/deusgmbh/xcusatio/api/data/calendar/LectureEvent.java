package com.deusgmbh.xcusatio.api.data.calendar;

import java.util.Date;

public class LectureEvent extends TimeFormattingUtils {
    String lectureTitle;
    String lecturerName;
    Date startTime;
    Date endTime;

    public LectureEvent(String lectureTitle, String lecturerName, Date startTime, Date endTime) {
        super();
        this.lectureTitle = lectureTitle;
        this.lecturerName = lecturerName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getLectureTitle() {
        return lectureTitle;
    }

    public void setLectureTitle(String lectureTitle) {
        this.lectureTitle = lectureTitle;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
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

}