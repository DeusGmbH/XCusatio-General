package com.deusgmbh.xcusatio.ui.editor.excuse;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.scenarios.ScenarioType;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.ui.editor.EditEntryPane;
import com.deusgmbh.xcusatio.ui.utility.DoubleListView;
import com.deusgmbh.xcusatio.ui.utility.TextFieldAddBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private TextFieldAddBox excuseTextField;
    private ChoiceBox<ScenarioType> excuseTypeChoiceBox;
    private DoubleListView<Tag> tagsListCellView;

    public ExcuseEditEntryPane() {
        super();
    }

    public ExcuseEditEntryPane(int id, ObservableList<Excuse> excuseList) {
        this();
        createEditForm(id, excuseList);
    }

    public void createEditForm(int id, ObservableList<Excuse> excuseList) {
        this.selectedItemId = id;
        this.editableItems = excuseList;

        Label lastUsedLabel = new Label(LAST_USED_LABEL_TEXT);
        Label excuseContentLabel = new Label(EXCUSE_CONTENT_LABEL_TEXT);
        Label excuseTypeLabel = new Label(EXCUSE_TYPE_LABEL_TEXT);
        Label tagsLabel = new Label(TAGS_LABEL_TEXT);

        Label lastUsedResponseLabel = new Label(editableItems.get(id)
                .getLastUsed() != null ? editableItems.get(id)
                        .getLastUsed()
                        .toString() : DEFAULT_LAST_USED_TEXT);
        this.excuseTextField = new TextFieldAddBox(editableItems.get(id)
                .getText(), wildcardSetSupplier.get());
        this.tagsListCellView = new DoubleListView<Tag>(editableItems.get(id)
                .getTags(),
                super.removeFromAllTagsList(editableItems.get(id)
                        .getTags()));

        scenarioTypes = Arrays.asList(ScenarioType.values());
        this.excuseTypeChoiceBox = new ChoiceBox<ScenarioType>(FXCollections.observableArrayList(scenarioTypes));
        excuseTypeChoiceBox.getSelectionModel()
                .select(scenarioTypes.indexOf(editableItems.get(id)
                        .getScenarioType()));

        excuseTextField.prefWidthProperty()
                .bind(this.widthProperty()
                        .multiply(0.6));

        super.addNodesToPane(lastUsedLabel, lastUsedResponseLabel, excuseContentLabel, excuseTextField, excuseTypeLabel,
                excuseTypeChoiceBox, tagsLabel, tagsListCellView);
    }

    public void registerWildcardSetSupplier(Supplier<List<String>> wildcardSetSupplier) {
        this.wildcardSetSupplier = wildcardSetSupplier;
    }

    @Override
    protected void saveChanges() {
        this.editableItems
                .set(this.selectedItemId,
                        new Excuse(excuseTextField.getText(), scenarioTypes.get(excuseTypeChoiceBox.getSelectionModel()
                                .getSelectedIndex()), tagsListCellView.getLeftListItems(),
                                editableItems.get(selectedItemId)
                                        .getLastUsed()));
    }
}
