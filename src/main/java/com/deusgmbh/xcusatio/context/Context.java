package com.deusgmbh.xcusatio.context;

import java.time.LocalDate;

import com.deusgmbh.xcusatio.context.wildcard.APIContext;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.usersettings.ExcuseVibes;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.ExcuseVibeMode;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.Sex;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Context {
    private ExcuseVibeMode excuseVibeMode;
    private LocalDate age;
    private Sex sex;
    private ExcuseVibes manuellExcusesVibes;

    private Lecturer lecturer;

    private APIContext apiContext;

    public Context(LocalDate age, Sex sex, ExcuseVibes excusesVibes, Lecturer lecturer, APIContext apiContext) {
        super();
        this.age = age;
        this.sex = sex;
        this.lecturer = lecturer;
        this.manuellExcusesVibes = excusesVibes;
        this.apiContext = apiContext;
    }

    public Context() {
        super();
    }

    public LocalDate getAge() {
        return age;
    }

    public Context setAge(LocalDate localDate) {
        this.age = localDate;
        return this;
    }

    public Sex getSex() {
        return sex;
    }

    public Context setSex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public ExcuseVibes getManuellExcusesVibes() {
        return manuellExcusesVibes;
    }

    public Context setManuellExcusesVibes(ExcuseVibes excusesVibes) {
        this.manuellExcusesVibes = excusesVibes;
        return this;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public Context setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
        return this;
    }

    public APIContext getApiContext() {
        return apiContext;
    }

    public Context setApiContext(APIContext apiContext) {
        this.apiContext = apiContext;
        return this;
    }

    public ExcuseVibeMode getExcuseVibeMode() {
        return excuseVibeMode;
    }

    public Context setExcuseVibeMode(ExcuseVibeMode excuseVibesMode) {
        this.excuseVibeMode = excuseVibesMode;
        return this;
    }

}
