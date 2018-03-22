package com.deusgmbh.xcusatio.ui.editor;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EntryListPane extends VBox {
    protected Button removeSelectedEntry;
    protected Button addEntry;

    public EntryListPane() {
        HBox entryOptionsPane = new HBox();
        removeSelectedEntry = new Button("Entfernen");
        addEntry = new Button("Hinzufügen");
        entryOptionsPane.getChildren().addAll(removeSelectedEntry, addEntry);

        this.getChildren().add(entryOptionsPane);
    }
}
