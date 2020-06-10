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

import com.google.firebase.firestore.Source;
import com.tnj.if_else.R;
import com.tnj.if_else.activities_and_fragments.activities.EditWorkflowActivity;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflow;
import com.tnj.if_else.databinding.FragmentEditBuiltInWorkflowBinding;
import com.tnj.if_else.firebaseRepository.FirebaseRepository;

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

        options = getActivity().getIntent().getExtras();
        setHasOptionsMenu(true);
        controls = FragmentEditBuiltInWorkflowBinding.inflate(LayoutInflater.from(getContext()), container, false);
        controls.detailsRoot.setBackgroundColor(ContextCompat.getColor(getContext(), options.getInt(EditWorkflowActivity.IntentExtras.COLOR)));
        startLoadingUI();
        FirebaseRepository.getInstance().builtIn()
                .getWorkflow(Source.CACHE,options.getString(EditWorkflowActivity.IntentExtras.ID), result -> {
                    endLoadingUI();
                    initializeUI(result);
                });

        return controls.getRoot();
    }

    private void startLoadingUI() {
        controls.progressBar.setVisibility(View.VISIBLE);
    }

    private void endLoadingUI() {
        controls.progressBar.setVisibility(View.INVISIBLE);
    }

    private void initializeUI(BuiltInWorkflow workflow) {
        controls.editTitleInput.setText(workflow.getDetails().getName());
        controls.editDescriptionInput.setText(workflow.getDetails().getDescription());
        controls.editColorInput.setText(workflow.getDetails().getColor().name);
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
