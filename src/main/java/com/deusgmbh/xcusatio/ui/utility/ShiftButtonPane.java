package com.deusgmbh.xcusatio.ui.utility;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * 
 * This class is used for the DoubleListView.java class. It creates the
 * ButtonShiftPane, with which you can shift entries between the two lists
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class ShiftButtonPane extends VBox {
    private Button shiftLeftToRight;
    private Button shiftRightToLeft;

    public ShiftButtonPane(EventHandler<ActionEvent> leftShiftAction, EventHandler<ActionEvent> rightShiftAction) {
        this.setAlignment(Pos.CENTER);
        shiftLeftToRight = new Button("+");
        shiftRightToLeft = new Button("-");

        shiftLeftToRight.getStyleClass()
                .add("shift-button");
        shiftRightToLeft.getStyleClass()
                .add("shift-button");

        shiftLeftToRight.setOnAction(leftShiftAction);
        shiftRightToLeft.setOnAction(rightShiftAction);

        this.getChildren()
                .addAll(shiftLeftToRight, shiftRightToLeft);
    }
}
