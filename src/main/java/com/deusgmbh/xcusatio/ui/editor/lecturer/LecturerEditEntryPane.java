package com.deusgmbh.xcusatio.ui.editor.lecturer;

import java.util.function.BiConsumer;

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.ui.editor.EditEntryPane;
import com.deusgmbh.xcusatio.ui.utility.DoubleListView;
import com.deusgmbh.xcusatio.ui.utility.ListViewTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * 
 * This class is based on EditEntryPane.java and creates the editForm for the
 * LecturerEditor
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class LecturerEditEntryPane extends EditEntryPane {
    private static final String LECTURER_NAME_LABEL_TEXT = "Name:";
    private static final String LECTURER_LECTURES_LABEL_TEXT = "Vorlesungen: ";
    private static final String TAGS_LABEL_TEXT = "Tags:";

    private int oldLecturerObjID;

    private TextField lecturerNameTextField;
    private ListViewTextField lecturerLecturesPane;
    private DoubleListView<String> tagsListCellView;

    public LecturerEditEntryPane() {
        super();
    }

    public LecturerEditEntryPane(int lecturerID, Lecturer lecturer) {
        this();
        createEditForm(lecturerID, lecturer);
    }

    public void createEditForm(int lecturerID, Lecturer lecturer) {
        this.oldLecturerObjID = lecturerID;

        Label lecturerNameLabel = new Label(LECTURER_NAME_LABEL_TEXT);
        Label lecturerLecturesLabel = new Label(LECTURER_LECTURES_LABEL_TEXT);
        Label tagsLabel = new Label(TAGS_LABEL_TEXT);

        this.lecturerNameTextField = new TextField(lecturer.getName());
        this.lecturerLecturesPane = new ListViewTextField(lecturer.getLectures());
        this.tagsListCellView = new DoubleListView<String>(lecturer.getTags(),
                super.removeFromAllTagsList(lecturer.getTags()));

        super.addNodesToPane(lecturerNameLabel, lecturerNameTextField, lecturerLecturesLabel, lecturerLecturesPane,
                tagsLabel, tagsListCellView);
    }

    public void createEditBtnAction(BiConsumer<Integer, Lecturer> editEntry) {
        super.submitEditedEntryBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Lecturer editedLecturerObj = new Lecturer(lecturerNameTextField.getText(),
                        lecturerLecturesPane.getItems(), tagsListCellView.getLeftListItems());
                editEntry.accept(oldLecturerObjID, editedLecturerObj);
            }
        });
    }
}
