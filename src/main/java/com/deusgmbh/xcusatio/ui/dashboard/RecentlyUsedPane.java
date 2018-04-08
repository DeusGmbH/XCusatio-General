package com.deusgmbh.xcusatio.ui.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * 
 * This class is for initializing and filling the recently used pane on the
 * right side of the user interface with belonging entries
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */
public class RecentlyUsedPane extends VBox {
    private static final String RECENTLY_USED_TITLE = "Zuletzt generierte Ausreden";

    private static final int LIMIT_RECENTLY_USED_ENTRIES = 10;
    private static final int CELL_HEIGHT = 36;
    private ObservableList<String> recentlyUsedList;
    private ObservableList<String> limitedRecentlyUsedList = FXCollections.observableArrayList();

    public RecentlyUsedPane(ObservableList<String> recentlyUsedList) {
        this.recentlyUsedList = recentlyUsedList;

        Label recentlyUsedLabel = new Label(RECENTLY_USED_TITLE);
        recentlyUsedLabel.getStyleClass()
                .add("h3");

        refreshLimitedRecentlyUsedList();
        recentlyUsedList.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                refreshLimitedRecentlyUsedList();
            }
        });

        ListView<String> recentlyUsedListView = new ListView<String>(limitedRecentlyUsedList);
        recentlyUsedListView.setPrefHeight(LIMIT_RECENTLY_USED_ENTRIES * CELL_HEIGHT);
        HBox spacer = new HBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren()
                .addAll(recentlyUsedLabel, recentlyUsedListView, spacer);
    }

    private void refreshLimitedRecentlyUsedList() {
        limitedRecentlyUsedList.clear();
        recentlyUsedList.stream()
                .limit(10)
                .forEach(entry -> {
                    limitedRecentlyUsedList.add(entry);
                });
    }
}
