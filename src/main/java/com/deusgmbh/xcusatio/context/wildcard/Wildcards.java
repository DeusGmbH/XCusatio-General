package com.deusgmbh.xcusatio.context.wildcard;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.deusgmbh.xcusatio.data.EnumTranslator;

/**
 * 
 * @author Lars.Dittert@de.ibm.com
 *
 */
public class Wildcards {
	private Set<Wildcard> wildcards;

	public Wildcards() {
		wildcards = new HashSet<Wildcard>();
		wildcards.add(getTemperatureWildcard());
		wildcards.add(getNextWeekDateWildcard());
	}

	private Wildcard getTemperatureWildcard() {
		return new Wildcard("$temperature$", "Wird ersetzt mit der aktuellen Temperatur in °C (Bsp.: '14°C').") {
			@Override
			public String replace(String source, APIContext apiContext) {
				String temperatureText = apiContext.getWeather().getTemperature() + "°C";
				source.replaceAll(this.getIdentifier(), temperatureText);
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

	private Wildcard getNextWeekDateWildcard() {
		return new Wildcard("$nextWeekDate$",
				"Wird mit dem Datum des aktuellen Tages in einer Woche ersetzt (Bspw.: '13.03.2018').") {
			@Override
			public String replace(String source, APIContext apiContext) {
				Calendar cal = new GregorianCalendar();
				cal.add(Calendar.DAY_OF_MONTH, 7);
				Date dateInSevenDays = cal.getTime();
				String nextWeekDate = dateInSevenDays.toString();
				source.replaceAll(this.getIdentifier(), nextWeekDate);
				return source;
			}

			@Override
			public boolean isValidContext(APIContext apiContext) {
				return true;
			}
		};
	}

	private Wildcard getLectureEventWildcard() {
		return new Wildcard("$lectureEvent$", "Wird ersetzt mit der aktuellen Vorlesung.") {
			@Override
			public String replace(String source, APIContext apiContext) {
				String lectureText = apiContext.getCalendar().getLectureEvent().getLectureName();
				source.replaceAll(this.getIdentifier(), lectureText);
				return source;
			}

			@Override
			public boolean isValidContext(APIContext apiContext) {
				if (apiContext != null) {
					if (apiContext.getCalendar() != null && apiContext.getCalendar().getLectureEvent() != null
							&& apiContext.getCalendar().getLectureEvent().getLectureName() != null) {
						return true;
					}
				}
				return false;
			}
		};
	}

	private Wildcard getMinutesPassedWildcard() {
		return new Wildcard("$minutesPassed$", "Wird ersetzt mit der Summe der verspäteten Minuten.") {
			@Override
			public String replace(String source, APIContext apiContext) {
				String minutesPassed = apiContext.getCalendar().getMinutesPassedText();
				source.replaceAll(this.getIdentifier(), minutesPassed);
				return source;
			}

			@Override
			public boolean isValidContext(APIContext apiContext) {
				if (apiContext != null) {
					if (apiContext.getCalendar() != null && apiContext.getCalendar().getMinutesPassedText() != null) {
						return true;
					}
				}
				return false;
			}
		};
	}

	private Wildcard getRainHourlyWildcard() {
		return new Wildcard("$rainHourly$", "Wird ersetzt mit dem aktuellen Niederschlag von Regen.") {
			@Override
			public String replace(String source, APIContext apiContext) {
				String rainHourly = String.valueOf(apiContext.getWeather().getRainHourly()) + " mm";
				source.replaceAll(this.getIdentifier(), rainHourly);
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

	private Wildcard getSnowHourlyWildcard() {
		return new Wildcard("$snowHourly$", "Wird ersetzt mit dem aktuellen Niederschlag von Schnee.") {
			@Override
			public String replace(String source, APIContext apiContext) {
				String snowHourly = String.valueOf(apiContext.getWeather().getSnowHourly()) + " mm";
				source.replaceAll(this.getIdentifier(), snowHourly);
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

	private Wildcard getWindSpeedWildcard() {
		return new Wildcard("$windSpeed$", "Wird ersetzt mit der aktuellen Windgeschwindigkeit.") {
			@Override
			public String replace(String source, APIContext apiContext) {
				String windSpeed = String.valueOf(apiContext.getWeather().getWindSpeed()) + " km/h";
				source.replaceAll(this.getIdentifier(), windSpeed);
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

	private Wildcard getIncidentLocationStreetWildcard() {
		return new Wildcard("$incidentLocationStreet$",
				"Wird ersetzt durch die Straße in der ein Zwischenfall aufgetreten ist.") {
			@Override
			public String replace(String source, APIContext apiContext) {
				String incidentLocationStreet = apiContext.getTraffic().getIncidentLocation().getStreetOfIncident();
				source.replaceAll(this.getIdentifier(), incidentLocationStreet);
				return source;
			}

			@Override
			public boolean isValidContext(APIContext apiContext) {
				if (apiContext != null) {
					if (apiContext.getTraffic() != null && apiContext.getTraffic().getIncidentLocation() != null
							&& apiContext.getTraffic().getIncidentLocation().getStreetOfIncident() != null) {
						return true;
					}
				}
				return false;
			}
		};
	}

	private Wildcard getIncidentLocationCityWildcard() {
		return new Wildcard("$incidentLocationCity$",
				"Wird ersetzt durch die Stadt in der ein Zwischenfall aufgetreten ist.") {
			@Override
			public String replace(String source, APIContext apiContext) {
				String incidentLocationCity = apiContext.getTraffic().getIncidentLocation().getCityOfIncident();
				source.replaceAll(this.getIdentifier(), incidentLocationCity);
				return source;
			}

			@Override
			public boolean isValidContext(APIContext apiContext) {
				if (apiContext != null) {
					if (apiContext.getTraffic() != null && apiContext.getTraffic().getIncidentLocation() != null
							&& apiContext.getTraffic().getIncidentLocation().getCityOfIncident() != null) {
						return true;
					}
				}
				return false;
			}
		};
	}

	private Wildcard getTrafficIncidentTypWildcard() {
		return new Wildcard("$trafficIncidentTyp$", "Wird ersetzt durch den Typ eines Zwischenfalls.") {
			String trafficIncidentTypGerman;

			@Override
			public String replace(String source, APIContext apiContext) {
				String trafficIncidentTypeGerman = EnumTranslator
						.toGerman(apiContext.getTraffic().getTrafficIncident().getIncidentType());
				source.replaceAll(this.getIdentifier(), trafficIncidentTypeGerman);
				return source;
			}

			@Override
			public boolean isValidContext(APIContext apiContext) {
				if (apiContext != null) {
					if (apiContext.getTraffic() != null && apiContext.getTraffic().getTrafficIncident() != null
							&& apiContext.getTraffic().getTrafficIncident().getIncidentType() != null) {
						return true;
					}
				}
				return false;
			}
		};
	}

	private Wildcard getTramLineLabelWildcard() {
		return new Wildcard("$tramLineLabel$", "Wird ersetzt durch den Namen einer Bahn.") {
			@Override
			public String replace(String source, APIContext apiContext) {
				String tramLineLabel = apiContext.getRnv().getTram().getLineLabel();
				source.replaceAll(this.getIdentifier(), tramLineLabel);
				return source;
			}

			@Override
			public boolean isValidContext(APIContext apiContext) {
				if (apiContext != null) {
					if (apiContext.getRnv() != null && apiContext.getRnv().getTram() != null
							&& apiContext.getRnv().getTram().getLineLabel() != null) {
						return true;
					}
				}
				return false;
			}
		};
	}

	private Wildcard getNewsEntryTitleWildcard() {
		return new Wildcard("$newsEntryTitle$",
				"Wird ersetzt durch den Titel einer Zwischenfallsnachricht einer Tram.") {
			@Override
			public String replace(String source, APIContext apiContext) {
				String newsEntryTitle = apiContext.getRnv().getNewsEntry().getTitle();
				source.replaceAll(this.getIdentifier(), newsEntryTitle);
				return source;
			}

			@Override
			public boolean isValidContext(APIContext apiContext) {
				if (apiContext != null) {
					if (apiContext.getRnv() != null && apiContext.getRnv().getNewsEntry() != null
							&& apiContext.getRnv().getNewsEntry().getTitle() != null) {
						return true;
					}
				}
				return false;
			}
		};
	}

	public String replace(String source, APIContext apiContext) {
		for (Wildcard wildcard : wildcards) {
			if (source.contains(wildcard.getIdentifier())) {
				source = wildcard.replace(source, apiContext);
			}
		}
		return source;
	}

	public boolean isValidContext(String source, APIContext apiContext) {
		return wildcards.stream().filter(wildcard -> source.contains(wildcard.getIdentifier()))
				.allMatch(wildcard -> wildcard.isValidContext(apiContext));
	}

	public List<String> getNames() {
		return wildcards.stream().map(Wildcard::getIdentifier).collect(Collectors.toList());
	}

	public Set<Wildcard> getWildcards() {
		return wildcards;
	}
}
