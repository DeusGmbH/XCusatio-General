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
public class ExcuseEditEntryPane extends EditEntryPane<Excuse> {
    private static final String LAST_USED_LABEL_TEXT = "Zuletzt verwendet:";
    private static final String EXCUSE_CONTENT_LABEL_TEXT = "Ausrede:";
    private static final String EXCUSE_TYPE_LABEL_TEXT = "Ausredentyp:";
    private static final String TAGS_LABEL_TEXT = "Tags:";
    private static final String DEFAULT_LAST_USED_TEXT = "Bisher nicht benutzt";
    private static final double TAGS_LIST_WIDTH_PROPERTY = 0.6;
    private static final double TAGS_LIST_HEIGHT_PROPERTY = 0.4;
    private static final double EXCUSE_TEXT_FIELD_WIDTH_PROPERTY = 0.6;
    private static final double EXCUSE_TEXT_FIELD_HEIGHT_MULTIPLIER = 0.15;

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
        super.createBaseEditForm();

        this.selectedItemId = id;
        this.editableItems = excuseList;

        Label lastUsedResponseLabel = new Label(editableItems.get(id)
                .getLastUsed() != null ? editableItems.get(id)
                        .getLastUsed()
                        .toString() : DEFAULT_LAST_USED_TEXT);

        this.excuseTextField = new TextFieldAddBox(editableItems.get(id)
                .getText(), wildcardSetSupplier.get());
        this.excuseTextField.bindSize(this.heightProperty()
                .multiply(EXCUSE_TEXT_FIELD_HEIGHT_MULTIPLIER));
        this.tagsListCellView = new DoubleListView<Tag>(editableItems.get(id)
                .getTags(),
                super.removeFromAllTagsList(editableItems.get(id)
                        .getTags()));
        this.tagsListCellView.bindSize(this.widthProperty()
                .multiply(TAGS_LIST_WIDTH_PROPERTY),
                this.heightProperty()
                        .multiply(TAGS_LIST_HEIGHT_PROPERTY));

        scenarioTypes = Arrays.asList(ScenarioType.values());

        this.excuseTypeChoiceBox = new ChoiceBox<ScenarioType>(FXCollections.observableArrayList(scenarioTypes)
                .filtered(type -> {
                    return type != ScenarioType.THUMBGESTURE;
                }));
        excuseTypeChoiceBox.getSelectionModel()
                .select(scenarioTypes.indexOf(editableItems.get(id)
                        .getScenarioType()));

        excuseTextField.prefWidthProperty()
                .bind(this.widthProperty()
                        .multiply(EXCUSE_TEXT_FIELD_WIDTH_PROPERTY));

        super.addNodeBoxToPane(LAST_USED_LABEL_TEXT, lastUsedResponseLabel);
        super.addNodeBoxToPane(EXCUSE_CONTENT_LABEL_TEXT, excuseTextField);
        super.addNodeBoxToPane(EXCUSE_TYPE_LABEL_TEXT, excuseTypeChoiceBox);
        super.addNodeBoxToPane(TAGS_LABEL_TEXT, tagsListCellView);

        this.setBottom(super.submitEditedEntryBtnPane);

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
