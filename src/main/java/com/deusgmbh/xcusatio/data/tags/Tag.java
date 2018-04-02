package com.deusgmbh.xcusatio.data.tags;

public class Tag {
    private TagText text;
    private String description;
    private TagFilterType filterType;

    public Tag(TagText text, String description, TagFilterType filterType) {
        super();
        this.text = text;
        this.description = description;
        this.filterType = filterType;
    }

    public Tag(TagText text) {
        this(text, null, TagFilterType.BOTH);
    }

    public TagText getText() {
        return text;
    }

    public Tag setText(TagText text) {
        this.text = text;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Tag setDescription(String description) {
        this.description = description;
        return this;
    }

    public TagFilterType getFilterType() {
        return filterType;
    }

    public Tag setFilterType(TagFilterType filterType) {
        this.filterType = filterType;
        return this;
    }

}
