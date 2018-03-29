package com.deusgmbh.xcusatio.ui.editor.lecturer;

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.ui.editor.EditEntryPane;
import com.deusgmbh.xcusatio.ui.utility.DoubleListView;
import com.deusgmbh.xcusatio.ui.utility.ListViewTextField;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
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

public class LecturerEditEntryPane extends EditEntryPane {
    private static final String LECTURER_NAME_LABEL_TEXT = "Name:";
    private static final String LECTURER_LECTURES_LABEL_TEXT = "Vorlesungen: ";
    private static final String TAGS_LABEL_TEXT = "Tags:";

    private int lecturerId;
    private ObservableList<Lecturer> lecturers;

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
        this.lecturerId = id;
        this.lecturers = lecturersList;

        Label lecturerNameLabel = new Label(LECTURER_NAME_LABEL_TEXT);
        Label lecturerLecturesLabel = new Label(LECTURER_LECTURES_LABEL_TEXT);
        Label tagsLabel = new Label(TAGS_LABEL_TEXT);

        this.lecturerNameTextField = new TextField(lecturers.get(lecturerId)
                .getName());
        this.lecturerLecturesPane = new ListViewTextField(lecturers.get(lecturerId)
                .getLectures());
        this.tagsListCellView = new DoubleListView<Tag>(lecturers.get(lecturerId)
                .getTags(),
                super.removeFromAllTagsList(lecturers.get(lecturerId)
                        .getTags()));

        super.addNodesToPane(lecturerNameLabel, lecturerNameTextField, lecturerLecturesLabel, lecturerLecturesPane,
                tagsLabel, tagsListCellView);
    }

    @Override
    protected void saveChanges() {
        this.lecturers.set(this.lecturerId, new Lecturer(lecturerNameTextField.getText(),
                lecturerLecturesPane.getItems(), tagsListCellView.getLeftListItems()));
    }

}
