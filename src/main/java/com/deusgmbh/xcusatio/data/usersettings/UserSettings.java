package com.deusgmbh.xcusatio.data.usersettings;

import java.time.LocalDate;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class UserSettings {
    private LocalDate birthdate;
    private Sex sex;
    private Address home;
    private Address university;
    private ExcuseVibeMode excuseVibesMode;
    private ExcuseVibes excuseVibes;

    public UserSettings(LocalDate birthdate, Sex sex, Address home, ExcuseVibeMode excuseVibesMode,
            ExcuseVibes ExcuseVibes) {
        super();
        this.home = home;
        this.birthdate = birthdate;
        this.sex = sex;
        this.excuseVibesMode = excuseVibesMode;
        this.excuseVibes = ExcuseVibes;
    }

    public UserSettings(String googleCalendar, LocalDate birthdate, Sex sex, Address home) {
        this(birthdate, sex, home, ExcuseVibeMode.AUTOMATIC, new ExcuseVibes(false, false, false));
    }

    public UserSettings() {
        super();
    }

    public Address getHome() {
        return home;
    }

    public UserSettings setHome(Address home) {
        this.home = home;
        return this;
    }

    public Address getUniversity() {
        return this.university;
    }

    public UserSettings setUniversity(Address university) {
        this.university = university;
        return this;
    }

    public LocalDate getBirthdate() {
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
        return excuseVibesMode;
    }

    public UserSettings setExcuseVibeMode(ExcuseVibeMode ExcuseVibesMode) {
        this.excuseVibesMode = ExcuseVibesMode;
        return this;
    }

    public ExcuseVibes getExcuseVibes() {
        return excuseVibes;
    }

    public UserSettings setExcuseVibes(ExcuseVibes excuseVibes) {
        this.excuseVibes = excuseVibes;
        return this;
    }

    public enum Sex {
        MALE, FEMALE;
    }

    public enum ExcuseVibeMode {
        AUTOMATIC, MANUALLY;
    }

}
