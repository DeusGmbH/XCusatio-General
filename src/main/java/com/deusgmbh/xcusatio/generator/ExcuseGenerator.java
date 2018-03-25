package com.deusgmbh.xcusatio.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.deusgmbh.xcusatio.context.Context;
import com.deusgmbh.xcusatio.context.wildcard.Wildcards;
import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.data.usersettings.ExcusesVibes;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class ExcuseGenerator {
    private final String NO_EXCUSE_FOUND = "Es konnte leider keine Ausrede f�r dieses Scenario gefunden werden. Im Editor k�nnen diese aber hinzugef�gt werden.";

    public ExcuseGenerator() {
        super();
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

    public String getContextBasedExcuse(List<Excuse> excuses, Context context, Scenario scenario) {
        if (excuses.isEmpty()) {
            throw new IllegalArgumentException("Non-empty excuse list expected");
        }

        int minExcuses = excuses.size() > 8 ? excuses.size() / 2 : 4;

        List<Tag> tags = this.getTags(context);
        List<Excuse> contextBasedExcuses = filterByTag(filterByScenario(excuses, scenario), tags, minExcuses);

        List<Excuse> finalExcuses = contextBasedExcuses.stream()
                .sorted(Excuse.byLastUsed)
                .limit(Math.round(contextBasedExcuses.size() / 2.0))
                .sorted(Excuse.byRating)
                .limit(Math.round(contextBasedExcuses.size() / 2.0))
                .collect(Collectors.toList());

        if (finalExcuses.isEmpty()) {
            return this.NO_EXCUSE_FOUND;
        }

        int randomExcuseId = ThreadLocalRandom.current()
                .nextInt(0, finalExcuses.size());

        return Wildcards.replace(finalExcuses.get(randomExcuseId)
                .getText(), context.getApiContext());
    }

    private List<Excuse> filterByScenario(List<Excuse> excuses, Scenario scenario) {
        return excuses.stream()
                .filter(excuse -> excuse.getScenarioType()
                        .equals(scenario.getScenarioType()))
                .collect(Collectors.toList());
    }

    /**
     * This method filters the given excuses by the given tags in a way that the
     * result contains at least n excuses
     * 
     * @param excuses
     *            to be filtered
     * @param tags
     *            to be filtered by. The first tag in the list has the highest
     *            priority and the last one the lowest
     * @param n
     *            minimal amount of excuses to be returned
     * @return filtered excuses
     */
    private List<Excuse> filterByTag(List<Excuse> excuses, List<Tag> tags, int n) {
        if (tags.isEmpty()) {
            return excuses;
        }

        List<Excuse> filteredExcuses = excuses.stream()
                .filter(excuse -> excuse.getTags()
                        .contains(tags.remove(0)))
                .collect(Collectors.toList());

        // make sure that at least n excuses are returned
        if (filteredExcuses.size() < n) {
            return excuses;
        } else {
            return filterByTag(filteredExcuses, tags, n);
        }
    }

    /**
     * Generates a list of tags based on context and scenario type
     * 
     * @return
     */
    private List<Tag> getTags(Context context) {
        List<Tag> tags = new ArrayList<>();
        if (context == null) {
            return tags;
        }

        tags.addAll(this.getExcusesVibeTags(context));
        tags.addAll(this.getLecturerTags(context));
        tags.addAll(this.getSexTag(context));
        // add temperature tags
        // add snow tags
        // add rain tags
        // add windy tag
        // add train delay tag
        // add car accident tag
        // add traffic jam tag

        // add ageGroup tag

        return tags.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    private Collection<Tag> getLecturerTags(Context context) {
        if (context.getLecturer() != null && context.getLecturer()
                .getTags() != null) {
            return context.getLecturer()
                    .getTags();
        }
        return new HashSet<>();
    }

    private Set<Tag> getExcusesVibeTags(Context context) {
        Set<Tag> excusesVibeTags = new HashSet<>();
        ExcusesVibes excusesVibes = context.getManuellExcusesVibes();
        if (excusesVibes != null) {
            if (excusesVibes.isAggresiv()) {
                excusesVibeTags.add(Tag.AGGRESSIVE);
            }
            if (excusesVibes.isFunny()) {
                excusesVibeTags.add(Tag.FUNNY);
            }
            if (excusesVibes.isSuckUp()) {
                excusesVibeTags.add(Tag.SUCKUP);
            }
        }
        return excusesVibeTags;
    }

    private Set<Tag> getSexTag(Context context) {
        Set<Tag> sexTags = new HashSet<>();
        if (context.getSex() != null) {
            switch (context.getSex()) {
            case MALE:
                sexTags.add(Tag.MALE);
            case FEMALE:
                sexTags.add(Tag.FEMALE);
            }
        }
        return sexTags;
    }
}
