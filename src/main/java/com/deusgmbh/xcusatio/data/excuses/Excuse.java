package com.deusgmbh.xcusatio.data.excuses;

import java.util.HashSet;
import java.util.Set;;

public class Excuse {
    private String text;
    private Set<String> tags;

    public Excuse(String text) {
        this(text, new HashSet<String>());
    }

    public Excuse(String text, Set<String> tags) {
        this.text = text;
        this.tags = tags;
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

}
