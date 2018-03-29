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
<<<<<<< HEAD
    private static final Color SWITCH_BACKGROUND_COLOR = Color.web("333");
    private static final Color SWITCH_STROKE_COLOR = Color.web("666");
    private static final Color SWITCH_ACTIVATED_COLOR = Color.web("108ACB");
=======
    private static final Color SWITCH_BACKGROUND_COLOR = Color.WHITE;
    private static final Color SWITCH_STROKE_COLOR = Color.LIGHTGRAY;
    private static final Color SWITCH_ACTIVATED_COLOR = Color.LIGHTBLUE;
    private static final Color UNCLICKABLE_BACKGROUND_COLOR = Color.GRAY;
    private static final Color SWITCH_UNCLICKABLE_COLOR = Color.LIGHTGRAY;
    private static final Color SWITCH_BACKGROUND = Color.WHITE;
>>>>>>> refs/remotes/origin/master

    private BooleanProperty isSwitched = new SimpleBooleanProperty(false);
    private BooleanProperty clickable = new SimpleBooleanProperty(false);

    private Rectangle background;
    private Circle toggleCircle;

    private Runnable changeUserSettings;

    public ToggleSwitch() {
        this(false, true);
    }

    public ToggleSwitch(boolean activatedState) {
        this(activatedState, true);
    }

    public ToggleSwitch(boolean activatedState, boolean clickable) {
        this.isSwitched = new SimpleBooleanProperty(activatedState);
        this.clickable = new SimpleBooleanProperty(clickable);

        background = createBackgroundRectangle(activatedState);
        toggleCircle = createToggleCircle(activatedState);

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

        setClickable(clickable);
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
            translateAnimation.setToX(originalState ? (isOn ? 0 : -SWITCH_WIDTH / 2) : (isOn ? SWITCH_WIDTH / 2 : 0));
            fillAnimation.setFromValue(originalState ? (isOn ? SWITCH_BACKGROUND_COLOR : SWITCH_ACTIVATED_COLOR)
                    : (isOn ? SWITCH_BACKGROUND_COLOR : SWITCH_ACTIVATED_COLOR));
            fillAnimation.setToValue(originalState ? (isOn ? SWITCH_ACTIVATED_COLOR : SWITCH_BACKGROUND_COLOR)
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
        setClickable(!this.clickable.get());
    }

    public void setClickable(boolean value) {
        this.clickable.set(value);
        if (this.clickable.get()) {
            this.setOnMouseClicked(event -> {
                isSwitched.set(!isSwitched.get());
                changeUserSettings.run();
            });
            background.setFill(this.isSwitched.get() ? SWITCH_ACTIVATED_COLOR : SWITCH_BACKGROUND_COLOR);
            toggleCircle.setFill(SWITCH_BACKGROUND);
        } else {
            this.setOnMouseClicked(null);
            background.setFill(UNCLICKABLE_BACKGROUND_COLOR);
            toggleCircle.setFill(SWITCH_UNCLICKABLE_COLOR);
        }
    }
}
