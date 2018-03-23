package com.deusgmbh.xcusatio.ui.editor;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ListCellView<T> extends BorderPane {
    private ListView<T> leftListCell;
    private ListView<T> rightListCell;

    public ListCellView(ObservableList<T> leftList, ObservableList<T> rightList) {
        int maxListCellSize = leftList.size() + rightList.size();
        leftListCell = new ListView<T>(leftList);
        leftListCell.prefWidthProperty().bind(this.widthProperty().multiply(0.4));
        leftListCell.setPrefHeight((leftList.size() + rightList.size()) * 30);
        rightListCell = new ListView<T>(rightList);
        rightListCell.prefWidthProperty().bind(this.widthProperty().multiply(0.4));
        rightListCell.setPrefHeight((leftList.size() + rightList.size()) * 30);

        VBox moveButtonPane = createMoveButtonPane();

        this.setLeft(leftListCell);
        this.setCenter(moveButtonPane);
        this.setRight(rightListCell);
    }

    private VBox createMoveButtonPane() {
        VBox moveButtonPane = new VBox();
        moveButtonPane.setAlignment(Pos.CENTER);
        Button leftToRight = new Button(">");
        Button rightToLeft = new Button("<");

        leftToRight.setOnAction(createMoveItemAction(leftListCell, rightListCell));
        rightToLeft.setOnAction(createMoveItemAction(rightListCell, leftListCell));

        moveButtonPane.getChildren().addAll(leftToRight, rightToLeft);

        return moveButtonPane;
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
}
