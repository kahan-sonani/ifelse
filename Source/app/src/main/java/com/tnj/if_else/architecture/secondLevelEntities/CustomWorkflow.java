package com.tnj.if_else.architecture.secondLevelEntities;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.tnj.if_else.architecture.baseLevelEntities.Workflow;
import com.tnj.if_else.architecture.baseLevelEntities.WorkflowSettingsProxy;

@IgnoreExtraProperties
public class CustomWorkflow extends Workflow {

    public CustomWorkflow(String id) {
        super(id);
        details = new CustomWorkflowDetailsProxy();
    }

    public CustomWorkflow()
    {
        super();
        details = new CustomWorkflowDetailsProxy();
    }

    public CustomWorkflow(String id, CustomWorkflowDetailsProxy details, WorkflowSettingsProxy settings){
        if(details != null && settings != null && id != null) {
            this.id = id;
            this.details = details;
            this.settings = settings;
        }
    }
}
