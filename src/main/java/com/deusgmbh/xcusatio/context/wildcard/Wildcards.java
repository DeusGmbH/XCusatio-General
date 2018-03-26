package com.deusgmbh.xcusatio.context.wildcard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Wildcards {
	private Set<Wildcard> wildcards;

	public Wildcards() {
		wildcards = new HashSet<Wildcard>();
		addTemperatureWildcard();
	}

	private void addTemperatureWildcard() {
		wildcards.add(new Wildcard("$temperature$", "Wird ersetzt mit der aktuellen Temperatur in °C (Bsp.: '14°C')") {
			@Override
			public String replace(String source, APIContext apiContext) {
				String temperatureText = apiContext.getWeather().getTemperature() + "°C";
				source.replaceAll("$temperature$", temperatureText);
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
		});

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
