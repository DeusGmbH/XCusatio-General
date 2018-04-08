package com.deusgmbh.xcusatio.context.wildcard;

import java.util.List;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.api.data.TimeFormattingUtils;
import com.deusgmbh.xcusatio.api.data.TramDetails;
import com.deusgmbh.xcusatio.api.data.TramNews;
import com.deusgmbh.xcusatio.api.data.TramStatus;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class RNVContext extends TimeFormattingUtils {

    private static final Logger LOGGER = Logger.getLogger(RNVContext.class.getName());

    private List<TramDetails> tramDetails;
    private List<TramNews> newsEntries; // can be empty if line not affected by any
                                // incidents
    private List<TramStatus> tramStatus;
    
    
    
    
	public RNVContext(List<TramDetails> tramDetails, List<TramNews> newsEntries,
										List<TramStatus> tramStatus) {
									super();
									this.tramDetails = tramDetails;
									this.newsEntries = newsEntries;
									this.tramStatus = tramStatus;
								}

	public List<TramDetails> getTramDetails() {
		return tramDetails;
	}
	
	public void setTramDetails(List<TramDetails> tramDetails) {
		this.tramDetails = tramDetails;
	}
	public List<TramNews> getNewsEntries() {
		return newsEntries;
	}
	public void setNewsEntries(List<TramNews> newsEntries) {
		this.newsEntries = newsEntries;
	}
	public List<TramStatus> getTramStatus() {
		return tramStatus;
	}
	public void setTramStatus(List<TramStatus> tramStatus) {
		this.tramStatus = tramStatus;
	}

    

}
