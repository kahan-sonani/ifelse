package com.tnj.if_else.viewModels;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.Source;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflow;
import com.tnj.if_else.firebaseRepository.FirebaseRepository;
import com.tnj.if_else.utils.helperClasses.Event;

public class EditBuiltInWorkflowDetailsViewModel extends ViewModel {

    public Event<String> workflowId;

    private ObservableBoolean  loading;

    private LiveData<BuiltInWorkflow> workflowLiveData;

    private FirebaseRepository repository;

    public LiveData<BuiltInWorkflow> getWorkflow() {
        return workflowLiveData;
    }

    public ObservableBoolean getLoading() {
        return loading;
    }


    public EditBuiltInWorkflowDetailsViewModel(){
        repository = FirebaseRepository.getInstance();
        loading = new ObservableBoolean(true);
        workflowId = new Event<>();
        workflowLiveData = Transformations.switchMap(workflowId,
                input -> Transformations.map(repository.builtIn().getWorkflow(Source.CACHE,input), input1 -> {
                    loading.set(false);
                    return (BuiltInWorkflow) input1.getResponse();
                }));
    }
}
