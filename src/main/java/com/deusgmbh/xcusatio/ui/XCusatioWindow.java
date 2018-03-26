package com.deusgmbh.xcusatio.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.deusgmbh.xcusatio.ui.dashboard.Dashboard;
import com.deusgmbh.xcusatio.ui.editor.Editor;
import com.deusgmbh.xcusatio.ui.profilsettings.ProfileSettings;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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

	private static final double NAVIGATION_PANEL_WIDTH_MULTIPLIER = 0.1;
	private static final String DASHBOARD_TAB_NAME = "Zur\u00fcck";
	private static final String EDITOR_TAB_NAME = "Editor";
	private static final String PROFILE_SETTINGS_TAB_NAME = "Profil";

	private BorderPane main;
	private NavigationPanel navigationPanel;
	private Dashboard dashboard;
	private Editor editor;
	private ProfileSettings profileSettings;
	private List<Scenario> scenarioList;

	@Override
	public void start(Stage stage) throws Exception {
		initMainStage(stage);

		navigationPanel = new NavigationPanel();
		navigationPanel.prefWidthProperty().bind(main.widthProperty().multiply(NAVIGATION_PANEL_WIDTH_MULTIPLIER));

		dashboard = new Dashboard();
		editor = new Editor();
		profileSettings = new ProfileSettings();

		navigationPanel.addNavigationEntry(DASHBOARD_TAB_NAME, dashboard, main);
		navigationPanel.addNavigationEntry(EDITOR_TAB_NAME, editor, main);
		navigationPanel.addNavigationEntry(PROFILE_SETTINGS_TAB_NAME, profileSettings, main);

		main.setLeft(navigationPanel);
		main.setCenter(dashboard);

		stage.show();
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

		return main;
	}

	public void registerScenarioButtonActionEvent(Consumer<Scenario> generateExcuse) {
		dashboard.createScenarioButtons(scenarioList, generateExcuse);
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

	public void setScenarios(List<Scenario> scenarioList) {
		this.scenarioList = scenarioList;
	}

	public void registerUserSettingsSupplier(Supplier<UserSettings> userSettingsSupplier) {
		profileSettings.registerUserSettingsSupplier(userSettingsSupplier);
	}

	public void registerChangeUserSettingsEvent(Consumer<UserSettings> userSettingsConsumer) {
		profileSettings.createEditProfileBtnAction(userSettingsConsumer);
	}

}