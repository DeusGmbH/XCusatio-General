package com.deusgmbh.xcusatio.data.tags;

import java.util.Arrays;

public enum Tag {
    MALE(TagFilterType.CONTAINED_IN_EXCUSE),
    FEMALE(TagFilterType.CONTAINED_IN_EXCUSE),
    AGGRESSIVE(TagFilterType.CONTAINED_IN_CONTEXT, true),
    FUNNY(TagFilterType.CONTAINED_IN_CONTEXT, true),
    SUCK_UP(TagFilterType.CONTAINED_IN_CONTEXT, true),
    AGE_UNDER_18(TagFilterType.CONTAINED_IN_EXCUSE),
    AGE_BETWEEN_18_AND_21(TagFilterType.CONTAINED_IN_EXCUSE),
    AGE_BETWEEN_21_AND_30(TagFilterType.CONTAINED_IN_EXCUSE),
    AGE_BETWEEN_30_AND_50(TagFilterType.CONTAINED_IN_EXCUSE),
    AGE_OVER_50(TagFilterType.CONTAINED_IN_EXCUSE),
    CAT(TagFilterType.CONTAINED_IN_CONTEXT, true),
    DOG(TagFilterType.CONTAINED_IN_CONTEXT, true),
    FOOTBALL(TagFilterType.CONTAINED_IN_CONTEXT, true),
    SOFTWARE_ENGINEERING(TagFilterType.CONTAINED_IN_CONTEXT, true),
    AI(TagFilterType.CONTAINED_IN_CONTEXT, true),
    HANGOVER(TagFilterType.CONTAINED_IN_CONTEXT, true),
    RAINY(TagFilterType.CONTAINED_IN_CONTEXT),
    WINDY(TagFilterType.CONTAINED_IN_CONTEXT),
    STORM(TagFilterType.CONTAINED_IN_CONTEXT),
    COLD(TagFilterType.CONTAINED_IN_CONTEXT),
    MILD(TagFilterType.CONTAINED_IN_CONTEXT),
    WARM(TagFilterType.CONTAINED_IN_CONTEXT),
    HOT(TagFilterType.CONTAINED_IN_CONTEXT),
    SNOW(TagFilterType.CONTAINED_IN_CONTEXT),
    HIGH_TRAFFIC(TagFilterType.CONTAINED_IN_CONTEXT),
    BICICLE(TagFilterType.CONTAINED_IN_CONTEXT, true),
    TRAIN_CANCELLED(TagFilterType.CONTAINED_IN_CONTEXT),
    TRAIN_DELAYED(TagFilterType.CONTAINED_IN_CONTEXT),
    TRAIN_HEAVILY_DELAYED(TagFilterType.CONTAINED_IN_CONTEXT),
    BUS(TagFilterType.CONTAINED_IN_CONTEXT),
    ACCIDENT(TagFilterType.CONTAINED_IN_CONTEXT),
    CONSTRUCTION(TagFilterType.CONTAINED_IN_CONTEXT),
    SETLX(TagFilterType.CONTAINED_IN_CONTEXT, true);

    private String description;
    private TagFilterType filterType;

    /**
     * This is used to define what tags can be assigned to a lecturer. Setting
     * this to true will automatically add this tag to the context <b> if </b>
     * the lecturer that has been defined as recipient of the excuse has this
     * tag
     */
    private boolean lecturerPreference;

    Tag(TagFilterType filterType, boolean lecturerPreference, String description) {
        this.description = description;
        this.filterType = filterType;
        this.lecturerPreference = lecturerPreference;
    }

    Tag(TagFilterType filterType, boolean lecturerPreference) {
        this(filterType, lecturerPreference, null);
    }

    Tag(TagFilterType filterType) {
        this(filterType, false, null);
    }

    Tag() {
        this(TagFilterType.NONE);
    }

    public boolean isLecturerPreference() {
        return lecturerPreference;
    }

    public Tag setLecturerPreference(boolean lecturerPreference) {
        this.lecturerPreference = lecturerPreference;
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

    public boolean isNotExcuseVibe() {
        return !this.isExcuseVibe();
    }

    private boolean isExcuseVibe() {
        return Arrays.asList(Tag.FUNNY, Tag.SUCK_UP, Tag.AGGRESSIVE)
                .contains(this);
    }
}
