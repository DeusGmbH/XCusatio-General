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
            public String replace(String source, WildcardContext wildcardContext) {
                String temperatureText = wildcardContext.getWeather().getTemperature() + " C";
                source.replaceAll("$temperature$", temperatureText);
                return source;
            }
        });
    }

    public String replace(String source, WildcardContext wildcardContext) {
        for (Wildcard wildcard : wildcards) {
            source = wildcard.replace(source, wildcardContext);
        }
        return source;
    }
}
