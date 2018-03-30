package com.deusgmbh.xcusatio.ui.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.tags.Tag;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * 
 * This class is a base class for ExcuseEditEntryPane and LecturerEditEntryPane
 * and should provide methods, which are the same for both classes. With these
 * classes an edit form for the selected entry in the EntryListPane is created.
 * So you can edit this fields and save the changes, which forces an update of
 * the entries.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public abstract class EditEntryPane extends GridPane {
    private static final String EDITOR_TITLE = "Editor";
    private static final String SUBMIT_EDITED_ENTRY_BTN_LABEL = "Speichern";

    protected Label editorTitleLabel;
    protected Button submitEditedEntryBtn;

    protected int selectedItemId;
    protected ObservableList<Excuse> editableItems;

    protected Supplier<List<Tag>> allTagsSetSupplier;

    public EditEntryPane() {
        editorTitleLabel = new Label(EDITOR_TITLE);
        submitEditedEntryBtn = new Button(SUBMIT_EDITED_ENTRY_BTN_LABEL);
        this.createEditBtnAction();
        this.add(editorTitleLabel, 0, 0);
    }

    protected void addNodesToPane(Node... nodesToAdd) {
        this.getChildren()
                .clear();
        this.add(this.editorTitleLabel, 0, 0);
        final AtomicInteger counter = new AtomicInteger();
        Arrays.asList(nodesToAdd)
                .stream()
                .forEach(node -> {
                    int columnIndex = counter.get() % 2;
                    int rowIndex = (int) Math.floor(counter.get() / 2d) + 1;
                    this.add(node, columnIndex, rowIndex);
                    counter.incrementAndGet();
                });
        this.add(this.submitEditedEntryBtn, 1, (int) Math.ceil(nodesToAdd.length / 2d) + 1);
    }

    protected List<Tag> removeFromAllTagsList(List<Tag> listToRemove) {
        List<Tag> reducedSet = new ArrayList<Tag>(this.allTagsSetSupplier.get());
        reducedSet.removeAll(listToRemove);
        return reducedSet;
    }

    public void registerTagsSetSupplier(Supplier<List<Tag>> tagsSetSupplier) {
        this.allTagsSetSupplier = tagsSetSupplier;
    }

    private void createEditBtnAction() {
        submitEditedEntryBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                saveChanges();
            }
        });
    }

    public void updateSelectionId(IntUnaryOperator newIdSupplier) {
        this.selectedItemId = newIdSupplier.applyAsInt(this.selectedItemId);
    }

    abstract protected void saveChanges();
}
