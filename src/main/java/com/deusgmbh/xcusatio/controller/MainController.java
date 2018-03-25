package com.deusgmbh.xcusatio.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.context.wildcard.Wildcards;
import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.scenarios.ScenarioType;
import com.deusgmbh.xcusatio.data.tags.Tag;

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

    public MainController() {
        wildcards = new Wildcards();
    }

    public void generateExcuse(Scenario scenario) {
        // TODO: Write generateExcuse method
    }

    public void generateExcuse(String excuseType) {
        // TODO: Write generateExcuse method
    }

    public List<Scenario> getScenarios() {
        List<Scenario> scenarioList = new ArrayList<>();
        // TODO Get all scenarios and write into this list
        return scenarioList;
    }

    public List<Excuse> getExcuses() {
        // TODO Write "real" method for getting excuses;
        // Following method is only for testing reasons => to replace!!
        List<Tag> tag1 = new ArrayList<>();
        tag1.add(Tag.TRAIN);
        tag1.add(Tag.MALE);
        tag1.add(Tag.FEMALE);
        List<Tag> tag2 = new ArrayList<>();
        tag2.add(Tag.RANNY);
        tag2.add(Tag.MALE);
        tag2.add(Tag.FEMALE);
        Excuse excuse1 = new Excuse("Die Bahn kam zu spät", ScenarioType.LATE_ARRIVAL, tag1);
        Excuse excuse2 = new Excuse("Der Regen hat unser Projekt zerstört", ScenarioType.DELAYED_SUBMISSION, tag2);
        List<Excuse> excusesList = new ArrayList<>();
        excusesList.add(excuse1);
        excusesList.add(excuse2);
        return excusesList;
    }

    public List<Lecturer> getLecturers() {
        // TODO Write "real" method for getting excuses;
        // Following method is only for testing reasons => to replace!!
        List<Tag> tag1 = new ArrayList<>();
        tag1.add(Tag.FUNNY);
        tag1.add(Tag.AGGRESSIVE);
        List<String> lectures1 = new ArrayList<>();
        lectures1.add("Algorithmen");
        lectures1.add("Statistik");
        lectures1.add("Logik");
        lectures1.add("Lineare Algebra");
        lectures1.add("Analysis");
        List<Tag> tag2 = new ArrayList<>();
        tag2.add(Tag.SUCKUP);
        List<String> lectures2 = new ArrayList<>();
        lectures2.add("C");
        lectures2.add("Softwareengineering");
        Lecturer lecturer1 = new Lecturer("Stroetmann", lectures1, tag1);
        Lecturer lecturer2 = new Lecturer("Kruse", lectures2, tag2);
        List<Lecturer> lecturerList = new ArrayList<>();
        lecturerList.add(lecturer1);
        lecturerList.add(lecturer2);
        return lecturerList;
    }

    public List<Tag> getTags() {
        return Arrays.asList(Tag.values());
    }

    public List<String> getWildcardNames() {
        return wildcards.getNames();
    }

    public void removeExcuse(Excuse excuse) {
        // TODO: Write removeExcuse method (via StorageUnit)
        // should end with following line:
        // this.triggerExcuseTableUpdate.accept(TODO: fill in new excuseList);
    }

    public void removeLecturer(Lecturer lecturer) {
        // TODO: Write removeLecturer method (via StorageUnit)
        // should end with following line:
        // this.triggerLecturerTableUpdate.accept(TODO: fill in new
        // lecturerList);
    }

    public void addExcuse(Excuse excuse) {
        // TODO: Write addExcuse method (via StorageUnit)
        // should end with following line:
        // this.triggerExcuseTableUpdate.accept(TODO: fill in new excuseList);
    }

    public void addLecturer(Lecturer lecturer) {
        // TODO: Write addLecturer method (via StorageUnit)
        // should end with following line:
        // this.triggerLecturerTableUpdate.accept(TODO: fill in new
        // lecturerList);
    }

    public void editExcuse(int excuseID, Excuse editedExcuseObj) {
        // TODO: Write editExcuse method (via StorateUnit)
        // should end with following line:
        // this.triggerExcuseTableUpdate.accept(TODO: fill in new excuseList)
    }

    public void editLecturer(int lecturerID, Lecturer editedLecturerObj) {
        // TODO: Write editLecturer method (via StorateUnit)
        // should end with following line:
        // this.triggerLecturerTableUpdate.accept(TODO: fill in new
        // lecturerList);
    }

    public void updateExcuseTable(Consumer<List<Excuse>> updateExcuseTable) {
        this.triggerExcuseTableUpdate = updateExcuseTable;
        this.triggerExcuseTableUpdate.accept(getExcuses());
    }

    public void updateLecturerTable(Consumer<List<Lecturer>> updateLecturerTable) {
        this.triggerLecturerTableUpdate = updateLecturerTable;
        this.triggerLecturerTableUpdate.accept(getLecturers());
    }
}
