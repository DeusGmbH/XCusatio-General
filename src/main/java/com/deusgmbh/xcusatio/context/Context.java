package com.deusgmbh.xcusatio.context;

import com.deusgmbh.xcusatio.context.wildcard.APIDrivenContext;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
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
    private ExcusesVibes manuellExcusesVibes;

    private Lecturer lecturer;

    private APIDrivenContext apiContext;

    public Context(int age, Sex sex, ExcusesVibes excusesVibes, Lecturer lecturer, APIDrivenContext apiContext) {
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

    public int getAge() {
        return age;
    }

    public Context setAge(int age) {
        this.age = age;
        return this;
    }

    public Sex getSex() {
        return sex;
    }

    public Context setSex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public ExcusesVibes getManuellExcusesVibes() {
        return manuellExcusesVibes;
    }

    public Context setManuellExcusesVibes(ExcusesVibes excusesVibes) {
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

    public APIDrivenContext getApiContext() {
        return apiContext;
    }

    public Context setApiContext(APIDrivenContext apiContext) {
        this.apiContext = apiContext;
        return this;
    }

}
