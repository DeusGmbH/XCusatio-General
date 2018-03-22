package com.deusgmbh.xcusatio.ui.editor;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EditEntryPane extends VBox {
    private static final String EDITOR_TITLE = "Editor";
    private static final String SUBMIT_EDITED_ENTRY_BTN_LABEL = "Speichern";

    protected Label editorTitleLabel;
    protected Button submitEditedEntryBtn;

    public EditEntryPane() {
        editorTitleLabel = new Label(EDITOR_TITLE);
        submitEditedEntryBtn = new Button(SUBMIT_EDITED_ENTRY_BTN_LABEL);

        this.getChildren().addAll(editorTitleLabel);
    }
}
