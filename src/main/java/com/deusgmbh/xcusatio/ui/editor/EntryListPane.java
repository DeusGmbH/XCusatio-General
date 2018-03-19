package com.deusgmbh.xcusatio.ui.editor;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EntryListPane<T> extends VBox {
    @SuppressWarnings("rawtypes")
    private TableView entryTable;

    private Button removeSelectedEntry;
    private Button addEntry;

    public EntryListPane() {
        entryTable = new TableView<T>();
        entryTable.setEditable(false);

        HBox entryOptionsPane = new HBox();
        removeSelectedEntry = new Button("Entfernen");
        addEntry = new Button("Hinzufügen");
        entryOptionsPane.getChildren().addAll(removeSelectedEntry, addEntry);

        this.getChildren().addAll(entryTable, entryOptionsPane);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void createRemoveEntryListener(Consumer removeEntry) {
        removeSelectedEntry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                removeEntry.accept((T) entryTable.getSelectionModel().getSelectedItem());
            }
        });
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void createAddEntryListener(Consumer addEntry, Object newObject) {
        removeSelectedEntry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                addEntry.accept(newObject);
            }
        });
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void setTableColumns(HashMap<String, String> tableColumnList) {
        tableColumnList.entrySet().stream().forEach(entry -> {
            TableColumn column = new TableColumn(entry.getValue().toString());
            column.setCellValueFactory(new PropertyValueFactory<T, String>(entry.getKey().toString()));
            column.prefWidthProperty().bind(entryTable.widthProperty().multiply(1d / tableColumnList.size()));
            entryTable.getColumns().add(column);
        });
    }

    @SuppressWarnings("unchecked")
    public void setTableContent(List<?> entryList) {
        entryTable.setItems(FXCollections.observableArrayList(entryList));
    }
}
