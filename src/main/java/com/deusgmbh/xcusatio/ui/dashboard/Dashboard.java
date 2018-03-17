package com.deusgmbh.xcusatio.ui.dashboard;

import java.util.ArrayList;

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
    private BorderPane innerPane;

    public Dashboard() {
        initScenarioButtonPane();

        quickSettingsPane = new QuickSettingsPane();
        quickSettingsPane.prefWidthProperty().bind(this.widthProperty().multiply(QUICK_SETTINGS_PANE_WIDTH_MULTIPLIER));

        excusePane = new ExcusePane();

        innerPane = new BorderPane();
        innerPane.setBottom(this.scenarioButtonPane);
        innerPane.setCenter(this.excusePane);

        this.setRight(this.quickSettingsPane);
        this.setCenter(innerPane);
    }

    private void initScenarioButtonPane() {
        scenarioButtonPane = new HBox();
        scenarioButtonPane.prefHeightProperty()
                .bind(this.heightProperty().multiply(SCENARIO_BUTTON_PANE_HEIGHT_MULTIPLIER));
        scenarioButtonPane.setStyle("-fx-border-color: " + SCENARIO_BUTTON_PANE_BACKGROUND_BORDER_COLOR);

    }

    public HBox getScenarioButtonPane() {
        return this.scenarioButtonPane;
    }

    public void setExcuseLabel(String excuse) {
        this.excusePane = new ExcusePane(excuse);
        innerPane.setCenter(excusePane);
    }

    public void setThumbGesture(int value) {
        this.excusePane = new ExcusePane(value);
        innerPane.setCenter(excusePane);
    }

    public void setRUList(ArrayList<String> ruList) {
        this.quickSettingsPane.setRUList(ruList);
    }

    public boolean getAutoMoodToggle() {
        return this.quickSettingsPane.getAutoMoodToggle();
    }

    public boolean getMoodHumorToggle() {
        return this.quickSettingsPane.getMoodHumorToggle();
    }

    public boolean getMoodAggressionToggle() {
        return this.quickSettingsPane.getMoodAggressionToggle();
    }

    public boolean getMoodFawnToggle() {
        return this.quickSettingsPane.getMoodFawnToggle();
    }
}
