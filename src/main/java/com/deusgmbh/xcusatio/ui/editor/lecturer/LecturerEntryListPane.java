package com.deusgmbh.xcusatio.ui.editor.lecturer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.ui.editor.EntryListPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LecturerEntryListPane extends EntryListPane {
    private static final String REQ_COLUMN_NAME_VARNAME = "name";
    private static final String REQ_COLUMN_LECTURES_VARNAME = "lectures";
    private static final String REQ_COLUMN_TAGS_VARNAME = "tags";
    private static final String REQ_COLUMN_NAME_TITLE = "Name";
    private static final String REQ_COLUMN_LECTURES_TITLE = "Vorlesungen";
    private static final String REQ_COLUMN_TAGS_TITLE = "Tags";
    protected static final String NEW_LECTURER_DEFAULT_TEXT = "Dozentenname";

    private TableView<Lecturer> entryTable;

    public LecturerEntryListPane() {
        super();
        entryTable = new TableView<Lecturer>();
        entryTable.setEditable(false);
        setTableColumns(getRequiredTableColumns());

        this.getChildren().add(0, entryTable);
    }

    private void setTableColumns(HashMap<String, String> columnList) {
        columnList.entrySet().stream().forEach(entry -> {
            TableColumn<Lecturer, String> column = new TableColumn<Lecturer, String>(entry.getValue().toString());
            column.setCellValueFactory(new PropertyValueFactory<Lecturer, String>(entry.getKey().toString()));
            column.prefWidthProperty().bind(entryTable.widthProperty().multiply(1d / columnList.size()));
            entryTable.getColumns().add(column);
        });
    }

    private HashMap<String, String> getRequiredTableColumns() {
        HashMap<String, String> requiredColumns = new HashMap<String, String>();
        requiredColumns.put(REQ_COLUMN_NAME_VARNAME, REQ_COLUMN_NAME_TITLE);
        requiredColumns.put(REQ_COLUMN_LECTURES_VARNAME, REQ_COLUMN_LECTURES_TITLE);
        requiredColumns.put(REQ_COLUMN_TAGS_VARNAME, REQ_COLUMN_TAGS_TITLE);
        return requiredColumns;
    }

    public void setTableContent(List<Lecturer> entryList) {
        entryTable.setItems((ObservableList<Lecturer>) FXCollections.observableArrayList(entryList));
    }

    public void createRemoveEntryListener(Consumer<Lecturer> removeEntry) {
        super.removeSelectedEntry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                removeEntry.accept(entryTable.getSelectionModel().getSelectedItem());
            }
        });
    }

    public void createAddEntryListener(Consumer<Lecturer> addEntry) {
        super.addEntry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                Lecturer newExcuse = new Lecturer(NEW_LECTURER_DEFAULT_TEXT, new ArrayList<String>(),
                        new HashSet<String>());
                addEntry.accept(newExcuse);
            }
        });
    }

    public void registerOnSelectEntryEvent(Consumer<Lecturer> selectEntryEvent) {
        entryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectEntryEvent.accept(newSelection);
            }
        });
    }
}
