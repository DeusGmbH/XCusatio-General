package com.deusgmbh.xcusatio.ui.editor;

import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class EditorTab<T> extends Tab {
    private EntryListPane entryListPane;
    private EditEntryPane editEntryPane;

    public <T> EditorTab(String name) {
        this.setText(name);

        BorderPane editor = new BorderPane();
        entryListPane = new EntryListPane<T>();
        editEntryPane = new EditEntryPane<T>();

        editor.setLeft(entryListPane);
        editor.setCenter(editEntryPane);
    }
}
