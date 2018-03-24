package com.deusgmbh.xcusatio.ui.profilsettings;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * 
 * This is the ProfileSettings class for the User Interface. This class creates
 * the ProfileSettings tab with all of its components
 * 
 * @author Lars.Dittert@de.ibm.com
 *
 */

public class ProfileSettings extends BorderPane {
	private static final String AGE_LABEL = "Alter";
	private static final String SEX_LABEL = "Geschlecht";
	private static final String LOCATION_LABEL = "Standort";
	private static final String CALENDAR_LABEL = "Google-Kalendar";
	private static final String CALENDAR_BUTTON_LABEL = "Hinzuf\u00fcgen";
	private static final String PROFILE_SETTINGS_PANE_BORDER_COLOR = "#000000";
	private static final String AGE_ZERO_TO_NINETEEN = "0-19";
	private static final String AGE_TWENTY_TO_THIRTYNINE = "20-39";
	private static final String AGE_FOURTY_TO_FIFTYNINE = "40-59";
	private static final String AGE_SIXTY_TO_SEVENTYNINE = "60-79";
	private static final String AGE_EIGHTY_TO_NINETYNINE = "80-99";
	private static final String DEFAULT_TEXT_TEXTFIELD = "PLZ";

	private GridPane gridPane;

	private Label age;
	private Label sex;
	private Label location;
	private Label calendar;

	private ChoiceBox ageChoiceBox;

	private Button calendarButton;
	private TextField address;
	private RadioButton male;
	private RadioButton female;

	public ProfileSettings() {
		this.setStyle("-fx-border-color: " + PROFILE_SETTINGS_PANE_BORDER_COLOR);
		gridPane = new GridPane();

		this.setStyle("-fx-border-color: " + PROFILE_SETTINGS_PANE_BORDER_COLOR);
		this.setCenter(gridPane);

		age = new Label(AGE_LABEL);
		sex = new Label(SEX_LABEL);
		location = new Label(LOCATION_LABEL);
		calendar = new Label(CALENDAR_LABEL);

		/* ageChoiceBox */
		ageChoiceBox = new ChoiceBox(FXCollections.observableArrayList(AGE_ZERO_TO_NINETEEN, AGE_TWENTY_TO_THIRTYNINE,
				AGE_FOURTY_TO_FIFTYNINE, AGE_SIXTY_TO_SEVENTYNINE, AGE_EIGHTY_TO_NINETYNINE));
		/* radioButton */
		ToggleGroup groupRadio = new ToggleGroup();
		male = new RadioButton();
		male.setToggleGroup(groupRadio);
		male.setSelected(true);
		female = new RadioButton();
		female.setToggleGroup(groupRadio);
		/* inputPLZ */
		address = new TextField(DEFAULT_TEXT_TEXTFIELD);
		/* button */
		calendarButton = new Button(CALENDAR_BUTTON_LABEL);

		calendarButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// mainPane.setCenter(paneToFocus);
			}
		});
		GridPane.setConstraints(sex, 0, 0);
		// GridPane.setConstraints(male, 1, 0);
		GridPane.setConstraints(female, 2, 0);
		gridPane.getChildren().addAll(age, sex, location, calendar, ageChoiceBox, male, female, address,
				calendarButton);
	}
}
