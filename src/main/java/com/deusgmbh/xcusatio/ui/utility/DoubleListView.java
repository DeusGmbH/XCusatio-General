package com.deusgmbh.xcusatio.ui.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class DoubleListView<T> extends BorderPane {
    private static final double LIST_VIEW_WIDTH_MULTIPLIER = 0.4;
    private static final int BUTTON_PANE_WIDTH = 31;
    private static final int CELL_SIZE = 30;

    private ListView<T> leftListView;
    private ListView<T> rightListView;
    private ShiftButtonPane shiftButtonPane;

    public DoubleListView(Set<T> leftSet, Set<T> rightSet) {
        this(FXCollections.observableArrayList(new ArrayList<T>(leftSet)),
                FXCollections.observableArrayList(new ArrayList<T>(rightSet)));
    }

    public DoubleListView(ObservableList<T> leftList, ObservableList<T> rightList) {
        leftListView = new ListView<T>(leftList);
        leftListView.maxWidthProperty().bind(this.widthProperty().multiply(LIST_VIEW_WIDTH_MULTIPLIER));
        leftListView.setPrefHeight((leftList.size() + rightList.size()) * CELL_SIZE);
        rightListView = new ListView<T>(rightList);
        rightListView.maxWidthProperty().bind(this.widthProperty().multiply(LIST_VIEW_WIDTH_MULTIPLIER));
        rightListView.setPrefHeight((leftList.size() + rightList.size()) * CELL_SIZE);

        shiftButtonPane = new ShiftButtonPane(createMoveItemAction(leftListView, rightListView),
                createMoveItemAction(rightListView, leftListView));
        shiftButtonPane.setMinWidth(BUTTON_PANE_WIDTH);

        this.setLeft(leftListView);
        this.setCenter(shiftButtonPane);
        this.setRight(rightListView);
    }

    private EventHandler<ActionEvent> createMoveItemAction(ListView<T> moveFrom, ListView<T> moveTo) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                T selectedItem = moveFrom.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    moveFrom.getItems().remove(selectedItem);
                    moveTo.getItems().add(selectedItem);
                }
            }
        };
    }

    public Set<T> getLeftListItems() {
        return new HashSet<T>(leftListView.getItems());
    }
}
