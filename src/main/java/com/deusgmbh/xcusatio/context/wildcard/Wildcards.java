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

    public List<String> getNames() {
        return wildcards.stream().map(Wildcard::getIdentifier).collect(Collectors.toList());
    }
}