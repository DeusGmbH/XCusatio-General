package com.deusgmbh.xcusatio.ui.editor.lecturer;

import java.util.ArrayList;
import java.util.HashMap;

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.tags.TagText;
import com.deusgmbh.xcusatio.ui.editor.EntryListPane;

/**
 * 
 * This class creates a table of all available lecturers. You can remove or add
 * an entry. Also you can select one and edit this in the adjacent
 * EditEntryPane.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class LecturerEntryListPane extends EntryListPane<Lecturer> {
    private static final String REQ_COLUMN_NAME_VARNAME = "name";
    private static final String REQ_COLUMN_LECTURES_VARNAME = "lectures";
    private static final String REQ_COLUMN_TAGS_VARNAME = "tags";
    private static final String REQ_COLUMN_NAME_TITLE = "Name";
    private static final String REQ_COLUMN_LECTURES_TITLE = "Vorlesungen";
    private static final String REQ_COLUMN_TAGS_TITLE = "Tags";
    protected static final String NEW_LECTURER_DEFAULT_TEXT = "Dozentenname";

    @Override
    protected Lecturer createNewItem() {
        return new Lecturer(NEW_LECTURER_DEFAULT_TEXT, new ArrayList<String>(), new ArrayList<TagText>());
    }

    @Override
    protected HashMap<String, String> getRequiredTableColumns() {
        HashMap<String, String> requiredColumns = new HashMap<String, String>();
        requiredColumns.put(REQ_COLUMN_NAME_VARNAME, REQ_COLUMN_NAME_TITLE);
        requiredColumns.put(REQ_COLUMN_LECTURES_VARNAME, REQ_COLUMN_LECTURES_TITLE);
        requiredColumns.put(REQ_COLUMN_TAGS_VARNAME, REQ_COLUMN_TAGS_TITLE);
        return requiredColumns;
    }
}
