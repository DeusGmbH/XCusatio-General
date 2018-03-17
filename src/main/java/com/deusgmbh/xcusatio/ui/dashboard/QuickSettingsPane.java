package com.deusgmbh.xcusatio.ui.dashboard;

import java.util.ArrayList;

import com.deusgmbh.xcusatio.ui.utility.LabeledToggleSwitch;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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
    private static final String RECENTLY_USED_TITLE = "Zuletzt generierte Ausreden";
    private static final String AUTO_MOOD_TOGGLE_LABEL = "Automatisch";
    private static final String MOOD_HUMOR_LABEL = "Humor";
    private static final String MOOD_AGGRESSION_LABEL = "Aggression";
    private static final String MOOD_FAWN_LABEL = "Schleimen";
    private static final String QUICK_SETTINGS_PANE_BORDER_COLOR = "#000000";
    private static final int LIMIT_RU_ENTRIES = 10;

    private VBox moodRegulatorPane;
    private VBox recentlyUsedPane;

    private ArrayList<String> recentlyUsedList;

    private LabeledToggleSwitch autoMoodToggle;
    private LabeledToggleSwitch moodHumorToggle;
    private LabeledToggleSwitch moodAggressionToggle;
    private LabeledToggleSwitch moodFawnToggle;

    public QuickSettingsPane() {
        this(new ArrayList<String>());
    }

    public QuickSettingsPane(ArrayList<String> RUList) {
        recentlyUsedList = RUList;

        initMoodRegulatorPane();
        initRecentlyUsedPane();

        this.setStyle("-fx-border-color: " + QUICK_SETTINGS_PANE_BORDER_COLOR);

        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);

        this.getChildren().add(moodRegulatorPane);
        this.getChildren().add(separator);
        this.getChildren().add(recentlyUsedPane);
    }

    private void initMoodRegulatorPane() {
        moodRegulatorPane = new VBox();
        Label moodRegulatorLabel = new Label(MOOD_REGULATOR_TITLE);
        autoMoodToggle = new LabeledToggleSwitch(AUTO_MOOD_TOGGLE_LABEL);
        moodHumorToggle = new LabeledToggleSwitch(MOOD_HUMOR_LABEL);
        moodAggressionToggle = new LabeledToggleSwitch(MOOD_AGGRESSION_LABEL);
        moodFawnToggle = new LabeledToggleSwitch(MOOD_FAWN_LABEL);

        moodRegulatorPane.getChildren().addAll(moodRegulatorLabel, autoMoodToggle, moodHumorToggle,
                moodAggressionToggle, moodFawnToggle);
    }

    private void initRecentlyUsedPane() {
        recentlyUsedPane = new VBox();
        Label recentlyUsedLabel = new Label(RECENTLY_USED_TITLE);
        recentlyUsedPane.getChildren().add(recentlyUsedLabel);

        recentlyUsedList.stream().limit(LIMIT_RU_ENTRIES).forEach(entry -> {
            Label tmpLabel = new Label(entry);
            recentlyUsedPane.getChildren().add(tmpLabel);
        });
    }

    public boolean getAutoMoodToggle() {
        return this.autoMoodToggle.getToggleState();
    }

    public boolean getMoodHumorToggle() {
        return this.moodHumorToggle.getToggleState();
    }

    public boolean getMoodAggressionToggle() {
        return this.moodAggressionToggle.getToggleState();
    }

    public boolean getMoodFawnToggle() {
        return this.moodFawnToggle.getToggleState();
    }

    public void setRUList(ArrayList<String> ruList) {
        this.recentlyUsedList = ruList;
    }
}
