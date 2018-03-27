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

    public LocalDate getAge() {
        return birthdate;
    }

    public void setAge(LocalDate age) {
        this.birthdate = age;
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
        Male, Female;
    }

    public enum ExcusesVibesMode {
        AUTOMATIC, MANUALLY;
    }
}
