package com.deusgmbh.xcusatio.api.data;

import java.util.Date;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 * */

import java.util.List;

public class TramNews {
    private Date timestamp;
    private String title;
    private String content;
    private List<String> affectedLines;

    public TramNews(Date timestamp, String title, String content, List<String> affectedLines) {
        super();
        this.timestamp = timestamp; // mind that the API returns a
                                    // date-as-String. conversion of String to
                                    // Date back to String??
        this.title = title;
        this.content = content;
        this.affectedLines = affectedLines;
    }

    public TramNews() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getAffectedLines() {
        return affectedLines;
    }

    public void setAffectedLines(List<String> affectedLines) {
        this.affectedLines = affectedLines;
    }

}