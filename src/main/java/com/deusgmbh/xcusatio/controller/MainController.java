package com.deusgmbh.xcusatio.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.context.wildcard.Wildcards;
import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;

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
    private Consumer<List<Excuse>> updateExcuseTable;
    private Consumer<List<Lecturer>> updateLecturerTable;

    public MainController() {
        wildcards = new Wildcards();
    }

    public void generateExcuse(Scenario scenario) {
        // TODO: Write generateExcuse method
    }

    public List<String> getWildcardNames() {
        return wildcards.getNames();
    }

    public void generateExcuse(String excuseType) {
        // TODO: Write generateExcuse method
    }

    public List<Scenario> getScenarios() {
        List<Scenario> scenarioList = new ArrayList<Scenario>();
        // TODO Get all scenarios
        return scenarioList;
    }

    public List<Excuse> getExcuses() {
        // TODO Write "real" method for getting excuses;
        // Following method is only for testing reasons => to replace!!
        HashSet<String> tag1 = new HashSet<String>();
        tag1.add("late");
        tag1.add("opnv");
        HashSet<String> tag2 = new HashSet<String>();
        tag2.add("project");
        tag2.add("opnv");
        Excuse excuse1 = new Excuse("Die Bahn kam zu spät", tag1);
        Excuse excuse2 = new Excuse("Der Regen hat unser Projekt zerstört", tag2);
        List<Excuse> excusesList = new ArrayList<Excuse>();
        excusesList.add(excuse1);
        excusesList.add(excuse2);
        return excusesList;
    }

    public List<Lecturer> getLecturers() {
        // TODO Write "real" method for getting excuses;
        // Following method is only for testing reasons => to replace!!
        HashSet<String> tag1 = new HashSet<String>();
        tag1.add("humor");
        tag1.add("aggressive");
        List<String> lectures1 = new ArrayList<String>();
        lectures1.add("Algorithmen");
        lectures1.add("Statistik");
        lectures1.add("Logik");
        lectures1.add("Lineare Algebra");
        lectures1.add("Analysis");
        HashSet<String> tag2 = new HashSet<String>();
        tag2.add("fawn");
        List<String> lectures2 = new ArrayList<String>();
        lectures2.add("C");
        lectures2.add("Softwareengineering");
        Lecturer lecturer1 = new Lecturer("Stroetmann", lectures1, tag1);
        Lecturer lecturer2 = new Lecturer("Kruse", lectures2, tag2);
        List<Lecturer> lecturerList = new ArrayList<Lecturer>();
        lecturerList.add(lecturer1);
        lecturerList.add(lecturer2);
        return lecturerList;
    }

    public void removeExcuse(Excuse excuse) {
        // TODO: Write removeExcuse method (via StorageUnit)
        // should end with following line:
        // this.updateExcuseTable.accept(TODO: fill in new excuseList);
    }

    public void removeLecturer(Lecturer lecturer) {
        // TODO: Write removeLecturer method (via StorageUnit)
        // should end with following line:
        // this.updateLecturerTable.accept(TODO: fill in new lecturerList);
    }

    public void addExcuse(Excuse excuse) {
        // TODO: Write addExcuse method (via StorageUnit)
        // should end with following line:
        // this.updateExcuseTable.accept(TODO: fill in new excuseList);
    }

    public void addLecturer(Lecturer lecturer) {
        // TODO: Write addLecturer method (via StorageUnit)
        // should end with following line:
        // this.updateLecturerTable.accept(TODO: fill in new lecturerList);
    }

    public void editExcuse(Excuse oldExcuseObj, Excuse editedExcuseObj) {
        // TODO: Write editExcuse method (via StorateUnit)
        // should end with following line:
        // this.updateExcuseTable.accept(TODO: fill in new excuseList)
    }

    public void editLecturer(Lecturer oldLecturerObj, Lecturer editedLecturerObj) {
        // TODO: Write editLecturer method (via StorateUnit)
        // should end with following line:
        // this.updateLecturerTable.accept(TODO: fill in new lecturerList);
    }

    public void registerUpdateExcuseTableEvent(Consumer<List<Excuse>> updateExcuseTable) {
        this.updateExcuseTable = updateExcuseTable;
    }

    public void registerUpdateLecturerTableEvent(Consumer<List<Lecturer>> updateLecturerTable) {
        this.updateLecturerTable = updateLecturerTable;
    }
}
