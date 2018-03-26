package com.deusgmbh.xcusatio;

import com.deusgmbh.xcusatio.controller.MainController;
import com.deusgmbh.xcusatio.ui.XCusatioWindow;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the entry point of the application. It is responsible for
 * instantiating the ui and the controller. After that it is registering all
 * callbacks between those two components.
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class XCusatio extends Application {
	private XCusatioWindow mainWindow;
	private MainController mainController;

	public XCusatio() {
		mainWindow = new XCusatioWindow();
		mainController = new MainController();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		mainWindow.setScenarios(mainController.getScenarios());
		mainWindow.registerScenarioButtonActionEvent(mainController::generateExcuse);
		mainWindow.registerUserSettingsSupplier(mainController::getUserSettings);
		mainWindow.start(stage);

		registerCallbacks();
	}

	public void registerCallbacks() {

	}

}
