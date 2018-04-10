package com.deusgmbh.xcusatio.context.wildcard;

import java.util.logging.Logger;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class WeatherContext {
    private static final Logger LOGGER = Logger.getLogger(WeatherContext.class.getName());

    private int temperature;
    private String description;
    private String windDirection;
    private int windSpeed;
    private int snowHourly;
    private int rainHourly;

    public WeatherContext() {
        super();
    }

    public int getTemperature() {
        return temperature;
    }

    public WeatherContext setTemperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WeatherContext setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public WeatherContext setWindDirection(String windDirection) {
        this.windDirection = windDirection;
        return this;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public WeatherContext setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
        return this;
    }

    public int getSnowHourly() {
        return snowHourly;
    }

    public WeatherContext setSnowHourly(int snowHourly) {
        this.snowHourly = snowHourly;
        return this;
    }

    public int getRainHourly() {
        return rainHourly;
    }

    public WeatherContext setRainHourly(int rainHourly) {
        this.rainHourly = rainHourly;
        return this;
    }

    public void logContextContent() {
        LOGGER.info("WeatherContext:\nTemperature: " + this.getTemperature() + " C\nDesc.: " + this.getDescription()
                + "\nWind comes from: " + this.getWindDirection() + "\nWind speed: " + this.getWindSpeed()
                + "\nSnow hourly: " + this.getSnowHourly() + "\nRain hourly: " + this.getRainHourly());
    }
}
