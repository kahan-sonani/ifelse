package com.tnj.if_else.utils.interfaces;

import com.tnj.if_else.architecture.baseLevelEntities.Workflow;

public interface OnWorkflowTouchListener<T extends Workflow> {
    void onClick(T workflow);

    void onLongClick(T workflow);
}
