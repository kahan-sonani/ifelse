package com.tnj.if_else.activities_and_fragments.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tnj.if_else.R;
import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.architecture.baseLevelEntities.Criteria;
import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.databinding.FragmentEnterWorkflowDetailsBinding;
import com.tnj.if_else.utils.UI.ColorPickerUtility;
import com.tnj.if_else.utils.helperClasses.Color;
import com.tnj.if_else.utils.helperClasses.EntitySet;
import com.tnj.if_else.utils.lookup.ColorUtility;
import com.tnj.if_else.viewModels.CookWorkflowModel;

import petrov.kristiyan.colorpicker.ColorPicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnterWorkflowDetailsFragment extends Fragment {

    private FragmentEnterWorkflowDetailsBinding controls;
    private CookWorkflowModel model;

    public EnterWorkflowDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        controls = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_workflow_details, container, false);
        model = new ViewModelProvider(getActivity()).get(CookWorkflowModel.class);
        controls.setModel(model);
        controls.colorLayout.getEditText().setOnClickListener(v -> showColorPicker());
        controls.saveWorkflow.setOnClickListener(view -> {
            /*if (model.getTitleValidator().isValid()) {
                try {
                    setWorkflowDetails();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                *//*CustomWorkflowConfigurationRepository.getInstance().createWorkflow(model.workflow);
                getActivity().finish();*//*
            } else*/
                controls.layoutTitle.setError("Required!");
        });

        controls.setLifecycleOwner(this);
        return controls.getRoot();
    }

    private void showColorPicker() {
        ColorPicker picker = new ColorPicker(getActivity())
                .setRoundColorButton(true);
        ColorPickerUtility.setDefaultColors(getContext(), picker);
        picker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
            @Override
            public void setOnFastChooseColorListener(int position, int color) {
                Color c = ColorUtility.getColorByIndex(position);
                controls.colorLayout.getEndIconDrawable().setTint(color);
                controls.chooseColor.setText(c.name);
                model.workflow.getDetails().setColor(ColorUtility.getColorByIndex(position));
                Log.i("ifelse",model.workflow.getDetails().getColor().name);
            }

            @Override
            public void onCancel() {

            }
        });
        picker.show();
    }

    private void setWorkflowDetails() throws ClassNotFoundException {
        model.workflow.getDetails().setName(controls.workflowTitleInput.getText().toString().trim())
                .setDescription(controls.workflowDescriptionInput.getText().toString().trim());
        for (EntitySet triggerSet : model.triggers) {
            model.workflow.getSettings().addTrigger((Class<Trigger>) Class.forName(triggerSet.className));
        }
        for (EntitySet actionSet : model.actions) {
            model.workflow.getSettings().addAction((Class<Action>) Class.forName(actionSet.className));
        }
        for (EntitySet criteriaSet : model.criteria) {
            model.workflow.getSettings().addCriteria((Class<Criteria>) Class.forName(criteriaSet.className));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        model.workflow.getDetails().setName(controls.workflowDescriptionInput.getText().toString().trim());
        model.workflow.getDetails().setDescription(controls.workflowDescriptionInput.getText().toString().trim());
        model = null;
    }
}
