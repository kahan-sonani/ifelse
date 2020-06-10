package com.tnj.if_else.architecture.secondLevelEntities;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.tnj.if_else.architecture.baseLevelEntities.Workflow;
import com.tnj.if_else.architecture.baseLevelEntities.WorkflowSettingsProxy;
import com.tnj.if_else.utils.helperClasses.BIWorkflowProviders;

@IgnoreExtraProperties
public class BuiltInWorkflow extends Workflow{

    public BuiltInWorkflow(String id, WorkflowSettingsProxy settings){
        this.id = id;
        this.settings = settings;
        details = BIWorkflowProviders.getInstance().find(id);
    }

    public BuiltInWorkflow(String id){
        super(id);
        details = BIWorkflowProviders.getInstance().find(id);
    }

    @Override
    public void setId(String id) { }
}
