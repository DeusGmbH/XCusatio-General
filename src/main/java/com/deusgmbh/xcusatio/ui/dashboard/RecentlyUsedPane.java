package com.deusgmbh.xcusatio.ui.dashboard;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

    // TODO: Not static, but dynamic dependent on the size of this Pane
    private static final int LIMIT_RECENTLY_USED_ENTRIES = 10;
    private static final int CELL_HEIGHT = 30;

    public RecentlyUsedPane(ObservableList<String> recentlyUsedList) {
        Label recentlyUsedLabel = new Label(RECENTLY_USED_TITLE);
        ListView<String> recentlyUsedListView = new ListView<String>(recentlyUsedList);
        this.getChildren()
                .addAll(recentlyUsedLabel, recentlyUsedListView);
        recentlyUsedListView.setMaxHeight(LIMIT_RECENTLY_USED_ENTRIES * CELL_HEIGHT);
    }
}
