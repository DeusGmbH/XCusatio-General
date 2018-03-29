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

<<<<<<< HEAD
    public List<Excuse> entryList;
    private TableView<Excuse> entryTable;

    public ExcuseEntryListPane() {
        super();
        entryTable = new TableView<Excuse>();
        entryTable.setEditable(false);
        entryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setTableColumns(getRequiredTableColumns());

        this.getChildren().add(0, entryTable);
    }

    private void setTableColumns(HashMap<String, String> columnList) {
        columnList.entrySet().stream().forEach(entry -> {
            TableColumn<Excuse, String> column = new TableColumn<Excuse, String>(entry.getValue().toString());
            column.setCellValueFactory(new PropertyValueFactory<Excuse, String>(entry.getKey().toString()));
            column.prefWidthProperty().bind(entryTable.widthProperty().multiply(1d / columnList.size()).subtract(20));
            entryTable.getColumns().add(column);
        });
    }

    private HashMap<String, String> getRequiredTableColumns() {
=======
    @Override
    protected HashMap<String, String> getRequiredTableColumns() {
>>>>>>> refs/remotes/origin/master
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
