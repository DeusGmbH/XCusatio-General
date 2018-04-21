package com.deusgmbh.xcusatio.ui.dashboard.excusepane;

import javafx.scene.layout.VBox;

/**
 * 
 * This class is for creating an ExcusePane which should show the generated
 * excuse if needed
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */
public class ExcusePane extends VBox {
    private static final double EXCUSE_OPTIONS_LABEL_PANE_HEIGHT_MULTIPLIER = 0.9;
    private static final double EXCUSE_OPTIONS_PANE_HEIGHT_MULTIPLIER = 0.1;

    private ExcuseLabelPane excuseLabelPane;
    private ExcuseOptionsPane excuseOptionsPane;
    private FeedbackPane feedbackPane;

    public ExcusePane(String excuse) {

        excuseLabelPane = new ExcuseLabelPane(excuse);
        excuseOptionsPane = new ExcuseOptionsPane();
        feedbackPane = new FeedbackPane();

        excuseLabelPane.prefHeightProperty()
                .bind(this.heightProperty()
                        .multiply(EXCUSE_OPTIONS_LABEL_PANE_HEIGHT_MULTIPLIER));
        excuseOptionsPane.prefHeightProperty()
                .bind(this.heightProperty()
                        .multiply(EXCUSE_OPTIONS_PANE_HEIGHT_MULTIPLIER));

        this.getChildren()
                .addAll(excuseLabelPane, excuseOptionsPane, feedbackPane);
    }
}
