package com.deusgmbh.xcusatio.ui.editor.excuse;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.ui.editor.EditorTab;

public class ExcuseEditorTab extends EditorTab {
    private ExcuseEntryListPane entryListPane;
    private ExcuseEditEntryPane editEntryPane;

    public ExcuseEditorTab(String name) {
        super(name);
        entryListPane = new ExcuseEntryListPane();
        entryListPane.registerOnSelectEntryEvent(this::createEditForm);
        editEntryPane = new ExcuseEditEntryPane();
        super.editor.getItems().addAll(entryListPane, editEntryPane);
    }

    public void setTableContent(List<Excuse> excuseList) {
        entryListPane.setTableContent(excuseList);
    }

    public void registerRemoveEntryEvent(Consumer<Excuse> removeEntry) {
        entryListPane.createRemoveEntryListener(removeEntry);
    }

    public void registerAddEntryEvent(Consumer<Excuse> addEntry) {
        entryListPane.createAddEntryListener(addEntry);
    }

    public void registerChangeEntryEvent(BiConsumer<Excuse, Excuse> editEntry) {
        editEntryPane.createEditBtnAction(editEntry);
    }

    private void createEditForm(Excuse selectedExcuse) {
        editEntryPane.createEditForm(selectedExcuse);
    }
}
