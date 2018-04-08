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
    private List<LocalDate[]> timestamps;
    private List<String> titles;
    private List<String> contents;
    private List<String> affectedLines;

    
    
    public TramNews(List<LocalDate[]> timestamps, List<String> titles, List<String> contents,
			List<String> affectedLines) {
		super();
		this.timestamps = timestamps;
		this.titles = titles;
		this.contents = contents;
		this.affectedLines = affectedLines;
	}
	public List<LocalDate[]> getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(List<LocalDate[]> timestamps) {
		this.timestamps = timestamps;
	}
	public List<String> getTitles() {
		return titles;
	}
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	public List<String> getContents() {
		return contents;
	}
	public void setContents(List<String> contents) {
		this.contents = contents;
	}
	public List<String> getAffectedLines() {
		return affectedLines;
	}
	public void setAffectedLines(List<String> affectedLines) {
		this.affectedLines = affectedLines;
	}

    

}