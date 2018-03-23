package com.deusgmbh.xcusatio.ui.editor.lecturer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.ui.editor.EditEntryPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class LecturerEditEntryPane extends EditEntryPane {
    private static final String LECTURER_NAME_LABEL_TEXT = "Name:";
    private static final String LECTURER_LECTURES_LABEL_TEXT = "Vorlesungen: ";
    private static final String TAGS_LABEL_TEXT = "Tags:";

    private int oldLecturerObjID;

    private TextField lecturerNameTextfield;

    public LecturerEditEntryPane() {
        super();
    }

    public LecturerEditEntryPane(int lecturerID, Lecturer lecturer) {
        this();
        createEditForm(lecturerID, lecturer);
    }

    public void createEditForm(int originalLecturerID, Lecturer originalLecturer) {
        this.oldLecturerObjID = originalLecturerID;

        BorderPane lecturerNameBox = createLecturerTextBox(originalLecturer.getName());
        BorderPane lecturerLecturesBox = createLecturerLecturesBox(originalLecturer.getLectures());
        BorderPane tagsBox = createLecturerTagsBox(originalLecturer.getTags());
        super.getChildren().clear();
        super.getChildren().addAll(super.editorTitleLabel, lecturerNameBox, lecturerLecturesBox, tagsBox,
                super.submitEditedEntryBtn);
    }

    private BorderPane createLecturerTextBox(String name) {
        BorderPane LecturerContentBox = new BorderPane();
        Label lecturerContentLabel = new Label(LECTURER_NAME_LABEL_TEXT);
        this.lecturerNameTextfield = new TextField(name);

        LecturerContentBox.setLeft(lecturerContentLabel);
        LecturerContentBox.setCenter(this.lecturerNameTextfield);
        return LecturerContentBox;
    }

    private BorderPane createLecturerLecturesBox(List<String> lectures) {
        BorderPane lecturesBox = new BorderPane();
        Label lecturesLabel = new Label(LECTURER_LECTURES_LABEL_TEXT);
        // TODO: Add Drag & Drop Boxes

        lecturesBox.setLeft(lecturesLabel);
        // lecturesBox.setCenter();
        return lecturesBox;
    }

    private BorderPane createLecturerTagsBox(Set<String> tags) {
        BorderPane tagsBox = new BorderPane();
        Label tagsLabel = new Label(TAGS_LABEL_TEXT);
        // TODO: Add Drag & Drop Boxes

        tagsBox.setLeft(tagsLabel);
        // tagsBox.setCenter();
        return tagsBox;
    }

    public void createEditBtnAction(BiConsumer<Integer, Lecturer> editEntry) {
        super.submitEditedEntryBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                List<String> newLecturerLectures = new ArrayList<String>();
                Set<String> newLecturerTags = new HashSet<String>();
                // TODO: Add reading all lectures out of lectures box & tags out
                // of tags box
                Lecturer editedLecturerObj = new Lecturer(lecturerNameTextfield.getText(), newLecturerLectures,
                        newLecturerTags);
                editEntry.accept(oldLecturerObjID, editedLecturerObj);
            }
        });
    }
}
