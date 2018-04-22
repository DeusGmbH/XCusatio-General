package com.deusgmbh.xcusatio.context.wildcard;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.deusgmbh.xcusatio.context.data.APIContext;
import com.deusgmbh.xcusatio.data.EnumTranslator;

/**
 * 
 * @author Lars.Dittert@de.ibm.com
 *
 */
public class WildcardTransformers {
    private Set<WildcardTransformer> wildcardTransformers;

    public WildcardTransformers() {
        wildcardTransformers = new HashSet<WildcardTransformer>();
        wildcardTransformers.add(this.getTemperatureWildcard());
        wildcardTransformers.add(this.getNextWeekDateWildcard());
        wildcardTransformers.add(this.getIncidentLocationCityWildcard());
        wildcardTransformers.add(this.getIncidentLocationStreetWildcard());
        wildcardTransformers.add(this.getLectureEventWildcard());
        wildcardTransformers.add(this.getMinutesPassedWildcard());
        wildcardTransformers.add(this.getRainHourlyWildcard());
        wildcardTransformers.add(this.getTrafficIncidentTypWildcard());
        wildcardTransformers.add(this.getSnowHourlyWildcard());
        wildcardTransformers.add(this.getTramLineLabelWildcard());
        wildcardTransformers.add(this.getWindSpeedWildcard());
    }

    private WildcardTransformer getTemperatureWildcard() {
        return new WildcardTransformer("temperature",
                "Wird ersetzt mit der aktuellen Temperatur in °C (Bsp.: '14°C').") {
            @Override
            public String replace(String source, APIContext apiContext) {
                String temperatureText = apiContext.getWeather()
                        .getTemperature() + "°C";
                source = source.replaceAll(this.getIdentifierRegEx(), temperatureText);
                return source;
            }

            @Override
            public boolean isValidContext(APIContext apiContext) {
                if (apiContext != null) {
                    if (apiContext.getWeather() != null) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private WildcardTransformer getNextWeekDateWildcard() {
        return new WildcardTransformer("nextWeekDate",
                "Wird mit dem Datum des aktuellen Tages in einer Woche ersetzt (Bspw.: '13.03.2018').") {
            @Override
            public String replace(String source, APIContext apiContext) {
                Calendar cal = new GregorianCalendar();
                cal.add(Calendar.DAY_OF_MONTH, 7);
                Date dateInSevenDays = cal.getTime();
                String nextWeekDate = dateInSevenDays.toString();

                source = source.replaceAll(this.getIdentifierRegEx(), nextWeekDate);
                return source;
            }

            @Override
            public boolean isValidContext(APIContext apiContext) {
                return true;
            }
        };
    }

    private WildcardTransformer getCurrentTimeWildcard() {
        return new WildcardTransformer("currentTime", "Wird durch die aktuelle Uhrzeit ersetzt.") {
            @Override
            public String replace(String source, APIContext apiContext) {
                SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");
                Date currentDate = new Date();
                String currentDateString = sdfDate.format(currentDate);

                source = source.replaceAll(this.getIdentifierRegEx(), currentDateString);
                return source;
            }

            @Override
            public boolean isValidContext(APIContext apiContext) {
                return true;
            }
        };
    }

    private WildcardTransformer getLectureEventWildcard() {
        return new WildcardTransformer("lectureEvent", "Wird ersetzt mit der aktuellen Vorlesung.") {
            @Override
            public String replace(String source, APIContext apiContext) {
                String lectureTitle = apiContext.getCalendar()
                        .getLectureEvent()
                        .getLectureTitle();
                source = source.replaceAll(this.getIdentifierRegEx(), lectureTitle);
                return source;
            }

            @Override
            public boolean isValidContext(APIContext apiContext) {
                if (apiContext != null) {
                    if (apiContext.getCalendar() != null && apiContext.getCalendar()
                            .getLectureEvent() != null && apiContext.getCalendar()
                                    .getLectureEvent()
                                    .getLectureTitle() != null) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private WildcardTransformer getMinutesPassedWildcard() {
        return new WildcardTransformer("minutesPassed", "Wird ersetzt mit der Summe der verspäteten Minuten.") {
            @Override
            public String replace(String source, APIContext apiContext) {
                String minutesPassed = apiContext.getCalendar()
                        .getMinutesPassedText();
                source = source.replaceAll(this.getIdentifierRegEx(), minutesPassed);
                return source;
            }

            @Override
            public boolean isValidContext(APIContext apiContext) {
                if (apiContext != null) {
                    if (apiContext.getCalendar() != null && apiContext.getCalendar()
                            .getMinutesPassedText() != null) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private WildcardTransformer getRainHourlyWildcard() {
        return new WildcardTransformer("rainHourly", "Wird ersetzt mit dem aktuellen Niederschlag von Regen.") {
            @Override
            public String replace(String source, APIContext apiContext) {
                String rainHourly = String.valueOf(apiContext.getWeather()
                        .getRainHourly()) + " mm";
                source = source.replaceAll(this.getIdentifierRegEx(), rainHourly);
                return source;
            }

            @Override
            public boolean isValidContext(APIContext apiContext) {
                if (apiContext != null) {
                    if (apiContext.getWeather() != null) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private WildcardTransformer getSnowHourlyWildcard() {
        return new WildcardTransformer("snowHourly", "Wird ersetzt mit dem aktuellen Niederschlag von Schnee.") {
            @Override
            public String replace(String source, APIContext apiContext) {
                String snowHourly = String.valueOf(apiContext.getWeather()
                        .getSnowHourly()) + " mm";
                source = source.replaceAll(this.getIdentifierRegEx(), snowHourly);
                return source;
            }

            @Override
            public boolean isValidContext(APIContext apiContext) {
                if (apiContext != null) {
                    if (apiContext.getWeather() != null) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private WildcardTransformer getWindSpeedWildcard() {
        return new WildcardTransformer("windSpeed", "Wird ersetzt mit der aktuellen Windgeschwindigkeit.") {
            @Override
            public String replace(String source, APIContext apiContext) {
                String windSpeed = String.valueOf(apiContext.getWeather()
                        .getWindSpeed()) + " km/h";
                source = source.replaceAll(this.getIdentifierRegEx(), windSpeed);
                return source;
            }

            @Override
            public boolean isValidContext(APIContext apiContext) {
                if (apiContext != null) {
                    if (apiContext.getWeather() != null) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private WildcardTransformer getIncidentLocationStreetWildcard() {
        return new WildcardTransformer("incidentLocationStreet",
                "Wird ersetzt durch die Straße in der ein Zwischenfall aufgetreten ist.") {
            @Override
            public String replace(String source, APIContext apiContext) {
                String incidentLocationStreet = apiContext.getTraffic()
                        .getTrafficIncidents()
                        .get(0)
                        .getTrafficIncidentLocation()
                        .getStreetOfIncident();
                source = source.replaceAll(this.getIdentifierRegEx(), incidentLocationStreet);
                return source;
            }

            @Override
            public boolean isValidContext(APIContext apiContext) {
                if (apiContext != null) {
                    if (apiContext.getTraffic() != null && !apiContext.getTraffic()
                            .getTrafficIncidents()
                            .isEmpty() && apiContext.getTraffic()
                                    .getTrafficIncidents()
                                    .get(0)
                                    .getTrafficIncidentLocation() != null
                            && apiContext.getTraffic()
                                    .getTrafficIncidents()
                                    .get(0)
                                    .getTrafficIncidentLocation()
                                    .getStreetOfIncident() != null) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private WildcardTransformer getIncidentLocationCityWildcard() {
        return new WildcardTransformer("incidentLocationCity",
                "Wird ersetzt durch die Stadt in der ein Zwischenfall aufgetreten ist.") {
            @Override
            public String replace(String source, APIContext apiContext) {
                String incidentLocationCity = apiContext.getTraffic()
                        .getTrafficIncidents()
                        .get(0)
                        .getTrafficIncidentLocation()
                        .getCityOfIncident();
                source = source.replaceAll(this.getIdentifierRegEx(), incidentLocationCity);
                return source;
            }

            @Override
            public boolean isValidContext(APIContext apiContext) {
                if (apiContext != null) {
                    if (apiContext.getTraffic() != null && !apiContext.getTraffic()
                            .getTrafficIncidents()
                            .isEmpty() && apiContext.getTraffic()
                                    .getTrafficIncidents()
                                    .get(0)
                                    .getTrafficIncidentLocation() != null
                            && apiContext.getTraffic()
                                    .getTrafficIncidents()
                                    .get(0)
                                    .getTrafficIncidentLocation()
                                    .getCityOfIncident() != null) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private WildcardTransformer getTrafficIncidentTypWildcard() {
        return new WildcardTransformer("trafficIncidentTyp", "Wird ersetzt durch den Typ eines Zwischenfalls.") {
            @Override
            public String replace(String source, APIContext apiContext) {
                String trafficIncidentTypeGerman = EnumTranslator.toGerman(apiContext.getTraffic()
                        .getTrafficIncidents()
                        .get(0)
                        .getTrafficIncidentDetails()
                        .getTrafficIncidentType());
                source = source.replaceAll(this.getIdentifierRegEx(), trafficIncidentTypeGerman);
                return source;
            }

            @Override
            public boolean isValidContext(APIContext apiContext) {
                if (apiContext != null) {
                    if (apiContext.getTraffic() != null && !apiContext.getTraffic()
                            .getTrafficIncidents()
                            .isEmpty() && apiContext.getTraffic()
                                    .getTrafficIncidents()
                                    .get(0)
                                    .getTrafficIncidentDetails() != null
                            && apiContext.getTraffic()
                                    .getTrafficIncidents()
                                    .get(0)
                                    .getTrafficIncidentDetails()
                                    .getTrafficIncidentType() != null) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private WildcardTransformer getTramLineLabelWildcard() {
        return new WildcardTransformer("tramLineLabel", "Wird ersetzt durch den Namen einer Bahn.") {
            @Override
            public String replace(String source, APIContext apiContext) {
                String tramLineLabel = apiContext.getRnv()
                        .getTram()
                        .getLineLabel();
                source = source.replaceAll(this.getIdentifierRegEx(), tramLineLabel);
                return source;
            }

            @Override
            public boolean isValidContext(APIContext apiContext) {
                if (apiContext != null) {
                    if (apiContext.getRnv() != null && apiContext.getRnv()
                            .getTram() != null && apiContext.getRnv()
                                    .getTram()
                                    .getLineLabel() != null) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    public String replace(String source, APIContext apiContext) {
        for (WildcardTransformer wildcard : wildcardTransformers) {
            if (source.contains(wildcard.getIdentifierExcuseString())) {
                source = wildcard.replace(source, apiContext);
            }
        }
        return source;
    }

    public boolean isValidContext(String source, APIContext apiContext) {
        return wildcardTransformers.stream()
                .filter(wildcard -> source.contains(wildcard.getIdentifierExcuseString()))
                .allMatch(wildcard -> wildcard.isValidContext(apiContext));
    }

    public List<String> getNames() {
        return wildcardTransformers.stream()
                .map(WildcardTransformer::getIdentifierExcuseString)
                .collect(Collectors.toList());
    }

    public Set<WildcardTransformer> getWildcards() {
        return wildcardTransformers;
    }
}
