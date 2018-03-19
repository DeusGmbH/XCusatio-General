package com.deusgmbh.xcusatio.data.lecturer;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Lecturer {
    private static final String REQ_COLUMN_NAME_VARNAME = "name";
    private static final String REQ_COLUMN_LECTURES_VARNAME = "lectures";
    private static final String REQ_COLUMN_TAGS_VARNAME = "tags";
    private static final String REQ_COLUMN_NAME_TITLE = "Name";
    private static final String REQ_COLUMN_LECTURES_TITLE = "Vorlesungen";
    private static final String REQ_COLUMN_TAGS_TITLE = "Tags";

    private String name;
    private List<String> lectures;
    private Set<String> tags;

    public Lecturer(String name, List<String> lectures, Set<String> tags) {
        super();
        this.name = name;
        this.lectures = lectures;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLectures() {
        return lectures;
    }

    public void setLectures(List<String> lectures) {
        this.lectures = lectures;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public static HashMap<String, String> getRequiredTableColumns() {
        HashMap<String, String> requiredColumns = new HashMap<String, String>();
        requiredColumns.put(REQ_COLUMN_NAME_VARNAME, REQ_COLUMN_NAME_TITLE);
        requiredColumns.put(REQ_COLUMN_LECTURES_VARNAME, REQ_COLUMN_LECTURES_TITLE);
        requiredColumns.put(REQ_COLUMN_TAGS_VARNAME, REQ_COLUMN_TAGS_TITLE);
        return requiredColumns;
    }

}
