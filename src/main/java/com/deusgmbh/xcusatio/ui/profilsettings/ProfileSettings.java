package com.deusgmbh.xcusatio.ui.profilsettings;

import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * This is the ProfileSettings class for the User Interface. This class creates
 * the ProfileSettings tab with all of its components
 * 
 * @author Lars.Dittert@de.ibm.com
 *
 */

public class ProfileSettings extends FlowPane {
    private static final String SEX_TOGGLE_PANE_LABEL_TEXT = "Geschlecht";
    private static final String AGE_LABEL_TEXT = "Geburtstag";
    private static final String LOCATION_LABEL_TEXT = "Standort";
    private static final String CALENDAR_LABEL_TEXT = "Google-Kalendar";
    private static final String CALENDAR_BUTTON_LABEL_TEXT = "Hinzuf\u00fcgen";

    private static final String SUBMIT_BUTTON_LABEL = "Speichern";
    private static final String TITLE_LABEL_TEXT = "Profileinstellungen";
    private static final double PROFILE_FORM_PANE_WIDTH_MULTIPLIER = 0.5;
    private static final double LABEL_WIDTH_MULTIPLIER = 0.3;
    protected static final String CALENDAR_CONFIG_TITLE = "Kalender Konfiguration";
    protected static final double CALENDAR_CONFIG_WIDTH = 800;
    protected static final double CALENDAR_CONFIG_HEIGHT = 400;

    private ObjectProperty<UserSettings> userSettings;

    private VBox profileFormPane;

    private SexTogglePane sexTogglePane;
    private AddressPane addressPane;
    private DatePicker birthdayDatePicker;

    private Button calendarButton;
    private Button saveProfileBtn;

    public ProfileSettings() {
        profileFormPane = new VBox();
        profileFormPane.getStyleClass()
                .add("profile-settings");
        profileFormPane.prefWidthProperty()
                .bind(this.widthProperty()
                        .multiply(PROFILE_FORM_PANE_WIDTH_MULTIPLIER));

        this.setAlignment(Pos.CENTER);
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

        calendarButton = new Button(CALENDAR_BUTTON_LABEL_TEXT);
        calendarButton.getStyleClass()
                .add("calendar-button");
        calendarButton.setOnAction(openCalendarConfiguartionWindow);
        // TODO: calendarButton Action
        saveProfileBtn = new Button(SUBMIT_BUTTON_LABEL);
        this.saveProfileBtn.setOnAction(editProfileAction);
        StackPane saveBtnPane = new StackPane(saveProfileBtn);
        saveBtnPane.setAlignment(Pos.BOTTOM_RIGHT);

        HBox sexPane = new HBox(sexLabel, sexTogglePane);
        HBox agePane = new HBox(ageLabel, birthdayDatePicker);
        HBox locationPane = new HBox(locationLabel, addressPane);
        HBox calendarPane = new HBox(calendarLabel, calendarButton);

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

    private EventHandler<ActionEvent> editProfileAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent arg0) {
            userSettings.set(new UserSettings(null, birthdayDatePicker.getValue(), sexTogglePane.getSex(),
                    addressPane.getAdress()));
        }
    };

    private EventHandler<ActionEvent> openCalendarConfiguartionWindow = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            CalendarConfiguration newWindow = new CalendarConfiguration();
            try {
                newWindow.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void registerUserSettings(ObjectProperty<UserSettings> userSettings) {
        this.userSettings = userSettings;
        this.createProfileSettingsForm();
    }
}
