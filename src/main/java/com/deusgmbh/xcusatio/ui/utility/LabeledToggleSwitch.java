package com.deusgmbh.xcusatio.ui.utility;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * 
 * This is a class for labeled ToggleSwitches. It combines the ToggleSwitch with
 * a matching label.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class LabeledToggleSwitch extends BorderPane {
    private Label label;
    private ToggleSwitch toggleSwitch;

    public LabeledToggleSwitch() {
        this(new String(""), false);
    }

    public LabeledToggleSwitch(String labelText) {
        this(labelText, false);
    }

    public LabeledToggleSwitch(String labelText, boolean activatedState) {
        this(labelText, activatedState, true);
    }

    public LabeledToggleSwitch(String labelText, boolean activatedState, boolean clickable) {
        label = new Label(labelText);
        toggleSwitch = new ToggleSwitch(activatedState, clickable);
        this.setLeft(label);
        this.setRight(toggleSwitch);
        this.getStyleClass().add("toggle-switch");
    }

    public void setLabel(String text) {
        label.setText(text);
    }

    public boolean getToggleState() {
        return toggleSwitch.getToggleState();
    }

    public void registerChangeUserSettingsAction(Runnable changeUserSettingsAction) {
        toggleSwitch.registerChangeUserSettignsAction(changeUserSettingsAction);
    }

    public void setOnChange(Runnable action) {
        toggleSwitch.setOnChange(action);
    }

    public void changeClickable() {
        toggleSwitch.changeClickable();
    }
}
