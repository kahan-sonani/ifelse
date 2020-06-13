package com.tnj.if_else.activities_and_fragments.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.tnj.if_else.R;
import com.tnj.if_else.adapters.BIAdapter;
import com.tnj.if_else.databinding.FragmentTryBuiltInBinding;
import com.tnj.if_else.utils.helperClasses.BIWorkflowProviders;
import com.tnj.if_else.viewModels.TryBuiltInFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TryBuiltInFragment extends Fragment {

    private BIAdapter adapter;

    public TryBuiltInFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new BIAdapter(this,new ViewModelProvider(this).get(TryBuiltInFragmentViewModel.class)
                ,BIWorkflowProviders.getInstance().getAllWorkflow());

        adapter.setListener(proxy -> NavHostFragment.findNavController(TryBuiltInFragment.this)
                .navigate(TryBuiltInFragmentDirections.actionTryBuiltInFragmentToProfilerSettingsActivity()));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTryBuiltInBinding controls = DataBindingUtil.inflate(inflater, R.layout.fragment_try_built_in, container, false);
        ((GridLayoutManager) controls.tryBuiltInWorkflows.getLayoutManager()).setSpanCount(
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 1);
        controls.tryBuiltInWorkflows.setAdapter(adapter);
        return controls.getRoot();
    }
}
