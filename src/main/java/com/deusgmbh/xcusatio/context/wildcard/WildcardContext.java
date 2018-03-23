package com.deusgmbh.xcusatio.context.wildcard;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class WildcardContext {

    private WeatherContext weather;
    private TrafficContext traffic;
    private RNVContext rnv;
    private NewsContext news;

    public WildcardContext(WeatherContext weather, TrafficContext traffic, RNVContext rnv, NewsContext news) {
        super();
        this.weather = weather;
        this.traffic = traffic;
        this.rnv = rnv;
        this.news = news;
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

    public NewsContext getNews() {
        return news;
    }

    public void setNews(NewsContext news) {
        this.news = news;
    }

}
