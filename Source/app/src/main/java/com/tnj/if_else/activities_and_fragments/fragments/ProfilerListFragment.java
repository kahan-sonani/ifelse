package com.tnj.if_else.activities_and_fragments.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.firestore.ListenerRegistration;
import com.tnj.if_else.R;
import com.tnj.if_else.activities_and_fragments.activities.ProfilerSettingsActivity;
import com.tnj.if_else.adapters.ProfilerAdapter;
import com.tnj.if_else.databinding.FragmentProfilerListBinding;
import com.tnj.if_else.viewModels.ProfilerModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilerListFragment extends Fragment implements ProfilerSettingsActivity.onFabClickListener {

    private FragmentProfilerListBinding controls;
    private ProfilerModel model;
    private ProfilerAdapter adapter;
    private ListenerRegistration registration;

    public ProfilerListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(getActivity()).get(ProfilerModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        controls = DataBindingUtil.inflate(inflater, R.layout.fragment_profiler_list, container, false);
        /*registration = BuiltInWorkflowConfigurationRepository.getInstance().getBuiltInWorkflowQuery(ProfilerBuilder.ID)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                        DocumentSnapshot snapshot = queryDocumentSnapshots.getDocuments().get(0);
                        if (adapter == null) {
                            controls.profilerList.setAdapter(adapter = new ProfilerAdapter(null)
                                    .setClick((action, trigger) -> {
                                        model.editMode = true;
                                        model.selectedTrigger = trigger;
                                        model.selectedAction = action;
                                        NavHostFragment.findNavController(ProfilerListFragment.this)
                                                .navigate(ProfilerListFragmentDirections.actionProfilerListFragmentToProfilerSettings());
                                    }));
                        }
                        adapter.setProfiler(BuiltInWorkflowParser.parseSnapshot(snapshot));
                    }
                });*/

        return controls.getRoot();
    }

    @Override
    public void onClick(View view) {
        model.editMode = false;
        model.selectedAction = null;
        model.selectedTrigger = null;
        NavHostFragment.findNavController(this).navigate(ProfilerListFragmentDirections.actionProfilerListFragmentToProfilerSettings());
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ProfilerSettingsActivity) getActivity()).registerListenerForFAB(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((ProfilerSettingsActivity) getActivity()).unRegisterListenerForFAB(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (registration != null) registration.remove();
    }
}
