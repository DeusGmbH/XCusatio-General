package com.deusgmbh.xcusatio.api.services;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.context.wildcard.CalendarContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class CalendarAPI extends APIService {

    @Override
    public CalendarContext get(UserSettings usersettings) {
        // TODO Auto-generated method stub
        return null;
    }

    // TODO: convert int to string for each time detail, e.g. minutesLeft = 11 -->
    // "11 Minuten" / 1 --> "1 Minute"

}
