package com.deusgmbh.xcusatio.ui.editor;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class EntryListPane<T> extends VBox {
    private static final String COLUMN_ID_TITLE = "ID";
    private static final String COLUMN_TEXT_TITLE = "Text";
    private static final String COLUMN_TAGS_TITLE = "Tags";

    public <T> EntryListPane() {
        TableView<T> entryTable = new TableView<T>();
        TableColumn id = new TableColumn(COLUMN_ID_TITLE);
        id.setCellValueFactory(new PropertyValueFactory<T, String>("id"));
        TableColumn text = new TableColumn(COLUMN_TEXT_TITLE);
        id.setCellValueFactory(new PropertyValueFactory<T, String>("text"));
        TableColumn tags = new TableColumn(COLUMN_TAGS_TITLE);
        id.setCellValueFactory(new PropertyValueFactory<T, String>("tags"));

        this.getChildren().add(entryTable);
    }
}
