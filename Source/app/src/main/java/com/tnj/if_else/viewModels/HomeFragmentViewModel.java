package com.tnj.if_else.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreArray;
import com.tnj.if_else.adapters.AdapterConfig;
import com.tnj.if_else.architecture.baseLevelEntities.Workflow;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflow;
import com.tnj.if_else.architecture.secondLevelEntities.CustomWorkflow;
import com.tnj.if_else.firebaseRepository.FirebaseRepository;
import com.tnj.if_else.firebaseRepository.schema.FirebaseConfig;
import com.tnj.if_else.firebaseRepository.schema.WorkflowSchema;
import com.tnj.if_else.utils.helperClasses.BuiltInWorkflowParser;
import com.tnj.if_else.utils.helperClasses.CustomWorkflowParser;

public class HomeFragmentViewModel extends ViewModel implements AdapterConfig.toggleListener{

    private AdapterConfig<BuiltInWorkflow> builtInWorkflowAdapterConfig;

    private AdapterConfig<CustomWorkflow>  customWorkflowAdapterConfig;

    private FirebaseRepository repository;

    public boolean didUserCloseDialog;

    private MutableLiveData<Boolean> builtInWorkflowList;

    private MutableLiveData<Boolean> customWorkflowList;

    private MediatorLiveData<Boolean> isListEmpty;

    private Workflow currentSelectedWorkflow;

    public LiveData<Boolean> isListEmpty() {
        return isListEmpty;
    }

    public void isBuiltInWorkflowListEmpty(boolean value) {
        builtInWorkflowList.setValue(value);
    }

    public void isCustomWorkflowListEmpty(boolean value) {
        customWorkflowList.setValue(value);
    }

    public HomeFragmentViewModel() {
        repository = FirebaseRepository.getInstance();

        builtInWorkflowAdapterConfig = new AdapterConfig<>(new FirestoreArray<>(repository.builtIn().queryOrderByState(true), BuiltInWorkflowParser::parseSnapshot));
        customWorkflowAdapterConfig = new AdapterConfig<>(new FirestoreArray<>(repository.custom().queryOrderByState(true), CustomWorkflowParser::parseSnapshot));

        builtInWorkflowAdapterConfig.setListener(this);
        customWorkflowAdapterConfig.setListener(this);

        builtInWorkflowList = new MutableLiveData<>(false);
        customWorkflowList = new MutableLiveData<>(false);

        isListEmpty = new MediatorLiveData<>();

        Observer<Boolean> observer = aBoolean -> isListEmpty.setValue(builtInWorkflowList.getValue() && customWorkflowList.getValue());
        isListEmpty.addSource(builtInWorkflowList,observer);
        isListEmpty.addSource(customWorkflowList,observer);

        didUserCloseDialog = false;
    }

    public Workflow getCurrentSelectedWorkflow() {
        return currentSelectedWorkflow;
    }

    public AdapterConfig<BuiltInWorkflow> getBuiltInWorkflowAdapterConfig() {
        return builtInWorkflowAdapterConfig;
    }

    public AdapterConfig<CustomWorkflow> getCustomWorkflowAdapterConfig() {
        return customWorkflowAdapterConfig;
    }

    public void deleteSelectedBuiltInWorkflow(){
        repository.builtIn()
                .deleteWorkflow(builtInWorkflowAdapterConfig.getSelectedWorkflows().toArray(new String[0]));
        builtInWorkflowAdapterConfig.getSelectedWorkflows().clear();
    }

    public int getTotalSelectionWorkflow(){
        return builtInWorkflowAdapterConfig.getSelectedItemCount() + customWorkflowAdapterConfig.getSelectedItemCount();
    }

    public void deleteSelectedCustomWorkflow(){
        FirebaseRepository.getInstance().custom()
                .deleteWorkflow(customWorkflowAdapterConfig.getSelectedWorkflows().toArray(new String[0]));
        customWorkflowAdapterConfig.getSelectedWorkflows().clear();
    }

    public void updateStateForBuiltInWorkflow(String value){
        repository.builtIn()
                .updateWorkflow(currentSelectedWorkflow.getId(),
                        WorkflowSchema.STATE,value);
    }

    public void updateStateForCustomWorkflow(String state){
        repository.custom()
                .updateWorkflow(currentSelectedWorkflow.getId(),
                        WorkflowSchema.STATE,state);

    }

    public void disableEnableAllWorkflow(FirebaseConfig.UPDATE update){
       repository.custom()
                .disableEnableAllWorkflow(update);
        repository.builtIn()
                .disableEnableAllWorkflow(update);
    }

    @Override
    public void onWorkflowSelected(Workflow workflow) {
        currentSelectedWorkflow = workflow;
    }

    @Override
    public void noneSelected() {
        currentSelectedWorkflow =  null;
    }
}
