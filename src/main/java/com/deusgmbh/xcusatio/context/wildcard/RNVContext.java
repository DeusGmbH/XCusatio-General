package com.deusgmbh.xcusatio.context.wildcard;

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

    private Tram tram;

    public RNVContext(Tram tram) {
        super();
        this.tram = tram;
    }

    public Tram getTram() {
        return tram;
    }

    public void setTram(Tram tram) {
        this.tram = tram;
    }

}
