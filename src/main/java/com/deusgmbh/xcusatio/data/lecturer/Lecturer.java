package com.deusgmbh.xcusatio.data.lecturer;

import java.util.List;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Lecturer {

    private String name;
    private List<String> lectures;
    private List<String> tags;

    public Lecturer(String name, List<String> lectures, List<String> tags) {
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
