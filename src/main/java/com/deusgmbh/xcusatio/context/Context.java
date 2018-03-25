package com.deusgmbh.xcusatio.context;

import java.util.List;

import com.deusgmbh.xcusatio.context.wildcard.WildcardContext;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.data.usersettings.ExcusesVibes;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.Sex;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Context {
    private int age;
    private Sex sex;
    private ExcusesVibes excusesVibes;

    private String lecturerName;
    private List<Tag> lecturerTags;

    private WildcardContext wildcardData;

    public Context(int age, Sex sex, ExcusesVibes excusesVibes, String lecturerName, List<Tag> lecturerTags,
            WildcardContext wildcardData) {
        super();
        this.age = age;
        this.sex = sex;
        this.excusesVibes = excusesVibes;
        this.lecturerName = lecturerName;
        this.lecturerTags = lecturerTags;
        this.wildcardData = wildcardData;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public ExcusesVibes getExcusesVibes() {
        return excusesVibes;
    }

    public void setExcusesVibes(ExcusesVibes excusesVibes) {
        this.excusesVibes = excusesVibes;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public List<Tag> getLecturerTags() {
        return lecturerTags;
    }

    public void setLecturerTags(List<Tag> lecturerTags) {
        this.lecturerTags = lecturerTags;
    }

    public WildcardContext getWildcardData() {
        return wildcardData;
    }

    public void setWildcardData(WildcardContext wildcardData) {
        this.wildcardData = wildcardData;
    }

}
