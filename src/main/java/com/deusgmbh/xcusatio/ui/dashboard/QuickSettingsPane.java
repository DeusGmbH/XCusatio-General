package com.deusgmbh.xcusatio.ui.dashboard;

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
    private static final String HUMOR_LABEL = "Humor";
    private static final String AGGRESSION_LABEL = "Aggression";
    private static final String FAWN_LABEL = "Schleimen";
    private static final String QUICK_SETTINGS_PANE_BORDER_COLOR = "#000000";

    private VBox moodRegulatorPane;
    private VBox recentlyUsedPane;

    public QuickSettingsPane() {
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
        LabeledToggleSwitch humor = new LabeledToggleSwitch(HUMOR_LABEL);
        LabeledToggleSwitch aggression = new LabeledToggleSwitch(AGGRESSION_LABEL);
        LabeledToggleSwitch fawn = new LabeledToggleSwitch(FAWN_LABEL);

        moodRegulatorPane.getChildren().addAll(moodRegulatorLabel, humor, aggression, fawn);
    }

    private void initRecentlyUsedPane() {
        recentlyUsedPane = new VBox();
        Label recentlyUsedLabel = new Label(RECENTLY_USED_TITLE);
        recentlyUsedPane.getChildren().add(recentlyUsedLabel);
    }
}
