package com.deusgmbh.xcusatio.api.data;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 * */

import java.util.List;

public class TramNews {
    String title;
    String content;
    List<String> affectedLines;

    public TramNews(String title, String content, List<String> affectedLines) {
        super();
        this.title = title;
        this.content = content;
        this.affectedLines = affectedLines;
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