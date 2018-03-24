package com.deusgmbh.xcusatio.ui.editor.excuse;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.ui.editor.EntryListPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ExcuseEntryListPane extends EntryListPane {
    private static final String REQ_COLUMN_TEXT_VARNAME = "text";
    private static final String REQ_COLUMN_TAGS_VARNAME = "tags";
    private static final String REQ_COLUMN_TEXT_TITLE = "Ausrede";
    private static final String REQ_COLUMN_TAGS_TITLE = "Tags";
    protected static final String NEW_EXCUSE_DEFAULT_TEXT = "Bitte hier neue Ausrede eingeben";

    // @Tobias: Is there a possibility to bring the table view as protected
    // TableView to the parent class?
    // (Generic Type which is first declared with the constructor.)
    // So we could also bring the setTableColumns method to parent class
    public List<Excuse> entryList;
    private TableView<Excuse> entryTable;

    public ExcuseEntryListPane() {
        super();
        entryTable = new TableView<Excuse>();
        entryTable.setEditable(false);
        setTableColumns(getRequiredTableColumns());

        this.getChildren().add(0, entryTable);
    }

    private void setTableColumns(HashMap<String, String> columnList) {
        columnList.entrySet().stream().forEach(entry -> {
            TableColumn<Excuse, String> column = new TableColumn<Excuse, String>(entry.getValue().toString());
            column.setCellValueFactory(new PropertyValueFactory<Excuse, String>(entry.getKey().toString()));
            column.prefWidthProperty().bind(entryTable.widthProperty().multiply(1d / columnList.size()).subtract(1));
            entryTable.getColumns().add(column);
        });
    }

    private HashMap<String, String> getRequiredTableColumns() {
        HashMap<String, String> requiredColumns = new HashMap<String, String>();
        requiredColumns.put(REQ_COLUMN_TEXT_VARNAME, REQ_COLUMN_TEXT_TITLE);
        requiredColumns.put(REQ_COLUMN_TAGS_VARNAME, REQ_COLUMN_TAGS_TITLE);
        return requiredColumns;
    }

    public void setTableContent(List<Excuse> entryList) {
        this.entryList = entryList;
        entryTable.setItems((ObservableList<Excuse>) FXCollections.observableArrayList(this.entryList));
    }

    public void createRemoveEntryListener(Consumer<Excuse> removeEntry) {
        super.removeSelectedEntry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                removeEntry.accept(entryTable.getSelectionModel().getSelectedItem());
            }
        });
    }

    public void createAddEntryListener(Consumer<Excuse> addEntry) {
        super.addEntry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                Excuse newExcuse = new Excuse(NEW_EXCUSE_DEFAULT_TEXT);
                addEntry.accept(newExcuse);
            }
        });
    }

    public void registerOnSelectEntryEvent(BiConsumer<Integer, Excuse> selectEntryEvent) {
        entryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectEntryEvent.accept(this.entryList.indexOf(newSelection), newSelection);
            }
        });
    }
}
