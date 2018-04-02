package com.deusgmbh.xcusatio.data.tags;

public enum Tag {
    MALE, FEMALE, AGGRESSIVE, FUNNY, SUCKUP, AGE_UNDER_18, AGE_UNDER_21, AGE_UNDER_30, AGE_UNDER_40, AGE_UNDER_50, AGE_OVER_50, CAT, DOG, FOOTBALL, RAINY, STORM, SNOW, HIGH_TRAFFIC, BICICLE, CAR, TRAIN, BUS;

    private String description;
    private TagFilterType filterType;

    Tag(TagFilterType filterType, String description) {
        this.description = description;
        this.filterType = filterType;
    }

    Tag(TagFilterType filterType) {
        this(filterType, null);
    }

    Tag() {
        this(TagFilterType.BOTH, null);
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
