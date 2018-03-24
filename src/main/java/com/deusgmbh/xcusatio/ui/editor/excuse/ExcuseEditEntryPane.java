package com.deusgmbh.xcusatio.ui.editor.excuse;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.ui.editor.EditEntryPane;
import com.deusgmbh.xcusatio.ui.utility.DoubleListView;
import com.deusgmbh.xcusatio.ui.utility.TextFieldAddBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

/**
 * 
 * This class creates the editForm for excuses. It gets the current selected
 * Entry of the ExcuseList and provides fields for editing it. After that you
 * can save your changes and the Excuse will be updated.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */
public class ExcuseEditEntryPane extends EditEntryPane {
    private static final String LAST_USED_LABEL_TEXT = "Zuletzt verwendet:";
    private static final String EXCUSE_CONTENT_LABEL_TEXT = "Ausrede:";
    private static final String TAGS_LABEL_TEXT = "Tags:";
    private static final String DEFAULT_LAST_USED_TEXT = "Bisher nicht benutzt";

    protected Supplier<List<String>> wildcardSetSupplier;

    private int oldExcuseObjID;
    private Excuse oldExcuseObj;

    private TextFieldAddBox excuseTextField;
    private DoubleListView<String> tagsListCellView;

    public ExcuseEditEntryPane() {
        super();
    }

    public ExcuseEditEntryPane(int excuseID, Excuse excuse) {
        this();
        createEditForm(excuseID, excuse);
    }

    public void createEditForm(int excuseID, Excuse excuse) {
        this.oldExcuseObjID = excuseID;
        this.oldExcuseObj = excuse;

        Label lastUsedLabel = new Label(LAST_USED_LABEL_TEXT);
        Label excuseContentLabel = new Label(EXCUSE_CONTENT_LABEL_TEXT);
        Label tagsLabel = new Label(TAGS_LABEL_TEXT);

        Label lastUsedResponseLabel = new Label(
                excuse.getLastUsed() != null ? excuse.getLastUsed().toString() : DEFAULT_LAST_USED_TEXT);
        this.excuseTextField = new TextFieldAddBox(excuse.getText(), wildcardSetSupplier.get());
        this.tagsListCellView = new DoubleListView<String>(excuse.getTags(),
                super.removeFromAllTagsList(excuse.getTags()));

        super.addNodesToPane(lastUsedLabel, lastUsedResponseLabel, excuseContentLabel, excuseTextField, tagsLabel,
                tagsListCellView);
    }

    public void createEditBtnAction(BiConsumer<Integer, Excuse> editEntry) {
        this.submitEditedEntryBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Excuse editedExcuseObj = new Excuse(excuseTextField.getText(), tagsListCellView.getLeftListItems(),
                        oldExcuseObj.getLastUsed());
                editEntry.accept(oldExcuseObjID, editedExcuseObj);
            }
        });
    }

    public void registerWildcardSetSupplier(Supplier<List<String>> wildcardSetSupplier) {
        this.wildcardSetSupplier = wildcardSetSupplier;
    }
}
