package com.deusgmbh.xcusatio.data.usersettings;

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
    private ExcuseVibeMode excusesVibesMode;
    private ExcuseVibes excusesVibes;

    public UserSettings() {
        super();
    }

    public String getGoogleCalendar() {
        return googleCalendar;
    }

    public UserSettings setGoogleCalendar(String googleCalendar) {
        this.googleCalendar = googleCalendar;
        return this;
    }

    public Address getHome() {
        return home;
    }

    public UserSettings setHome(Address home) {
        this.home = home;
        return this;
    }

    public Address getUniversity() {
        return university;
    }

    public UserSettings setUniversity(Address university) {
        this.university = university;
        return this;
    }

    public int getAge() {
        return age;
    }

    public UserSettings setAge(int age) {
        this.age = age;
        return this;
    }

    public Sex getSex() {
        return sex;
    }

    public UserSettings setSex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public ExcuseVibeMode getExcuseVibeMode() {
        return excusesVibesMode;
    }

    public UserSettings setExcuseVibeMode(ExcuseVibeMode excusesVibesMode) {
        this.excusesVibesMode = excusesVibesMode;
        return this;
    }

    public ExcuseVibes getExcusesVibes() {
        return excusesVibes;
    }

    public UserSettings setExcusesVibes(ExcuseVibes excusesVibes) {
        this.excusesVibes = excusesVibes;
        return this;
    }

    public enum Sex {
        MALE, FEMALE;
    }

    public enum ExcuseVibeMode {
        AUTOMATIC, MANUALLY;
    }

}
