package com.deusgmbh.xcusatio.generator;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.deusgmbh.xcusatio.api.data.TrafficIncidentType;
import com.deusgmbh.xcusatio.api.data.TramStatus;
import com.deusgmbh.xcusatio.context.Context;
import com.deusgmbh.xcusatio.context.wildcard.RNVContext;
import com.deusgmbh.xcusatio.context.wildcard.TrafficContext;
import com.deusgmbh.xcusatio.context.wildcard.WeatherContext;
import com.deusgmbh.xcusatio.context.wildcard.Wildcards;
import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.data.usersettings.ExcuseVibes;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.ExcuseVibeMode;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class ExcuseGenerator {
    private final String NO_EXCUSE_FOUND = "Es konnte leider keine Ausrede für dieses Scenario gefunden werden. Im Editor können diese aber hinzugefügt werden.";
    private Wildcards wildcards;

    public ExcuseGenerator(Wildcards wildcards) {
        this.wildcards = wildcards;
    }

    /**
     * 
     * @param context
     * @returns the percentage indicating the approval rate to be displayed
     */
    public double getThumbGesture(Context context) {
        // TODO: calculate random percantage base on context (e.g. more likely
        // to be near 1 if context.vibemodes.suckup == true)
        return Math.random();
    }

    public Excuse getContextBasedExcuse(List<Excuse> excuses, Context context, Scenario scenario) {
        if (excuses == null || excuses.isEmpty()) {
            throw new IllegalArgumentException("Excuses must not be null and not empty");
        }
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null");
        }
        if (!scenario.isExcuseType()) {
            throw new IllegalArgumentException("Scenario is not of excuse type ");
        }

        List<Tag> contextTags = this.getContextTag(context);
        List<Excuse> filteredExcuses = new ExcuseFilter(excuses).byScenario(scenario)
                .byValidWildcard(wildcards, context)
                .byContextTags(contextTags)
                .get();

        List<Excuse> sortedByLastUsed = filteredExcuses.stream()
                .sorted(Excuse.byLastUsed.reversed())
                .collect(Collectors.toList());

        List<Excuse> sortedByRating = sortedByLastUsed.stream()
                .limit(getRelativeSize(sortedByLastUsed, 0.5))
                .sorted(Excuse.byRating)
                .collect(Collectors.toList());

        List<Excuse> finalExcuses = sortedByRating.stream()
                .limit(getRelativeSize(sortedByRating, 0.8))
                .collect(Collectors.toList());

        if (finalExcuses.isEmpty()) {
            return new Excuse(NO_EXCUSE_FOUND, scenario.getScenarioType());
        }

        int randomExcuseId = ThreadLocalRandom.current()
                .nextInt(0, finalExcuses.size());

        return finalExcuses.get(randomExcuseId);
    }

    private long getRelativeSize(List<Excuse> excuses, double d) {
        int relativeSize = (int) Math.ceil(excuses.size() * d);
        return relativeSize <= 1 ? 1 : relativeSize;
    }

    /**
     * Generates a list of Tag based on context and scenario type
     * 
     * @return
     */
    private List<Tag> getContextTag(Context context) {
        List<Tag> tags = new ArrayList<>();
        if (context == null) {
            return tags;
        }

        tags.addAll(this.getExcusesVibeTag(context));
        tags.addAll(this.getLecturerTag(context));
        tags.addAll(this.getSexTag(context));
        tags.addAll(this.getWeatherTags(context));
        tags.addAll(this.getTrafficTags(context));
        tags.addAll(this.getPublicTransportTags(context));
        tags.addAll(this.getAgeGroupeTags(context));

        // add traffic jam tag

        return tags.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    private Collection<? extends Tag> getAgeGroupeTags(Context context) {
        Set<Tag> ageGroupeTags = new HashSet<>();
        int age = Period.between(context.getAge(), LocalDate.now())
                .getYears();
        if (between(age, 0, 18)) {
            ageGroupeTags.add(Tag.AGE_UNDER_18);
        } else if (between(age, 18, 21)) {
            ageGroupeTags.add(Tag.AGE_BETWEEN_18_AND_21);
        } else if (between(age, 21, 30)) {
            ageGroupeTags.add(Tag.AGE_BETWEEN_21_AND_30);
        } else if (between(age, 30, 50)) {
            ageGroupeTags.add(Tag.AGE_BETWEEN_30_AND_50);
        } else if (between(age, 50, 100)) {
            ageGroupeTags.add(Tag.AGE_OVER_50);

        }
        return ageGroupeTags;
    }

    private Collection<? extends Tag> getPublicTransportTags(Context context) {
        Set<Tag> publicTransportTags = new HashSet<>();
        RNVContext publicTransportContext = context.getApiContext()
                .getRnv();
        if (publicTransportContext != null) {
            if (publicTransportContext.getTramStatus()
                    .equals(TramStatus.CANCELLED)) {
                publicTransportTags.add(Tag.TRAIN_CANCELLED);
            }
            if (publicTransportContext.getDifferenceTimeInMinutes() > 0) {
                publicTransportTags.add(Tag.TRAIN_DELAYED);
            }
            if (publicTransportContext.getDifferenceTimeInMinutes() > 15) {
                publicTransportTags.add(Tag.TRAIN_HEAVILY_DELAYED);
            }
        }
        return publicTransportTags;
    }

    private Collection<? extends Tag> getTrafficTags(Context context) {
        // TODO: add more traffic tags
        Set<Tag> trafficTags = new HashSet<>();
        TrafficContext trafficContext = context.getApiContext()
                .getTraffic();
        if (trafficContext != null) {
            if (trafficContext.getTrafficIncident()
                    .getIncidentType()
                    .equals(TrafficIncidentType.ACCIDENT)) {
                trafficTags.add(Tag.ACCIDENT);
            }
            if (trafficContext.getTrafficIncident()
                    .getIncidentType()
                    .equals(TrafficIncidentType.CONSTRUCTION)) {
                trafficTags.add(Tag.CONSTRUCTION);
            }
        }
        return trafficTags;
    }

    private Collection<? extends Tag> getWeatherTags(Context context) {
        Set<Tag> weatherTags = new HashSet<>();
        WeatherContext watherContext = context.getApiContext()
                .getWeather();
        if (watherContext != null) {
            if (watherContext.getRainHourly() > 0) {
                weatherTags.add(Tag.RAINY);
            }
            if (watherContext.getSnowHourly() > 0) {
                weatherTags.add(Tag.SNOW);
            }
            if (watherContext.getWindSpeed() > 30) {
                weatherTags.add(Tag.WINDY);
            }
            if (watherContext.getWindSpeed() > 60) {
                weatherTags.add(Tag.STORM);
            }
            if (watherContext.getTemperature() <= 0) {
                weatherTags.add(Tag.COLD);
            }
            if (watherContext.getTemperature() < 10 && watherContext.getTemperature() > 0) {
                weatherTags.add(Tag.MILD);
            }
            if (watherContext.getTemperature() > 20) {
                weatherTags.add(Tag.WARM);
            }
            if (watherContext.getTemperature() > 30) {
                weatherTags.add(Tag.HOT);
            }
        }
        return weatherTags;

    }

    private Collection<Tag> getLecturerTag(Context context) {
        if (context.getLecturer() != null && context.getLecturer()
                .getTags() != null) {
            List<Tag> lecturerTags = context.getLecturer()
                    .getTags();
            if (context.getExcuseVibeMode()
                    .equals(ExcuseVibeMode.MANUALLY)) {
                return lecturerTags.stream()
                        .filter(Tag::isNotExcuseVibe)
                        .collect(Collectors.toList());
            }
            return lecturerTags;
        }
        return new HashSet<>();
    }

    private Collection<? extends Tag> getExcusesVibeTag(Context context) {
        Set<Tag> excusesVibeTag = new HashSet<>();
        ExcuseVibes excusesVibes = context.getManuellExcusesVibes();
        if (excusesVibes != null) {
            if (excusesVibes.isAggresiv()) {
                excusesVibeTag.add(Tag.AGGRESSIVE);
            }
            if (excusesVibes.isFunny()) {
                excusesVibeTag.add(Tag.FUNNY);
            }
            if (excusesVibes.isSuckUp()) {
                excusesVibeTag.add(Tag.SUCK_UP);
            }
        }
        return excusesVibeTag;
    }

    private Collection<? extends Tag> getSexTag(Context context) {
        Set<Tag> sexTag = new HashSet<>();
        if (context.getSex() != null) {
            switch (context.getSex()) {
            case MALE:
                sexTag.add(Tag.MALE);
                break;
            case FEMALE:
                sexTag.add(Tag.FEMALE);
                break;
            }
        }
        return sexTag;
    }

    private static boolean between(int i, int minValueInclusive, int maxValue) {
        return (i >= minValueInclusive && i < maxValue);
    }
}
