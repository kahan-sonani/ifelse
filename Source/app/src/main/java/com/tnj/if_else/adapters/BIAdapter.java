package com.tnj.if_else.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tnj.if_else.R;
import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.architecture.baseLevelEntities.Workflow;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflowDetailsProxy;
import com.tnj.if_else.viewModels.TryBuiltInFragmentViewModel;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;

public class BIAdapter extends RecyclerView.Adapter<BIAdapter.BIHolder>  {

    private ArrayList<BuiltInWorkflowDetailsProxy> list;
    private OnBuiltInWorkflowDetailsProxyClick listener;
    private TryBuiltInFragmentViewModel model;

    public BIAdapter(TryBuiltInFragmentViewModel model, ArrayList<BuiltInWorkflowDetailsProxy> list) {
        this.list = list;
        this.model = model;
    }

    @NonNull
    @Override
    public BIHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BIHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bi_workflow_layout,parent,false));
    }

    public void setListener(OnBuiltInWorkflowDetailsProxyClick listener) {
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull BIHolder holder, int position) {
        BuiltInWorkflowDetailsProxy w = list.get(position);

        holder.workflowHeader.getBackground().setTint(
                ContextCompat.getColor(holder.biActionIcon.getContext(),w.getColor().color));

        holder.biTitle.setText(w.getName());
        holder.biDescription.setText(w.getDescription());

        Action action = w.getInitialSettings().getAction();
        Trigger trigger = w.getInitialSettings().getTrigger();

        holder.biActionIcon.setIcon(action.getActionDetails().icon);
        holder.biTriggerIcon.setIcon(trigger.getTriggerDetails().icon);
        holder.biCriteriaIcon.setIcon(MaterialDrawableBuilder.IconValue.MINUS);

        holder.biActionText.setText(action.getActionDetails().name);
        holder.biTriggerText.setText(trigger.getTriggerDetails().name);
        holder.biCriteriaText.setText("None");
        holder.startLoadingUI();
        model.getStatusForWorkflow(position, list.get(position).getId(),
                result -> {
                    holder.endLoadingUI();
                    holder.biStatusEnable.setChecked(result);
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BIHolder extends RecyclerView.ViewHolder{

        private RelativeLayout workflowHeader;
        private TextView biTitle;
        private TextView biDescription;
        private Switch biStatusEnable;
        private MaterialIconView biActionIcon;
        private MaterialIconView biTriggerIcon;
        private MaterialIconView biCriteriaIcon;
        private TextView biActionText;
        private TextView biTriggerText;
        private TextView biCriteriaText;
        private ProgressBar progressForStatus;

        BIHolder(View itemView) {
            super(itemView);
            workflowHeader = itemView.findViewById(R.id.workflow_header);
            biTitle = itemView.findViewById( R.id.bi_title);
            biDescription = itemView.findViewById(R.id.bi_description);
            biStatusEnable = itemView.findViewById(R.id.biStatusEnable);
            biActionIcon = itemView.findViewById(R.id.bi_action_icon);
            biTriggerIcon = itemView.findViewById(R.id.bi_trigger_icon);
            biCriteriaIcon = itemView.findViewById(R.id.bi_criteria_icon);
            progressForStatus = itemView.findViewById(R.id.progressForStatus);
            biActionText = itemView.findViewById(R.id.bi_action_text);
            biTriggerText = itemView.findViewById(R.id.bi_trigger_text);
            biCriteriaText = itemView.findViewById(R.id.bi_criteria_text);
            itemView.setOnClickListener(v -> listener.onProxyClick(list.get(getAbsoluteAdapterPosition())));
            listenForChange();
        }

        public void startLoadingUI() {
            progressForStatus.setVisibility(View.VISIBLE);
            biStatusEnable.setEnabled(false);
        }

        public void endLoadingUI() {
            progressForStatus.setVisibility(View.GONE);
            biStatusEnable.setEnabled(true);
        }

        void listenForChange(){
            biStatusEnable.setOnCheckedChangeListener((compoundButton, b) -> {
                startLoadingUI();
                String newState = b ? Workflow.State.ACTIVATED : Workflow.State.DEACTIVATED;
                model.updateStatusForWorkflow(getAbsoluteAdapterPosition(), list.get(getAbsoluteAdapterPosition()).getId(),
                        result -> endLoadingUI(),newState);
            });
        }
    }

    public interface OnBuiltInWorkflowDetailsProxyClick{
        void onProxyClick(BuiltInWorkflowDetailsProxy proxy);
    };
}
