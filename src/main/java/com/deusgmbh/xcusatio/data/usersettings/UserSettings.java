package com.deusgmbh.xcusatio.data.usersettings;

import java.util.Set;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class UserSettings {
    // TODO: What kind of data does the calendar API need? And how can this be
    // represented here?
    // some kind of config json and refresh token
    private String googleCalendar;

    private int age;
    private Sex sex;
    private Address home;
    private Address university;
    private ExcusesVibesMode excusesVibesMode;
    private ExcusesVibes excusesVibes;
    private Set<String> interests;

    public UserSettings(String googleCalendar, int age, Sex sex, Address home, Address university,
            ExcusesVibesMode excusesVibesMode, ExcusesVibes excusesVibes, Set<String> interests) {
        super();
        this.googleCalendar = googleCalendar;
        this.home = home;
        this.university = university;
        this.age = age;
        this.sex = sex;
        this.excusesVibesMode = excusesVibesMode;
        this.excusesVibes = excusesVibes;
        this.interests = interests;
    }

    public boolean isValid() {
        // TODO: check if all values are != null, watch out for nested objects
        return false;
    }

    public String getGoogleCalendar() {
        return googleCalendar;
    }

    public void setGoogleCalendar(String googleCalendar) {
        this.googleCalendar = googleCalendar;
    }

    public Address getHome() {
        return home;
    }

    public void setHome(Address home) {
        this.home = home;
    }

    public Address getUniversity() {
        return university;
    }

    public void setUniversity(Address university) {
        this.university = university;
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

    public Set<String> getInterests() {
        return interests;
    }

    public void setInterests(Set<String> interests) {
        this.interests = interests;
    }

    public ExcusesVibesMode getExcusesVibesMode() {
        return excusesVibesMode;
    }

    public void setExcusesVibesMode(ExcusesVibesMode excusesVibesMode) {
        this.excusesVibesMode = excusesVibesMode;
    }

    public ExcusesVibes getExcusesVibes() {
        return excusesVibes;
    }

    public void setExcusesVibes(ExcusesVibes excusesVibes) {
        this.excusesVibes = excusesVibes;
    }

    public enum Sex {
        Male, Female;
    }

    public enum ExcusesVibesMode {
        AUTOMATIC, MANUALLY;
    }
}
