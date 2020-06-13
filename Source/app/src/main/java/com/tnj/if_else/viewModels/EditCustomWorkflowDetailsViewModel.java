package com.tnj.if_else.viewModels;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.Source;
import com.tnj.if_else.architecture.secondLevelEntities.CustomWorkflow;
import com.tnj.if_else.firebaseRepository.FirebaseRepository;
import com.tnj.if_else.utils.helperClasses.Event;
import com.tnj.if_else.utils.interfaces.State;

public class EditCustomWorkflowDetailsViewModel extends ViewModel {

    public Event<String> workflowId;

    private ObservableBoolean loading;

    private LiveData<CustomWorkflow> workflowLiveData;

    private FirebaseRepository repository;

    public LiveData<CustomWorkflow> getWorkflow() {
        return workflowLiveData;
    }

    public ObservableBoolean getLoading() {
        return loading;
    }

    public EditCustomWorkflowDetailsViewModel(){
        repository = FirebaseRepository.getInstance();
        loading  = new ObservableBoolean(true);
        workflowId = new Event<>();

        workflowLiveData = Transformations.switchMap(workflowId,
                input -> Transformations.map(repository.custom().getWorkflow(Source.CACHE,input), input1 -> {
                    loading.set(false);
                    return (CustomWorkflow) input1.getResponse();
                }));
    }

    public LiveData<State> updateWorkflow(String key, Object value , Object... FieldAndValues){
        loading.set(true);
        return Transformations.map(repository.custom().updateWorkflow(workflowId.getValue(), key, value, FieldAndValues),
                input -> {
                    loading.set(!input.isFinished());
                    return input.getResponse();
                });
    }
}
