package com.deusgmbh.xcusatio.ui.dashboard;

import java.util.ArrayList;
import java.util.List;

import com.deusgmbh.xcusatio.data.excuses.Excuse;

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

    // TODO: Not static, but dynamic dependent on the size of this Pane
    private static final int LIMIT_RECENTLY_USED_ENTRIES = 10;

    private List<Excuse> recentlyUsedList;

    public RecentlyUsedPane() {
        this(new ArrayList<>());
    }

    public RecentlyUsedPane(List<Excuse> recentlyUsedList) {
        this.recentlyUsedList = recentlyUsedList;

        Label recentlyUsedLabel = new Label(RECENTLY_USED_TITLE);
        this.getChildren()
                .add(recentlyUsedLabel);

        this.recentlyUsedList.stream()
                .limit(LIMIT_RECENTLY_USED_ENTRIES)
                .forEach(excuse -> {
                    this.getChildren()
                            .add(new Label(excuse.getText()));
                });
    }
}
