package com.deusgmbh.xcusatio.ui.utility;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * 
 * This class creates a TextField and a ListView. You can add entries of the
 * ListView as String to the TextField for adding important parameters
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class TextFieldAddBox extends BorderPane {
    private static final int CELL_SIZE = 30;
    private static final double ADD_BOX_WIDTH_MULTIPLIER = 0.45;
    private static final double TEXT_FIELD_WIDTH_MULTIPLIER = 0.45;
    private static final int SHIFT_BUTTON_WIDTH = 31;

    private TextField textField;
    private ListView<String> addBox;

    public TextFieldAddBox(String text, List<String> list) {
        this(text, FXCollections.observableArrayList(list));
    }

    public TextFieldAddBox(String text, ObservableList<String> possibleAddsList) {
        textField = new TextField(text);
        textField.prefWidthProperty().bind(this.widthProperty().multiply(TEXT_FIELD_WIDTH_MULTIPLIER));
        textField.maxWidthProperty().bind(this.widthProperty().multiply(TEXT_FIELD_WIDTH_MULTIPLIER));
        addBox = new ListView<String>(possibleAddsList);
        addBox.prefWidthProperty().bind(this.widthProperty().multiply(ADD_BOX_WIDTH_MULTIPLIER));
        addBox.maxWidthProperty().bind(this.widthProperty().multiply(ADD_BOX_WIDTH_MULTIPLIER));
        addBox.setPrefHeight(possibleAddsList.size() * CELL_SIZE);

        Button shiftEntryButton = new Button("<");
        shiftEntryButton.setMinWidth(SHIFT_BUTTON_WIDTH);
        shiftEntryButton.setOnAction(shiftEntryAction);

        this.setLeft(textField);
        this.setCenter(shiftEntryButton);
        this.setRight(addBox);
    }

    private EventHandler<ActionEvent> shiftEntryAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (addBox.getSelectionModel() != null && addBox.getSelectionModel().getSelectedItem() != null) {
                textField.setText(textField.getText() + " " + addBox.getSelectionModel().getSelectedItem() + " ");
            }
        }
    };

    public String getText() {
        return textField.getText();
    }
}
