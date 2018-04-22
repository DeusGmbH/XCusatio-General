package com.deusgmbh.xcusatio.ui.editor;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * 
 * This class is a base class for ExcuseEntryListPane and LecturerEntryListPane
 * and should provide the part of the constructor, which is the same for both
 * classes. This
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public abstract class EntryListPane<T> extends BorderPane {

    private static final String ENTRY_LIST_STYLESHEET_PATH = "assets/entry_list_pane_stylesheet.css";

    protected TableView<T> entryTable;
    protected Button addEntryButton;
    protected Button removeSelectedEntryButton;
    private Consumer<IntUnaryOperator> itemSelectionIdUpdateEvent;

    public EntryListPane() {
        HBox entryOptionsPane = new HBox();

        entryTable = new TableView<>();
        entryTable.setEditable(false);
        entryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        this.setTableColumns(this.getRequiredTableColumns());

        removeSelectedEntryButton = new Button("Entfernen");
        addEntryButton = new Button("Hinzuf\u00fcgen");

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        removeSelectedEntryButton.getStyleClass()
                .add("entry-list-button");
        addEntryButton.getStyleClass()
                .add("entry-list-button");

        entryOptionsPane.getChildren()
                .addAll(removeSelectedEntryButton, spacer, addEntryButton);

        this.setCenter(entryTable);
        this.setBottom(entryOptionsPane);

        this.createRemoveEntryButtonListener();
        this.createAddEntryButtonListener();

        this.getStylesheets()
                .add(ENTRY_LIST_STYLESHEET_PATH);
    }

    private void setTableColumns(HashMap<String, String> columnList) {
        columnList.entrySet()
                .stream()
                .forEach(entry -> {
                    TableColumn<T, String> column = new TableColumn<T, String>(entry.getValue()
                            .toString());
                    column.setCellValueFactory(new PropertyValueFactory<T, String>(entry.getKey()
                            .toString()));
                    column.prefWidthProperty()
                            .bind(entryTable.widthProperty()
                                    .multiply(1d / columnList.size())
                                    .subtract(40));
                    entryTable.getColumns()
                            .add(column);
                });
    }

    public void setTableContent(ObservableList<T> entryList) {

        entryTable.setItems(entryList);
        entryTable.getItems()
                .addListener(new ListChangeListener<T>() {
                    @Override
                    public void onChanged(javafx.collections.ListChangeListener.Change<? extends T> c) {
                        while (c.next()) {
                            if (c.wasPermutated()) {
                                itemSelectionIdUpdateEvent.accept(c::getPermutation);
                            }
                        }
                    }
                });
    }

    public void registerItemSelectionIdUpdate(Consumer<IntUnaryOperator> itemSelectionIdUpdateEvent) {
        this.itemSelectionIdUpdateEvent = itemSelectionIdUpdateEvent;
    }

    private void createRemoveEntryButtonListener() {
        removeSelectedEntryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                if (entryTable.getSelectionModel()
                        .getSelectedItems()
                        .size() > 0) {
                    if (entryTable.getItems()
                            .size() > 1) {
                        entryTable.getItems()
                                .remove(entryTable.getSelectionModel()
                                        .getSelectedIndex());
                    }
                }
            }
        });
    }

    public void registerOnSelectEntryEvent(BiConsumer<Integer, ObservableList<T>> selectEntryEvent) {
        entryTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectEntryEvent.accept(entryTable.getSelectionModel()
                                .getSelectedIndex(), entryTable.getItems());
                    }
                });
    }

    private void createAddEntryButtonListener() {
        this.addEntryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                entryTable.getItems()
                        .add(createNewItem());
                entryTable.getSelectionModel()
                        .selectLast();
            }
        });
    }

    abstract protected HashMap<String, String> getRequiredTableColumns();

    abstract protected T createNewItem();
}