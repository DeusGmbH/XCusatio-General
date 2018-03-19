package com.deusgmbh.xcusatio.ui.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;

import javafx.scene.control.TabPane;

/**
 * 
 * This is the Editor class for the User Interface. This class creates the
 * Editor tab with all of its components
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class Editor extends TabPane {
    private static final String DEFAULT_LECTURER_NAME = "Name des Dozenten";
    private static final String DEFAULT_EXCUSE_TEXT = "Ausrede";

    private EditorTab<Excuse> excuseEditor;
    private EditorTab<Lecturer> lecturerEditor;

    public Editor() {
        excuseEditor = new EditorTab<Excuse>("Ausreden");
        lecturerEditor = new EditorTab<Lecturer>("Dozenten");

        this.getTabs().add(excuseEditor);
        this.getTabs().add(lecturerEditor);
        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

    public void setExcuseTableColumns(HashMap<String, String> excuseTableColumnList) {
        excuseEditor.setTableColumns(excuseTableColumnList);
    }

    public void setLecturerTableColumns(HashMap<String, String> lecturerTableColumnList) {
        lecturerEditor.setTableColumns(lecturerTableColumnList);
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
        Excuse newExcuse = new Excuse(DEFAULT_EXCUSE_TEXT, new HashSet<String>());
        excuseEditor.registerAddEntryEvent(addExcuse, newExcuse);
    }

    public void registerAddLecturerEvent(Consumer<Lecturer> addLecturer) {
        Lecturer newLecturer = new Lecturer(DEFAULT_LECTURER_NAME, new ArrayList<String>(), new HashSet<String>());
        lecturerEditor.registerAddEntryEvent(addLecturer, newLecturer);
    }
}
