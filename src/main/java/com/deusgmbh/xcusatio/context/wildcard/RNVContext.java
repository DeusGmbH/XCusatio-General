package com.deusgmbh.xcusatio.context.wildcard;

import java.util.List;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.api.data.TimeFormattingUtils;
import com.deusgmbh.xcusatio.api.data.Tram;

/**
 * 
 * @author jan.leiblein@gmail.com
 * 
 */

public class RNVContext extends TimeFormattingUtils {

    private static final Logger LOGGER = Logger.getLogger(RNVContext.class.getName());

    private List<Tram> trams;

    public RNVContext(List<Tram> trams) {
        super();
        this.trams = trams;
    }

    public List<Tram> getTrams() {
        return trams;
    }

    public void setTrams(List<Tram> trams) {
        this.trams = trams;
    }

}
