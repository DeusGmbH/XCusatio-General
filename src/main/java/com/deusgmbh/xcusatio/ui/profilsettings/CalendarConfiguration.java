package com.deusgmbh.xcusatio.ui.profilsettings;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalendarConfiguration extends Application {
    public static final int HEIGHT = 600;
    public static final int WIDTH = 500;
    public static final String WINDOW_TITLE = "Kalender Konfiguration";
    private static final String CLOSE_BUTTON_UNICODE = "\u274C";
    private static final String INSTRUCTION_STEP_I_IMAGE_PATH = "file:src/main/resources/instructionImgI.png";
    private static final int INSTRUCTION_IMAGE_WIDTH = 400;
    private static final int INSTRUCTION_STEP_NUM = 5;
    private static final String NEXT_BUTTON_TEXT = "Weiter";

    BorderPane calendarMain;

    @Override
    public void start(Stage stage) throws Exception {
        calendarMain = new BorderPane();

        Button closeButton = new Button(CLOSE_BUTTON_UNICODE);
        HBox windowPane = new HBox(closeButton);
        windowPane.setAlignment(Pos.CENTER_RIGHT);

        calendarMain.setTop(windowPane);
        calendarMain.setCenter(firstStep());

        Scene calendarConfigScene = new Scene(calendarMain, WIDTH, HEIGHT);

        Stage calendarConfigStage = new Stage();
        // calendarConfigStage.initStyle(StageStyle.UNDECORATED);
        calendarConfigStage.setTitle(WINDOW_TITLE);
        calendarConfigStage.setScene(calendarConfigScene);

        calendarConfigStage.show();
    }

    private VBox firstStep() {
        String linkDescription = "Einrichtung Google Kalender";
        String url = "https://console.developers.google.com/flows/enableapi?apiid=calendar&pli=1";
        String instruction = "Zum Einrichten Ihres Google-Kalenders besuchen sie bitte oben stehenden Link und melden Sie sich mit Ihren Google Daten an. W‰hlen Sie anschlieﬂend die Optionen wie im Bild unten vorgegeben und klicken Sie auf weiter.";

        Button nextButton = new Button("NEXT_BUTTON_TEXT");
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calendarMain.setCenter(secondStep());
            }
        });

        return createInstructionStep(1, url, linkDescription, instruction, INSTRUCTION_STEP_I_IMAGE_PATH, nextButton);
    }

    private VBox secondStep() {
        String instruction = "Zum Einrichten Ihres Google-Kalenders besuchen sie bitte oben stehenden Link und melden Sie sich mit Ihren Google Daten an. W‰hlen Sie anschlieﬂend die Optionen wie im Bild unten vorgegeben und klicken Sie auf weiter.";

        Button nextButton = new Button("NEXT_BUTTON_TEXT");
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calendarMain.setCenter(secondStep());
            }
        });

        return createInstructionStep(2, instruction, INSTRUCTION_STEP_I_IMAGE_PATH, nextButton);
    }

    private VBox createInstructionStep(int pageNum, String url, String linkDescription, String instruction,
            String imagePath, Button nextButton) {
        VBox instructionPage = createInstructionStep(pageNum, instruction, imagePath, nextButton);

        Hyperlink hyperlink = new Hyperlink(linkDescription);
        setLink(hyperlink, url);

        instructionPage.getChildren()
                .add(1, hyperlink);

        return instructionPage;
    }

    private VBox createInstructionStep(int pageNum, String instruction, String imagePath, Button nextButton) {
        VBox instructionPage = new VBox();

        Label titleLabel = new Label("Einrichtung " + pageNum + "/" + INSTRUCTION_STEP_NUM);

        Label instructionLabel = new Label(instruction);
        instructionLabel.setWrapText(true);

        Image instructionImg = new Image(imagePath);
        ImageView instructionImgView = new ImageView(instructionImg);
        instructionImgView.setPreserveRatio(true);
        instructionImgView.setFitHeight(INSTRUCTION_IMAGE_WIDTH);

        StackPane nextButtonPane = new StackPane(nextButton);
        nextButtonPane.setAlignment(Pos.BOTTOM_RIGHT);

        instructionPage.getChildren()
                .addAll(titleLabel, instructionLabel, instructionImgView, nextButtonPane);

        return instructionPage;

        // I
        // Aktiviert => Zu den Ameldeaten
        // II
        // III
        // IV
        // V
        // Datei speichern
        // FERTIG
        // Hier einlesen

    }

    private void setLink(Hyperlink hyperlink, String url) {
        hyperlink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                getHostServices().showDocument(url);
            }
        });
    }
}
