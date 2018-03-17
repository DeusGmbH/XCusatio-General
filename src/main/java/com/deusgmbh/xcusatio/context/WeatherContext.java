package com.deusgmbh.xcusatio.context;

public class WeatherContext {
    private int temperature;
    private String description;
    private String windDirection;
    private int maxTemparature;
    private int minTemparature;
    private int windSpeed;
    private int snowHourly;
    private int rainHourly;

    public WeatherContext(int temperature, String description, String windDirection, int maxTemparature,
            int minTemparature, int windSpeed, int snowHourly, int rainHourly) {
        super();
        this.temperature = temperature;
        this.description = description;
        this.windDirection = windDirection;
        this.maxTemparature = maxTemparature;
        this.minTemparature = minTemparature;
        this.windSpeed = windSpeed;
        this.snowHourly = snowHourly;
        this.rainHourly = rainHourly;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public int getMaxTemparature() {
        return maxTemparature;
    }

    public void setMaxTemparature(int maxTemparature) {
        this.maxTemparature = maxTemparature;
    }

    public int getMinTemparature() {
        return minTemparature;
    }

    public void setMinTemparature(int minTemparature) {
        this.minTemparature = minTemparature;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getSnowHourly() {
        return snowHourly;
    }

    public void setSnowHourly(int snowHourly) {
        this.snowHourly = snowHourly;
    }

    public int getRainHourly() {
        return rainHourly;
    }

    public void setRainHourly(int rainHourly) {
        this.rainHourly = rainHourly;
    }

}
