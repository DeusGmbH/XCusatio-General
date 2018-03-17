package com.deusgmbh.xcusatio.ui.dashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * 
 * This is the ExcusePane class for the User Interface. This class is settled on
 * the Dashboard and is responsible for the output of the excuses.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class ExcusePane extends VBox {
    private static final String DEFAULT_OUTPUT = "Hier könnte Ihre Ausrede stehen";
    private static final String EXCUSE_TITLE = "Ausrede";
    private static final int EXCUSE_TITLE_LABEL_FONT_SIZE = 40;
    private static final int EXCUSE_LABEL_FONT_SIZE = 24;

    private static final double EXCUSE_OPTIONS_PANE_HEIGHT_MULTIPLIER = 0.1;
    private static final double FEEDBACK_PANE_HEIGHT_MULTIPLIER = 0.25;

    private static final String THUMB_GESTURE_PATH = "file:src/main/resources/thumbGesture.png";
    private static final int THUMB_GESTURE_HEIGHT = 300;

    private String output;
    private int thumbGestureValue;
    private VBox excuseLabelPane;
    private HBox excuseOptionsPane;
    private HBox feedbackPane;
    private BorderPane thumbGesturePane;

    public ExcusePane() {
        this(DEFAULT_OUTPUT);
    }

    public ExcusePane(String excuse) {
        this.output = excuse;
        this.setAlignment(Pos.CENTER);

        initExcuseLabelPane();
        initExcuseOptionsPane();
        initFeedbackPane();

        this.getChildren().addAll(excuseLabelPane, excuseOptionsPane, feedbackPane);
    }

    public ExcusePane(int thumbGestureValue) {
        this.thumbGestureValue = thumbGestureValue;
        this.setAlignment(Pos.CENTER);

        initThumbGesturePane();

        this.getChildren().add(thumbGesturePane);
    }

    private void initExcuseLabelPane() {
        excuseLabelPane = new VBox();

        Label excuseTitleLabel = createCenteredLabel(EXCUSE_TITLE, EXCUSE_TITLE_LABEL_FONT_SIZE,
                new Insets(5, 0, 20, 0));
        Label excuseLabel = createCenteredLabel(output, EXCUSE_LABEL_FONT_SIZE, new Insets(10, 0, 15, 0));

        excuseLabelPane.getChildren().addAll(excuseTitleLabel, excuseLabel);
    }

    private void initExcuseOptionsPane() {
        excuseOptionsPane = new HBox();
        excuseOptionsPane.prefHeightProperty()
                .bind(this.heightProperty().multiply(EXCUSE_OPTIONS_PANE_HEIGHT_MULTIPLIER));
        excuseOptionsPane.maxHeightProperty()
                .bind(this.heightProperty().multiply(EXCUSE_OPTIONS_PANE_HEIGHT_MULTIPLIER));
        // only for testing purposes
        excuseOptionsPane.setStyle("-fx-background-color: #AAAAAA");
    }

    private void initFeedbackPane() {
        feedbackPane = new HBox();
        feedbackPane.prefHeightProperty().bind(this.heightProperty().multiply(FEEDBACK_PANE_HEIGHT_MULTIPLIER));
        feedbackPane.maxHeightProperty().bind(this.heightProperty().multiply(FEEDBACK_PANE_HEIGHT_MULTIPLIER));
        // only for testing purposes
        feedbackPane.setStyle("-fx-background-color: #CCCCCC");
    }

    private void initThumbGesturePane() {
        thumbGesturePane = new BorderPane();

        Image thumbGestureImg = new Image(THUMB_GESTURE_PATH);
        ImageView thumbGestureImgView = new ImageView(thumbGestureImg);
        thumbGestureImgView.setPreserveRatio(true);
        thumbGestureImgView.setFitHeight(THUMB_GESTURE_HEIGHT);
        thumbGestureImgView.setRotate(thumbGestureValue);

        thumbGesturePane.setCenter(thumbGestureImgView);
    }

    private Label createCenteredLabel(String text, int fontSize, Insets paddingInsets) {
        Label label = new Label(text);
        // design only temporary, later with CSS
        label.setMaxWidth(Double.MAX_VALUE);
        label.setFont(new Font("Arial", fontSize));
        label.setAlignment(Pos.CENTER);
        label.setPadding(paddingInsets);
        label.setWrapText(true);
        return label;
    }
}
