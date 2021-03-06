package com.deusgmbh.xcusatio.data.lecturer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.deusgmbh.xcusatio.data.tags.Tag;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Lecturer {

    private String name;
    private List<String> lectures;
    private List<Tag> tags;

    public Lecturer(String name, List<String> lectures, List<Tag> tags) {
        super();
        this.name = name;
        this.lectures = lectures;
        this.tags = tags;
    }

    public Lecturer(String name) {
        this(name, new ArrayList<String>(), new ArrayList<Tag>());
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

    public Lecturer addLecture(String lecture) {
        this.lectures.add(lecture);
        return this;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Lecturer addTag(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public static Predicate<Lecturer> hasLecture(String lecture) {
        return lecturer -> lecturer.getLectures()
                .contains(lecture);
    }
}
