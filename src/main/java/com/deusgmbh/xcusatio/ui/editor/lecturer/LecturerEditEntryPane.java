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

    public void createEditForm(int id, ObservableList<Lecturer> lecturersList) {
        super.createTitleLabel();

        this.selectedItemId = id;
        this.editableItems = lecturersList;

        this.lecturerNameTextField = new TextField(editableItems.get(selectedItemId)
                .getName());
        this.lecturerLecturesPane = new ListViewTextField(editableItems.get(selectedItemId)
                .getLectures());
        this.tagsListCellView = new DoubleListView<Tag>(editableItems.get(selectedItemId)
                .getTags(),
                super.removeFromAllTagsList(editableItems.get(selectedItemId)
                        .getTags()));

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
