package com.deusgmbh.xcusatio.ui.editor.excuse;

import java.util.ArrayList;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class TextFieldAddBox extends BorderPane {
    private static final int CELL_SIZE = 30;
    private static final double ADD_BOX_WIDTH_MULTIPLIER = 0.45;
    private static final double TEXT_FIELD_WIDTH_MULTIPLIER = 0.45;
    private static final int SHIFT_BUTTON_WIDTH = 31;

    private TextField textField;
    private ListView<String> addBox;

    public TextFieldAddBox(String text, Set<String> set) {
        this(text, FXCollections.observableArrayList(new ArrayList<String>(set)));
    }

    public TextFieldAddBox(String text, ObservableList<String> possibleAddsList) {
        textField = new TextField(text);
        textField.maxWidthProperty().bind(this.widthProperty().multiply(TEXT_FIELD_WIDTH_MULTIPLIER));
        addBox = new ListView<String>(possibleAddsList);
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
            if (addBox.getSelectionModel() != null) {
                textField.setText(textField.getText() + " $" + addBox.getSelectionModel().getSelectedItem() + " ");
            }
        }
    };

    public String getText() {
        return textField.getText();
    }
}
