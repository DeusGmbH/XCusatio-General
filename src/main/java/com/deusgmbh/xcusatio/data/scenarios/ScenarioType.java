package com.deusgmbh.xcusatio.data.scenarios;

public enum ScenarioType {
    WHEELOFFORTUNE, DELAYED_SUBMISSION, LATE_ARRIVAL, THUMBGESTURE;

    @Override
    public String toString() {
        switch (this) {
        case WHEELOFFORTUNE:
            return "Gl\u00fccksrad";
        case DELAYED_SUBMISSION:
            return "Versp\u00e4tete Abgabe";
        case LATE_ARRIVAL:
            return "Zusp\u00e4tkommen";
        case THUMBGESTURE:
            return "Daumengeste";

        default:
            return super.toString();
        }
    }
}
