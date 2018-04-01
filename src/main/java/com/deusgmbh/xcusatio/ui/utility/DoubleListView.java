package com.deusgmbh.xcusatio.ui.utility;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

/**
 * 
 * This class creates two ListViews, which can shift Entrys between each other.
 * Also it provides a method for getting the items of the left ListView
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 * @param <T>
 *            This type is used for the type of the ListView and it belonging
 *            items
 */

public class DoubleListView<T> extends BorderPane {
    private static final int BUTTON_PANE_WIDTH = 31;
    private static final int CELL_SIZE = 30;
    private static final int MAX_TABLE_ROW = 8;
    private static final double LIST_VIEW_WIDTH_MULTIPLIER = 0.45;
    private static final double LIST_VIEW_HEIGHT_MULTIPLIER = 0.9;

    private ListView<T> leftListView;
    private ListView<T> rightListView;
    private ShiftButtonPane shiftButtonPane;

    public DoubleListView(List<T> leftList, List<T> rightList) {
        this(FXCollections.observableArrayList(leftList), FXCollections.observableArrayList(rightList));
    }

    public DoubleListView(ObservableList<T> leftList, ObservableList<T> rightList) {
        int maxListSize = leftList.size() + rightList.size();
        leftListView = new ListView<T>(leftList);
        leftListView.setPrefHeight(maxListSize > MAX_TABLE_ROW ? MAX_TABLE_ROW * CELL_SIZE : maxListSize * CELL_SIZE);
        rightListView = new ListView<T>(rightList);
        rightListView.setPrefHeight(maxListSize > MAX_TABLE_ROW ? MAX_TABLE_ROW * CELL_SIZE : maxListSize * CELL_SIZE);

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
                T selectedItem = moveFrom.getSelectionModel()
                        .getSelectedItem();
                if (selectedItem != null) {
                    moveFrom.getItems()
                            .remove(selectedItem);
                    moveTo.getItems()
                            .add(selectedItem);
                }
            }
        };
    }

    public List<T> getLeftListItems() {
        return new ArrayList<T>(leftListView.getItems());
    }

    public void bindSize(DoubleBinding widthProperty, DoubleBinding heightProperty) {
        leftListView.prefWidthProperty()
                .bind(widthProperty.multiply(LIST_VIEW_WIDTH_MULTIPLIER));
        rightListView.prefWidthProperty()
                .bind(widthProperty.multiply(LIST_VIEW_WIDTH_MULTIPLIER));
        leftListView.prefHeightProperty()
                .bind(heightProperty.multiply(LIST_VIEW_HEIGHT_MULTIPLIER));
        rightListView.prefHeightProperty()
                .bind(heightProperty.multiply(LIST_VIEW_HEIGHT_MULTIPLIER));
    }
}
