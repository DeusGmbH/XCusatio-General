<<<<<<< HEAD
package com.deusgmbh.xcusatio.ui.editor;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * 
 * This class is a base class for ExcuseEntryListPane and LecturerEntryListPane
 * and should provide the part of the constructor, which is the same for both
 * classes. This
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class EntryListPane extends VBox {
    protected Button removeSelectedEntry;
    protected Button addEntry;

    public EntryListPane() {
        HBox entryOptionsPane = new HBox();
        removeSelectedEntry = new Button("Entfernen");
        addEntry = new Button("Hinzufügen");

        Pane spacer = new Pane();
        entryOptionsPane.setHgrow(spacer, Priority.ALWAYS);

        removeSelectedEntry.getStyleClass().add("entry-list-button");
        addEntry.getStyleClass().add("entry-list-button");

        entryOptionsPane.getChildren().addAll(removeSelectedEntry, spacer, addEntry);

        this.getChildren().addAll(entryOptionsPane);
    }
}
=======
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * This class is a base class for ExcuseEntryListPane and LecturerEntryListPane
 * and should provide the part of the constructor, which is the same for both
 * classes. This
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public abstract class EntryListPane<T> extends VBox {
    protected Button removeEntryButton;
    protected Button addEntryButton;
    protected TableView<T> entryTable;
    private Consumer<IntUnaryOperator> itemSelectionIdUpdateEvent;

    public EntryListPane() {
        HBox entryOptionsPane = new HBox();
        removeEntryButton = new Button("Entfernen");
        addEntryButton = new Button("Hinzufügen");
        entryOptionsPane.getChildren()
                .addAll(removeEntryButton, addEntryButton);

        this.getChildren()
                .add(entryOptionsPane);

        entryTable = new TableView<>();
        entryTable.setEditable(false);

        this.setTableColumns(this.getRequiredTableColumns());

        this.createRemoveEntryButtonListener();
        this.createAddEntryButtonListener();

        this.getChildren()
                .add(0, entryTable);
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
                                    .subtract(1));
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
        removeEntryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                entryTable.getItems()
                        .remove(entryTable.getSelectionModel()
                                .getSelectedIndex());
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
>>>>>>> refs/remotes/origin/master
