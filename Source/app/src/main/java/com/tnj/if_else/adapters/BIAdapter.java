package com.tnj.if_else.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.tnj.if_else.R;
import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.architecture.baseLevelEntities.Workflow;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflowDetailsProxy;
import com.tnj.if_else.firebaseRepository.schema.WorkflowSchema;
import com.tnj.if_else.utils.enums.WorkflowErrorCodes;
import com.tnj.if_else.utils.enums.WorkflowSuccessCodes;
import com.tnj.if_else.viewModels.TryBuiltInFragmentViewModel;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;

public class BIAdapter extends RecyclerView.Adapter<BIAdapter.BIHolder>  {

    private LifecycleOwner owner;
    private ArrayList<BuiltInWorkflowDetailsProxy> list;
    private OnBuiltInWorkflowDetailsProxyClick listener;
    private TryBuiltInFragmentViewModel model;

    public BIAdapter(LifecycleOwner lifeCycleOwner, TryBuiltInFragmentViewModel model, ArrayList<BuiltInWorkflowDetailsProxy> list) {
        this.list = list;
        this.model = model;
        owner = lifeCycleOwner;
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
        holder.loading(true);
        model.getStatusForWorkflow(position, list.get(position).getId())
                .observe(owner, s -> {
                    holder.loading(false);
                    if(s != null) holder.biStatusEnable.setChecked(Workflow.State.ACTIVATED.equals(s));
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

        public void loading(boolean loadOrNot) {
            progressForStatus.setVisibility(loadOrNot ? View.VISIBLE : View.GONE);
            biStatusEnable.setEnabled(!loadOrNot);
        }

        void listenForChange(){
            biStatusEnable.setOnCheckedChangeListener((compoundButton, b) -> {
                loading(true);
                String newState = b ? Workflow.State.ACTIVATED : Workflow.State.DEACTIVATED;
                model.updateStatusForWorkflow(list.get(getAbsoluteAdapterPosition()).getId() , newState)
                        .observe(owner, state -> {
                            loading(false);
                            if(state instanceof WorkflowErrorCodes)
                                Toast.makeText(biStatusEnable.getContext(), state.getMessage(biStatusEnable.getContext(),
                                        WorkflowSchema.STATE), Toast.LENGTH_SHORT).show();
                            else if(state == WorkflowSuccessCodes.WORKFLOW_UPDATE_SUCCESS)
                                Toast.makeText(biStatusEnable.getContext(), state.getMessage(biStatusEnable.getContext()
                                        ,WorkflowSchema.STATE), Toast.LENGTH_SHORT).show();
                        });
            });
        }
    }

    public interface OnBuiltInWorkflowDetailsProxyClick{
        void onProxyClick(BuiltInWorkflowDetailsProxy proxy);
    };
}
