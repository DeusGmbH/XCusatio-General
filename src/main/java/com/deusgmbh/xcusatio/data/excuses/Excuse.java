package com.deusgmbh.xcusatio.data.excuses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Excuse {

    private String text;
    private List<String> tags;
    private Date lastUsed;
    private int positiveRatings;
    private int negativeRatings;

    public Excuse(String text) {
        this(text, new ArrayList<String>());
    }

    public Excuse(String text, List<String> tags) {
        this(text, tags, null, 0, 0);
    }

    public Excuse(String text, List<String> tags, Date lastUsed) {
        this(text, tags, lastUsed, 0, 0);
    }

    public Excuse(String text, List<String> tags, Date lastUsed, int positiveRatings, int negativeRatings) {
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
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
}
