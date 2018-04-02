package com.deusgmbh.xcusatio.ui.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.tags.Tag;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * 
 * This class is a base class for TEditEntryPane and LecturerEditEntryPane and
 * should provide methods, which are the same for both classes. With these
 * classes an edit form for the selected entry in the EntryListPane is created.
 * So you can edit this fields and save the changes, which forces an update of
 * the entries.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public abstract class EditEntryPane<T> extends BorderPane {
    private static final String EDIT_ENTRY_STYLESHEET_PATH = "file:assets/edit_entry_stylesheet.css";

    private static final String EDITOR_TITLE = "Editor";
    private static final String SUBMIT_EDITED_ENTRY_BTN_LABEL = "Speichern";

    protected Label editorTitleLabel;
    protected StackPane submitEditedEntryBtnPane;
    private VBox editFormPane;

    protected int selectedItemId;
    protected ObservableList<T> editableItems;

    protected Supplier<List<Tag>> allTagsSetSupplier;

    public EditEntryPane() {
        createBaseEditForm();
    }

    protected void createBaseEditForm() {
        editorTitleLabel = new Label(EDITOR_TITLE);
        editorTitleLabel.getStyleClass()
                .add("h2");
        Button submitEditedEntryBtn = new Button(SUBMIT_EDITED_ENTRY_BTN_LABEL);
        submitEditedEntryBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                saveChanges();
            }

        });

        submitEditedEntryBtnPane = new StackPane();
        submitEditedEntryBtnPane.setAlignment(Pos.BOTTOM_RIGHT);
        submitEditedEntryBtnPane.getChildren()
                .add(submitEditedEntryBtn);

        editFormPane = new VBox();
        editFormPane.setPadding(new Insets(15, 0, 0, 0));

        this.setCenter(editFormPane);
        this.setTop(editorTitleLabel);

        this.getStylesheets()
                .add(EDIT_ENTRY_STYLESHEET_PATH);
    }

    protected void addNodeBoxToPane(String descriptionText, Parent contentNode) {
        HBox nodeBox = new HBox();

        Label descriptionLabel = new Label(descriptionText);
        descriptionLabel.setAlignment(Pos.CENTER_RIGHT);
        descriptionLabel.getStyleClass()
                .add("p");
        descriptionLabel.prefWidthProperty()
                .bind(this.widthProperty()
                        .multiply(0.35));

        nodeBox.getChildren()
                .addAll(descriptionLabel, contentNode);
        nodeBox.setPadding(new Insets(10, 5, 10, 0));
        editFormPane.getChildren()
                .add(nodeBox);
    }

    protected List<Tag> removeFromAllTagsList(List<Tag> listToRemove) {
        List<Tag> reducedSet = new ArrayList<Tag>(this.allTagsSetSupplier.get());
        reducedSet.removeAll(listToRemove);
        return reducedSet;
    }

    public void registerTagsSetSupplier(Supplier<List<Tag>> tagsSetSupplier) {
        this.allTagsSetSupplier = tagsSetSupplier;
    }

    public void updateSelectionId(IntUnaryOperator newIdSupplier) {
        this.selectedItemId = newIdSupplier.applyAsInt(this.selectedItemId);
    }

    abstract protected void saveChanges();
}