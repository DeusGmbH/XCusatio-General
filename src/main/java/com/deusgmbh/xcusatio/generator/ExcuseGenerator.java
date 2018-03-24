package com.deusgmbh.xcusatio.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.deusgmbh.xcusatio.context.Context;
import com.deusgmbh.xcusatio.context.wildcard.Wildcards;
import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.scenarios.Scenario.ScenarioType;
import com.deusgmbh.xcusatio.data.usersettings.ExcusesVibes;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class ExcuseGenerator {

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

    public String getContextBasedExcuse(List<Excuse> excuses, Context context, ScenarioType scenarioType) {
        if (excuses.isEmpty()) {
            throw new IllegalArgumentException("Non-empty excuses list expected");
        }
        List<String> tags = this.getTags(context, scenarioType);
        List<Excuse> contextBasedExcuses = filterByTag(excuses, tags, 10);

        List<Excuse> finalExcuses = contextBasedExcuses.stream()
                .sorted(Excuse.byLastUsed)
                .limit(Math.round(contextBasedExcuses.size() / 2.0))
                .sorted(Excuse.byRating)
                .limit(Math.round(contextBasedExcuses.size() / 2.0))
                .collect(Collectors.toList());

        int randomExcuseId = ThreadLocalRandom.current()
                .nextInt(0, finalExcuses.size());

        return Wildcards.replace(finalExcuses.get(randomExcuseId)
                .getText(), context.getWildcardData());
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
    private List<Excuse> filterByTag(List<Excuse> excuses, List<String> tags, int n) {
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
    private List<String> getTags(Context context, ScenarioType scenarioType) {
        List<String> tags = new ArrayList<>();
        tags.add(this.getScenarioTypeTag(scenarioType));
        tags.addAll(this.getExcusesVibeTags(context));
        tags.addAll(context.getLecturerTags());
        tags.addAll(context.getUserInterests());
        tags.add(this.getAgeGroupTag(context));
        tags.add(this.getSexTag(context));
        // add temperature tags
        // add snow tags
        // add rain tags
        // add windy tag
        // add train delay tag
        // add car accident tag
        // add traffic jam tag
        // add

        // remove all empty "" tags
        return tags.stream()
                .filter(tag -> !tag.isEmpty())
                .collect(Collectors.toList());
    }

    private String getScenarioTypeTag(ScenarioType scenarioType) {
        switch (scenarioType) {
        case WheelOfFurtune:
            return "Glücksrad";
        case LateArrival:
            return "Verspätung";
        case DelayedSubmission:
            return "Verspätete Abgabe";
        default:
            return "";
        }
    }

    private Set<String> getExcusesVibeTags(Context context) {
        Set<String> excusesVibeTags = new HashSet<String>();
        ExcusesVibes excusesVibes = context.getExcusesVibes();
        if (excusesVibes.isAggresiv()) {
            excusesVibeTags.add("Aggresiv");
        }
        if (excusesVibes.isFunny()) {
            excusesVibeTags.add("Witzig");
        }
        if (excusesVibes.isSuckUp()) {
            excusesVibeTags.add("Scheimerisch");
        }
        return excusesVibeTags;
    }

    private String getSexTag(Context context) {
        switch (context.getSex()) {
        case MALE:
            return "Männlich";
        case FEMALE:
            return "Weiblich";
        default:
            return "";
        }
    }

    private String getAgeGroupTag(Context context) {
        int[] ageGroupSeperators = { 18, 21, 16, 30, 35, 45, 50, 60, 70, 80, 90, 100 };
        for (int ageSeperator : ageGroupSeperators) {
            if (context.getAge() < ageSeperator) {
                return "Alter unter " + ageSeperator;
            }
        }
        return "Alter über " + ageGroupSeperators[ageGroupSeperators.length - 1];
    }

}
