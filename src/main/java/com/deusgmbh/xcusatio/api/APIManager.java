package com.deusgmbh.xcusatio.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.deusgmbh.xcusatio.api.services.CalendarAPI;
import com.deusgmbh.xcusatio.api.services.RNVAPI;
import com.deusgmbh.xcusatio.api.services.TrafficAPI;
import com.deusgmbh.xcusatio.api.services.WeatherAPI;
import com.deusgmbh.xcusatio.context.wildcard.APIContext;
import com.deusgmbh.xcusatio.context.wildcard.CalendarContext;
import com.deusgmbh.xcusatio.context.wildcard.RNVContext;
import com.deusgmbh.xcusatio.context.wildcard.TrafficContext;
import com.deusgmbh.xcusatio.context.wildcard.WeatherContext;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class APIManager {
    List<Class<? extends APIService>> apis;

    public APIManager(List<Class<? extends APIService>> apis) {
        super();
        this.apis = apis;
    }

    public APIManager(Scenario scenario) {
        this(scenario.getRequiredAPIs());
    }

    public APIContext getAPIData(UserSettings userSettings) throws JSONException, IOException, ParseException {
        if (apis == null) {
            return null;
        }

        // TODO: Maybe some performance optimization is necessary here:
        // investigate Futures and completableFutures

        WeatherContext weather = callWeatherAPI(userSettings);
        TrafficContext traffic = callTrafficAPI(userSettings);
        RNVContext rnv = calRNVAPI(userSettings);
        CalendarContext calendar = callCalendarAPI(userSettings);

        return new APIContext(weather, traffic, rnv, calendar);
    }

    private WeatherContext callWeatherAPI(UserSettings userSettings) throws JSONException, IOException, ParseException {
        return callIfRequired(WeatherAPI.class, WeatherContext.class, userSettings);
    }

    private TrafficContext callTrafficAPI(UserSettings userSettings) throws JSONException, IOException, ParseException {
        return callIfRequired(TrafficAPI.class, TrafficContext.class, userSettings);
    }

    private RNVContext calRNVAPI(UserSettings userSettings) throws JSONException, IOException, ParseException {
        return callIfRequired(RNVAPI.class, RNVContext.class, userSettings);
    }

    private CalendarContext callCalendarAPI(UserSettings userSettings) throws JSONException, IOException, ParseException {
        return callIfRequired(CalendarAPI.class, CalendarContext.class, userSettings);
    }

    private <T extends Object> T callIfRequired(Class<? extends APIService> api, Class<T> returnType,
            UserSettings userSettings) throws JSONException, IOException, ParseException {
        if (this.containsAPI(api)) {
            try {
                return api.newInstance()
                        .get(userSettings);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("APIService does not have an accessible constructor");
            }
        }
        return null;
    }

    private boolean containsAPI(Class<? extends APIService> api) {
        return apis.stream()
                .filter(api::equals)
                .findFirst()
                .isPresent();
    }

}
