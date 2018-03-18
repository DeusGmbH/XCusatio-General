package com.deusgmbh.xcusatio.ui.dashboard;

import java.util.ArrayList;

import com.deusgmbh.xcusatio.ui.utility.LabeledToggleSwitch;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * 
 * This is the QuickSettingsPane class for the User Interface. This class
 * creates the MoodRegulatorPane and the RecentlyUsedPane.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class QuickSettingsPane extends VBox {
    private static final String MOOD_REGULATOR_TITLE = "Stimmungsregulator";
    private static final String AUTO_MOOD_TOGGLE_LABEL = "Automatisch";
    private static final String MOOD_HUMOR_LABEL = "Humor";
    private static final String MOOD_AGGRESSION_LABEL = "Aggression";
    private static final String MOOD_FAWN_LABEL = "Schleimen";
    private static final String QUICK_SETTINGS_PANE_BORDER_COLOR = "#000000";

    private VBox moodRegulatorPane;
    private VBox recentlyUsedPane;

    private ArrayList<String> recentlyUsedList;

    private LabeledToggleSwitch autoMoodToggle;
    private LabeledToggleSwitch humorToggle;
    private LabeledToggleSwitch aggressionToggle;
    private LabeledToggleSwitch fawnToggle;

    public QuickSettingsPane() {
        this.setStyle("-fx-border-color: " + QUICK_SETTINGS_PANE_BORDER_COLOR);

        Label moodRegulatorLabel = new Label(MOOD_REGULATOR_TITLE);
        autoMoodToggle = new LabeledToggleSwitch(AUTO_MOOD_TOGGLE_LABEL);
        humorToggle = new LabeledToggleSwitch(MOOD_HUMOR_LABEL);
        aggressionToggle = new LabeledToggleSwitch(MOOD_AGGRESSION_LABEL);
        fawnToggle = new LabeledToggleSwitch(MOOD_FAWN_LABEL);

        this.getChildren().addAll(moodRegulatorLabel, autoMoodToggle, humorToggle, aggressionToggle, fawnToggle);
    }

    public boolean getAutoMoodToggle() {
        return this.autoMoodToggle.getToggleState();
    }

    public boolean getMoodHumorToggle() {
        return this.humorToggle.getToggleState();
    }

    public boolean getMoodAggressionToggle() {
        return this.aggressionToggle.getToggleState();
    }

    public boolean getMoodFawnToggle() {
        return this.fawnToggle.getToggleState();
    }
}
