package com.deusgmbh.xcusatio.ui.editor.lecturer;

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.ui.editor.EditEntryPane;
import com.deusgmbh.xcusatio.ui.utility.DoubleListView;
import com.deusgmbh.xcusatio.ui.utility.ListViewTextField;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

/**
 * 
 * This class creates the editForm for lecturers. It gets the current selected
 * Entry of the LecturerList and provides fields for editing it. After that you
 * can save your changes and the Excuse will be updated.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class LecturerEditEntryPane extends EditEntryPane<Lecturer> {
    private static final String LECTURER_NAME_LABEL_TEXT = "Name:";
    private static final String LECTURER_LECTURES_LABEL_TEXT = "Vorlesungen: ";
    private static final String TAGS_LABEL_TEXT = "Tags:";
    private static final double RIGHT_EDIT_HALF_MULTIPLIER = 0.6;
    private static final double LECTURER_LECTURES_LIST_VIEW_HEIGHT_MULTIPLIER = 0.15;
    private static final double TAGS_LIST_HEIGHT_MULTIPLIER = 0.4;
    private static final String LECTURER_NAME_TEXTFIELD_PLACEHOLDER = "Dozentenname";
    private static final String LECTURER_LECTURES_PLACEHOLDER = "Vorlesung hinzufügen";

    private TextField lecturerNameTextField;
    private ListViewTextField lecturerLecturesPane;
    private DoubleListView<Tag> tagsListCellView;

    public LecturerEditEntryPane() {
        super();
    }

    public LecturerEditEntryPane(int lecturerID, ObservableList<Lecturer> lecturers) {
        this();
        createEditForm(lecturerID, lecturers);
    }

    @Override
    protected void createCustomizedEditForm(int id, ObservableList<Lecturer> lecturersList) {
        this.selectedItemId = id;
        this.editableItems = lecturersList;

        this.lecturerNameTextField = new TextField(editableItems.get(selectedItemId)
                .getName());
        this.lecturerNameTextField.prefWidthProperty()
                .bind(this.widthProperty()
                        .multiply(RIGHT_EDIT_HALF_MULTIPLIER));
        this.lecturerNameTextField.setPromptText(LECTURER_NAME_TEXTFIELD_PLACEHOLDER);
        this.lecturerLecturesPane = new ListViewTextField(editableItems.get(selectedItemId)
                .getLectures());
        this.lecturerLecturesPane.bindSize(this.widthProperty()
                .multiply(RIGHT_EDIT_HALF_MULTIPLIER),
                this.heightProperty()
                        .multiply(LECTURER_LECTURES_LIST_VIEW_HEIGHT_MULTIPLIER));
        this.lecturerLecturesPane.setPlaceholder(LECTURER_LECTURES_PLACEHOLDER);
        this.tagsListCellView = new DoubleListView<Tag>(editableItems.get(selectedItemId)
                .getTags(),
                super.removeFromAllTagsList(editableItems.get(selectedItemId)
                        .getTags()));
        this.tagsListCellView.bindSize(this.widthProperty()
                .multiply(RIGHT_EDIT_HALF_MULTIPLIER),
                this.heightProperty()
                        .multiply(TAGS_LIST_HEIGHT_MULTIPLIER));

        super.addNodeBoxToPane(LECTURER_NAME_LABEL_TEXT, lecturerNameTextField);
        super.addNodeBoxToPane(LECTURER_LECTURES_LABEL_TEXT, lecturerLecturesPane);
        super.addNodeBoxToPane(TAGS_LABEL_TEXT, tagsListCellView);

        this.setBottom(super.submitEditedEntryBtnPane);
    }

    @Override
    protected void saveChanges() {
        this.editableItems.set(this.selectedItemId, new Lecturer(lecturerNameTextField.getText(),
                lecturerLecturesPane.getItems(), tagsListCellView.getLeftListItems()));
    }

}
