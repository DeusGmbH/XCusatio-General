package com.deusgmbh.xcusatio.ui.dashboard;

import java.util.ArrayList;

import javafx.scene.control.Label;
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
    private static final int LIMIT_RU_ENTRIES = 10;

    private ArrayList<String> recentlyUsedList;

    public RecentlyUsedPane() {
        this(new ArrayList<String>());
    }

    public RecentlyUsedPane(ArrayList<String> ruList) {
        this.recentlyUsedList = ruList;

        Label recentlyUsedLabel = new Label(RECENTLY_USED_TITLE);
        this.getChildren().add(recentlyUsedLabel);

        recentlyUsedList.stream().limit(LIMIT_RU_ENTRIES).forEach(entry -> {
            Label tmpLabel = new Label(entry);
            this.getChildren().add(tmpLabel);
        });
    }

    public void setRUList(ArrayList<String> ruList) {
        this.recentlyUsedList = ruList;
    }
}
