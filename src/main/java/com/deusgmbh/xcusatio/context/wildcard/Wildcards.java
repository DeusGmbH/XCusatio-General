package com.deusgmbh.xcusatio.context.wildcard;

import java.util.Set;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Wildcards {
    private Set<Wildcard> wildcards;

    public Wildcards() {
        addTemperatureWildcard();
    }

    private void addTemperatureWildcard() {
        wildcards.add(new Wildcard("$temperature$") {
            @Override
            public String replaceWildcard(String source, WildcardContext wildcardContext) {
                String temperatureText = wildcardContext.getWeather().getTemperature() + " C";
                source.replaceAll("$temperature$", temperatureText);
                return source;
            }
        });
    }
}
