package com.tnj.if_else.activities_and_fragments.fragments;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.tnj.if_else.R;
import com.tnj.if_else.activities_and_fragments.activities.EditWorkflowActivity;
import com.tnj.if_else.architecture.secondLevelEntities.CustomWorkflow;
import com.tnj.if_else.databinding.FragmentEditCustomWorkflowDetailsBinding;
import com.tnj.if_else.firebaseRepository.schema.WorkflowSchema;
import com.tnj.if_else.utils.UI.ColorPickerUtility;
import com.tnj.if_else.utils.helperClasses.Color;
import com.tnj.if_else.utils.helperClasses.ColorUtility;
import com.tnj.if_else.utils.helperClasses.Snacker;
import com.tnj.if_else.viewModels.EditCustomWorkflowDetailsViewModel;

import petrov.kristiyan.colorpicker.ColorPicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditCustomWorkflowDetailsFragment extends Fragment {

    private FragmentEditCustomWorkflowDetailsBinding controls;
    private EditCustomWorkflowDetailsViewModel model;

    public EditCustomWorkflowDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        model = new ViewModelProvider(this).get(EditCustomWorkflowDetailsViewModel.class);
        Bundle options = getActivity().getIntent().getExtras();
        model.workflowId.setValue(options.getString(EditWorkflowActivity.IntentExtras.ID));
        controls = FragmentEditCustomWorkflowDetailsBinding.inflate(inflater, container, false);
        controls.setModel(model);
        controls.detailsRoot.setBackgroundColor(ContextCompat.getColor(getContext(), options.getInt(EditWorkflowActivity.IntentExtras.COLOR)));

        controls.editTitleLayout.setTag(false);
        controls.editDescriptionLayout.setTag(false);

        enableToggleBehavior(controls.editTitleLayout, WorkflowSchema.NAME);
        enableToggleBehavior(controls.editDescriptionLayout, WorkflowSchema.DESCRIPTION);

        model.getWorkflow().observe(getViewLifecycleOwner(), customWorkflow -> initializeUI(customWorkflow));
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
                    model.updateWorkflow(WorkflowSchema.COLOR, ColorUtility.getColorByIndex(position))
                            .observe(getViewLifecycleOwner(), state -> Snacker.show(controls.getRoot(), state.getMessage(getContext(), WorkflowSchema.COLOR),
                                    Snackbar.LENGTH_LONG, 4, false, null));
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
            } else {
                l.setTag(false);
                l.getEditText().setEnabled(false);
                l.setEndIconDrawable(getContext().getDrawable(R.drawable.ic_cook_workflow));
                String text = l.getEditText().getText().toString().trim();

                model.updateWorkflow(Name, text).observe(getViewLifecycleOwner(), state ->
                        Snacker.show(controls.getRoot(), state.getMessage(getContext(), Name), Snackbar.LENGTH_LONG
                                , 4, false, null));
            }
        });
    }

    private void initializeUI(CustomWorkflow workflow) {
        controls.editTitleInput.setText(workflow.getDetails().getName());
        controls.editDescriptionInput.setText(workflow.getDetails().getDescription());
        controls.editColorInput.setText(workflow.getDetails().getColor().name);
        controls.editColorLayout.getEndIconDrawable()
                .setColorFilter(ContextCompat.getColor(getContext(), workflow.getDetails().getColor().color), PorterDuff.Mode.SRC_IN);
    }
}
