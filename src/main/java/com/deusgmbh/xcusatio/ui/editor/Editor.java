package com.deusgmbh.xcusatio.ui.editor;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.ui.editor.excuse.ExcuseEditorTab;
import com.deusgmbh.xcusatio.ui.editor.lecturer.LecturerEditorTab;

import javafx.scene.control.TabPane;

/**
 * 
 * This is the Editor class for the User Interface. This class creates the
 * Editor tab with all of its components. This includes an ExcuseEditorTab and
 * an LecturerEditorTab. In these are each a EntryListPane and an EditEntryPane.
 * With the EntryListPane you can see all available entries (excuses/lecturers),
 * add new ones or remove unneeded entries. Also you can edit these in the
 * EditEntryPane by selecting one and save it, which forces an update of the
 * entry.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class Editor extends TabPane {
    private static final String EXCUSE_TAB_TITLE = "Ausreden";
    private static final String LECTURER_TAB_TITLE = "Dozenten";

    private ExcuseEditorTab excuseEditor;
    private LecturerEditorTab lecturerEditor;

    public Editor() {
        excuseEditor = new ExcuseEditorTab(EXCUSE_TAB_TITLE);
        lecturerEditor = new LecturerEditorTab(LECTURER_TAB_TITLE);

        this.getTabs().add(excuseEditor);
        this.getTabs().add(lecturerEditor);

        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

    public void setExcuseTableContent(List<Excuse> excuseList) {
        excuseEditor.setTableContent(excuseList);
    }

    public void setLecturerTableContent(List<Lecturer> lecturerList) {
        lecturerEditor.setTableContent(lecturerList);
    }

    public void registerRemoveExcuseEvent(Consumer<Excuse> removeExcuse) {
        excuseEditor.registerRemoveEntryEvent(removeExcuse);
    }

    public void registerRemoveLecturerEvent(Consumer<Lecturer> removeLecturer) {
        lecturerEditor.registerRemoveEntryEvent(removeLecturer);
    }

    public void registerAddExcuseEvent(Consumer<Excuse> addExcuse) {
        excuseEditor.registerAddEntryEvent(addExcuse);
    }

    public void registerAddLecturerEvent(Consumer<Lecturer> addLecturer) {
        lecturerEditor.registerAddEntryEvent(addLecturer);
    }

    public void registerEditExcuseEvent(BiConsumer<Integer, Excuse> editExcuse) {
        excuseEditor.registerChangeEntryEvent(editExcuse);
    }

    public void registerEditLecturerEvent(BiConsumer<Integer, Lecturer> editLecturer) {
        lecturerEditor.registerChangeEntryEvent(editLecturer);
    }

    public void registerTagsSetSupplier(Supplier<List<Tag>> tagsSetSupplier) {
        excuseEditor.registerTagsSetSupplier(tagsSetSupplier);
        lecturerEditor.registerTagsSetSupplier(tagsSetSupplier);
    }

    public void registerWildcardSetSupplier(Supplier<List<String>> wildcardSetSupplier) {
        excuseEditor.registerWildcardSetSupplier(wildcardSetSupplier);
    }
}
