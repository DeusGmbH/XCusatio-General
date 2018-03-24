package com.deusgmbh.xcusatio.ui.profilsettings;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * 
 * This is the ProfileSettings class for the User Interface. This class creates
 * the ProfileSettings tab with all of its components
 * 
 * @author Lars.Dittert@de.ibm.com
 *
 */

public class ProfileSettings extends FlowPane {
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
	private static final String SUBMIT_BUTTON = "Speichern";
	private static final String TITLE_LABEL = "Profileinstellungen";

	private GridPane gridPane;

	private Label titleLabel;
	private Label age;
	private Label sex;
	private Label location;
	private Label calendar;

	private ChoiceBox ageChoiceBox;
	private HBox sexTogglePane;

	private Button calendarButton;
	private Button submitEditedEntryBtn;
	private TextField address;
	private RadioButton male;
	private RadioButton female;

	public ProfileSettings() {
		gridPane = new GridPane();
		sexTogglePane = new HBox();

		this.setStyle("-fx-border-color: " + PROFILE_SETTINGS_PANE_BORDER_COLOR);
		this.gridPane.setStyle("-fx-border-color: " + PROFILE_SETTINGS_PANE_BORDER_COLOR);

		titleLabel = new Label(TITLE_LABEL);
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
		sexTogglePane.getChildren().addAll(male, female);
		/* inputPLZ */
		address = new TextField(DEFAULT_TEXT_TEXTFIELD);
		/* button */
		calendarButton = new Button(CALENDAR_BUTTON_LABEL);
		submitEditedEntryBtn = new Button(SUBMIT_BUTTON);

		addNodesToPane(age, ageChoiceBox, sex, sexTogglePane, location, address, calendar, calendarButton);
		this.getChildren().add(gridPane);
		this.setAlignment(Pos.CENTER);
	}

	protected void addNodesToPane(Node... nodesToAdd) {
		gridPane.getChildren().clear();
		gridPane.add(this.titleLabel, 0, 0);
		final AtomicInteger counter = new AtomicInteger();
		Arrays.asList(nodesToAdd).stream().forEach(node -> {
			int columnIndex = counter.get() % 2;
			int rowIndex = (int) Math.floor(counter.get() / 2d) + 1;
			gridPane.add(node, columnIndex, rowIndex);
			counter.incrementAndGet();
		});
		gridPane.add(this.submitEditedEntryBtn, 1, (int) Math.ceil(nodesToAdd.length / 2d) + 1);
	}
}
