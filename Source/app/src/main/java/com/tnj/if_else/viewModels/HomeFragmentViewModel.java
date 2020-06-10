package com.tnj.if_else.viewModels;

import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreArray;
import com.tnj.if_else.adapters.AdapterConfig;
import com.tnj.if_else.architecture.baseLevelEntities.Workflow;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflow;
import com.tnj.if_else.architecture.secondLevelEntities.CustomWorkflow;
import com.tnj.if_else.firebaseRepository.FirebaseRepository;
import com.tnj.if_else.firebaseRepository.HomeFragmentRepository;
import com.tnj.if_else.firebaseRepository.schema.FirebaseConfig;
import com.tnj.if_else.utils.firebase.BuiltInWorkflowParser;
import com.tnj.if_else.utils.firebase.CustomWorkflowParser;
import com.tnj.if_else.utils.interfaces.ResultSet;

public class HomeFragmentViewModel extends ViewModel implements AdapterConfig.toggleListener{

    private AdapterConfig<BuiltInWorkflow> builtInWorkflowAdapterConfig;
    private AdapterConfig<CustomWorkflow>  customWorkflowAdapterConfig;

    private HomeFragmentRepository repository;
    public boolean didUserCloseDialog;
    public boolean isDialogShown;

    private Workflow currentSelectedWorkflow;

    public HomeFragmentViewModel() {
        repository = new HomeFragmentRepository();

        builtInWorkflowAdapterConfig = new AdapterConfig<>(new FirestoreArray<>(repository.getBuiltInWorkflowQuery(), BuiltInWorkflowParser::parseSnapshot));
        customWorkflowAdapterConfig = new AdapterConfig<>(new FirestoreArray<>(repository.getCustomWorkflowQuery(), CustomWorkflowParser::parseSnapshot));;

        builtInWorkflowAdapterConfig.setListener(this);
        customWorkflowAdapterConfig.setListener(this);

        didUserCloseDialog = false;
        isDialogShown = false;
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
        FirebaseRepository.getInstance().builtIn()
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

    public void updateStateForBuiltInWorkflow(ResultSet<Boolean> listener , String value){
        /*FirebaseRepository.getInstance().builtIn()
                .updateWorkflow(currentSelectedWorkflow.getId(),listener,
                        WorkflowSchema.STATE,value);*/
    }

    public void updateStateForCustomWorkflow(ResultSet<Boolean> listener , String state){
        /*FirebaseRepository.getInstance().custom()
                .updateWorkflow(currentSelectedWorkflow.getId(),listener,
                        WorkflowSchema.STATE,state);*/

    }

    public void disableEnableAllWorkflow(FirebaseConfig.UPDATE update , ResultSet<Boolean> listener){
        /*FirebaseRepository.getInstance().custom()
                .disableEnableAllWorkflow(update, listener);
        FirebaseRepository.getInstance().builtIn()
                .disableEnableAllWorkflow(update,listener);*/
    }

    public void isListEmpty(ResultSet<Boolean> listener){
        //repository.isListEmpty(listener);
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
