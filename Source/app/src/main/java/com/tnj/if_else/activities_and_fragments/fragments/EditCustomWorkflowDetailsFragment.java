package com.tnj.if_else.activities_and_fragments.fragments;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.tnj.if_else.R;
import com.tnj.if_else.activities_and_fragments.activities.EditWorkflowActivity;
import com.tnj.if_else.architecture.secondLevelEntities.CustomWorkflow;
import com.tnj.if_else.databinding.FragmentEditCustomWorkflowDetailsBinding;
import com.tnj.if_else.firebaseRepository.schema.WorkflowSchema;
import com.tnj.if_else.utils.UI.ColorPickerUtility;
import com.tnj.if_else.utils.helperClasses.Color;
import com.tnj.if_else.utils.helperClasses.validator.RequiredValidator;
import com.tnj.if_else.utils.lookup.ColorUtility;

import petrov.kristiyan.colorpicker.ColorPicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditCustomWorkflowDetailsFragment extends Fragment {

    private FragmentEditCustomWorkflowDetailsBinding controls;
    private RequiredValidator titleValidator;
    private Bundle options;

    public EditCustomWorkflowDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleValidator = new RequiredValidator();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        options = getActivity().getIntent().getExtras();
        controls = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_custom_workflow_details, container, false);
        controls.detailsRoot.setBackgroundColor(ContextCompat.getColor(getContext(), options.getInt(EditWorkflowActivity.IntentExtras.COLOR)));
        //titleValidator.viewToValidate(controls.editTitleLayout);

        controls.editTitleLayout.setTag(false);
        controls.editDescriptionLayout.setTag(false);

        startLoadingUI();
        /*CustomWorkflowConfigurationRepository.getInstance()
                .getWorkflow(Source.CACHE, options.getString(EditWorkflowActivity.IntentExtras.ID), result -> {
                    endLoadingUI();
                    initializeUI(result);
                });*/

        enableToggleBehavior(controls.editTitleLayout, WorkflowSchema.NAME);
        enableToggleBehavior(controls.editDescriptionLayout, WorkflowSchema.DESCRIPTION);

        controls.editColorInput.setOnClickListener(v -> {
            ColorPicker dialog = new ColorPicker(getActivity());
            dialog.setRoundColorButton(true);
            ColorPickerUtility.setDefaultColors(getContext(), dialog);
            dialog.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                @Override
                public void setOnFastChooseColorListener(int position, int color) {
                    Color c = ColorUtility.getColorByIndex(position);
                    controls.editColorInput.setText(c.name);
                    controls.editColorLayout.getEndIconDrawable()
                            .setColorFilter(ContextCompat.getColor(getContext(), c.color), PorterDuff.Mode.SRC_IN);
                    updateProperty(WorkflowSchema.COLOR,ColorUtility.getColorByIndex(position));
                }

                @Override
                public void onCancel() {
                }
            }).show();
        });

        return controls.getRoot();
    }


    private void enableToggleBehavior(TextInputLayout l, String Name) {
        l.setEndIconOnClickListener(v -> {
            if (!((boolean) l.getTag())) {
                l.setTag(true);
                l.getEditText().setEnabled(true);
                l.requestFocus();
                l.getEditText().moveCursorToVisibleOffset();
                l.setEndIconDrawable(getContext().getDrawable(R.drawable.ic_save));
                if(Name.equals(WorkflowSchema.NAME)){}
                    //titleValidator.addTextWatcher(this);
            } else {
                l.setTag(false);
                l.getEditText().setEnabled(false);
                l.setEndIconDrawable(getContext().getDrawable(R.drawable.ic_cook_workflow));
                String text = l.getEditText().getText().toString().trim();
                if (Name.equals(WorkflowSchema.NAME)) {
                    /*if (titleValidator.isValid())
                        updateProperty(Name, text);*/
                }else
                    updateProperty(Name, text);
            }
        });
    }

    private void startLoadingUI() {
        controls.progressBar.setVisibility(View.VISIBLE);
    }

    private void endLoadingUI() {
        controls.progressBar.setVisibility(View.INVISIBLE);
    }

    private void initializeUI(CustomWorkflow workflow) {
        controls.editTitleInput.setText(workflow.getDetails().getName());
        controls.editDescriptionInput.setText(workflow.getDetails().getDescription());
        controls.editColorInput.setText(workflow.getDetails().getColor().name);
        GradientDrawable drawable = (GradientDrawable) controls.editColorLayout.getEndIconDrawable();
        drawable.setColorFilter(ContextCompat.getColor(getContext(), workflow.getDetails().getColor().color), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        controls = null;
        options = null;
    }

    private <T> void updateProperty(String property, T object) {
        /*CustomWorkflowConfigurationRepository.getInstance()
                .updateWorkflow(options.getString(EditWorkflowActivity.IntentExtras.ID), null,property, object);*/
    }
}
