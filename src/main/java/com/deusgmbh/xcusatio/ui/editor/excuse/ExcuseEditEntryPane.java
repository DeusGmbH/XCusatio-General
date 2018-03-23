package com.deusgmbh.xcusatio.ui.editor.excuse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.ui.editor.EditEntryPane;
import com.deusgmbh.xcusatio.ui.editor.ListCellView;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class ExcuseEditEntryPane extends EditEntryPane {
    private static final String LAST_USED_LABEL_TEXT = "Zuletzt verwendet:";
    private static final String EXCUSE_CONTENT_LABEL_TEXT = "Ausrede:";
    private static final String TAGS_LABEL_TEXT = "Tags:";
    private static final String DEFAULT_LAST_USED_TEXT = "Bisher nicht benutzt";

    private int oldExcuseObjID;

    private TextField excuseContentTextfield;
    private Date lastUsedResponse;

    public ExcuseEditEntryPane() {
        super();
    }

    public ExcuseEditEntryPane(int excuseID, Excuse excuse) {
        this();
        createEditForm(excuseID, excuse);
    }

    public void createEditForm(int originalExcuseID, Excuse originalExcuse) {
        this.oldExcuseObjID = originalExcuseID;

        BorderPane lastUsedBox = createExcuseLastUsedBox(originalExcuse.getLastUsed());
        BorderPane excuseContentBox = createExcuseTextBox(originalExcuse.getText());
        BorderPane tagsBox = createExcuseTagsBox(originalExcuse.getTags());

        super.getChildren().clear();
        super.getChildren().addAll(super.editorTitleLabel, lastUsedBox, excuseContentBox, tagsBox,
                super.submitEditedEntryBtn);
    }

    private BorderPane createExcuseLastUsedBox(Date lastUsed) {
        BorderPane lastUsedBox = new BorderPane();
        Label lastUsedLabel = new Label(LAST_USED_LABEL_TEXT);
        lastUsedResponse = lastUsed;
        Label lastUsedResponseLabel = new Label(
                lastUsedResponse != null ? lastUsedResponse.toString() : DEFAULT_LAST_USED_TEXT);

        lastUsedBox.setLeft(lastUsedLabel);
        lastUsedBox.setCenter(lastUsedResponseLabel);
        return lastUsedBox;
    }

    private BorderPane createExcuseTextBox(String text) {
        BorderPane excuseContentBox = new BorderPane();
        Label excuseContentLabel = new Label(EXCUSE_CONTENT_LABEL_TEXT);
        this.excuseContentTextfield = new TextField(text);

        excuseContentBox.setLeft(excuseContentLabel);
        excuseContentBox.setCenter(this.excuseContentTextfield);
        return excuseContentBox;
    }

    private BorderPane createExcuseTagsBox(Set<String> tags) {
        BorderPane tagsBox = new BorderPane();
        Label tagsLabel = new Label(TAGS_LABEL_TEXT);
        ListCellView<String> tagsListCell = new ListCellView<String>(
                FXCollections.observableArrayList(new ArrayList<String>(tags)),
                FXCollections.observableArrayList(new ArrayList<String>(tags)));

        tagsBox.setLeft(tagsLabel);
        tagsBox.setCenter(tagsListCell);
        // tagsBox.setCenter();
        return tagsBox;
    }

    public void createEditBtnAction(BiConsumer<Integer, Excuse> editEntry) {
        super.submitEditedEntryBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Set<String> newExcuseTags = new HashSet<String>();
                // TODO: Add reading all tags out of tags box
                Excuse editedExcuseObj = new Excuse(excuseContentTextfield.getText(), newExcuseTags, lastUsedResponse);
                editEntry.accept(oldExcuseObjID, editedExcuseObj);
            }
        });
    }
}
