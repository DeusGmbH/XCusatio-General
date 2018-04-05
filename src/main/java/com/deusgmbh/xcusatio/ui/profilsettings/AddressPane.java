package com.deusgmbh.xcusatio.ui.profilsettings;

import com.deusgmbh.xcusatio.data.usersettings.Address;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AddressPane extends VBox {
    private static final String STREET_NAME_PLACEHOLDER = "Straﬂe";
    private static final String STREET_NUM_PLACEHOLDER = "Hausnummer";
    private static final String CITY_PLACEHOLDER = "Stadt";
    private static final String ZIP_PLACEHOLDER = "Postleitzahl";
    private TextField streetNameTextField;
    private TextField streetNumTextField;
    private TextField zipTextField;
    private TextField cityTextField;

    public AddressPane(Address userAddress) {
        streetNameTextField = new TextField(userAddress.getStreetName());
        streetNameTextField.setPromptText(STREET_NAME_PLACEHOLDER);
        streetNumTextField = new TextField(userAddress.getStreetnum());
        streetNumTextField.setPromptText(STREET_NUM_PLACEHOLDER);
        cityTextField = new TextField(userAddress.getCity());
        cityTextField.setPromptText(CITY_PLACEHOLDER);
        zipTextField = new TextField(userAddress.getZip());
        zipTextField.setPromptText(ZIP_PLACEHOLDER);

        this.getChildren()
                .addAll(streetNameTextField, streetNumTextField, cityTextField, zipTextField);
    }

    public Address getAdress() {
        return new Address(streetNameTextField.getText(), streetNumTextField.getText(), zipTextField.getText(),
                cityTextField.getText());
    }
}
