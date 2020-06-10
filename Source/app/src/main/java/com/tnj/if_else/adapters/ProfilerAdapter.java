package com.tnj.if_else.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.architecture.concreteEntities.triggerClasses.DayTimeTrigger;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflow;
import com.tnj.if_else.databinding.ProfilerListLayoutBinding;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class ProfilerAdapter extends RecyclerView.Adapter<ProfilerAdapter.ProfileHolder> {

    private onProfileClick click;
    private String[] daysInString;
    private BuiltInWorkflow profiler;

    public ProfilerAdapter setClick(onProfileClick click) {
        this.click = click;
        return this;
    }

    public void setProfiler(BuiltInWorkflow profiler) {
        this.profiler = profiler;
    }

    public ProfilerAdapter(BuiltInWorkflow profiler){
        this.profiler = profiler;
        daysInString = DateFormatSymbols.getInstance().getShortWeekdays();
    }

    @NonNull
    @Override
    public ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfileHolder(ProfilerListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileHolder holder, int position) {

        Trigger trigger = profiler.getSettings().getTrigger(position);
        holder.binding.startTime.setText(trigger.getConfiguration(DayTimeTrigger.Settings.START_TIME, String.class));
        holder.binding.endTime.setText(trigger.getConfiguration(DayTimeTrigger.Settings.END_TIME, String.class));
        StringBuilder builder = new StringBuilder();
        for (Number value : (ArrayList<Number>) trigger.getConfiguration(DayTimeTrigger.Settings.DAYS, ArrayList.class))
            builder.append(daysInString[value.intValue()]).append(", ");

        builder.deleteCharAt(builder.length() - 2);
        holder.binding.days.setText(builder.toString());
    }


    class ProfileHolder extends RecyclerView.ViewHolder {

        ProfilerListLayoutBinding binding;

        public ProfileHolder(ProfilerListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(view -> click.onClick(profiler.getSettings().getAction(getAdapterPosition())
                    ,profiler.getSettings().getTrigger(getAdapterPosition())));
            this.binding.profileDelete.setOnClickListener(view -> {
                view.setEnabled(false);
                /*BuiltInWorkflowConfigurationRepository.getInstance()
                        .updateWorkflow(profiler.getId(), null
                                , WorkflowSchema.ALL_ACTION, FieldValue.arrayRemove(profiler.getSettings().getAction(getAdapterPosition())),
                                WorkflowSchema.ALL_TRIGGER, FieldValue.arrayRemove(profiler.getSettings().getTrigger(getAdapterPosition())));*/
            });
        }
    }
    public interface onProfileClick{
        void onClick(Action action, Trigger trigger);
    }

    @Override
    public int getItemCount() {
        if(profiler != null && profiler.getSettings().getTriggerCount() == profiler.getSettings().getActionCount())
            return profiler.getSettings().getTriggerCount();
        return 0;
    }
}
