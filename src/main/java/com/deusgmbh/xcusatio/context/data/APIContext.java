package com.deusgmbh.xcusatio.context.data;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class APIContext {

    private WeatherContext weather;
    private TrafficContext traffic;
    private RNVContext rnv;
    private CalendarContext calendar;

    public APIContext(WeatherContext weather, TrafficContext traffic, RNVContext rnv, CalendarContext calendar) {
        super();
        this.weather = weather;
        this.traffic = traffic;
        this.rnv = rnv;
        this.calendar = calendar;
    }

    public APIContext() {
        super();
    }

    public WeatherContext getWeather() {
        return weather;
    }

    public void setWeather(WeatherContext weather) {
        this.weather = weather;
    }

    public TrafficContext getTraffic() {
        return traffic;
    }

    public void setTraffic(TrafficContext traffic) {
        this.traffic = traffic;
    }

    public RNVContext getRnv() {
        return rnv;
    }

    public void setRnv(RNVContext rnv) {
        this.rnv = rnv;
    }

    public CalendarContext getCalendar() {
        return calendar;
    }

    public void setCalendar(CalendarContext calendar) {
        this.calendar = calendar;
    }

}
