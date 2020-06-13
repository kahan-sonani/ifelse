package com.tnj.if_else.activities_and_fragments.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tnj.if_else.R;
import com.tnj.if_else.activities_and_fragments.activities.EditWorkflowActivity;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflow;
import com.tnj.if_else.databinding.FragmentEditBuiltInWorkflowBinding;
import com.tnj.if_else.viewModels.EditBuiltInWorkflowDetailsViewModel;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditBuiltInWorkflowFragment extends Fragment {


    private FragmentEditBuiltInWorkflowBinding controls;
    private Bundle options;

    public EditBuiltInWorkflowFragment() {
        // Required empty public constructor
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        options = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EditBuiltInWorkflowDetailsViewModel model = new ViewModelProvider(this).get(EditBuiltInWorkflowDetailsViewModel.class);
        options = getActivity().getIntent().getExtras();
        model.workflowId.setValue(options.getString(EditWorkflowActivity.IntentExtras.ID));
        setHasOptionsMenu(true);
        controls = FragmentEditBuiltInWorkflowBinding.inflate(LayoutInflater.from(getContext()), container, false);
        controls.setModel(model);
        controls.detailsRoot.setBackgroundColor(ContextCompat.getColor(getContext(), options.getInt(EditWorkflowActivity.IntentExtras.COLOR)));

        model.getWorkflow().observe(getViewLifecycleOwner(), builtInWorkflow -> initializeUI(builtInWorkflow));
        return controls.getRoot();
    }

    private void initializeUI(BuiltInWorkflow workflow) {
        controls.editColorInput.setText(workflow.getDetails().getColor().name);
        controls.editDescriptionInput.setText(workflow.getDetails().getDescription());
        controls.editTitleInput.setText(workflow.getDetails().getName());
        controls.editColorLayout.getEndIconDrawable()
                .setColorFilter(ContextCompat
                        .getColor(getContext(), workflow.getDetails().getColor().color), PorterDuff.Mode.SRC);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MaterialMenuInflater
                .with(getContext())
                .setDefaultColorResource(R.color.colorWhite)
                .inflate(R.menu.edit_workflow_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
