package com.deusgmbh.xcusatio.data.tags;

public enum Tag {
    MALE(TagFilterType.CONTAINED_IN_EXCUSE),
    FEMALE(TagFilterType.CONTAINED_IN_EXCUSE),
    AGGRESSIVE(TagFilterType.CONTAINED_IN_CONTEXT),
    FUNNY(TagFilterType.CONTAINED_IN_CONTEXT),
    SUCK_UP(TagFilterType.CONTAINED_IN_CONTEXT),
    AGE_UNDER_18(TagFilterType.CONTAINED_IN_EXCUSE),
    AGE_BETWEEN_18_AND_21(TagFilterType.CONTAINED_IN_EXCUSE),
    AGE_BETWEEN_21_AND_30(TagFilterType.CONTAINED_IN_EXCUSE),
    AGE_BETWEEN_30_AND_50(TagFilterType.CONTAINED_IN_EXCUSE),
    AGE_OVER_50(TagFilterType.CONTAINED_IN_EXCUSE),
    CAT(TagFilterType.CONTAINED_IN_CONTEXT),
    DOG(TagFilterType.CONTAINED_IN_CONTEXT),
    FOOTBALL(TagFilterType.CONTAINED_IN_EXCUSE),
    RAINY(TagFilterType.CONTAINED_IN_CONTEXT),
    WINDY(TagFilterType.CONTAINED_IN_CONTEXT),
    STORM(TagFilterType.CONTAINED_IN_CONTEXT),
    COLD(TagFilterType.CONTAINED_IN_CONTEXT),
    MILD(TagFilterType.CONTAINED_IN_CONTEXT),
    WARM(TagFilterType.CONTAINED_IN_CONTEXT),
    HOT(TagFilterType.CONTAINED_IN_CONTEXT),
    SNOW(TagFilterType.CONTAINED_IN_CONTEXT),
    HIGH_TRAFFIC(TagFilterType.CONTAINED_IN_CONTEXT),
    BICICLE(TagFilterType.CONTAINED_IN_CONTEXT),
    TRAIN_CANCELLED(TagFilterType.CONTAINED_IN_CONTEXT),
    TRAIN_DELAYED(TagFilterType.CONTAINED_IN_CONTEXT),
    TRAIN_HEAVILY_DELAYED(TagFilterType.CONTAINED_IN_CONTEXT),
    BUS(TagFilterType.CONTAINED_IN_CONTEXT),
    ACCIDENT(TagFilterType.CONTAINED_IN_CONTEXT),
    CONSTRUCTION(TagFilterType.CONTAINED_IN_CONTEXT);

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
        this(TagFilterType.NONE, null);
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

    public boolean inContext() {
        return this.getFilterType()
                .equals(TagFilterType.CONTAINED_IN_CONTEXT)
                || this.getFilterType()
                        .equals(TagFilterType.BOTH);
    }

    public boolean inExcuse() {
        return this.getFilterType()
                .equals(TagFilterType.CONTAINED_IN_EXCUSE)
                || this.getFilterType()
                        .equals(TagFilterType.BOTH);
    }
}
