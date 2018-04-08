package com.deusgmbh.xcusatio.api.data;

import java.time.LocalDate;
import java.util.Date;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 * */

import java.util.List;

public class TramNews {
    private LocalDate[] timestamps;
    private String title;
    private String content;
    private List<String> affectedLines;
	public TramNews(LocalDate[] timestamps, String title, String content, List<String> affectedLines) {
		super();
		this.timestamps = timestamps;
		this.title = title;
		this.content = content;
		this.affectedLines = affectedLines;
	}
	public LocalDate[] getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(LocalDate[] timestamps) {
		this.timestamps = timestamps;
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