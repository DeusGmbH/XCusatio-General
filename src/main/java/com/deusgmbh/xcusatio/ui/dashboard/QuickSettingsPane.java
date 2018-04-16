package com.deusgmbh.xcusatio.ui.dashboard;

import com.deusgmbh.xcusatio.data.usersettings.ExcuseVibes;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.ExcuseVibeMode;
import com.deusgmbh.xcusatio.ui.utility.LabeledToggleSwitch;

import javafx.beans.property.ObjectProperty;
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

    private LabeledToggleSwitch autoMoodToggle;
    private LabeledToggleSwitch humorToggle;
    private LabeledToggleSwitch aggressionToggle;
    private LabeledToggleSwitch fawnToggle;

    private ObjectProperty<UserSettings> userSettings;

    public QuickSettingsPane(ObjectProperty<UserSettings> userSettings) {
        this.userSettings = userSettings;

        Label moodRegulatorLabel = new Label(MOOD_REGULATOR_TITLE);
        moodRegulatorLabel.getStyleClass()
                .add("h3");
        autoMoodToggle = new LabeledToggleSwitch(AUTO_MOOD_TOGGLE_LABEL, userSettings.getValue()
                .getExcuseVibeMode() == ExcuseVibeMode.AUTOMATIC ? true : false);

        humorToggle = new LabeledToggleSwitch(MOOD_HUMOR_LABEL, userSettings.getValue()
                .getExcuseVibes()
                .isFunny(), !autoMoodToggle.getToggleState());

        aggressionToggle = new LabeledToggleSwitch(MOOD_AGGRESSION_LABEL, userSettings.getValue()
                .getExcuseVibes()
                .isAggresiv(), !autoMoodToggle.getToggleState());

        fawnToggle = new LabeledToggleSwitch(MOOD_FAWN_LABEL, userSettings.getValue()
                .getExcuseVibes()
                .isSuckUp(), !autoMoodToggle.getToggleState());

        autoMoodToggle.setOnChange(this::deactivateToggles);
        registerChangeUserSettingsEvents();

        this.getChildren()
                .addAll(moodRegulatorLabel, autoMoodToggle, humorToggle, aggressionToggle, fawnToggle);
    }

    private void registerChangeUserSettingsEvents() {
        autoMoodToggle.registerChangeUserSettingsAction(this::changeUserSettings);
        humorToggle.registerChangeUserSettingsAction(this::changeUserSettings);
        aggressionToggle.registerChangeUserSettingsAction(this::changeUserSettings);
        fawnToggle.registerChangeUserSettingsAction(this::changeUserSettings);
    }

    private void deactivateToggles() {
        humorToggle.changeClickable();
        aggressionToggle.changeClickable();
        fawnToggle.changeClickable();
    }

    private void changeUserSettings() {
        UserSettings oldValue = this.userSettings.getValue();
        ExcuseVibes newExcuseVibes = new ExcuseVibes(this.aggressionToggle.getToggleState(),
                this.humorToggle.getToggleState(), this.fawnToggle.getToggleState());
        this.userSettings.set(new UserSettings(oldValue.getBirthdate(), oldValue.getSex(), oldValue.getHome(),
                autoMoodToggle.getToggleState() ? ExcuseVibeMode.AUTOMATIC : ExcuseVibeMode.MANUALLY, newExcuseVibes));
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
