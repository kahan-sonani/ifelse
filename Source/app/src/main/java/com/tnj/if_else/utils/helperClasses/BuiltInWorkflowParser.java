package com.tnj.if_else.utils.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;
import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflow;
import com.tnj.if_else.firebaseRepository.schema.WorkflowSchema;
import com.tnj.if_else.utils.helperClasses.BIWorkflowProviders;

import java.util.ArrayList;
import java.util.HashMap;

public class BuiltInWorkflowParser {

    @NonNull
    public static BuiltInWorkflow parseSnapshot(@NonNull DocumentSnapshot snapshot) {

        BuiltInWorkflow workflow = new BuiltInWorkflow(snapshot.getId());
        workflow.getSettings().setState((String) snapshot.get(WorkflowSchema.STATE));
        //Log.i("ifelse","built-in parser called for workflow[" + snapshot.getId() + "]: Built-in workflow created with reference: [" + workflow.toString() + "]");
        ArrayList<HashMap<String, Object>> list;
        Class actionClass = null;
        Class triggerClass = null;
        try {
            actionClass = Class.forName(BIWorkflowProviders.getInstance().find(snapshot.getId())
                    .getInitialSettings().getAction().getActionDetails().className);
            triggerClass = Class.forName(BIWorkflowProviders.getInstance().find(snapshot.getId())
                    .getInitialSettings().getTrigger().getTriggerDetails().className);
        }catch (Exception e){}
        try {
            list = (ArrayList<HashMap<String, Object>>) snapshot.get(WorkflowSchema.ALL_ACTION);
            for (int i = 0; i < ((Number) snapshot.get(WorkflowSchema.ACTION_COUNT)).intValue(); i++) {
                workflow.getSettings().addAction((Class<Action>) actionClass);
                workflow.getSettings().getAction(i).setConfiguration((HashMap<String, Object>) list.get(i).get("configuration"));
            }
        }catch (Exception e){}
        try{
            list = (ArrayList<HashMap<String, Object>>) snapshot.get(WorkflowSchema.ALL_TRIGGER);
            for(int i = 0 ; i < ((Number)snapshot.get(WorkflowSchema.TRIGGER_COUNT)).intValue() ; i++) {
                workflow.getSettings().addTrigger((Class<Trigger>) triggerClass);
                workflow.getSettings().getTrigger(i).setConfiguration((HashMap<String, Object>) list.get(i).get("configuration"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return workflow;
    }
}
