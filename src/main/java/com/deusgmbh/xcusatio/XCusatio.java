package com.deusgmbh.xcusatio;

import com.deusgmbh.xcusatio.controller.MainController;
import com.deusgmbh.xcusatio.ui.XCusatioWindow;

import javafx.application.Application;
import javafx.stage.Stage;

public class XCusatio extends Application {
    private XCusatioWindow mainWindow;
    private MainController mainController;

    public XCusatio() {
        mainWindow = new XCusatioWindow();
    }

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        mainController = new MainController();
        mainWindow.start(stage);
        // register callbacks
    }

}
