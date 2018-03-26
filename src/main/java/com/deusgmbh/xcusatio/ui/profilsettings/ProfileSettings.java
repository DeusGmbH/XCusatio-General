package com.deusgmbh.xcusatio.ui.profilsettings;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.Sex;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
	private static final String AGE_LABEL_TEXT = "Geburtstag";
	private static final String SEX_LABEL_TEXT = "Geschlecht";
	private static final String SEX_MALE_LABEL_TEXT = "m\u00e4nnlich";
	private static final String SEX_FEMALE_LABEL_TEXT = "weiblich";
	private static final String LOCATION_LABEL_TEXT = "Standort";
	private static final String CALENDAR_LABEL_TEXT = "Google-Kalendar";
	private static final String CALENDAR_BUTTON_LABEL_TEXT = "Hinzuf\u00fcgen";

	private static final String SUBMIT_BUTTON_LABEL = "Speichern";
	private static final String TITLE_LABEL_TEXT = "Profileinstellungen";

	private Supplier<UserSettings> userSettingsSupplier;

	private GridPane profileFormPane;

	private DatePicker birthdayDatePicker;
	private HBox sexTogglePane;
	private HBox addressPane;

	private Button calendarButton;
	private Button saveProfileBtn;
	private TextField streetNameTextField;
	private TextField streetNumTextField;
	private TextField zipTextField;
	private TextField cityTextField;
	private RadioButton setSexMale;
	private RadioButton setSexFemale;

	public ProfileSettings() {
		profileFormPane = new GridPane();
		sexTogglePane = new HBox();
		addressPane = new HBox();

		Label ageLabel = new Label(AGE_LABEL_TEXT);
		Label sexLabel = new Label(SEX_LABEL_TEXT);
		Label sexMaleLabel = new Label(SEX_MALE_LABEL_TEXT);
		Label sexFemaleLabel = new Label(SEX_FEMALE_LABEL_TEXT);
		Label locationLabel = new Label(LOCATION_LABEL_TEXT);
		Label calendarLabel = new Label(CALENDAR_LABEL_TEXT);

		birthdayDatePicker = new DatePicker();
		birthdayDatePicker.setShowWeekNumbers(false);

		ToggleGroup groupRadio = new ToggleGroup();
		setSexMale = new RadioButton();
		setSexMale.setToggleGroup(groupRadio);
		setSexMale.setSelected(userSettingsSupplier.get().getSex() == Sex.Male);
		setSexFemale = new RadioButton();
		setSexFemale.setSelected(userSettingsSupplier.get().getSex() == Sex.Female);
		setSexFemale.setToggleGroup(groupRadio);
		sexTogglePane.getChildren().addAll(sexMaleLabel, setSexMale, sexFemaleLabel, setSexFemale);
		streetNameTextField = new TextField(userSettingsSupplier.get().getHome().getStreetName());
		streetNumTextField = new TextField(userSettingsSupplier.get().getHome().getStreetnum());
		cityTextField = new TextField(userSettingsSupplier.get().getHome().getCity());
		zipTextField = new TextField(userSettingsSupplier.get().getHome().getZip());
		addressPane.getChildren().addAll(streetNameTextField, streetNumTextField, cityTextField, zipTextField);
		calendarButton = new Button(CALENDAR_BUTTON_LABEL_TEXT);
		saveProfileBtn = new Button(SUBMIT_BUTTON_LABEL);

		addNodesToPane(ageLabel, birthdayDatePicker, sexLabel, sexTogglePane, locationLabel, addressPane, calendarLabel,
				calendarButton);
		this.getChildren().add(profileFormPane);
		this.setAlignment(Pos.CENTER);
	}

	private void addNodesToPane(Node... nodesToAdd) {
		profileFormPane.getChildren().clear();
		Label titleLabel = new Label(TITLE_LABEL_TEXT);
		profileFormPane.add(titleLabel, 0, 0);
		final AtomicInteger counter = new AtomicInteger();
		Arrays.asList(nodesToAdd).stream().forEach(node -> {
			int columnIndex = counter.get() % 2;
			int rowIndex = (int) Math.floor(counter.get() / 2d) + 1;
			profileFormPane.add(node, columnIndex, rowIndex);
			counter.incrementAndGet();
		});
		profileFormPane.add(this.saveProfileBtn, 1, (int) Math.ceil(nodesToAdd.length / 2d) + 1);
	}

	private LocalDate getDateFromBirthdayPicker() {
		LocalDate localDate = birthdayDatePicker.getValue();
		return localDate;
	}

	public void registerUserSettingsSupplier(Supplier<UserSettings> userSettingsSupplier) {
		this.userSettingsSupplier = userSettingsSupplier;
	}
}
