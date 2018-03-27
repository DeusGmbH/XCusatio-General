package com.deusgmbh.xcusatio.context.wildcard;

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
}