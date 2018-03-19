package com.deusgmbh.xcusatio.ui.editor;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;

public class EditorTab<T> extends Tab {
    private EntryListPane<?> entryListPane;
    private EditEntryPane<?> editEntryPane;

    public EditorTab(String name) {
        this.setText(name);

        SplitPane editor = new SplitPane();
        entryListPane = new EntryListPane<T>();
        editEntryPane = new EditEntryPane<T>();

        editor.getItems().addAll(entryListPane, editEntryPane);

        this.setContent(editor);
    }

    public void setTableColumns(HashMap<String, String> tableColumnList) {
        entryListPane.setTableColumns(tableColumnList);
    }

    public void setTableContent(List<?> entryList) {
        entryListPane.setTableContent(entryList);
    }

    @SuppressWarnings("rawtypes")
    public void registerRemoveEntryEvent(Consumer removeEntry) {
        entryListPane.createRemoveEntryListener(removeEntry);
    }

    @SuppressWarnings("rawtypes")
    public void registerAddEntryEvent(Consumer addEntry, Object newObject) {
        entryListPane.createAddEntryListener(addEntry, newObject);
    }
}
