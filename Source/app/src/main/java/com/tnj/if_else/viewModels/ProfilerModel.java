package com.tnj.if_else.viewModels;

import androidx.lifecycle.ViewModel;

import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.architecture.concreteEntities.built_in_workflow.ProfilerBuilder;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflowDetailsProxy;

public class ProfilerModel extends ViewModel {

    public BuiltInWorkflowDetailsProxy proxy;

    public Action selectedAction;
    public Trigger selectedTrigger;

    public boolean editMode;

    public ProfilerModel(){
        proxy = new ProfilerBuilder().getDetails();
        editMode = false;
        selectedAction = null;
        selectedTrigger = null;
    }
}
