package com.deusgmbh.xcusatio.ui;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.deusgmbh.xcusatio.ui.dashboard.Dashboard;
import com.deusgmbh.xcusatio.ui.editor.Editor;
import com.deusgmbh.xcusatio.ui.profilsettings.ProfileSettings;
import com.deusgmbh.xcusatio.ui.utility.ResizeHelper;
import com.deusgmbh.xcusatio.util.TriConsumer;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * This is the main class for the User Interface. This class creates the main
 * scene and merges all components
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class XCusatioWindow extends Application {
    private static final String WINDOW_TITLE = "Deus GmbH - xCusatio";

    private static final int WINDOW_BORDER_HEIGHT = 40;
    private static final int WINDOW_DEF_WIDTH = 1280;
    private static final int WINDOW_DEF_HEIGHT = 720 + WINDOW_BORDER_HEIGHT;
    private static final int WINDOW_MAX_WIDTH = 1920;
    private static final int WINDOW_MAX_HEIGHT = 1080;

    private static final double NAVIGATION_PANEL_WIDTH_MULTIPLIER = 0.11;
    private static final String DASHBOARD_TAB_NAME = "Generator";
    private static final String EDITOR_TAB_NAME = "Editor";
    private static final String PROFILE_SETTINGS_TAB_NAME = "Profile";

    private static final String SCENE_STYLESHEET_PATH = "file:assets/stage_stylesheet.css";

    private BorderPane main;
    private WindowBorder windowBorder;
    private NavigationPanel navigationPanel;
    private Dashboard dashboard;
    private Editor editor;
    private ProfileSettings profileSettings;
    private List<Scenario> scenarioList;

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        initMainStage(this.stage);

        windowBorder = new WindowBorder(minimizeWindow, restoreWindow, closeWindow);
        windowBorder.setPrefHeight(WINDOW_BORDER_HEIGHT);

        navigationPanel = new NavigationPanel();
        navigationPanel.prefWidthProperty()
                .bind(main.widthProperty()
                        .multiply(NAVIGATION_PANEL_WIDTH_MULTIPLIER));

        dashboard = new Dashboard();
        editor = new Editor();
        profileSettings = new ProfileSettings();

        navigationPanel.addNavigationEntry(DASHBOARD_TAB_NAME, dashboard, this::setContent);
        navigationPanel.addNavigationEntry(EDITOR_TAB_NAME, editor, this::setContent);
        navigationPanel.addNavigationEntry(PROFILE_SETTINGS_TAB_NAME, profileSettings, this::setContent);
        navigationPanel.getChildren()
                .get(0)
                .getStyleClass()
                .add("active");

        windowBorder.toFront();

        main.setTop(windowBorder);
        main.setLeft(navigationPanel);
        main.setCenter(dashboard);

        stage.show();
    }

    private void setContent(Node node) {
        navigationPanel.getChildren()
                .stream()
                .forEach(btn -> {
                    if (btn.getStyleClass()
                            .get(btn.getStyleClass()
                                    .size() - 1)
                            .equals("active")) {
                        btn.getStyleClass()
                                .remove(btn.getStyleClass()
                                        .size() - 1);
                    }
                });
        main.setCenter(node);
    }

    private BorderPane initMainStage(Stage stage) {
        main = new BorderPane();
        stage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(main);
        scene.getStylesheets()
                .add(SCENE_STYLESHEET_PATH);
        stage.setWidth(WINDOW_DEF_WIDTH);
        stage.setHeight(WINDOW_DEF_HEIGHT);
        stage.setMinWidth(WINDOW_DEF_WIDTH);
        stage.setMinHeight(WINDOW_DEF_HEIGHT);
        stage.setMaxWidth(WINDOW_MAX_WIDTH);
        stage.setMaxHeight(WINDOW_MAX_HEIGHT);
        stage.setScene(scene);
        stage.setTitle(WINDOW_TITLE);
        ResizeHelper.addResizeListener(stage, stage.getMinWidth(), stage.getMinHeight(), stage.getMaxWidth(),
                stage.getMaxHeight());

        return main;
    }

    private EventHandler<ActionEvent> minimizeWindow = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            stage.setIconified(true);
        }
    };

    private boolean isFullScreen = false;
    private EventHandler<ActionEvent> restoreWindow = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            if (!isFullScreen) {
                isFullScreen = true;
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());
                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
            } else {
                isFullScreen = false;
                stage.setWidth(stage.getMinWidth());
                stage.setHeight(stage.getMinHeight());
                stage.setX((bounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((bounds.getHeight() - stage.getHeight()) / 2);
            }
            windowBorder.toggleRestoreButton(isFullScreen);
        }
    };

    private EventHandler<ActionEvent> closeWindow = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            stage.close();
        }
    };

    public void registerScenarioButtonActionEvent(
            TriConsumer<Scenario, Consumer<String>, DoubleConsumer> generateExcuse) {
        dashboard.createScenarioButtons(scenarioList, generateExcuse);
    }

    public void registerTagsSupplier(Supplier<List<Tag>> tagsSetSupplier) {
        editor.registerTagsSetSupplier(tagsSetSupplier);
    }

    public void registerWildcardSupplier(Supplier<List<String>> wildcardSetSupplier) {
        editor.registerWildcardSetSupplier(wildcardSetSupplier);
    }

    public void setExcuseLabel(String excuse) {
        this.dashboard.setExcuseLabel(excuse);
    }

    public void setThumbGesture(int value) {
        this.dashboard.setThumbGesture(value);
    }

    public boolean getAutoMoodToggle() {
        return this.dashboard.getAutoMoodToggle();
    }

    public boolean getMoodHumorToggle() {
        return this.dashboard.getMoodHumorToggle();
    }

    public boolean getMoodAggressionToggle() {
        return this.dashboard.getMoodAggressionToggle();
    }

    public boolean getMoodFawnToggle() {
        return this.dashboard.getMoodFawnToggle();
    }

    public void setScenarios(List<Scenario> scenarioList) {
        this.scenarioList = scenarioList;
    }

    public void registerExcuseSupplier(Supplier<ObservableList<Excuse>> excuseSupplier) {
        this.editor.registerExcuseSupplier(excuseSupplier);
    }

    public void registerLecturerSupplier(Supplier<ObservableList<Lecturer>> lecturerSupplier) {
        this.editor.registerLecturerSupplier(lecturerSupplier);
    }

    public void registerMostRecentlyUsedExcusesSupplier(ObservableList<String> mostRecentlyUsedObservableList) {
        this.dashboard.registerMostRecentlyUsedExcuses(mostRecentlyUsedObservableList);
    }

    public void registerChangeUserSettingsEvent(Consumer<UserSettings> userSettingsConsumer) {
        profileSettings.createEditProfileBtnAction(userSettingsConsumer);
    }

    public void registerUserSettings(ObjectProperty<UserSettings> userSettings) {
        this.dashboard.registerUserSettings(userSettings);
        this.profileSettings.registerUserSettings(userSettings);
    }
}
