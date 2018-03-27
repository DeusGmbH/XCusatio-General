package com.deusgmbh.xcusatio.ui.utility;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * 
 * This class creates a ListView and a TextField. With the Textfield you can add
 * items to the ListView. Also it provides a method for getting all items of the
 * ListView
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class ListViewTextField extends BorderPane {
    private static final String ADD_ITEM_BUTTON_LABEL = "Hinzufügen";
    private static final String REMOVE_BUTTON_LABEL = "Entfernen";
    private static final double LIST_VIEW_WIDTH_MULTIPLIER = 0.45;
    private static final int CELL_SIZE = 30;
    private static final int LIST_VIEW_HEIGHT_ADDITION = 2;

    private ListView<String> availableItemListView;
    private TextField addItemTextField;

    public ListViewTextField(List<String> lectures) {
        this(FXCollections.observableArrayList(lectures));
    }

    public ListViewTextField(ObservableList<String> availableItemList) {
        VBox availableItemsPane = new VBox();
        VBox newItemsPane = new VBox();

        availableItemListView = new ListView<String>(availableItemList);
        availableItemListView.maxWidthProperty().bind(this.widthProperty().multiply(LIST_VIEW_WIDTH_MULTIPLIER));
        availableItemListView.setPrefHeight(((availableItemList.size() + LIST_VIEW_HEIGHT_ADDITION) * CELL_SIZE));

        addItemTextField = new TextField();
        Button addItemButton = new Button(ADD_ITEM_BUTTON_LABEL);
        Button removeItemButton = new Button(REMOVE_BUTTON_LABEL);

        addItemButton.setOnAction(addItemAction);
        removeItemButton.setOnAction(removeItemAction);

        availableItemsPane.getChildren().addAll(availableItemListView, removeItemButton);
        newItemsPane.getChildren().addAll(addItemTextField, addItemButton);

        this.setLeft(availableItemsPane);
        this.setCenter(newItemsPane);
    }

    private EventHandler<ActionEvent> addItemAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (addItemTextField.getText() != null && addItemTextField.getText() != "") {
                availableItemListView.getItems().add(addItemTextField.getText());
            }
        }
    };

    private EventHandler<ActionEvent> removeItemAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (availableItemListView.getSelectionModel() != null) {
                availableItemListView.getItems().remove(availableItemListView.getSelectionModel().getSelectedIndex());
            }
        }
    };

    public List<String> getItems() {
        return new ArrayList<String>(availableItemListView.getItems());
    }

}
