package com.deusgmbh.xcusatio.ui.editor.lecturer;

import java.util.List;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.ui.editor.EditorTab;

import javafx.collections.ObservableList;

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
        super.editor.setLeft(entryListPane);
        super.editor.setCenter(editEntryPane);
    }

    public void registerTagsSetSupplier(Supplier<List<Tag>> tagsSetSupplier) {
        editEntryPane.registerTagsSetSupplier(tagsSetSupplier);
    }

    private void createEditForm(int selectedLecturerID, ObservableList<Lecturer> lecturers) {
        editEntryPane.createEditForm(selectedLecturerID, lecturers);
    }

    public void registerLecturerSupplier(Supplier<ObservableList<Lecturer>> lecturerSupplier) {
        this.entryListPane.setTableContent(lecturerSupplier.get());
    }

}
