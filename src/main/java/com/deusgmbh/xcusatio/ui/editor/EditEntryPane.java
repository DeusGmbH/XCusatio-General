package com.deusgmbh.xcusatio.ui.editor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * 
 * This class is a base class for ExcuseEditEntryPane and LecturerEditEntryPane
 * and should provide methods, which are the same for both classes
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class EditEntryPane extends GridPane {
    private static final String EDITOR_TITLE = "Editor";
    private static final String SUBMIT_EDITED_ENTRY_BTN_LABEL = "Speichern";

    protected Label editorTitleLabel;
    protected Button submitEditedEntryBtn;

    protected Supplier<List<String>> allTagsSetSupplier;

    public EditEntryPane() {
        editorTitleLabel = new Label(EDITOR_TITLE);
        submitEditedEntryBtn = new Button(SUBMIT_EDITED_ENTRY_BTN_LABEL);

        this.add(editorTitleLabel, 0, 0);
    }

    protected void addNodesToPane(Node... nodesToAdd) {
        this.getChildren().clear();
        this.add(this.editorTitleLabel, 0, 0);
        final AtomicInteger counter = new AtomicInteger();
        Arrays.asList(nodesToAdd).stream().forEach(node -> {
            int columnIndex = counter.get() % 2;
            int rowIndex = (int) Math.floor(counter.get() / 2d) + 1;
            this.add(node, columnIndex, rowIndex);
            counter.incrementAndGet();
        });
        this.add(this.submitEditedEntryBtn, 1, (int) Math.ceil(nodesToAdd.length / 2d) + 1);
    }

    protected List<String> removeFromAllTagsList(List<String> listToRemove) {
        List<String> reducedSet = this.allTagsSetSupplier.get();
        reducedSet.removeAll(listToRemove);
        return reducedSet;
    }

    public void registerTagsSetSupplier(Supplier<List<String>> tagsSetSupplier) {
        this.allTagsSetSupplier = tagsSetSupplier;
    }
}
