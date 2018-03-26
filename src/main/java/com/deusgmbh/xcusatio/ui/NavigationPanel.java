package com.deusgmbh.xcusatio.ui;

import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * 
 * This class is for initializing and filling the navigation pane of the user
 * interface with linked entries
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class NavigationPanel extends VBox {

    private static final double NAVIGATION_PANEL_BUTTON_HEIGHT_MULTIPLIER = 0.1;

    public NavigationPanel() {
        this.getStyleClass().add("navigation-bar");
    }

    public void addNavigationEntry(String text, Node paneToFocus, Consumer<Node> setContent) {
        Button btn = new Button(text);
        btn.maxWidthProperty().bind(this.widthProperty());
        btn.maxHeightProperty().bind(this.heightProperty().multiply(NAVIGATION_PANEL_BUTTON_HEIGHT_MULTIPLIER));
        btn.getStyleClass().add("navigation-button");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setContent.accept(paneToFocus);
                btn.getStyleClass().add("active");
            }
        });
        this.getChildren().add(btn);
    }
}
