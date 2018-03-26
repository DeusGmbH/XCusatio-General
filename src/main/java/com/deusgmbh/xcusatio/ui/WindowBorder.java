package com.deusgmbh.xcusatio.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class WindowBorder extends HBox {
    private static final String WINDOW_BORDER_STYLESHEET_PATH = "file:assets/window_border_stylesheet.css";

    private static final String CLOSE_BUTTON_UNICODE = "\u274C";
    private static final String FULLSCREEN_BUTTON_UNICODE = "\u25FB";
    private static final String RESTORE_BUTTON_UNICODE = "\u2750";
    private static final String MINIMIZE_BUTTON_UNICODE = "\u2581";

    private Button minimizeButton;
    private Button fullScreenButton;
    private Button closeButton;

    public WindowBorder(EventHandler<ActionEvent> minimizeWindow, EventHandler<ActionEvent> restoreWindow,
            EventHandler<ActionEvent> closeWindow) {
        this.getStyleClass().add("window-border");

        minimizeButton = new Button(MINIMIZE_BUTTON_UNICODE);
        fullScreenButton = new Button(FULLSCREEN_BUTTON_UNICODE);
        closeButton = new Button(CLOSE_BUTTON_UNICODE);

        minimizeButton.setMaxHeight(20);

        minimizeButton.setOnAction(minimizeWindow);
        fullScreenButton.setOnAction(restoreWindow);
        closeButton.setOnAction(closeWindow);

        this.getChildren().addAll(minimizeButton, fullScreenButton, closeButton);
        this.setAlignment(Pos.TOP_RIGHT);

    }

    public void toggleRestoreButton(boolean isFullScreen) {
        if (isFullScreen) {
            fullScreenButton.setText(RESTORE_BUTTON_UNICODE);
        } else {
            fullScreenButton.setText(FULLSCREEN_BUTTON_UNICODE);
        }
    }
}
