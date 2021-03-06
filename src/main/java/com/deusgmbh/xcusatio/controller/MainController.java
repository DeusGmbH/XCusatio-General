package com.deusgmbh.xcusatio.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.context.Context;
import com.deusgmbh.xcusatio.context.ContextHandler;
import com.deusgmbh.xcusatio.context.wildcard.WildcardTransformer;
import com.deusgmbh.xcusatio.context.wildcard.WildcardTransformers;
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

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

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
    private ExcusesManager excusesManager;
    private LecturerManager lecturerManager;
    private ScenarioManager scenarioManager;
    private UserSettingsManager userSettingsManager;
    private ExcuseGenerator excuseGenerator;
    private ContextHandler contextHandler;
    private WildcardTransformers wildcards;

    public MainController() {
        wildcards = new WildcardTransformers();
        excusesManager = new ExcusesManager();
        lecturerManager = new LecturerManager();
        scenarioManager = new ScenarioManager();
        userSettingsManager = new UserSettingsManager();
        excuseGenerator = new ExcuseGenerator(wildcards);
        contextHandler = new ContextHandler();
    }

    public void generateExcuse(Scenario scenario, Consumer<String> displayExcuse, DoubleConsumer displayThumbGesture) {
        Context context = contextHandler.buildContext(this.getUserSettings()
                .getValue(), this.getLecturers(), scenario);
        if (scenario.isExcuseType()) {
            Excuse excuse = excuseGenerator.getContextBasedExcuse(this.getExcuses(), context, scenario);
            this.excusesManager.applyFor(excuse, e -> e.setLastUsed(new Date()));
            displayExcuse.accept(wildcards.replace(excuse.getText(), context.getApiContext()));
        } else {
            displayThumbGesture.accept(excuseGenerator.getThumbGesture(context));
        }
    }

    public ObjectProperty<UserSettings> getUserSettings() {
        return userSettingsManager.get(0);
    }

    public ObservableList<Scenario> getScenarios() {
        return scenarioManager.get();
    }

    public ObservableList<Excuse> getExcuses() {
        return this.excusesManager.get();
    }

    public ObservableList<Lecturer> getLecturers() {
        return this.lecturerManager.get();
    }

    public ObservableList<String> getMostRecentlyUsedExcuses() {
        return this.excusesManager.getMostRecentlyUsed();
    }

    public List<Tag> getTags() {
        return Arrays.asList(Tag.values());
    }

    public List<String> getWildcardNames() {
        return wildcards.getNames();
    }

    public Set<WildcardTransformer> getWildcards() {
        return wildcards.getWildcards();
    }

    public void persistAllElements() {
        this.excusesManager.persist();
        this.lecturerManager.persist();
        this.scenarioManager.persist();
        this.userSettingsManager.persist();
    }

    public void loadAllElements() {
        this.excusesManager.load();
        this.lecturerManager.load();
        this.scenarioManager.load();
        this.userSettingsManager.load();
    }

    public void resetAllElements() {
        this.excusesManager.reset();
        this.lecturerManager.reset();
        this.scenarioManager.reset();
        this.userSettingsManager.reset();
    }
}
