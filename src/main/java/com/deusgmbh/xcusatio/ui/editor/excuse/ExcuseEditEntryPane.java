package com.deusgmbh.xcusatio.ui.editor.excuse;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.scenarios.ScenarioType;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.ui.editor.EditEntryPane;
import com.deusgmbh.xcusatio.ui.utility.DoubleListView;
import com.deusgmbh.xcusatio.ui.utility.TextFieldAddBox;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
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
    private static final String EXCUSE_TYPE_LABEL_TEXT = "Ausredentyp:";
    private static final String TAGS_LABEL_TEXT = "Tags:";
    private static final String DEFAULT_LAST_USED_TEXT = "Bisher nicht benutzt";

    private Supplier<List<String>> wildcardSetSupplier;
    private List<ScenarioType> scenarioTypes;

    private int oldExcuseObjID;
    private Excuse oldExcuseObj;

    private TextFieldAddBox excuseTextField;
    private ChoiceBox<ScenarioType> excuseTypeChoiceBox;
    private DoubleListView<Tag> tagsListCellView;

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

        // ColumnConstraints col1 = new ColumnConstraints();
        // col1.setPercentWidth(30);
        // ColumnConstraints col2 = new ColumnConstraints();
        // col2.setPercentWidth(70);
        //
        // this.getColumnConstraints().addAll(col1, col2);

        Label lastUsedLabel = new Label(LAST_USED_LABEL_TEXT);
        Label excuseContentLabel = new Label(EXCUSE_CONTENT_LABEL_TEXT);
        Label excuseTypeLabel = new Label(EXCUSE_TYPE_LABEL_TEXT);
        Label tagsLabel = new Label(TAGS_LABEL_TEXT);

        Label lastUsedResponseLabel = new Label(
                excuse.getLastUsed() != null ? excuse.getLastUsed().toString() : DEFAULT_LAST_USED_TEXT);
        this.excuseTextField = new TextFieldAddBox(excuse.getText(), wildcardSetSupplier.get());
        this.tagsListCellView = new DoubleListView<Tag>(excuse.getTags(),
                super.removeFromAllTagsList(excuse.getTags()));

        scenarioTypes = Arrays.asList(ScenarioType.values());
        this.excuseTypeChoiceBox = new ChoiceBox<ScenarioType>(FXCollections.observableArrayList(scenarioTypes));
        excuseTypeChoiceBox.getSelectionModel().select(scenarioTypes.indexOf(excuse.getScenarioType()));

        excuseTextField.prefWidthProperty().bind(this.widthProperty().multiply(0.6));

        super.addNodesToPane(lastUsedLabel, lastUsedResponseLabel, excuseContentLabel, excuseTextField, excuseTypeLabel,
                excuseTypeChoiceBox, tagsLabel, tagsListCellView);
    }

    public void createEditBtnAction(BiConsumer<Integer, Excuse> editEntry) {
        this.submitEditedEntryBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Excuse editedExcuseObj = new Excuse(excuseTextField.getText(),
                        scenarioTypes.get(excuseTypeChoiceBox.getSelectionModel().getSelectedIndex()),
                        tagsListCellView.getLeftListItems(), oldExcuseObj.getLastUsed());
                editEntry.accept(oldExcuseObjID, editedExcuseObj);
            }
        });
    }

    public void registerWildcardSetSupplier(Supplier<List<String>> wildcardSetSupplier) {
        this.wildcardSetSupplier = wildcardSetSupplier;
    }
}
