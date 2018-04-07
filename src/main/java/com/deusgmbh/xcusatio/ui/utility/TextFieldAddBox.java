package com.deusgmbh.xcusatio.ui.utility;

import java.util.List;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

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
    private static final double ADD_BOX_WIDTH_MULTIPLIER = 0.7;
    private static final double TEXT_FIELD_WIDTH_MULTIPLIER = 1;
    private static final int SHIFT_BUTTON_WIDTH = 31;

    private TextField textField;
    private ListView<String> addBox;

    public TextFieldAddBox(String text, List<String> list) {
        this(text, FXCollections.observableArrayList(list));
    }

    public TextFieldAddBox(String text, ObservableList<String> possibleAddsList) {
        textField = new TextField(text);
        textField.prefWidthProperty()
                .bind(this.widthProperty()
                        .multiply(TEXT_FIELD_WIDTH_MULTIPLIER));
        textField.maxWidthProperty()
                .bind(this.widthProperty()
                        .multiply(TEXT_FIELD_WIDTH_MULTIPLIER));

        addBox = new ListView<String>(possibleAddsList);
        addBox.prefWidthProperty()
                .bind(this.widthProperty()
                        .multiply(ADD_BOX_WIDTH_MULTIPLIER));
        addBox.maxWidthProperty()
                .bind(this.widthProperty()
                        .multiply(ADD_BOX_WIDTH_MULTIPLIER));
        addBox.setPrefHeight(possibleAddsList.size() * CELL_SIZE);

        StackPane addBoxStackPane = new StackPane(addBox);
        addBoxStackPane.setAlignment(Pos.CENTER_LEFT);

        Button shiftEntryButton = new Button("+");
        shiftEntryButton.getStyleClass()
                .add("shift-button");

        shiftEntryButton.setMinWidth(SHIFT_BUTTON_WIDTH);
        shiftEntryButton.setOnAction(shiftEntryAction);

        StackPane shiftEntryButtonStackPane = new StackPane(shiftEntryButton);
        shiftEntryButtonStackPane.prefWidthProperty()
                .bind(this.widthProperty()
                        .multiply(1 - ADD_BOX_WIDTH_MULTIPLIER));
        shiftEntryButtonStackPane.setAlignment(Pos.CENTER);

        this.setTop(textField);
        this.setRight(shiftEntryButtonStackPane);
        this.setCenter(addBoxStackPane);
        this.getStyleClass()
                .add("text-field-add-box");
    }

    private EventHandler<ActionEvent> shiftEntryAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (addBox.getSelectionModel()
                    .getSelectedItems()
                    .size() > 0) {
                textField.setText(textField.getText() + " " + addBox.getSelectionModel()
                        .getSelectedItem() + " ");
            }
        }
    };

    public String getText() {
        return textField.getText();
    }

    public void setPlaceholder(String placeholder) {
        textField.setPromptText(placeholder);
    }

    public void bindSize(DoubleBinding doubleBinding) {
        this.addBox.prefHeightProperty()
                .bind(doubleBinding);
    }
}
