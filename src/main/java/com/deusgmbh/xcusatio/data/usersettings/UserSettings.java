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
    private ExcusesVibesMode excusesVibesMode;
    private ExcusesVibes excusesVibes;

    public UserSettings(String googleCalendar, int age, Sex sex, Address home, Address university,
            ExcusesVibesMode excusesVibesMode, ExcusesVibes excusesVibes) {
        super();
        this.googleCalendar = googleCalendar;
        this.home = home;
        this.university = university;
        this.age = age;
        this.sex = sex;
        this.excusesVibesMode = excusesVibesMode;
        this.excusesVibes = excusesVibes;
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
        MALE, FEMALE;
    }

    public enum ExcusesVibesMode {
        AUTOMATIC, MANUALLY;
    }

}
