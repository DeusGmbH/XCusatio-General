package com.deusgmbh.xcusatio.context.data;

/**
 * 
 * @author jan.leiblein@gmail.com
 *
 */
public class LectureEvent {
    String lectureName;
    String startTimeAsClocktime;
    String endTimeAsClocktime;

    public LectureEvent(String lectureName, String startTimeAsClocktime, String endTimeAsClocktime) {
        this.lectureName = lectureName;
        this.startTimeAsClocktime = startTimeAsClocktime;
        this.endTimeAsClocktime = endTimeAsClocktime;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getStartTimeAsClocktime() {
        return startTimeAsClocktime;
    }

    public void setStartTimeAsClocktime(String startTimeAsClocktime) {
        this.startTimeAsClocktime = startTimeAsClocktime;
    }

    public String getEndTimeAsClocktime() {
        return endTimeAsClocktime;
    }

    public void setEndTimeAsClocktime(String endTimeAsClocktime) {
        this.endTimeAsClocktime = endTimeAsClocktime;
    }

}