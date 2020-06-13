package com.tnj.if_else.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.tnj.if_else.architecture.baseLevelEntities.Workflow;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflow;
import com.tnj.if_else.firebaseRepository.FirebaseRepository;
import com.tnj.if_else.firebaseRepository.schema.WorkflowSchema;
import com.tnj.if_else.utils.helperClasses.Response;
import com.tnj.if_else.utils.interfaces.State;

import java.util.ArrayList;

public class TryBuiltInFragmentViewModel extends ViewModel {

    private FirebaseRepository repository;

    private ArrayList<LiveData<String>> workflowStateLiveData;

    public TryBuiltInFragmentViewModel() {
        repository = FirebaseRepository.getInstance();
        workflowStateLiveData = new ArrayList<>();
    }


    public LiveData<State> updateStatusForWorkflow(String id, Object value) {

        return Transformations.map(repository.builtIn().updateOrCreateWorkflow(id, WorkflowSchema.STATE, value), Response::getResponse);
    }

    public LiveData<String> getStatusForWorkflow(int index, String id) {

        try {
            return workflowStateLiveData.get(index);
        }catch (Exception e){
            workflowStateLiveData.add(index, Transformations.map(repository.builtIn().getWorkflow(id), input -> {
                if (!input.isSuccessful())
                    return Workflow.State.DEACTIVATED;
                else return ((BuiltInWorkflow) input.getResponse()).getSettings().getState();
            }));
            return workflowStateLiveData.get(index);
        }
    }
}
