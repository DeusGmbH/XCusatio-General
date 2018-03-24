package com.deusgmbh.xcusatio.data.excuses;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Excuse {
    private String text;
    private Set<String> tags;
    private Date lastUsed;
    private int positiveRatings;
    private int negativeRatings;

    public Excuse(String text) {
        this(text, new HashSet<String>());
    }

    public Excuse(String text, Set<String> tags) {
        this(text, tags, null, 0, 0);
    }

    public Excuse(String text, Set<String> tags, Date lastUsed) {
        this(text, tags, lastUsed, 0, 0);
    }

    public Excuse(String text, Set<String> tags, Date lastUsed, int positiveRatings, int negativeRatings) {
        super();
        this.text = text;
        this.tags = tags;
        this.lastUsed = lastUsed;
        this.positiveRatings = positiveRatings;
        this.negativeRatings = negativeRatings;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public int getPositiveRatings() {
        return positiveRatings;
    }

    public void setPositiveRatings(int positiveRatings) {
        this.positiveRatings = positiveRatings;
    }

    public int getNegativeRatings() {
        return negativeRatings;
    }

    public void setNegativeRatings(int negativeRatings) {
        this.negativeRatings = negativeRatings;
    }

    public static Comparator<Excuse> byRating = new Comparator<Excuse>() {
        @Override
        public int compare(Excuse e1, Excuse e2) {
            int ratingE1 = e1.getPositiveRatings() - e1.getNegativeRatings();
            int ratingE2 = e2.getPositiveRatings() - e2.getNegativeRatings();
            return ratingE1 - ratingE2;
        }
    };

    public static Comparator<Excuse> byLastUsed = new Comparator<Excuse>() {
        @Override
        public int compare(Excuse e1, Excuse e2) {
            Date lastUsedE1 = e1.getLastUsed();
            Date lastUsedE2 = e2.getLastUsed();
            return lastUsedE1.compareTo(lastUsedE2);
        }
    };
}
