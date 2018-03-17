package com.deusgmbh.xcusatio.ui.dashboard;

import java.util.ArrayList;

import com.deusgmbh.xcusatio.data.scenarios.Scenario;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * 
 * This is the Dashboard class for the User Interface. This class creates the
 * Dashboard tab with all of its components
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class Dashboard extends BorderPane {
    private static final double QUICK_SETTINGS_PANE_WIDTH_MULTIPLIER = 0.3;
    private static final double SCENARIO_BUTTON_PANE_HEIGHT_MULTIPLIER = 0.25;
    private static final String SCENARIO_BUTTON_PANE_BACKGROUND_BORDER_COLOR = "#000000";

    private HBox scenarioButtonPane;
    private QuickSettingsPane quickSettingsPane;
    private ExcusePane excusePane;

    public Dashboard() {
        initScenarioButtonPane();

        quickSettingsPane = new QuickSettingsPane();
        quickSettingsPane.prefWidthProperty().bind(this.widthProperty().multiply(QUICK_SETTINGS_PANE_WIDTH_MULTIPLIER));

        excusePane = new ExcusePane();

        this.setBottom(this.scenarioButtonPane);
        this.setRight(this.quickSettingsPane);
        this.setCenter(this.excusePane);
    }

    private void initScenarioButtonPane() {
        scenarioButtonPane = new HBox();
        scenarioButtonPane.prefHeightProperty()
                .bind(this.heightProperty().multiply(SCENARIO_BUTTON_PANE_HEIGHT_MULTIPLIER));
        scenarioButtonPane.setStyle("-fx-border-color: " + SCENARIO_BUTTON_PANE_BACKGROUND_BORDER_COLOR);

        // only for testing purposes. Later these buttons should be generated
        // by getting every scenario given in scenarioConfig
        this.getScenarioUINames().stream().forEach(scenario -> {
            Button tmpBtn = new Button(scenario.getUIName());
            scenarioButtonPane.getChildren().add(tmpBtn);
        });
    }

    // only for testing purposes
    private ArrayList<Scenario> getScenarioUINames() {
        ArrayList<Scenario> testScenarioList = new ArrayList<Scenario>();
        testScenarioList.add(new Scenario("Verspätung"));
        testScenarioList.add(new Scenario("Glücksrad"));
        testScenarioList.add(new Scenario("Projektabgabe"));
        testScenarioList.add(new Scenario("Daumengeste"));

        return testScenarioList;
    }
}
