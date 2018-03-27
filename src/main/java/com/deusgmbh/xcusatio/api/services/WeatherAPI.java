package com.deusgmbh.xcusatio.api.services;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.context.wildcard.WeatherContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class WeatherAPI extends APIService {

    @Override
    public WeatherContext get(UserSettings usersettings) {
        // TODO: implement API call and result processing
        new WeatherContext(11, "Sturmböen", "SE", 15, 8, 34, 0, 2);
        return null;
    }

}
