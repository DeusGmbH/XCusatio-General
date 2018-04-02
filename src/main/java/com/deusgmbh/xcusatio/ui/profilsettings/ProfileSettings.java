package com.deusgmbh.xcusatio.ui.profilsettings;

import java.io.IOException;

import com.deusgmbh.xcusatio.api.calendar.CalendarAPI;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * 
 * This is the ProfileSettings class for the User Interface. This class creates
 * the ProfileSettings tab with all of its components
 * 
 * @author Lars.Dittert@de.ibm.com
 *
 */

public class ProfileSettings extends FlowPane {
    private static final String PROFILE_SETTINGS_STYLESHEET_PATH = "file:assets/profile_settings_stylesheet.css";

    private static final String SEX_TOGGLE_PANE_LABEL_TEXT = "Geschlecht";
    private static final String AGE_LABEL_TEXT = "Geburtstag";
    private static final String LOCATION_LABEL_TEXT = "Standort";
    private static final String CALENDAR_LABEL_TEXT = "Google-Kalendar";

    private static final String SUBMIT_BUTTON_LABEL = "Speichern";
    private static final String TITLE_LABEL_TEXT = "Profileinstellungen";
    private static final double PROFILE_FORM_PANE_WIDTH_MULTIPLIER = 0.5;
    private static final double LABEL_WIDTH_MULTIPLIER = 0.3;
    protected static final String CALENDAR_CONFIG_TITLE = "Kalender Konfiguration";
    protected static final double CALENDAR_CONFIG_WIDTH = 800;
    protected static final double CALENDAR_CONFIG_HEIGHT = 400;
    private static final String CALENDER_BUTTON_REMOVE_TEXT = "Entfernen";
    private static final String CALENDAR_BUTTON_ADD_TEXT = "Authorisieren";

    private ObjectProperty<UserSettings> userSettings;

    private VBox profileFormPane;

    private SexTogglePane sexTogglePane;
    private AddressPane addressPane;
    private DatePicker birthdayDatePicker;

    private Button calendarButton;
    private Button saveProfileBtn;
    private HBox calendarPane;

    public ProfileSettings() {
        profileFormPane = new VBox();
        profileFormPane.getStyleClass()
                .add("profile-settings");
        profileFormPane.prefWidthProperty()
                .bind(this.widthProperty()
                        .multiply(PROFILE_FORM_PANE_WIDTH_MULTIPLIER));

        this.setAlignment(Pos.CENTER);
        this.getStylesheets()
                .add(PROFILE_SETTINGS_STYLESHEET_PATH);
    }

    private void createProfileSettingsForm() {
        Label titleLabel = new Label(TITLE_LABEL_TEXT);
        titleLabel.getStyleClass()
                .add("h1");

        StackPane sexLabel = createFormattedLabel(SEX_TOGGLE_PANE_LABEL_TEXT);
        StackPane ageLabel = createFormattedLabel(AGE_LABEL_TEXT);
        StackPane locationLabel = createFormattedLabel(LOCATION_LABEL_TEXT);
        StackPane calendarLabel = createFormattedLabel(CALENDAR_LABEL_TEXT);

        sexTogglePane = new SexTogglePane(userSettings.getValue()
                .getSex());
        addressPane = new AddressPane(userSettings.getValue()
                .getHome());

        birthdayDatePicker = new DatePicker();
        birthdayDatePicker.setShowWeekNumbers(false);
        birthdayDatePicker.setValue(userSettings.getValue()
                .getBirthdate());

        createCalendarButton(CalendarAPI.hasCredentials());

        saveProfileBtn = new Button(SUBMIT_BUTTON_LABEL);
        this.saveProfileBtn.setOnAction(saveProfileBtnAction);
        StackPane saveBtnPane = new StackPane(saveProfileBtn);
        saveBtnPane.setAlignment(Pos.BOTTOM_RIGHT);

        HBox sexPane = new HBox(sexLabel, sexTogglePane);
        HBox agePane = new HBox(ageLabel, birthdayDatePicker);
        HBox locationPane = new HBox(locationLabel, addressPane);
        calendarPane = new HBox(calendarLabel, calendarButton);

        this.profileFormPane.getChildren()
                .addAll(titleLabel, sexPane, agePane, locationPane, calendarPane, saveBtnPane);
        this.getChildren()
                .add(profileFormPane);
    }

    private StackPane createFormattedLabel(String labelContent) {
        Label label = new Label(labelContent);
        StackPane labelPane = new StackPane(label);

        labelPane.minWidthProperty()
                .bind(this.profileFormPane.widthProperty()
                        .multiply(LABEL_WIDTH_MULTIPLIER));
        labelPane.maxWidthProperty()
                .bind(this.profileFormPane.widthProperty()
                        .multiply(LABEL_WIDTH_MULTIPLIER));
        labelPane.setAlignment(Pos.CENTER_RIGHT);

        return labelPane;
    }

    private EventHandler<ActionEvent> saveProfileBtnAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent arg0) {
            userSettings.set(new UserSettings(null, birthdayDatePicker.getValue(), sexTogglePane.getSex(),
                    addressPane.getAdress()));
        }
    };

    private void createCalendarButton(boolean existingCalendar) {
        calendarButton = new Button();
        calendarButton.getStyleClass()
                .add("calendar-button");
        if (existingCalendar) {
            calendarButton.setText(CALENDER_BUTTON_REMOVE_TEXT);
            calendarButton.setOnAction(removeCalendarAuth);

        } else {
            calendarButton.setText(CALENDAR_BUTTON_ADD_TEXT);
            calendarButton.setOnAction(authorizeCalendar);
        }
    }

    private EventHandler<ActionEvent> removeCalendarAuth = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            try {
                CalendarAPI.removeCredentials();
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("REMOVING AUTHORIZATION ERROR");
                alert.setContentText("Your authorization token could not be successfully removed");
            }
            createCalendarButton(CalendarAPI.hasCredentials());
            calendarPane.getChildren()
                    .set(1, calendarButton);
        }
    };

    private EventHandler<ActionEvent> authorizeCalendar = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            try {
                CalendarAPI.authorize();
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("AUTHORIZATION ERROR");
                alert.setContentText("You could not be successfully authorized");

                alert.showAndWait();
            }
            createCalendarButton(CalendarAPI.hasCredentials());
            calendarPane.getChildren()
                    .set(1, calendarButton);
        }
    };

    public void registerUserSettings(ObjectProperty<UserSettings> userSettings) {
        this.userSettings = userSettings;
        this.createProfileSettingsForm();
    }
}
