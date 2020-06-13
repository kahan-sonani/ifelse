package com.tnj.if_else.utils.helperClasses;

import com.google.firebase.firestore.DocumentSnapshot;
import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.architecture.baseLevelEntities.Criteria;
import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.architecture.baseLevelEntities.WorkflowSettingsProxy;
import com.tnj.if_else.architecture.secondLevelEntities.CustomWorkflow;
import com.tnj.if_else.architecture.secondLevelEntities.CustomWorkflowDetailsProxy;
import com.tnj.if_else.firebaseRepository.schema.WorkflowSchema;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomWorkflowParser {

    public static CustomWorkflow parseSnapshot(DocumentSnapshot snapshot){

        CustomWorkflowDetailsProxy detailsProxy = new CustomWorkflowDetailsProxy();
        detailsProxy.setName((String) snapshot.get(WorkflowSchema.NAME))
                .setDescription((String) snapshot.get(WorkflowSchema.DESCRIPTION));
        HashMap map  = (HashMap) snapshot.get(WorkflowSchema.COLOR);
        detailsProxy.setColor(new Color(((Number)map.get("color")).intValue(),(String) map.get("name")));
        CustomWorkflow workflow = new CustomWorkflow(snapshot.getId(),detailsProxy,new WorkflowSettingsProxy());
        workflow.getSettings().setState((String) snapshot.get(WorkflowSchema.STATE));

        ArrayList<HashMap<String,Object>> settingsList;
        try {
            settingsList = (ArrayList<HashMap<String, Object>>) snapshot.get(WorkflowSchema.ALL_ACTION);
            for (int i = 0; i < ((Number) snapshot.get(WorkflowSchema.ACTION_COUNT)).intValue(); i++) {
                workflow.getSettings().addAction((Class<Action>) Class.forName(
                        (String) ((HashMap) settingsList.get(i).get("actionDetails")).get("className")));
                workflow.getSettings().getAction(i).setConfiguration((HashMap<String, Object>) settingsList.get(i).get("configuration"));
            }
        }catch (Exception e){}
        try {
            settingsList = (ArrayList<HashMap<String, Object>>) snapshot.get(WorkflowSchema.ALL_TRIGGER);
            for (int i = 0; i < ((Number) snapshot.get(WorkflowSchema.TRIGGER_COUNT)).intValue(); i++) {
                workflow.getSettings().addTrigger((Class<Trigger>) Class.forName(
                        (String) ((HashMap) settingsList.get(i).get("triggerDetails")).get("className")));
                workflow.getSettings().getTrigger(i).setConfiguration((HashMap<String, Object>) settingsList.get(i).get("configuration"));
            }
        }catch (Exception e){}
        try {
            settingsList = (ArrayList<HashMap<String, Object>>) snapshot.get(WorkflowSchema.ALL_CRITERIA);
            for (int i = 0; i < ((Number) snapshot.get(WorkflowSchema.CRITERIA_COUNT)).intValue(); i++) {
                workflow.getSettings().addCriteria((Class<Criteria>) Class.forName(
                        (String) ((HashMap) settingsList.get(i).get("criteriaDetails")).get("className")));
                workflow.getSettings().getCriteria(i).setConfiguration((HashMap<String, Object>) settingsList.get(i).get("configuration"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return workflow;
    }
}
