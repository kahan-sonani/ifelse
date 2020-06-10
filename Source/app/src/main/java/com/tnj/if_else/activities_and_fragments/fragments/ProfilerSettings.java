package com.tnj.if_else.activities_and_fragments.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tnj.if_else.R;
import com.tnj.if_else.activities_and_fragments.activities.ProfilerSettingsActivity;
import com.tnj.if_else.architecture.concreteEntities.actionClasses.VolumeChangeAction;
import com.tnj.if_else.architecture.concreteEntities.triggerClasses.DayTimeTrigger;
import com.tnj.if_else.databinding.ProfilerSettingsFragmentBinding;
import com.tnj.if_else.viewModels.ProfilerModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilerSettings extends Fragment implements ProfilerSettingsActivity.onFabClickListener, CompoundButton.OnCheckedChangeListener {

    private ProfilerSettingsFragmentBinding controls;
    private ProfilerModel model;

    public ProfilerSettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        controls = DataBindingUtil.inflate(inflater, R.layout.profiler_settings_fragment, container, false);
        model = new ViewModelProvider(getActivity()).get(ProfilerModel.class);
        initializeSliders();
        if (model.editMode) {
            initializeVolume();
            initializeDayPicker();
            initializeTime();
        }
        controls.profilerEndTimeLayout.getEditText().setOnClickListener(v -> showTimePickerDialog((view, hourOfDay, minute) -> controls.profilerEndTime.setText(String.valueOf(hourOfDay).concat(":").concat(String.valueOf(minute)))));
        controls.profilerStartTimeLayout.getEditText().setOnClickListener(v ->
                showTimePickerDialog(((view, hourOfDay, minute) -> controls.profilerStartTime.setText(String.valueOf(hourOfDay).concat(":").concat(String.valueOf(minute))))));
        return controls.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ProfilerSettingsActivity) requireActivity()).registerListenerForFAB(this);
    }

    private void showTimePickerDialog(TimePickerDialog.OnTimeSetListener listener) {
        new TimePickerDialog(getContext(), listener, 0, 0, true)
                .show();
    }

    @Override
    public void onClick(View view) {
        if (isProfilerSettingsValid()) {
            VolumeChangeAction action = new VolumeChangeAction();

            if (controls.isVoiceCall.isChecked())
                action.addVolumeProfile(VolumeChangeAction.Settings.VOICE_CALL_VALUE, ((int) controls.voice.getValue()));
            if (controls.isRing.isChecked())
                action.addVolumeProfile(VolumeChangeAction.Settings.RINGER_VALUE, ((int) controls.ringer.getValue()));
            if (controls.isNotification.isChecked())
                action.addVolumeProfile(VolumeChangeAction.Settings.NOTIFICATION_VALUE, ((int) controls.notification.getValue()));
            if (controls.isMedia.isChecked())
                action.addVolumeProfile(VolumeChangeAction.Settings.MEDIA_VALUE, ((int) controls.media.getValue()));
            if (controls.isAlarm.isChecked())
                action.addVolumeProfile(VolumeChangeAction.Settings.ALARM_VALUE, ((int) controls.alarm.getValue()));

            DayTimeTrigger trigger = new DayTimeTrigger.DayTimeBuilder()
                    .addDays(controls.dayPicker.getSelectedDays())
                    .setTimeRange(controls.profilerStartTime.getText().toString(), controls.profilerEndTime.getText().toString())
                    .build();

            action.setActionDetails(null);
            trigger.setTriggerDetails(null);
            /*if (model.editMode) {
                BuiltInWorkflowConfigurationRepository.getInstance()
                        .updateWorkflow(ProfilerBuilder.ID, null,
                                WorkflowSchema.ALL_ACTION, FieldValue.arrayRemove(model.selectedAction),
                                WorkflowSchema.ALL_TRIGGER, FieldValue.arrayRemove(model.selectedTrigger),
                                WorkflowSchema.ALL_ACTION, FieldValue.arrayUnion(action),
                                WorkflowSchema.ALL_TRIGGER, FieldValue.arrayUnion(trigger));
            } else {
                BuiltInWorkflowConfigurationRepository.getInstance()
                        .updateOrCreateWorkflow(model.proxy.getId(), null,
                                WorkflowSchema.ALL_ACTION, FieldValue.arrayUnion(action),
                                WorkflowSchema.ALL_TRIGGER, FieldValue.arrayUnion(trigger));
            }*/
            NavHostFragment.findNavController(ProfilerSettings.this)
                    .navigate(ProfilerSettingsDirections.actionProfilerSettingsToProfilerListFragment());

        } else
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Error!")
                    .setMessage("Invalid Profiler details")
                    .setPositiveButton("OKAY", (dialog, which) -> dialog.dismiss()).create().show();
    }

    private boolean isProfilerSettingsValid() {
        boolean dateValid = false, volumeValid = false, daysValid = false;
        Date startDate;
        Date endDate;

        if ("".equals(controls.profilerStartTime.getText().toString()) || "".equals(controls.profilerEndTime.getText().toString()))
            return false;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
            startDate = formatter.parse(controls.profilerStartTime.getText().toString());
            endDate = formatter.parse(controls.profilerEndTime.getText().toString());
            dateValid = endDate.after(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        volumeValid = controls.isAlarm.isChecked() || controls.isMedia.isChecked() || controls.isNotification.isChecked()
                || controls.isRing.isChecked() || controls.isVoiceCall.isChecked();

        daysValid = controls.dayPicker.getSelectedDays().size() != 0;

        return dateValid && volumeValid && daysValid;
    }

    @Override
    public void onPause() {
        super.onPause();
        ((ProfilerSettingsActivity) requireActivity()).unRegisterListenerForFAB(this);
    }

    private void initializeSliders() {
        int color = ContextCompat.getColor(getContext(), model.proxy.getColor().color);
        controls.mediaIcon.setColor(color);
        controls.alarmIcon.setColor(color);
        controls.notificationIcon.setColor(color);
        controls.ringIcon.setColor(color);
        controls.voiceIcon.setColor(color);

        controls.ringer.setEnabled(false);
        controls.media.setEnabled(false);
        controls.notification.setEnabled(false);
        controls.voice.setEnabled(false);
        controls.alarm.setEnabled(false);

        controls.isAlarm.setOnCheckedChangeListener(this);
        controls.isMedia.setOnCheckedChangeListener(this);
        controls.isNotification.setOnCheckedChangeListener(this);
        controls.isRing.setOnCheckedChangeListener(this);
        controls.isVoiceCall.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        View v;
        switch (compoundButton.getId()) {
            case R.id.isAlarm:
                v = controls.alarm;
                break;
            case R.id.isMedia:
                v = controls.media;
                break;
            case R.id.isNotification:
                v = controls.notification;
                break;
            case R.id.isRing:
                v = controls.ringer;
                break;
            case R.id.isVoice_call:
                v = controls.voice;
                break;
            default:
                v = null;
        }
        if (b)
            v.setEnabled(true);
        else
            v.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        controls = null;
    }

    private void initializeDayPicker() {
        controls.dayPicker._$_clearFindViewByIdCache();
        controls.dayPicker.clearSelection();
        controls.dayPicker.setSelectedDays(DayTimeTrigger.convertCalendarDaysIntoWeekDays((ArrayList<Number>) model.selectedTrigger.getConfiguration(DayTimeTrigger.Settings.DAYS, ArrayList.class)));
    }

    private void initializeTime() {
        controls.profilerStartTime.setText(model.selectedTrigger.getConfiguration(DayTimeTrigger.Settings.START_TIME, String.class));
        controls.profilerEndTime.setText(model.selectedTrigger.getConfiguration(DayTimeTrigger.Settings.END_TIME, String.class));
    }

    private void initializeVolume() {

        try {
            controls.ringer.setValue(model.selectedAction.getConfiguration(VolumeChangeAction.Settings.RINGER_VALUE, Number.class).floatValue());
            controls.isRing.setChecked(true);
        }catch (Exception e){ }
        try{
            controls.notification.setValue(model.selectedAction.getConfiguration(VolumeChangeAction.Settings.NOTIFICATION_VALUE, Number.class).floatValue());
            controls.isNotification.setChecked(true);
        }
        catch (Exception e){}
        try{
            controls.media.setValue(model.selectedAction.getConfiguration(VolumeChangeAction.Settings.MEDIA_VALUE, Number.class).floatValue());
            controls.isMedia.setChecked(true);
        }
        catch (Exception e){}
        try{
            controls.voice.setValue(model.selectedAction.getConfiguration(VolumeChangeAction.Settings.VOICE_CALL_VALUE, Number.class).floatValue());
            controls.isVoiceCall.setChecked(true);
        }
        catch (Exception e){}
        try{
            controls.alarm.setValue(model.selectedAction.getConfiguration(VolumeChangeAction.Settings.ALARM_VALUE, Number.class).floatValue());
            controls.isAlarm.setChecked(true);
        }
        catch (Exception e){}

    }
}
