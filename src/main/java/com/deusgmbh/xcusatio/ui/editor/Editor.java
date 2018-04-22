package com.deusgmbh.xcusatio.ui.editor;

import java.util.List;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.ui.editor.excuse.ExcuseEditorTab;
import com.deusgmbh.xcusatio.ui.editor.lecturer.LecturerEditorTab;

import javafx.collections.ObservableList;
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
    private static final String EDITOR_STYLESHEET_PATH = "assets/editor_stylesheet.css";

    private static final String EXCUSE_TAB_TITLE = "Ausreden";
    private static final String LECTURER_TAB_TITLE = "Dozenten";

    private ExcuseEditorTab excuseEditor;
    private LecturerEditorTab lecturerEditor;

    public Editor() {
        excuseEditor = new ExcuseEditorTab(EXCUSE_TAB_TITLE);
        lecturerEditor = new LecturerEditorTab(LECTURER_TAB_TITLE);

        this.getTabs()
                .add(excuseEditor);
        this.getTabs()
                .add(lecturerEditor);

        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        this.getStylesheets()
                .add(EDITOR_STYLESHEET_PATH);
    }

    public void registerTagsSetSupplier(Supplier<List<Tag>> tagsSetSupplier) {
        excuseEditor.registerTagsSetSupplier(tagsSetSupplier);
        lecturerEditor.registerTagsSetSupplier(tagsSetSupplier);
    }

    public void registerWildcardSetSupplier(Supplier<List<String>> wildcardSetSupplier) {
        excuseEditor.registerWildcardSetSupplier(wildcardSetSupplier);
    }

    public void registerExcuseSupplier(Supplier<ObservableList<Excuse>> excuseSupplier) {
        this.excuseEditor.registerExcuseSupplier(excuseSupplier);
    }

    public void registerLecturerSupplier(Supplier<ObservableList<Lecturer>> lecturerSupplier) {
        this.lecturerEditor.registerLecturerSupplier(lecturerSupplier);
    }

}
