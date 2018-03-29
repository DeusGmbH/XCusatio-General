package com.deusgmbh.xcusatio.ui.editor.excuse;

import java.util.HashMap;

import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.scenarios.ScenarioType;
import com.deusgmbh.xcusatio.ui.editor.EntryListPane;

/**
 * 
 * This class creates a table of all available excuses. You can remove or add an
 * entry. Also you can select one and edit this in the adjacent EditEntryPane.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */

public class ExcuseEntryListPane extends EntryListPane<Excuse> {
    private static final String REQ_COLUMN_TEXT_VARNAME = "text";
    private static final String REQ_COLUMN_TAGS_VARNAME = "tags";
    private static final String REQ_COLUMN_TEXT_TITLE = "Ausrede";
    private static final String REQ_COLUMN_TAGS_TITLE = "Tags";
    private static final String NEW_EXCUSE_DEFAULT_TEXT = "Bitte hier neue Ausrede eingeben";
    private static final ScenarioType NEW_EXCUSE_DEFAULT_SCNEARIO = ScenarioType.LATE_ARRIVAL;

    @Override
    protected HashMap<String, String> getRequiredTableColumns() {
        HashMap<String, String> requiredColumns = new HashMap<String, String>();
        requiredColumns.put(REQ_COLUMN_TEXT_VARNAME, REQ_COLUMN_TEXT_TITLE);
        requiredColumns.put(REQ_COLUMN_TAGS_VARNAME, REQ_COLUMN_TAGS_TITLE);
        return requiredColumns;
    }

    @Override
    protected Excuse createNewItem() {
        return new Excuse(NEW_EXCUSE_DEFAULT_TEXT, NEW_EXCUSE_DEFAULT_SCNEARIO);
    }

}
