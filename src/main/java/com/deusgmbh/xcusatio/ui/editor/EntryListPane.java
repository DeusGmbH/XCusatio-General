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
