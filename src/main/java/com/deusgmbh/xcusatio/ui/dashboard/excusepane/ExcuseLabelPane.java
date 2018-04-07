package com.deusgmbh.xcusatio.ui.dashboard.excusepane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * 
 * This class if for creating the excuse labels in the excuse pane
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */
public class ExcuseLabelPane extends VBox {
    private static final String EXCUSE_TITLE = "Ausrede";

    public ExcuseLabelPane(String output) {
        Label excuseTitleLabel = createFormattedLabel(EXCUSE_TITLE, "h1");
        Label excuseLabel = createFormattedLabel(output, "excuse-label");

        this.getChildren()
                .addAll(excuseTitleLabel, excuseLabel);
    }

    private Label createFormattedLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass()
                .add(styleClass);
        return label;
    }
}
