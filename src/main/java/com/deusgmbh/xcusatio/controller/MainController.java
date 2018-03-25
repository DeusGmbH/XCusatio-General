package com.deusgmbh.xcusatio.controller;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.context.Context;
import com.deusgmbh.xcusatio.context.ContextHandler;
import com.deusgmbh.xcusatio.context.wildcard.Wildcards;
import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.excuses.ExcusesManager;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.lecturer.LecturerManager;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.scenarios.ScenarioManager;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.deusgmbh.xcusatio.data.usersettings.UserSettingsManager;
import com.deusgmbh.xcusatio.generator.ExcuseGenerator;

/**
 * This class handles inputs of the userinterface via an event listener
 * interface and serves as mediator between the userinterface and the text
 * processing
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class MainController {
    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());
    private Wildcards wildcards;
    private Consumer<List<Excuse>> triggerExcuseTableUpdate;
    private Consumer<List<Lecturer>> triggerLecturerTableUpdate;
    private ExcusesManager excusesManager;
    private LecturerManager lecturerManager;
    private ScenarioManager scenarioManager;
    private UserSettingsManager userSettingsManager;
    private ExcuseGenerator excuseGenerator;
    private ContextHandler contextHandler;

    public MainController() {
        Wildcards.initialize();
        excusesManager = new ExcusesManager();
        lecturerManager = new LecturerManager();
        scenarioManager = new ScenarioManager();
        userSettingsManager = new UserSettingsManager();
        excuseGenerator = new ExcuseGenerator();
        contextHandler = new ContextHandler();
    }

    public void generateExcuse(Scenario scenario, Consumer<String> displayExcuse, DoubleConsumer displayThumbGesture) {
        Context context = contextHandler.buildContext(this.getUserSettings(), this.getLecturers(), scenario);
        if (scenario.isExcuseType()) {
            displayExcuse.accept(excuseGenerator.getContextBasedExcuse(this.getExcuses(), context, scenario));
        } else {
            displayThumbGesture.accept(excuseGenerator.getThumbGesture(context));
        }
    }

    public UserSettings getUserSettings() {
        return userSettingsManager.get(0);
    }

    public List<Scenario> getScenarios() {
        return scenarioManager.get();
    }

    public List<Excuse> getExcuses() {
        return this.excusesManager.get();
    }

    public List<Lecturer> getLecturers() {
        return this.lecturerManager.get();
    }

    public List<Tag> getTags() {
        return Arrays.asList(Tag.values());
    }

    public List<String> getWildcardNames() {
        return wildcards.getNames();
    }

    public void removeExcuse(Excuse excuse) {
        this.excusesManager.remove(excuse);
        this.triggerExcuseTableUpdate.accept(this.getExcuses());
    }

    public void removeLecturer(Lecturer lecturer) {
        this.lecturerManager.remove(lecturer);
        this.triggerLecturerTableUpdate.accept(this.getLecturers());
    }

    public void addExcuse(Excuse excuse) {
        this.excusesManager.add(excuse);
        this.triggerExcuseTableUpdate.accept(this.getExcuses());
    }

    public void addLecturer(Lecturer lecturer) {
        this.lecturerManager.add(lecturer);
        this.triggerLecturerTableUpdate.accept(this.getLecturers());
    }

    public void editExcuse(int excuseID, Excuse editedExcuseObj) {
        this.excusesManager.edit(excuseID, editedExcuseObj);
        this.triggerExcuseTableUpdate.accept(this.getExcuses());
    }

    public void editLecturer(int lecturerID, Lecturer editedLecturerObj) {
        this.lecturerManager.edit(lecturerID, editedLecturerObj);
        this.triggerLecturerTableUpdate.accept(this.getLecturers());
    }

    public void registerUpdateExcuseTable(Consumer<List<Excuse>> updateExcuseTable) {
        this.triggerExcuseTableUpdate = updateExcuseTable;
        this.triggerExcuseTableUpdate.accept(this.getExcuses());
    }

    public void registerUpdateLecturerTable(Consumer<List<Lecturer>> updateLecturerTable) {
        this.triggerLecturerTableUpdate = updateLecturerTable;
        this.triggerLecturerTableUpdate.accept(this.getLecturers());
    }
}
