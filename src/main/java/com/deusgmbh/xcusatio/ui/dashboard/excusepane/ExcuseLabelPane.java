package com.deusgmbh.xcusatio.ui.dashboard.excusepane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ExcuseLabelPane extends VBox {
    private static final int EXCUSE_TITLE_LABEL_FONT_SIZE = 40;
    private static final int EXCUSE_LABEL_FONT_SIZE = 24;

    private static final String EXCUSE_TITLE = "Ausrede";

    public ExcuseLabelPane(String output) {
        Label excuseTitleLabel = createCenteredLabel(EXCUSE_TITLE, EXCUSE_TITLE_LABEL_FONT_SIZE,
                new Insets(5, 0, 20, 0));
        Label excuseLabel = createCenteredLabel(output, EXCUSE_LABEL_FONT_SIZE, new Insets(10, 0, 15, 0));

        this.getChildren().addAll(excuseTitleLabel, excuseLabel);
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
