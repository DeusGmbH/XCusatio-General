package com.deusgmbh.xcusatio.ui.profilsettings;

import com.deusgmbh.xcusatio.data.usersettings.UserSettings.Sex;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class SexTogglePane extends HBox {
    private static final String SEX_MALE_LABEL_TEXT = "m\u00e4nnlich";
    private static final String SEX_FEMALE_LABEL_TEXT = "weiblich";

    private RadioButton setSexMale;
    private RadioButton setSexFemale;

    public SexTogglePane(Sex userSex) {

        Label sexMaleLabel = new Label(SEX_MALE_LABEL_TEXT);
        Label sexFemaleLabel = new Label(SEX_FEMALE_LABEL_TEXT);

        ToggleGroup groupRadio = new ToggleGroup();
        setSexMale = new RadioButton();
        setSexMale.setToggleGroup(groupRadio);
        setSexMale.setSelected(userSex == Sex.MALE);
        setSexFemale = new RadioButton();
        setSexFemale.setSelected(userSex == Sex.FEMALE);
        setSexFemale.setToggleGroup(groupRadio);

        this.getStyleClass()
                .add("sex-toggle-pane");
        this.getChildren()
                .addAll(sexMaleLabel, setSexMale, sexFemaleLabel, setSexFemale);

    }

    public Sex getSex() {
        return setSexMale.isSelected() ? Sex.MALE : Sex.FEMALE;
    }

}
