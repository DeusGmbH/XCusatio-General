package com.deusgmbh.xcusatio.ui.profilsettings;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

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

    private Supplier<UserSettings> userSettingsSupplier;

    private GridPane profileFormPane;

    private SexTogglePane sexTogglePane;
    private AddressPane addressPane;
    private DatePicker birthdayDatePicker;

    private Button calendarButton;
    private Button saveProfileBtn;

    public ProfileSettings() {
        profileFormPane = new GridPane();

        Label sexLabelTogglePane = new Label(SEX_TOGGLE_PANE_LABEL_TEXT);
        Label ageLabel = new Label(AGE_LABEL_TEXT);
        Label locationLabel = new Label(LOCATION_LABEL_TEXT);
        Label calendarLabel = new Label(CALENDAR_LABEL_TEXT);

        sexTogglePane = new SexTogglePane(userSettingsSupplier.get().getSex());
        addressPane = new AddressPane(userSettingsSupplier.get().getHome());

        birthdayDatePicker = new DatePicker();
        birthdayDatePicker.setShowWeekNumbers(false);

        calendarButton = new Button(CALENDAR_BUTTON_LABEL_TEXT);
        saveProfileBtn = new Button(SUBMIT_BUTTON_LABEL);

        addNodesToPane(ageLabel, birthdayDatePicker, sexLabelTogglePane, sexTogglePane, locationLabel, addressPane,
                calendarLabel, calendarButton);
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

    public void createEditProfileBtnAction(Consumer<UserSettings> editProfile) {
        this.saveProfileBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                UserSettings editedUserSettingsObj = new UserSettings(null, birthdayDatePicker.getValue(),
                        sexTogglePane.getSex(), addressPane.getAdress());
                editProfile.accept(editedUserSettingsObj);
            }
        });
    }

    public void registerUserSettingsSupplier(Supplier<UserSettings> userSettingsSupplier) {
        this.userSettingsSupplier = userSettingsSupplier;
    }
}
