package com.deusgmbh.xcusatio.data.usersettings;

import java.time.LocalDate;

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

    private LocalDate birthdate;
    private Sex sex;
    private Address home;
    private ExcusesVibesMode excusesVibesMode;
    private ExcusesVibes excusesVibes;

    UserSettings(String googleCalendar, LocalDate birthdate, Sex sex, Address home, ExcusesVibesMode excusesVibesMode,
            ExcusesVibes excusesVibes) {
        super();
        this.googleCalendar = googleCalendar;
        this.home = home;
        this.birthdate = birthdate;
        this.sex = sex;
        this.excusesVibesMode = excusesVibesMode;
        this.excusesVibes = excusesVibes;
    }
  
    public UserSettings(String googleCalendar, LocalDate birthdate, Sex sex, Address home) {
        this(googleCalendar, birthdate, sex, home, ExcusesVibesMode.AUTOMATIC, new ExcusesVibes(false, false, false));
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

    public LocalDate getAge() {
        return birthdate;
    }

    public UserSettings setAge(LocalDate age) {
        this.birthdate = age;
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
