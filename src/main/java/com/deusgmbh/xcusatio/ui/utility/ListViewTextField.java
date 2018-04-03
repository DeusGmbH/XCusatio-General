package com.deusgmbh.xcusatio.ui.utility;

import java.util.ArrayList;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

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
    private static final double BUTTON_MULTIPLIER = 0.4;

    private ListView<String> availableItemListView;
    private TextField addItemTextField;
    private StackPane addItemPane;
    private StackPane removeItemPane;

    public ListViewTextField(List<String> lectures) {
        this(FXCollections.observableArrayList(lectures));
    }

    public ListViewTextField(ObservableList<String> availableItemList) {
        HBox availableItemsPane = new HBox();
        HBox newItemsPane = new HBox();

        availableItemListView = new ListView<String>(availableItemList);

        addItemTextField = new TextField();

        Button addItemButton = new Button(ADD_ITEM_BUTTON_LABEL);
        Button removeItemButton = new Button(REMOVE_BUTTON_LABEL);

        addItemButton.setOnAction(addItemAction);
        addItemPane = new StackPane(addItemButton);
        addItemPane.setAlignment(Pos.CENTER);
        removeItemButton.setOnAction(removeItemAction);
        removeItemPane = new StackPane(removeItemButton);
        removeItemPane.setAlignment(Pos.CENTER);

        availableItemsPane.getChildren()
                .addAll(availableItemListView, removeItemPane);
        newItemsPane.getChildren()
                .addAll(addItemTextField, addItemPane);

        this.setTop(availableItemsPane);
        this.setCenter(newItemsPane);
        this.getStyleClass()
                .add("list-view-text-field");
    }

    private EventHandler<ActionEvent> addItemAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (addItemTextField.getText() != null && addItemTextField.getText()
                    .trim()
                    .length() > 0) {
                availableItemListView.getItems()
                        .add(addItemTextField.getText());
            }
        }
    };

    private EventHandler<ActionEvent> removeItemAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (availableItemListView.getSelectionModel()
                    .getSelectedItems()
                    .size() > 0) {
                availableItemListView.getItems()
                        .remove(availableItemListView.getSelectionModel()
                                .getSelectedIndex());
            }
        }
    };

    public List<String> getItems() {
        return new ArrayList<String>(availableItemListView.getItems());
    }

    public void bindSize(DoubleBinding widthProperty, DoubleBinding heightProperty) {
        availableItemListView.minWidthProperty()
                .bind(widthProperty.multiply(1 - BUTTON_MULTIPLIER));
        availableItemListView.maxWidthProperty()
                .bind(widthProperty.multiply(1 - BUTTON_MULTIPLIER));
        addItemTextField.minWidthProperty()
                .bind(widthProperty.multiply(1 - BUTTON_MULTIPLIER));
        addItemTextField.maxWidthProperty()
                .bind(widthProperty.multiply(1 - BUTTON_MULTIPLIER));
        addItemPane.minWidthProperty()
                .bind(widthProperty.multiply(BUTTON_MULTIPLIER));
        addItemPane.maxWidthProperty()
                .bind(widthProperty.multiply(BUTTON_MULTIPLIER));
        removeItemPane.minWidthProperty()
                .bind(widthProperty.multiply(BUTTON_MULTIPLIER));
        removeItemPane.maxWidthProperty()
                .bind(widthProperty.multiply(BUTTON_MULTIPLIER));
        availableItemListView.prefHeightProperty()
                .bind(heightProperty);
    }

}
