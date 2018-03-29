package com.deusgmbh.xcusatio.ui.utility;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * 
 * This is a class for creating ToggleSwitches. It designs the ToggleSwitch with
 * belonging animations and provides functions for reading and changing the
 * toggle state
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class ToggleSwitch extends Parent {
    private static final int SWITCH_WIDTH = 50;
    private static final int SWITCH_HEIGHT = 25;
    private static final Color SWITCH_BACKGROUND_COLOR = Color.WHITE;
    private static final Color SWITCH_STROKE_COLOR = Color.LIGHTGRAY;
    private static final Color SWITCH_ACTIVATED_COLOR = Color.LIGHTBLUE;

    private BooleanProperty isSwitched = new SimpleBooleanProperty(false);

    private Runnable changeUserSettings;

    public ToggleSwitch() {
        this(false);
    }

    public ToggleSwitch(boolean activatedState) {
        Rectangle background = createBackgroundRectangle(activatedState);
        Circle toggleCircle = createToggleCircle(activatedState);

        TranslateTransition translateAnimation = new TranslateTransition(Duration.seconds(0.25));
        FillTransition fillAnimation = new FillTransition(Duration.seconds(0.25));
        translateAnimation.setNode(toggleCircle);
        fillAnimation.setShape(background);

        this.getChildren().addAll(background, toggleCircle);
        createStateChangeOnClick(isSwitched, activatedState, translateAnimation, fillAnimation);

        this.setOnMouseClicked(event -> {
            isSwitched.set(!isSwitched.get());
            changeUserSettings.run();
        });
    }

    private Rectangle createBackgroundRectangle(boolean activatedState) {
        Rectangle backgroundRectangle = new Rectangle(SWITCH_WIDTH, SWITCH_HEIGHT);
        backgroundRectangle.setArcWidth(SWITCH_HEIGHT);
        backgroundRectangle.setArcHeight(SWITCH_HEIGHT);
        backgroundRectangle.setFill(activatedState ? SWITCH_ACTIVATED_COLOR : SWITCH_BACKGROUND_COLOR);
        backgroundRectangle.setStroke(SWITCH_STROKE_COLOR);
        return backgroundRectangle;
    }

    private Circle createToggleCircle(boolean activatedState) {
        Circle toggleCircle = new Circle(SWITCH_HEIGHT / 2);
        toggleCircle.setCenterX(activatedState ? SWITCH_WIDTH - SWITCH_HEIGHT / 2 : SWITCH_HEIGHT / 2);
        toggleCircle.setCenterY(SWITCH_HEIGHT / 2);
        toggleCircle.setFill(Color.WHITE);
        toggleCircle.setStroke(Color.LIGHTGRAY);
        return toggleCircle;
    }

    private void createStateChangeOnClick(BooleanProperty node, boolean originalState,
            TranslateTransition translateAnimation, FillTransition fillAnimation) {
        node.addListener((obs, oldState, newState) -> {
            ParallelTransition animation = new ParallelTransition(translateAnimation, fillAnimation);

            boolean isOn = newState.booleanValue();
            translateAnimation.setToX(originalState ? (isOn ? -SWITCH_WIDTH / 2 : 0) : (isOn ? SWITCH_WIDTH / 2 : 0));
            fillAnimation.setFromValue(originalState ? (isOn ? SWITCH_ACTIVATED_COLOR : SWITCH_BACKGROUND_COLOR)
                    : (isOn ? SWITCH_BACKGROUND_COLOR : SWITCH_ACTIVATED_COLOR));
            fillAnimation.setToValue(originalState ? (isOn ? SWITCH_BACKGROUND_COLOR : SWITCH_ACTIVATED_COLOR)
                    : (isOn ? SWITCH_ACTIVATED_COLOR : SWITCH_BACKGROUND_COLOR));

            animation.play();

        });
    }

    public boolean getToggleState() {
        return isSwitched.get();
    }

    public void registerChangeUserSettignsAction(Runnable changeUserSettingsAction) {
        this.changeUserSettings = changeUserSettingsAction;
    }

    public void setOnChange(Runnable action) {
        this.setOnMouseClicked(event -> {
            isSwitched.set(!isSwitched.get());
            changeUserSettings.run();
            action.run();
        });
    }

    public void changeClickable() {
        if (isSwitched.get()) {
            this.setOnMouseClicked(null);
        } else {
            this.setOnMouseClicked(event -> {
                isSwitched.set(!isSwitched.get());
                changeUserSettings.run();
            });
        }
    }
}
