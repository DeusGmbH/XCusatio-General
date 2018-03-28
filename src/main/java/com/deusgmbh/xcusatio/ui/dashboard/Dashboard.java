package com.deusgmbh.xcusatio.ui.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.scenarios.ScenarioType;
import com.deusgmbh.xcusatio.util.TriConsumer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
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

    // TODO: Change ui names; discuss in design review
    private static final String THUMB_GESTURE_UI_NAME = "Daumengeste";
    private static final String WHEEL_OF_FORTUNE_UI_NAME = "Glücksrad";
    private static final String LATE_ARRIVAL_UI_NAME = "Verspätung";
    private static final String DELAYED_SUBMISSION_UI_NAME = "Abgabe";

    private HBox scenarioButtonPane;
    private ScenarioReactionPane reactionPane;
    private QuickSettingsPane quickSettingsPane;
    private RecentlyUsedPane recentlyUsedPane;
    private BorderPane leftPane;
    private BorderPane rightPane;

    public Dashboard() {
        scenarioButtonPane = new HBox();
        scenarioButtonPane.prefHeightProperty()
                .bind(this.heightProperty().multiply(SCENARIO_BUTTON_PANE_HEIGHT_MULTIPLIER));

        reactionPane = new ScenarioReactionPane();

        quickSettingsPane = new QuickSettingsPane();
        quickSettingsPane.prefWidthProperty().bind(this.widthProperty().multiply(QUICK_SETTINGS_PANE_WIDTH_MULTIPLIER));

        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);

        // left and right pane for designing reasons - left side output &
        // scenario buttons; right side quick settings & recently used
        leftPane = new BorderPane();
        leftPane.setBottom(this.scenarioButtonPane);
        leftPane.setCenter(this.reactionPane);

        rightPane = new BorderPane();
        rightPane.setTop(quickSettingsPane);
        rightPane.setCenter(recentlyUsedPane);

        this.setRight(this.quickSettingsPane);
        this.setCenter(leftPane);
    }

    public void createScenarioButtons(List<Scenario> scenarioList,
            TriConsumer<Scenario, Consumer<String>, DoubleConsumer> generateExcuse) {
        Dashboard thisDashboard = this;
        scenarioList.stream().forEach(scenario -> {
            Button tmpBtn = new Button(getUINameByType(scenario.getScenarioType()));
            tmpBtn.prefWidthProperty().bind(this.widthProperty().multiply(1d / scenarioList.size()));
            tmpBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    generateExcuse.accept(scenario, thisDashboard::setExcuseLabel, thisDashboard::setThumbGesture);
                }
            });
            scenarioButtonPane.getChildren().add(tmpBtn);
        });
    }

    public void setExcuseLabel(String excuse) {
        this.reactionPane = new ScenarioReactionPane(excuse);
        leftPane.setCenter(reactionPane);
    }

    public void setThumbGesture(double value) {
        // TODO: calculate thumb rotation in steps
        this.reactionPane = new ScenarioReactionPane((int) (value * 180));
        leftPane.setCenter(reactionPane);
    }

    public void setRUList(ArrayList<String> ruList) {
        this.recentlyUsedPane.setRUList(ruList);
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

    private static String getUINameByType(ScenarioType scenarioType) {
        switch (scenarioType) {
        case THUMBGESTURE:
            return THUMB_GESTURE_UI_NAME;
        case WHEELOFFORTUNE:
            return WHEEL_OF_FORTUNE_UI_NAME;
        case LATE_ARRIVAL:
            return LATE_ARRIVAL_UI_NAME;
        case DELAYED_SUBMISSION:
            return DELAYED_SUBMISSION_UI_NAME;
        default:
            return scenarioType.toString();
        }
    }
}
