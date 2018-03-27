package com.deusgmbh.xcusatio.data.excuses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.scenarios.ScenarioType;
import com.deusgmbh.xcusatio.data.tags.Tag;;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Excuse {

    private String text;
    private List<Tag> tags;
    private Date lastUsed;
    private int positiveRating;
    private int negativeRating;
    private ScenarioType scenarioType;

    public Excuse(String text, ScenarioType scenarioType) {
        this(text, scenarioType, new ArrayList<Tag>());
    }

    public Excuse(String text, ScenarioType scenarioType, List<Tag> tags) {
        this(text, scenarioType, tags, null, 0, 0);
    }

    public Excuse(String text, ScenarioType scenarioType, List<Tag> tags, Date lastUsed) {
        this(text, scenarioType, tags, lastUsed, 0, 0);
    }

    public Excuse(String text, ScenarioType scenarioType, List<Tag> tags, Date lastUsed, int positiveRatings,
            int negativeRatings) {
        super();
        this.text = text;
        this.scenarioType = scenarioType;
        this.tags = tags;
        this.lastUsed = lastUsed;
        this.positiveRating = positiveRatings;
        this.negativeRating = negativeRatings;
    }

    public ScenarioType getScenarioType() {
        return scenarioType;
    }

    public void setScenarioType(ScenarioType scenarioType) {
        this.scenarioType = scenarioType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {

        this.tags = tags;
    }

    public Excuse addTag(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public void increasePositiveRating() {
        positiveRating++;
    }

    public void increaseNegativeRating() {
        negativeRating++;
    }

    public int getPositiveRating() {
        return positiveRating;
    }

    public void setPositiveRating(int positiveRatings) {
        this.positiveRating = positiveRatings;
    }

    public int getNegativeRating() {
        return negativeRating;
    }

    public void setNegativeRating(int negativeRatings) {
        this.negativeRating = negativeRatings;
    }

    public static Predicate<Excuse> byScenario(Scenario scenario) {
        return excuse -> excuse.getScenarioType()
                .equals(scenario.getScenarioType());
    }

    public static Comparator<Excuse> byRating = new Comparator<Excuse>() {
        @Override
        public int compare(Excuse e1, Excuse e2) {
            int ratingE1 = e1.getPositiveRating() - e1.getNegativeRating();
            int ratingE2 = e2.getPositiveRating() - e2.getNegativeRating();
            return ratingE1 - ratingE2;
        }
    };

    public static Comparator<Excuse> byLastUsed = new Comparator<Excuse>() {
        @Override
        public int compare(Excuse e1, Excuse e2) {
            Date lastUsedE1 = e1.getLastUsed();
            Date lastUsedE2 = e2.getLastUsed();
            lastUsedE1 = lastUsedE1 == null ? new Date(0) : lastUsedE1;
            lastUsedE2 = lastUsedE2 == null ? new Date(0) : lastUsedE2;
            return lastUsedE1.compareTo(lastUsedE2);
        }
    };
}
