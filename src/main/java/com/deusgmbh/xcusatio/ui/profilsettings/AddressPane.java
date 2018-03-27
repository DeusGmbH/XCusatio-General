package com.deusgmbh.xcusatio.ui.profilsettings;

import com.deusgmbh.xcusatio.data.usersettings.Address;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class AddressPane extends HBox {
    private TextField streetNameTextField;
    private TextField streetNumTextField;
    private TextField zipTextField;
    private TextField cityTextField;

    public AddressPane(Address userAddress) {
        streetNameTextField = new TextField(userAddress.getStreetName());
        streetNumTextField = new TextField(userAddress.getStreetnum());
        cityTextField = new TextField(userAddress.getCity());
        zipTextField = new TextField(userAddress.getZip());
        this.getChildren().addAll(streetNameTextField, streetNumTextField, cityTextField, zipTextField);
    }

    public Address getAdress() {
        return new Address(streetNameTextField.getText(), streetNumTextField.getText(), zipTextField.getText(),
                cityTextField.getText());
    }
}
