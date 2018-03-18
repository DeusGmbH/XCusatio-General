package com.deusgmbh.xcusatio.ui.dashboard.excusepane;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class ExcusePane extends VBox {
    private static final double EXCUSE_OPTIONS_PANE_HEIGHT_MULTIPLIER = 0.1;
    private static final double FEEDBACK_PANE_HEIGHT_MULTIPLIER = 0.25;

    private ExcuseLabelPane excuseLabelPane;
    private ExcuseOptionsPane excuseOptionsPane;
    private FeedbackPane feedbackPane;

    public ExcusePane(String excuse) {
        this.setAlignment(Pos.CENTER);

        excuseLabelPane = new ExcuseLabelPane(excuse);
        excuseOptionsPane = new ExcuseOptionsPane();
        feedbackPane = new FeedbackPane();

        excuseOptionsPane.prefHeightProperty()
                .bind(this.heightProperty().multiply(EXCUSE_OPTIONS_PANE_HEIGHT_MULTIPLIER));
        excuseOptionsPane.maxHeightProperty()
                .bind(this.heightProperty().multiply(EXCUSE_OPTIONS_PANE_HEIGHT_MULTIPLIER));

        feedbackPane.prefHeightProperty().bind(this.heightProperty().multiply(FEEDBACK_PANE_HEIGHT_MULTIPLIER));
        feedbackPane.maxHeightProperty().bind(this.heightProperty().multiply(FEEDBACK_PANE_HEIGHT_MULTIPLIER));

        this.getChildren().addAll(excuseLabelPane, excuseOptionsPane, feedbackPane);
    }
}
