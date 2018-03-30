package com.deusgmbh.xcusatio.ui.editor;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;

/**
 * 
 * This class is the base class for ExcuseEditorTab and LecturerEditorTab and
 * should provide the part of the constructor, which is for both classes the
 * same. These classes merge the list of entries with the edit form of these
 * into one SplitPane. This opens the possibility to see all available entries,
 * select one and edit this selected entry in the EditEntryPane.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class EditorTab extends Tab {
    protected SplitPane editor;

    public EditorTab(String name) {
        this.setText(name);
        this.setClosable(false);

        editor = new SplitPane();

        this.setContent(editor);
    }
}
