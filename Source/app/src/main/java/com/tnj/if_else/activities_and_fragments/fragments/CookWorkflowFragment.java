
package com.tnj.if_else.activities_and_fragments.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.tnj.if_else.R;
import com.tnj.if_else.adapters.EntitySelection;
import com.tnj.if_else.adapters.EntitySetAdapter;
import com.tnj.if_else.databinding.FragmentCookWorkflowBinding;
import com.tnj.if_else.viewModels.CookWorkflowModel;
import com.tnj.if_else.viewPager.cookWorkflowViewPager;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class CookWorkflowFragment extends Fragment implements SearchView.OnQueryTextListener, View.OnClickListener {

    private FragmentCookWorkflowBinding controls;
    public static final String SHOW_DETAILS = "show_details";
    public static final String SHOW_CATEGORY = "show_category";
    private CookWorkflowModel model;
    private BottomSheetBehavior behavior;

    public CookWorkflowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(getActivity()).get(CookWorkflowModel.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        model = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        behavior = null;
        controls = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        controls = FragmentCookWorkflowBinding.inflate(inflater);
        setBottomSheetBehavior();
        setHasOptionsMenu(true);
        setTabListener();
        setViewPagerListener();
        setBottomSheetBehavior();
        setSelectionList();
        controls.tabPages.setAdapter(new cookWorkflowViewPager(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        controls.tabPages.setOffscreenPageLimit(2);
        controls.saveWorkflowConfigurationButton.setOnClickListener(view ->
                NavHostFragment.findNavController(CookWorkflowFragment.this)
                        .navigate(R.id.enterWorkflowDetailsFragment));
        controls.setLifecycleOwner(this);
        return controls.getRoot();
    }

    private void setSelectionList() {

        controls.bottomSheet.setModel(model);
        int color = model.getRandomColorForAdapter();
        controls.bottomSheet.setSelectedAction(new EntitySelection(model.actions).setColor(getContext(), color)
                .setContextualMenuListener(() -> controls.bottomSheet.getActionCountNo().set(controls.bottomSheet.getActionCountNo().get() - 1)));
        controls.bottomSheet.setSelectedCriterias(new EntitySelection(model.criteria).setColor(getContext(), color)
                .setContextualMenuListener(() -> controls.bottomSheet.getCriteriaCountNo().set(controls.bottomSheet.getCriteriaCountNo().get() - 1)));
        controls.bottomSheet.setSelectedTrigger(new EntitySelection(model.triggers).setColor(getContext(), color)
                .setContextualMenuListener(() -> controls.bottomSheet.getTriggerCountNo().set(controls.bottomSheet.getTriggerCountNo().get() - 1)));

        controls.bottomSheet.setActionCountNo(new ObservableInt(0));
        controls.bottomSheet.setTriggerCountNo(new ObservableInt(0));
        controls.bottomSheet.setCriteriaCountNo(new ObservableInt(0));

        controls.bottomSheet.criteriaRemoveAll.setOnClickListener(this);
        controls.bottomSheet.triggerRemoveAll.setOnClickListener(this);
        controls.bottomSheet.actionRemoveAll.setOnClickListener(this);

        model.onSelectCriteria.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                controls.bottomSheet.getCriteriaCountNo().set(model.criteria.size());
                controls.bottomSheet.getSelectedCriterias().notifyDataSetChanged();
            }
        });
        model.onSelectAction.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                controls.bottomSheet.getActionCountNo().set(model.actions.size());
                controls.bottomSheet.getSelectedAction().notifyDataSetChanged();
            }
        });
        model.onSelectTrigger.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                controls.bottomSheet.getTriggerCountNo().set(model.triggers.size());
                controls.bottomSheet.getSelectedTrigger().notifyDataSetChanged();
            }
        });
    }

    private void setViewPagerListener() {
        controls.tabPages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                controls.tabLayout.setScrollPosition(position, positionOffset, false);
            }

            @Override
            public void onPageSelected(int position) {
                controls.tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTabListener() {
        controls.tabLayout.getTabAt(0).getIcon().setTint(ContextCompat.getColor(getContext(), R.color.colorBlack));
        controls.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectTab(tab);
                controls.tabPages.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setTint(ContextCompat.getColor(getContext(), R.color.colorGray));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void selectTab(TabLayout.Tab tab) {
        tab.getIcon().setTint(ContextCompat.getColor(getContext(), R.color.colorBlack));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MaterialMenuInflater.with(getContext())
                .inflate(R.menu.cook_workflow_activity_menu, menu);
        menu.findItem(R.id.showDescription).setChecked(model.showEntityDetails.get());
        menu.findItem(R.id.showCategories).setChecked(model.showCategory.getValue());
        MenuItem item = menu.findItem(R.id.searchEntity);
        SearchView view = (SearchView) item.getActionView();
        view.setImeOptions(EditorInfo.IME_ACTION_DONE);
        view.setOnQueryTextListener(CookWorkflowFragment.this);

        model.showCategory.observe(getViewLifecycleOwner(), aBoolean -> {
            item.setEnabled(!aBoolean);
            item.setVisible(!aBoolean);
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.showCategories) {
            boolean bool = !item.isChecked();
            model.showCategory.setValue(bool);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            preferences.edit().putBoolean(SHOW_CATEGORY, bool).apply();
            item.setChecked(bool);
        } else if (item.getItemId() == R.id.showDescription) {
            boolean bool = !item.isChecked();
            model.showEntityDetails.set(bool);
            item.setChecked(bool);
        }
        return true;
    }

    private void setBottomSheetBehavior() {
        behavior = BottomSheetBehavior.from(controls.bottomSheet.getRoot());
        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                    controls.bottomSheet.arrow.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_collapse));
                else if (newState == BottomSheetBehavior.STATE_EXPANDED)
                    controls.bottomSheet.arrow.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_expand));
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.cook_contextual_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ((EntitySetAdapter) model.criteriaAdapter).getFilter().filter(newText);
        ((EntitySetAdapter) model.triggerAdapter).getFilter().filter(newText);
        ((EntitySetAdapter) model.actionAdapter).getFilter().filter(newText);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.criteria_remove_all) {
            model.criteria.clear();
            controls.bottomSheet.getCriteriaCountNo().set(0);
            controls.bottomSheet.getSelectedCriterias().notifyDataSetChanged();
        }
        else if(view.getId() == R.id.action_remove_all) {
            model.actions.clear();
            controls.bottomSheet.getActionCountNo().set(0);
            controls.bottomSheet.getSelectedAction().notifyDataSetChanged();
        }
        else if(view.getId() == R.id.trigger_remove_all) {
            model.triggers.clear();
            controls.bottomSheet.getTriggerCountNo().set(0);
            controls.bottomSheet.getSelectedTrigger().notifyDataSetChanged();
        }
    }
}
