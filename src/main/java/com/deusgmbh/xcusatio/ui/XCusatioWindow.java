package com.deusgmbh.xcusatio.ui;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.ui.dashboard.Dashboard;
import com.deusgmbh.xcusatio.ui.editor.Editor;
import com.deusgmbh.xcusatio.ui.profilsettings.ProfileSettings;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private static final int WINDOW_DEF_WIDTH = 800;
    private static final int WINDOW_DEF_HEIGHT = 600;

    private static final String NAVIGATION_PANEL_BACKGROUND_COLOR = "#888888";
    private static final double NAVIGATION_PANEL_WIDTH_MULTIPLIER = 0.1;
    private static final double NAVIGATION_PANEL_BUTTON_HEIGHT_MULTIPLIER = 0.1;

    private static final String DASHBOARD_TAB_NAME = "Zurück";
    private static final String EDITOR_TAB_NAME = "Editor";
    private static final String PROFILE_SETTINGS_TAB_NAME = "Profile";

    private BorderPane main;
    private VBox navigationPanel;
    private Dashboard dashboard;
    private Editor editor;
    private ProfileSettings profileSettings;

    @Override
    public void start(Stage stage) throws Exception {
        initMainStage(stage);
        dashboard = new Dashboard();
        editor = new Editor();
        profileSettings = new ProfileSettings();
        initNavigationPanel();

        main.setLeft(navigationPanel);
        main.setCenter(dashboard);

        stage.show();
    }

    public void createScenarioButtons(ArrayList<Scenario> scenarioList, Consumer<Scenario> generateExcuse) {
        scenarioList.stream().forEach(scenario -> {
            Button tmpBtn = new Button(scenario.getUIName());
            tmpBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    generateExcuse.accept(scenario);
                }
            });
            dashboard.getScenarioButtonPane().getChildren().add(tmpBtn);
        });
    }

    private BorderPane initMainStage(Stage stage) {
        main = new BorderPane();
        Scene scene = new Scene(main);
        stage.setWidth(WINDOW_DEF_WIDTH);
        stage.setHeight(WINDOW_DEF_HEIGHT);
        stage.setMinWidth(WINDOW_DEF_WIDTH);
        stage.setMinHeight(WINDOW_DEF_HEIGHT);
        stage.setScene(scene);
        stage.setTitle(WINDOW_TITLE);
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
                    Number newSceneWidth) {
            }
        });
        return main;
    }

    private void initNavigationPanel() {
        navigationPanel = new VBox();
        navigationPanel.setStyle("-fx-background-color: " + NAVIGATION_PANEL_BACKGROUND_COLOR);
        navigationPanel.prefWidthProperty().bind(main.widthProperty().multiply(NAVIGATION_PANEL_WIDTH_MULTIPLIER));

        Button switchFocusToDashboardBtn = createNavPaneButton(DASHBOARD_TAB_NAME, dashboard);
        Button switchFocusToEditorBtn = createNavPaneButton(EDITOR_TAB_NAME, editor);
        Button switchFocusToProfileSettingsBtn = createNavPaneButton(PROFILE_SETTINGS_TAB_NAME, profileSettings);

        navigationPanel.getChildren().addAll(switchFocusToDashboardBtn, switchFocusToEditorBtn,
                switchFocusToProfileSettingsBtn);
    }

    private Button createNavPaneButton(String text, Node paneToFocus) {
        Button btn = new Button(text);
        btn.maxWidthProperty().bind(navigationPanel.widthProperty());
        btn.maxHeightProperty()
                .bind(navigationPanel.heightProperty().multiply(NAVIGATION_PANEL_BUTTON_HEIGHT_MULTIPLIER));
        // btn.setStyle("-fx-background-color: rgb(240, 248, 255);
        // -fx-font-size: 14px; [...]");

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.setCenter(paneToFocus);
            }
        });
        return btn;
    }

    public void setExcuseLabel(String excuse) {
        this.dashboard.setExcuseLabel(excuse);
    }

    public void setThumbGesture(int value) {
        this.dashboard.setThumbGesture(value);
    }

    public void setRUList(ArrayList<String> ruList) {
        this.dashboard.setRUList(ruList);
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
}