package com.tnj.if_else.adapters;

import androidx.lifecycle.LifecycleOwner;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.tnj.if_else.architecture.baseLevelEntities.Workflow;
import com.tnj.if_else.utils.interfaces.WorkflowActionModes;

import java.util.ArrayList;

public class AdapterConfig<T extends Workflow> implements WorkflowActionModes {

    private toggleListener listener;
    private ObservableSnapshotArray<T> snapshotArray;
    private ArrayList<String> selectedWorkflows;
    private FirestoreRecyclerOptions.Builder<T> optionBuilder;

    public AdapterConfig(ObservableSnapshotArray<T> snapshotArray){
        selectedWorkflows = new ArrayList<>();
        optionBuilder = new FirestoreRecyclerOptions.Builder<>();
        this.snapshotArray =snapshotArray;
        optionBuilder.setSnapshotArray(snapshotArray);
    }

    public void setListener(toggleListener listener) {
        this.listener = listener;
    }

    public AdapterConfig setLifeCycleForAdapter(LifecycleOwner owner){
        optionBuilder.setLifecycleOwner(owner);
        return this;
    }

    public ArrayList<String> getSelectedWorkflows() {
        return selectedWorkflows;
    }

    public void setSelectedWorkflows(ArrayList<String> selectedWorkflows) {
        this.selectedWorkflows = selectedWorkflows;
    }

    public void setSnapshotArray(ObservableSnapshotArray<T> snapshotArray) {
        this.snapshotArray = snapshotArray;
    }

    public ObservableSnapshotArray<T> getSnapshotArray() {
        return snapshotArray;
    }

    public FirestoreRecyclerOptions<T> getFireStoreRecyclerOptions(){
        return optionBuilder.build();
    }

    @Override
    public int getSelectedItemCount() {
        return selectedWorkflows.size();
    }

    boolean containsId(String id) {
        return selectedWorkflows.contains(id);
    }

    @Override
    public boolean toggleItem(String id) {
        boolean v;
        if (v = containsId(id))
            selectedWorkflows.remove(id);
        else
            selectedWorkflows.add(id);
        if(selectedWorkflows.size() == 0) listener.noneSelected();
        else if(selectedWorkflows.size() == 1) listener.onWorkflowSelected(find(selectedWorkflows.get(0)));

        return v;
    }

    @Override
    public void clearSelection() {
        selectedWorkflows.clear();
    }

    @Override
    public void selectAll() {
        for (T workflow : snapshotArray)
            if (!containsId(workflow.getId()))
                selectedWorkflows.add(workflow.getId());
    }

    @Override
    public boolean isAllSelected() {
        return getSelectedItemCount() == snapshotArray.size();
    }

    public interface toggleListener{
        void onWorkflowSelected(Workflow workflow);
        void noneSelected();
    }
    private Workflow find(String id){
        for(Workflow w : snapshotArray)
            if(w.getId().equals(id))
                return w;
        return null;
    }
}
