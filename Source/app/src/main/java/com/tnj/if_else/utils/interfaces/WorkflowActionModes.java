package com.tnj.if_else.utils.interfaces;

public interface WorkflowActionModes {
    int getSelectedItemCount();

    boolean toggleItem(String id);

    void clearSelection();

    void selectAll();

    boolean isAllSelected();
}
