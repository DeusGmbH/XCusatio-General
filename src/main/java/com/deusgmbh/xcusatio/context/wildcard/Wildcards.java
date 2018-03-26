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
        wildcards.add(getTemperatureWildcard());
        wildcards.add(getNextWeekDateWildcard());
    }

    private Wildcard getTemperatureWildcard() {
        return new Wildcard("$temperature$", "Wird ersetzt mit der aktuellen Temperatur in °C (Bsp.: '14°C')") {
            @Override
            public String replace(String source, APIContext apiContext) {
                String temperatureText = apiContext.getWeather()
                        .getTemperature() + "°C";
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
                "Wird mit dem Datum des aktuellen Tages in einer Woche ersetzt (Bspw.: '13.03.2018')") {
            @Override
            public String replace(String source, APIContext apiContext) {
                // TODO: to be implemented
                return source;
            }

            @Override
            public boolean isValidContext(APIContext apiContext) {
                // TODO: this should actually be true, because this wildcard
                // doesnt't depend on any apiContext. However, until the replace
                // method is implemented this has to be false
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
        return wildcards.stream()
                .filter(wildcard -> source.contains(wildcard.getIdentifier()))
                .allMatch(wildcard -> wildcard.isValidContext(apiContext));
    }

    public List<String> getNames() {
        return wildcards.stream()
                .map(Wildcard::getIdentifier)
                .collect(Collectors.toList());
    }

    public Set<Wildcard> getWildcards() {
        return wildcards;
    }
}
