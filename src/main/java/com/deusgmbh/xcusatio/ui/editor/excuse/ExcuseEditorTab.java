package com.deusgmbh.xcusatio.ui.editor.excuse;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.ui.editor.EditorTab;

/**
 * 
 * This class merges the list of excuses and the editor for excuses into one
 * SplitPane. This opens the possibility to see all available excuses, select
 * one and edit this selected excuse in the EditEntryPane. These classes create
 * a table of all available entries. You can remove or add an entry. Also you
 * can select one and edit this in the adjacent EditEntryPane.
 * 
 * @author Pascal.Schroeder@de.ibm.com
 *
 */
public class ExcuseEditorTab extends EditorTab {
    private ExcuseEntryListPane entryListPane;
    private ExcuseEditEntryPane editEntryPane;

    public ExcuseEditorTab(String name) {
        super(name);
        entryListPane = new ExcuseEntryListPane();
        entryListPane.registerOnSelectEntryEvent(this::createEditForm);
        entryListPane.minWidthProperty().bind(this.editor.widthProperty().multiply(0.39));
        entryListPane.maxWidthProperty().bind(this.editor.widthProperty().multiply(0.39));
        editEntryPane = new ExcuseEditEntryPane();
        editEntryPane.minWidthProperty().bind(this.editor.widthProperty().multiply(0.59));
        editEntryPane.maxWidthProperty().bind(this.editor.widthProperty().multiply(0.59));
        super.editor.setLeft(entryListPane);
        super.editor.setCenter(editEntryPane);
    }

    public void setTableContent(List<Excuse> excuseList) {
        entryListPane.setTableContent(excuseList);
    }

    public void registerRemoveEntryEvent(Consumer<Excuse> removeEntry) {
        entryListPane.createRemoveEntryListener(removeEntry);
    }

    public void registerAddEntryEvent(Consumer<Excuse> addEntry) {
        entryListPane.createAddEntryListener(addEntry);
    }

    public void registerChangeEntryEvent(BiConsumer<Integer, Excuse> editEntry) {
        editEntryPane.createEditBtnAction(editEntry);
    }

    public void registerTagsSetSupplier(Supplier<List<String>> tagsSetSupplier) {
        editEntryPane.registerTagsSetSupplier(tagsSetSupplier);
    }

    public void registerWildcardSetSupplier(Supplier<List<String>> wildcardSetSupplier) {
        editEntryPane.registerWildcardSetSupplier(wildcardSetSupplier);
    }

    private void createEditForm(int selectedExcuseID, Excuse selectedExcuse) {
        editEntryPane.createEditForm(selectedExcuseID, selectedExcuse);
    }
}
