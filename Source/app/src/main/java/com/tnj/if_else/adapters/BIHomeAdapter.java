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
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflow;
import com.tnj.if_else.databinding.BiWorkflowListLayoutBinding;
import com.tnj.if_else.utils.interfaces.OnWorkflowTouchListener;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.ArrayList;

public class BIHomeAdapter extends FirestoreRecyclerAdapter<BuiltInWorkflow, RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_DATA = 1;

    private View header;
    private MergeAdapter joiner;
    private OnWorkflowTouchListener<BuiltInWorkflow> actionListener;
    private ArrayList<String> selectedWorkflows;

    public BIHomeAdapter(@NonNull FirestoreRecyclerOptions<BuiltInWorkflow> options) {
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

    public OnWorkflowTouchListener<BuiltInWorkflow> getActionListener() {
        return actionListener;
    }

    public void setActionListener(OnWorkflowTouchListener<BuiltInWorkflow> actionListener) {
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_DATA)
            return new BuiltInWorkflowHolder(BiWorkflowListLayoutBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false));
        else
           return new BuiltInWorkflowHeader(header = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.divider_rv_in_built,parent,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull BuiltInWorkflow builtInWorkflow) {
        if(getItemViewType(i) == TYPE_DATA) {
            BuiltInWorkflowHolder biHomeWorkflowHolder = ((BuiltInWorkflowHolder) viewHolder);
            biHomeWorkflowHolder.binding.getRoot().setSelected(selectedWorkflows.contains(builtInWorkflow.getId()));
            String state = builtInWorkflow.getSettings().getState();
            biHomeWorkflowHolder.binding.workflowDetails.workflowStatus.setText(state);
            biHomeWorkflowHolder.readStatus(biHomeWorkflowHolder, state, builtInWorkflow);
            biHomeWorkflowHolder.binding.workflowDetails.workflowTitle.setText(builtInWorkflow.getDetails().getName());
        }
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

    class BuiltInWorkflowHeader extends RecyclerView.ViewHolder{

        public BuiltInWorkflowHeader(@NonNull View itemView) {
            super(itemView);
        }
    }
    class BuiltInWorkflowHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        BiWorkflowListLayoutBinding binding;

        public BuiltInWorkflowHolder(BiWorkflowListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
            this.binding.getRoot().setOnLongClickListener(this);

        }

        public void readStatus(BuiltInWorkflowHolder workflowHolder, String state, BuiltInWorkflow workflow) {
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

        @Override
        public void onClick(View view) {
            actionListener.onClick(getSnapshots().get(joiner.findRelativeAdapterPositionIn(BIHomeAdapter.this,
                    this,getAbsoluteAdapterPosition()) - 1));
        }

        @Override
        public boolean onLongClick(View view) {
            actionListener.onLongClick(getSnapshots().get(
                    joiner.findRelativeAdapterPositionIn(BIHomeAdapter.this,this,getAbsoluteAdapterPosition()) - 1));
            return true;
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
    public BuiltInWorkflow getItem(int position) {
        return position == 0 ? null : getSnapshots().get(position - 1);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_DATA;
    }
}
