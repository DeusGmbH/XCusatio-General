package com.deusgmbh.xcusatio.ui.dashboard;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * 
 * This class is for creating the thumb gesture if needed
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */
public class ThumbGesturePane extends BorderPane {
    private static final String THUMB_GESTURE_PATH = "file:src/main/resources/thumbGesture.png";

    public ThumbGesturePane(int thumbGestureValue) {
        Image thumbGestureImg = new Image(THUMB_GESTURE_PATH);

        ImageView thumbGestureImgView = new ImageView(thumbGestureImg);
        thumbGestureImgView.fitWidthProperty()
                .bind(this.widthProperty()
                        .multiply(0.3));
        thumbGestureImgView.setPreserveRatio(true);
        thumbGestureImgView.setRotate(Math.floor(360 - thumbGestureValue / 10) * 10);
        StackPane thumbGesturePane = new StackPane(thumbGestureImgView);

        this.setCenter(thumbGesturePane);
    }

}
