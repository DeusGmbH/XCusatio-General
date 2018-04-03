package com.deusgmbh.xcusatio.data.tags;

public enum Tag {
    MALE(TagFilterType.CONTAINED_IN_EXCUSE),
    FEMALE(TagFilterType.CONTAINED_IN_EXCUSE),
    AGGRESSIVE(TagFilterType.CONTAINED_IN_CONTEXT),
    FUNNY(TagFilterType.CONTAINED_IN_CONTEXT),
    SUCKUP(TagFilterType.CONTAINED_IN_CONTEXT),
    AGE_UNDER_18(TagFilterType.CONTAINED_IN_EXCUSE),
    AGE_UNDER_21(TagFilterType.CONTAINED_IN_EXCUSE),
    AGE_UNDER_30(TagFilterType.CONTAINED_IN_EXCUSE),
    AGE_UNDER_40(TagFilterType.CONTAINED_IN_EXCUSE),
    AGE_UNDER_50(TagFilterType.CONTAINED_IN_EXCUSE),
    AGE_OVER_50(TagFilterType.CONTAINED_IN_EXCUSE),
    CAT(TagFilterType.CONTAINED_IN_CONTEXT),
    DOG(TagFilterType.CONTAINED_IN_CONTEXT),
    FOOTBALL(TagFilterType.CONTAINED_IN_EXCUSE),
    RAINY(TagFilterType.CONTAINED_IN_CONTEXT),
    STORM(TagFilterType.CONTAINED_IN_CONTEXT),
    SNOW(TagFilterType.CONTAINED_IN_CONTEXT),
    HIGH_TRAFFIC(TagFilterType.CONTAINED_IN_CONTEXT),
    BICICLE(TagFilterType.CONTAINED_IN_CONTEXT),
    CAR(TagFilterType.CONTAINED_IN_CONTEXT),
    TRAIN(TagFilterType.CONTAINED_IN_CONTEXT),
    BUS(TagFilterType.CONTAINED_IN_CONTEXT);

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
