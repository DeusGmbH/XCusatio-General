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
import com.deusgmbh.xcusatio.data.usersettings.ExcuseVibes;

import javafx.collections.ObservableList;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class ExcuseGenerator {
    private final String NO_EXCUSE_FOUND = "Es konnte leider keine Ausrede f�r dieses Scenario gefunden werden. Im Editor k�nnen diese aber hinzugef�gt werden.";
    private Wildcards wildcards;
    private ObservableList<Excuse> excuseList;

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
        // List<Excuse> excuses = (List<Excuse>) originalExcuses;
        if (excuses == null || excuses.isEmpty()) {
            throw new IllegalArgumentException("Excuses must not be null and not empty");
        }
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null");
        }
        if (!scenario.isExcuseType()) {
            throw new IllegalArgumentException("Scenario is not ");
        }

        List<Tag> Tag = this.getTag(context);
        List<Excuse> contextBasedExcuses = filterByTag(
                filterByValidWildcards(filterByScenario(excuses, scenario), context), Tag);

        List<Excuse> finalExcuses = contextBasedExcuses.stream()
                .sorted(Excuse.byLastUsed.reversed())
                .limit((int) Math.ceil(contextBasedExcuses.size() / 2.0))
                .sorted(Excuse.byRating)
                .collect(Collectors.toList());

        finalExcuses = finalExcuses.stream()
                .limit((int) Math.ceil(finalExcuses.size() * 0.8))
                .collect(Collectors.toList());

        if (finalExcuses.isEmpty()) {
            return new Excuse(NO_EXCUSE_FOUND, scenario.getScenarioType());
        }

        int randomExcuseId = ThreadLocalRandom.current()
                .nextInt(0, finalExcuses.size());

        return finalExcuses.get(randomExcuseId);
    }

    private List<Excuse> filterByValidWildcards(List<Excuse> excuses, Context context) {
        return excuses.stream()
                .filter(excuse -> wildcards.isValidContext(excuse.getText(), context.getApiContext()))
                .collect(Collectors.toList());
    }

    private List<Excuse> filterByScenario(List<Excuse> excuses, Scenario scenario) {
        return excuses.stream()
                .filter(Excuse.byScenario(scenario))
                .collect(Collectors.toList());
    }

    /**
     * Filters all excuses by the Tag
     * 
     * @param excuses
     *            to be filtered
     * @param tag
     *            to be filtered by
     * @return all excuses that contain all the giving Tag
     */
    private List<Excuse> filterByTag(List<Excuse> excuses, List<Tag> tag) {
        return excuses.stream()
                .filter(Excuse.containsAllTags(tag))
                .collect(Collectors.toList());
    }

    /**
     * Generates a list of Tag based on context and scenario type
     * 
     * @return
     */
    private List<Tag> getTag(Context context) {
        List<Tag> tags = new ArrayList<>();
        if (context == null) {
            return tags;
        }

        tags.addAll(this.getExcusesVibeTag(context));
        tags.addAll(this.getLecturerTag(context));
        tags.addAll(this.getSexTag(context));
        // add temperature Tag
        // add snow Tag
        // add rain Tag
        // add windy tag
        // add train delay tag
        // add car accident tag
        // add traffic jam tag

        // add ageGroup tag

        return tags.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    private Collection<Tag> getLecturerTag(Context context) {
        if (context.getLecturer() != null && context.getLecturer()
                .getTags() != null) {
            return context.getLecturer()
                    .getTags();
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
                excusesVibeTag.add(Tag.SUCKUP);
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
}
