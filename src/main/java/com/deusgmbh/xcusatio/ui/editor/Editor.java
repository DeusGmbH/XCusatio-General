package com.deusgmbh.xcusatio.ui.editor;

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
    private EditorTab excuseEditor;
    private EditorTab lecturerEditor;

    public Editor() {
        excuseEditor = new EditorTab<Excuse>("Ausreden");
        lecturerEditor = new EditorTab<Lecturer>("Dozenten");

        this.getTabs().add(excuseEditor);
        this.getTabs().add(lecturerEditor);
        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }
}
