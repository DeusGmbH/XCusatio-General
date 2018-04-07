package com.deusgmbh.xcusatio.ui.dashboard;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
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
    private static final int CELL_HEIGHT = 37;

    public RecentlyUsedPane(ObservableList<String> recentlyUsedList) {
        Label recentlyUsedLabel = new Label(RECENTLY_USED_TITLE);
        recentlyUsedLabel.getStyleClass()
                .add("h3");
        ListView<String> recentlyUsedListView = new ListView<String>(recentlyUsedList);
        recentlyUsedListView.setMaxHeight(LIMIT_RECENTLY_USED_ENTRIES * CELL_HEIGHT);
        HBox spacer = new HBox();
        spacer.minHeightProperty()
                .bind(this.heightProperty()
                        .multiply(0.07));
        spacer.maxHeightProperty()
                .bind(this.heightProperty()
                        .multiply(0.07));

        this.getChildren()
                .addAll(recentlyUsedLabel, recentlyUsedListView, spacer);
    }
}
