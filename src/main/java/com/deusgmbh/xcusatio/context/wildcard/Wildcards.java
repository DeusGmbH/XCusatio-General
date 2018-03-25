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
    private static Set<Wildcard> wildcards;

    public static void initialize() {
        wildcards = new HashSet<Wildcard>();
        addTemperatureWildcard();
    }

    private static void addTemperatureWildcard() {
        wildcards.add(new Wildcard("$temperature$") {
            @Override
            public String replace(String source, APIDrivenContext apiContext) {
                if (apiContext != null) {
                    String temperatureText = apiContext.getWeather()
                            .getTemperature() + " C";
                    source.replaceAll("$temperature$", temperatureText);
                }
                return source;
            }
        });

    }

    public static String replace(String source, APIDrivenContext apiContext) {
        for (Wildcard wildcard : wildcards) {
            source = wildcard.replace(source, apiContext);
        }
        return source;
    }

    public static List<String> getNames() {
        return wildcards.stream()
                .map(Wildcard::getIdentifier)
                .collect(Collectors.toList());
    }
}
