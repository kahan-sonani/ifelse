package com.tnj.if_else.viewModels;

import androidx.lifecycle.ViewModel;

import com.tnj.if_else.utils.helperClasses.BIWorkflowProviders;
import com.tnj.if_else.utils.interfaces.ResultSet;

public class TryBuiltInFragmentViewModel extends ViewModel {

    private Boolean[] workflowEnabled;

    public TryBuiltInFragmentViewModel() {
        workflowEnabled = new Boolean[BIWorkflowProviders.getInstance().getAllWorkflow().size()];
    }

    public void updateStatusForWorkflow(int index, String id, ResultSet<Boolean> listener, Object value) {
        /*FirebaseRepository.getInstance().builtIn()
                .updateOrCreateWorkflow(id, result -> {
                    listener.onResult(result);
                    workflowEnabled[index] =  Workflow.State.ACTIVATED.equals(value);
                }, WorkflowSchema.STATE, value);*/
    }

    public void getStatusForWorkflow(int index, String id, ResultSet<Boolean> resultSet) {

        Boolean value = workflowEnabled[index];
        if (value != null)
            resultSet.onResult(value);
        else{
            /*FirebaseRepository.getInstance().builtIn()
                    .getWorkflow(id, result -> {
                        boolean b;
                        workflowEnabled[index] = b = result != null && Workflow.State.ACTIVATED.equals(result.getSettings().getState());
                        resultSet.onResult(b);
                    });*/
        }
    }
}
