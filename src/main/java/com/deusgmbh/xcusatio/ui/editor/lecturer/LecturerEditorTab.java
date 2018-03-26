package com.deusgmbh.xcusatio.ui.editor.lecturer;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.ui.editor.EditorTab;

/**
 * 
 * This class merges the list of lecturers and the editor for lecturers into one
 * SplitPane. This opens the possibility to see all available lecturers, select
 * one and edit this selected lecturer in the EditEntryPane.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class LecturerEditorTab extends EditorTab {
    private LecturerEntryListPane entryListPane;
    private LecturerEditEntryPane editEntryPane;

    public LecturerEditorTab(String name) {
        super(name);
        entryListPane = new LecturerEntryListPane();
        entryListPane.registerOnSelectEntryEvent(this::createEditForm);
        editEntryPane = new LecturerEditEntryPane();
        super.editor.getItems().addAll(entryListPane, editEntryPane);
    }

    public void setTableContent(List<Lecturer> excuseList) {
        entryListPane.setTableContent(excuseList);
    }

    public void registerRemoveEntryEvent(Consumer<Lecturer> removeEntry) {
        entryListPane.createRemoveEntryListener(removeEntry);
    }

    public void registerAddEntryEvent(Consumer<Lecturer> addEntry) {
        entryListPane.createAddEntryListener(addEntry);
    }

    public void registerChangeEntryEvent(BiConsumer<Integer, Lecturer> editEntry) {
        editEntryPane.createEditBtnAction(editEntry);
    }

    public void registerTagsSetSupplier(Supplier<List<Tag>> tagsSetSupplier) {
        editEntryPane.registerTagsSetSupplier(tagsSetSupplier);
    }

    private void createEditForm(int selectedLecturerID, Lecturer selectedExcuse) {
        editEntryPane.createEditForm(selectedLecturerID, selectedExcuse);
    }
}
