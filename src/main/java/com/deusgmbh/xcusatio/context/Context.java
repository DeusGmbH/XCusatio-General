package com.deusgmbh.xcusatio.context;

import java.util.Set;

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
    private Set<String> UserInterests;

    private String lecturerName;
    private Set<String> lecturerTags;

    private WildcardContext wildcardData;

    public Context(int age, Sex sex, ExcusesVibes excusesVibes, Set<String> userInterests, String lecturerName,
            Set<String> lecturerTags, WildcardContext wildcardData) {
        super();
        this.age = age;
        this.sex = sex;
        this.excusesVibes = excusesVibes;
        UserInterests = userInterests;
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

    public Set<String> getUserInterests() {
        return UserInterests;
    }

    public void setUserInterests(Set<String> userInterests) {
        UserInterests = userInterests;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public Set<String> getLecturerTags() {
        return lecturerTags;
    }

    public void setLecturerTags(Set<String> lecturerTags) {
        this.lecturerTags = lecturerTags;
    }

    public WildcardContext getWildcardData() {
        return wildcardData;
    }

    public void setWildcardData(WildcardContext wildcardData) {
        this.wildcardData = wildcardData;
    }

}
