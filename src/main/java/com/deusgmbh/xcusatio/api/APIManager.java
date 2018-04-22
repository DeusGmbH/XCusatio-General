package com.deusgmbh.xcusatio.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONException;

import com.deusgmbh.xcusatio.api.services.CalendarAPI;
import com.deusgmbh.xcusatio.api.services.RNVAPI;
import com.deusgmbh.xcusatio.api.services.TrafficAPI;
import com.deusgmbh.xcusatio.api.services.WeatherAPI;
import com.deusgmbh.xcusatio.context.data.APIContext;
import com.deusgmbh.xcusatio.context.data.CalendarContext;
import com.deusgmbh.xcusatio.context.data.RNVContext;
import com.deusgmbh.xcusatio.context.data.TrafficContext;
import com.deusgmbh.xcusatio.context.data.WeatherContext;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.google.api.client.util.DateTime;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class APIManager {
    private static final Logger LOGGER = Logger.getLogger(APIManager.class.getName());
    List<Class<? extends APIService>> apis;

    public APIManager(List<Class<? extends APIService>> apis) {
        super();
        this.apis = apis;
    }

    public APIManager(Scenario scenario) {
        this(scenario.getRequiredAPIs());
    }

    public APIContext getAPIData(UserSettings userSettings) {
        if (apis == null) {
            return null;
        }

        DateTime beforeAPICall = new DateTime(System.currentTimeMillis());
        WeatherContext weather = callWeatherAPI(userSettings);
        long weathertime = beforeAPICall.getValue() - new DateTime(System.currentTimeMillis()).getValue();

        beforeAPICall = new DateTime(System.currentTimeMillis());
        TrafficContext traffic = callTrafficAPI(userSettings);
        long traffictime = beforeAPICall.getValue() - new DateTime(System.currentTimeMillis()).getValue();

        beforeAPICall = new DateTime(System.currentTimeMillis());
        RNVContext rnv = calRNVAPI(userSettings);
        long rnvtime = beforeAPICall.getValue() - new DateTime(System.currentTimeMillis()).getValue();

        beforeAPICall = new DateTime(System.currentTimeMillis());
        CalendarContext calendar = callCalendarAPI(userSettings);
        long calendartime = beforeAPICall.getValue() - new DateTime(System.currentTimeMillis()).getValue();

        LOGGER.info("Request time of " + weathertime + " ms for Weather API");
        LOGGER.info("Request time of " + traffictime + " ms for Traffic API");
        LOGGER.info("Request time of " + rnvtime + " ms for RNV API");
        LOGGER.info("Request time of " + calendartime + " ms for Calendar API");

        return new APIContext(weather, traffic, rnv, calendar);
    }

    private WeatherContext callWeatherAPI(UserSettings userSettings) {
        return callIfRequired(WeatherAPI.class, WeatherContext.class, userSettings);
    }

    private TrafficContext callTrafficAPI(UserSettings userSettings) {
        return callIfRequired(TrafficAPI.class, TrafficContext.class, userSettings);
    }

    private RNVContext calRNVAPI(UserSettings userSettings) {
        return callIfRequired(RNVAPI.class, RNVContext.class, userSettings);
    }

    private CalendarContext callCalendarAPI(UserSettings userSettings) {
        return callIfRequired(CalendarAPI.class, CalendarContext.class, userSettings);
    }

    private <T extends Object> T callIfRequired(Class<? extends APIService> api, Class<T> returnType,
            UserSettings userSettings) {
        if (this.containsAPI(api)) {
            try {
                try {
                    return api.newInstance()
                            .get(userSettings);
                } catch (JSONException | IOException | ParseException e) {
                    LOGGER.warning("An API Service could not processe the api result:");
                    LOGGER.warning(e.getMessage());
                    return null;
                }
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
