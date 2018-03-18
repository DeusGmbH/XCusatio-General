package com.deusgmbh.xcusatio.ui.dashboard;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ThumbGesturePane extends BorderPane {
    private static final String THUMB_GESTURE_PATH = "file:src/main/resources/thumbGesture.png";
    private static final int THUMB_GESTURE_HEIGHT = 200;

    public ThumbGesturePane(int thumbGestureValue) {
        Image thumbGestureImg = new Image(THUMB_GESTURE_PATH);
        ImageView thumbGestureImgView = new ImageView(thumbGestureImg);
        thumbGestureImgView.setPreserveRatio(true);
        thumbGestureImgView.setFitHeight(THUMB_GESTURE_HEIGHT);
        thumbGestureImgView.setRotate(thumbGestureValue);

        this.setCenter(thumbGestureImgView);
    }

}
