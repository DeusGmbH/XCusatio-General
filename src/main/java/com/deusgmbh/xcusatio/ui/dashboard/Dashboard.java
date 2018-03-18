package com.deusgmbh.xcusatio.ui.dashboard;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.deusgmbh.xcusatio.data.scenarios.Scenario;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private ReactionPane reactionPane;
    private BorderPane innerPane;

    public Dashboard() {
        initScenarioButtonPane();

        quickSettingsPane = new QuickSettingsPane();
        quickSettingsPane.prefWidthProperty().bind(this.widthProperty().multiply(QUICK_SETTINGS_PANE_WIDTH_MULTIPLIER));

        reactionPane = new ReactionPane();

        // innerPane for designing reasons for giving right side all height of
        // dashboard
        innerPane = new BorderPane();
        innerPane.setBottom(this.scenarioButtonPane);
        innerPane.setCenter(this.reactionPane);

        this.setRight(this.quickSettingsPane);
        this.setCenter(innerPane);
    }

    private void initScenarioButtonPane() {
        scenarioButtonPane = new HBox();
        scenarioButtonPane.prefHeightProperty()
                .bind(this.heightProperty().multiply(SCENARIO_BUTTON_PANE_HEIGHT_MULTIPLIER));
        scenarioButtonPane.setStyle("-fx-border-color: " + SCENARIO_BUTTON_PANE_BACKGROUND_BORDER_COLOR);
    }

    public void createScenarioButtons(ArrayList<Scenario> scenarioList, Consumer<String> generateExcuse) {
        scenarioList.stream().forEach(scenario -> {
            Button tmpBtn = new Button(scenario.getUIName());
            tmpBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    generateExcuse.accept(scenario.getScenarioType());
                }
            });
            scenarioButtonPane.getChildren().add(tmpBtn);
        });
    }

    public void setExcuseLabel(String excuse) {
        this.reactionPane = new ReactionPane(excuse);
        innerPane.setCenter(reactionPane);
    }

    public void setThumbGesture(int value) {
        this.reactionPane = new ReactionPane(value);
        innerPane.setCenter(reactionPane);
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
