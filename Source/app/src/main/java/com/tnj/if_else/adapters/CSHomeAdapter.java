package com.tnj.if_else.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.MergeAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.tnj.if_else.R;
import com.tnj.if_else.architecture.baseLevelEntities.Workflow;
import com.tnj.if_else.architecture.secondLevelEntities.CustomWorkflow;
import com.tnj.if_else.databinding.WorkflowListLayoutBinding;
import com.tnj.if_else.utils.interfaces.OnWorkflowTouchListener;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.ArrayList;

public class CSHomeAdapter extends FirestoreRecyclerAdapter<CustomWorkflow, RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_DATA = 1;

    private View header;
    private ArrayList<String> selectedWorkflows;
    private MergeAdapter joiner;
    private OnWorkflowTouchListener<CustomWorkflow> actionListener;

    public CSHomeAdapter(@NonNull FirestoreRecyclerOptions<CustomWorkflow> options) {
        super(options);
    }

    public void setSelectedWorkflows(ArrayList<String> selectedWorkflows) {
        this.selectedWorkflows = selectedWorkflows;
    }

    public ArrayList<String> getSelectedWorkflows() {
        return selectedWorkflows;
    }

    public void setJoiner(MergeAdapter joiner) {
        this.joiner = joiner;
    }

    public OnWorkflowTouchListener<CustomWorkflow> getActionListener() {
        return actionListener;
    }

    public void setActionListener(OnWorkflowTouchListener<CustomWorkflow> actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        if(header != null)
            header.setVisibility(getItemCount() == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void onError(@NonNull FirebaseFirestoreException e) {
        super.onError(e);
        e.printStackTrace();
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull CustomWorkflow customWorkflow) {
        if(getItemViewType(i) == TYPE_DATA) {

            CustomWorkflowHolder customWorkflowHolder = ((CustomWorkflowHolder) viewHolder);
            customWorkflowHolder.binding.getRoot().setSelected(selectedWorkflows.contains(customWorkflow.getId()));
            String state = customWorkflow.getSettings().getState();

            customWorkflowHolder.binding.workflowDetails.workflowStatus.setText(state);
            customWorkflowHolder.readStatus(customWorkflowHolder, state, customWorkflow);
            customWorkflowHolder.binding.workflowDetails.workflowTitle.setText(customWorkflow.getDetails().getName());

            customWorkflowHolder.binding.criteriaIcon.setIcon(customWorkflow.getSettings().getCriteria(0) != null ?
                    customWorkflow.getSettings().getCriteria(0).getCriteriaDetails().icon : MaterialDrawableBuilder.IconValue.MINUS);
            customWorkflowHolder.binding.triggerIcon.setIcon(customWorkflow.getSettings().getTrigger(0) != null ?
                    customWorkflow.getSettings().getTrigger(0).getTriggerDetails().icon : MaterialDrawableBuilder.IconValue.MINUS);
            customWorkflowHolder.binding.actionIcon.setIcon(customWorkflow.getSettings().getAction(0) != null ?
                    customWorkflow.getSettings().getAction(0).getActionDetails().icon : MaterialDrawableBuilder.IconValue.MINUS);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_DATA)
            return new CustomWorkflowHolder(WorkflowListLayoutBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false));
        else
            return new CustomWorkflowHeader(header = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.divider_rv_user_defined,parent,false));
    }

    class CustomWorkflowHeader extends RecyclerView.ViewHolder{

        public CustomWorkflowHeader(@NonNull View itemView) {
            super(itemView);
        }
    }
    class CustomWorkflowHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        WorkflowListLayoutBinding binding;

        public CustomWorkflowHolder(WorkflowListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            this.binding.getRoot().setOnClickListener(this);
            this.binding.getRoot().setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            actionListener.onClick(getSnapshots().get(joiner.findRelativeAdapterPositionIn(CSHomeAdapter.this,
                    this,getAbsoluteAdapterPosition()) - 1));
        }

        @Override
        public boolean onLongClick(View view) {
            actionListener.onLongClick(getSnapshots().get(joiner.findRelativeAdapterPositionIn(CSHomeAdapter.this,
                    this,getAbsoluteAdapterPosition()) - 1));
            return true;
        }

        public void readStatus(CustomWorkflowHolder workflowHolder, String state, CustomWorkflow workflow) {
            Context c = workflowHolder.binding.getRoot().getContext();
            
            if(Workflow.State.ACTIVATED.equals(state)){
                workflowHolder.binding.workflowDetails.statusIcon.setIcon(MaterialDrawableBuilder.IconValue.RADIOBOX_MARKED);
                ((MaterialCardView) workflowHolder.binding.getRoot()).setCardBackgroundColor(ContextCompat.getColor(c,workflow.getDetails().getColor().color));
            }
            else{
                workflowHolder.binding.workflowDetails.statusIcon.setIcon(MaterialDrawableBuilder.IconValue.RADIOBOX_BLANK);
                ((MaterialCardView) workflowHolder.binding.getRoot()).setCardBackgroundColor(ContextCompat.getColor(c,R.color.colorGray));
            }
        }
    }
    @Override
    public void onChildChanged(@NonNull ChangeEventType type, @NonNull DocumentSnapshot snapshot, int newIndex, int oldIndex) {
        newIndex = ++newIndex;
        oldIndex = ++oldIndex;
        switch (type) {
            case ADDED:
                notifyItemInserted(newIndex);
                break;
            case CHANGED:
                notifyItemChanged(newIndex);
                break;
            case REMOVED:
                notifyItemRemoved(oldIndex);
                break;
            case MOVED:
                notifyItemMoved(oldIndex, newIndex);
                break;
            default:
                throw new IllegalStateException("Incomplete case statement");
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @NonNull
    @Override
    public CustomWorkflow getItem(int position) {
        return position == 0 ? null : getSnapshots().get(position - 1);
    }
    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_DATA;
    }
}
