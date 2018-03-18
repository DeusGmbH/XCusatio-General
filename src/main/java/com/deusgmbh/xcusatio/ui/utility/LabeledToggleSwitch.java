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
        this(new String(""));
    }

    public LabeledToggleSwitch(String labelText) {
        label = new Label(labelText);
        toggleSwitch = new ToggleSwitch();
        this.setLeft(label);
        this.setRight(toggleSwitch);
    }

    public void setLabel(String text) {
        label.setText(text);
    }

    public boolean getToggleState() {
        return toggleSwitch.getToggleState();
    }
}
