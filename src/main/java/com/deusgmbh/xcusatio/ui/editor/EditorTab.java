package com.deusgmbh.xcusatio.ui.editor;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;

public class EditorTab extends Tab {
    protected SplitPane editor;

    public EditorTab(String name) {
        this.setText(name);
        this.setClosable(false);

        editor = new SplitPane();
        this.setContent(editor);
    }
}
