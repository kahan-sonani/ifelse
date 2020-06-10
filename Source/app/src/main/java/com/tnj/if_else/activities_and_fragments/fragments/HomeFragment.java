package com.tnj.if_else.activities_and_fragments.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.MergeAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tnj.if_else.R;
import com.tnj.if_else.activities_and_fragments.activities.EditWorkflowActivity;
import com.tnj.if_else.adapters.AdapterConfig;
import com.tnj.if_else.adapters.BIHomeAdapter;
import com.tnj.if_else.adapters.CSHomeAdapter;
import com.tnj.if_else.architecture.baseLevelEntities.Workflow;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflow;
import com.tnj.if_else.architecture.secondLevelEntities.CustomWorkflow;
import com.tnj.if_else.databinding.FragmentHomeBinding;
import com.tnj.if_else.databinding.HelperInfoLayoutBinding;
import com.tnj.if_else.firebaseRepository.schema.FirebaseConfig;
import com.tnj.if_else.utils.interfaces.OnWorkflowTouchListener;
import com.tnj.if_else.viewModels.HomeFragmentViewModel;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding controls;
    private HomeFragmentViewModel model;
    private BIHomeAdapter adapterBuiltIn;
    private CSHomeAdapter adapterCustom;
    private ActionMode actionMode;
    private HelperInfoLayoutBinding binding = null;
    private ActionMode.Callback actionModeCallback;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        setHasOptionsMenu(true);
        initializeActionModeCallback();
        initializeAdapter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        controls = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        joinAdapters();
        listenForScrollChange();
        controls.cookWorkflowFAB.setOnClickListener(view -> NavHostFragment.findNavController(HomeFragment.this)
                .navigate(HomeFragmentDirections.actionHomeFragmentToSingleWorkflowManagerActivity()));
        controls.setLifecycleOwner(this);
        return controls.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        showHelperDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        model.isDialogShown = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        actionModeCallback = null;
        actionMode = null;
        adapterBuiltIn = null;
        adapterCustom = null;
        binding = null;
        controls = null;
    }

    private void initializeAdapter() {

        model.getBuiltInWorkflowAdapterConfig().setLifeCycleForAdapter(getActivity());
        model.getCustomWorkflowAdapterConfig().setLifeCycleForAdapter(getActivity());

        adapterBuiltIn = new BIHomeAdapter(model.getBuiltInWorkflowAdapterConfig().getFireStoreRecyclerOptions());
        adapterCustom = new CSHomeAdapter(model.getCustomWorkflowAdapterConfig().getFireStoreRecyclerOptions());

        adapterCustom.setSelectedWorkflows(model.getCustomWorkflowAdapterConfig().getSelectedWorkflows());
        adapterBuiltIn.setSelectedWorkflows(model.getBuiltInWorkflowAdapterConfig().getSelectedWorkflows());

        adapterBuiltIn.setActionListener(new OnWorkflowTouchListener<BuiltInWorkflow>() {
            @Override
            public void onClick(BuiltInWorkflow workflow) {
                if (actionMode == null) {
                    Intent i = new Intent(getActivity(), EditWorkflowActivity.class);
                    i.putExtra(EditWorkflowActivity.IntentExtras.CATEGORY, FirebaseConfig.CATEGORY.BUILT_IN.ordinal());
                    i.putExtra(EditWorkflowActivity.IntentExtras.ID, workflow.getId());
                    i.putExtra(EditWorkflowActivity.IntentExtras.COLOR_NAME, workflow.getDetails().getColor().name);
                    i.putExtra(EditWorkflowActivity.IntentExtras.COLOR, workflow.getDetails().getColor().color);
                    getActivity().startActivity(i);
                } else {
                    model.getBuiltInWorkflowAdapterConfig().toggleItem(workflow.getId());
                    adapterBuiltIn.notifyDataSetChanged();
                    updateActionMode();
                }
            }

            @Override
            public void onLongClick(BuiltInWorkflow workflow) {
                model.getBuiltInWorkflowAdapterConfig().toggleItem(workflow.getId());
                enableActionMode();
                adapterBuiltIn.notifyDataSetChanged();
            }
        });

        adapterCustom.setActionListener(new OnWorkflowTouchListener<CustomWorkflow>() {
            @Override
            public void onClick(CustomWorkflow workflow) {
                if (actionMode == null) {
                    Intent i = new Intent(getActivity(), EditWorkflowActivity.class);
                    i.putExtra(EditWorkflowActivity.IntentExtras.CATEGORY, FirebaseConfig.CATEGORY.CUSTOM.ordinal());
                    i.putExtra(EditWorkflowActivity.IntentExtras.ID, workflow.getId());
                    i.putExtra(EditWorkflowActivity.IntentExtras.COLOR_NAME, workflow.getDetails().getColor().name);
                    i.putExtra(EditWorkflowActivity.IntentExtras.COLOR, workflow.getDetails().getColor().color);
                    getActivity().startActivity(i);
                } else {
                    model.getCustomWorkflowAdapterConfig().toggleItem(workflow.getId());
                    adapterCustom.notifyDataSetChanged();
                    updateActionMode();
                }
            }

            @Override
            public void onLongClick(CustomWorkflow workflow) {
                model.getCustomWorkflowAdapterConfig().toggleItem(workflow.getId());
                enableActionMode();
                adapterCustom.notifyDataSetChanged();
            }
        });
    }

    private void initializeActionModeCallback() {
        AdapterConfig<BuiltInWorkflow> builtInWorkflowAdapterConfig = model.getBuiltInWorkflowAdapterConfig();
        AdapterConfig<CustomWorkflow> customWorkflowAdapterConfig = model.getCustomWorkflowAdapterConfig();

        actionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return createContextualMenu(R.color.colorWhite, R.menu.contextual_home_menu, menu);
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                boolean bool = customWorkflowAdapterConfig.isAllSelected() && builtInWorkflowAdapterConfig.isAllSelected();
                menu.findItem(R.id.workflow_selectAll).setVisible(!bool);
                menu.findItem(R.id.workflow_unSelectAll).setVisible(bool);

                if (model.getTotalSelectionWorkflow() == 1) {
                    boolean b = Workflow.State.ACTIVATED.equals(model.getCurrentSelectedWorkflow().getSettings().getState());
                    menu.findItem(R.id.workflow_status_active).setVisible(!b);
                    menu.findItem(R.id.workflow_status_inactive).setVisible(b);
                } else {
                    menu.findItem(R.id.workflow_status_inactive).setVisible(false);
                    menu.findItem(R.id.workflow_status_active).setVisible(false);
                }
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.workflow_delete:
                        model.deleteSelectedBuiltInWorkflow();
                        model.deleteSelectedCustomWorkflow();
                        finishActionMode();
                        return true;
                    case R.id.workflow_selectAll:
                        builtInWorkflowAdapterConfig.selectAll();
                        customWorkflowAdapterConfig.selectAll();
                        updateActionMode();
                        dataSetChange();
                        return true;
                    case R.id.workflow_unSelectAll:
                        builtInWorkflowAdapterConfig.clearSelection();
                        customWorkflowAdapterConfig.clearSelection();
                        updateActionMode();
                        dataSetChange();
                        return true;
                    case R.id.workflow_status_active:
                        if (model.getCurrentSelectedWorkflow() instanceof BuiltInWorkflow)
                            model.updateStateForBuiltInWorkflow(null,Workflow.State.ACTIVATED);
                        else
                            model.updateStateForCustomWorkflow(null,Workflow.State.ACTIVATED);
                        finishActionMode();
                        return true;

                    case R.id.workflow_status_inactive:
                        if (model.getCurrentSelectedWorkflow() instanceof BuiltInWorkflow)
                            model.updateStateForBuiltInWorkflow(null,Workflow.State.DEACTIVATED);
                        else
                            model.updateStateForCustomWorkflow(null,Workflow.State.DEACTIVATED);
                        finishActionMode();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                ((DrawerLayout) getActivity().findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                controls.cookWorkflowFAB.show();
                builtInWorkflowAdapterConfig.clearSelection();
                customWorkflowAdapterConfig.clearSelection();
                dataSetChange();
                actionMode = null;
            }
        };
    }

    private void enableActionMode() {
        if (actionMode == null) {
            actionMode = ((AppCompatActivity) requireActivity()).startSupportActionMode(actionModeCallback);
            controls.cookWorkflowFAB.hide();
            ((DrawerLayout) getActivity().findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        updateActionMode();
    }

    private boolean createContextualMenu(int colorId, int menuId, Menu menu) {
        MaterialMenuInflater.with(getContext())
                .setDefaultColorResource(colorId)
                .inflate(menuId, menu);
        return true;
    }

    private void updateActionMode() {
        actionMode.setTitle(String.valueOf(model.getTotalSelectionWorkflow()));
        actionMode.invalidate();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController controller = NavHostFragment.findNavController(this);
        switch (item.getItemId()) {
            case R.id.try_built_in_workflows:
                controller.navigate(HomeFragmentDirections.actionHomeFragmentToTryBuiltInFragment());
                break;
            case R.id.disable_all_workflow:
                model.disableEnableAllWorkflow(FirebaseConfig.UPDATE.DISABLE,null);
                break;
            case R.id.enable_all_workflow:
                model.disableEnableAllWorkflow(FirebaseConfig.UPDATE.ENABLE,null);
                break;
        }
        return true;
    }

    private void finishActionMode() {
        if (actionMode != null) actionMode.finish();
        // make UI updates here...
    }

    private void showHelperDialog() {
        model.isListEmpty(result -> {
            if (result) {
                if (!model.isDialogShown && !model.didUserCloseDialog) {
                    binding = HelperInfoLayoutBinding
                            .inflate(LayoutInflater.from(getContext()), controls.homeMain, false);
                    controls.homeMain.addView(binding.getRoot());
                    model.isDialogShown = true;
                    binding.noteTryIt.setOnClickListener(view -> {
                        NavHostFragment.findNavController(HomeFragment.this)
                                .navigate(HomeFragmentDirections.actionHomeFragmentToTryBuiltInFragment());
                        model.isDialogShown = false;
                    });
                    binding.noteClose.setOnClickListener(view -> {
                        controls.homeMain.removeView(binding.getRoot());
                        model.didUserCloseDialog = true;
                        model.isDialogShown = false;
                    });
                }
            } else if (binding != null)
                controls.homeMain.removeView(binding.getRoot());
        });
    }

    private void listenForScrollChange() {
        controls.workflowList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && actionMode == null
                        && recyclerView.canScrollVertically(View.SCROLL_AXIS_VERTICAL))
                    controls.cookWorkflowFAB.show();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0)
                    controls.cookWorkflowFAB.hide();
            }
        });
    }

    private void dataSetChange(){
        adapterBuiltIn.notifyDataSetChanged();
        adapterCustom.notifyDataSetChanged();
    }
    private void joinAdapters(){
        MergeAdapter joiner = new MergeAdapter(adapterBuiltIn,adapterCustom);
        adapterCustom.setJoiner(joiner);
        adapterBuiltIn.setJoiner(joiner);
        controls.workflowList.setAdapter(joiner);
    }
}
