package com.deusgmbh.xcusatio.ui.dashboard;

import com.deusgmbh.xcusatio.ui.dashboard.excusepane.ExcusePane;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * 
 * This is the ExcusePane class for the User Interface. This class is settled on
 * the Dashboard and is responsible for the output of the excuses.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class ScenarioReactionPane extends VBox {
    // TODO: Change
    private static final String DEFAULT_OUTPUT = "Hier könnte Ihre Ausrede stehen";

    private ExcusePane excusePane;
    private ThumbGesturePane thumbGesturePane;

    public ScenarioReactionPane() {
        this(DEFAULT_OUTPUT);
    }

    public ScenarioReactionPane(String excuse) {
        this.setAlignment(Pos.CENTER);

        excusePane = new ExcusePane(excuse);
        excusePane.prefHeightProperty()
                .bind(this.heightProperty());

        this.getChildren()
                .add(excusePane);
    }

    // TODO: @Pascal what is the unit of thumbGestureValue?
    public ScenarioReactionPane(int thumbGestureValue) {
        this.setAlignment(Pos.CENTER);

        thumbGesturePane = new ThumbGesturePane(thumbGestureValue);
        thumbGesturePane.prefHeightProperty()
                .bind(this.heightProperty());

        this.getChildren()
                .add(thumbGesturePane);
    }
}
