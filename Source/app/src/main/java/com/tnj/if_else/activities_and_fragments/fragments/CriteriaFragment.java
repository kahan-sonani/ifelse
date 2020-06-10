package com.tnj.if_else.activities_and_fragments.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tnj.if_else.R;
import com.tnj.if_else.adapters.CategoryAdapter;
import com.tnj.if_else.adapters.EntitySetAdapter;
import com.tnj.if_else.databinding.FragmentCriteriaBinding;
import com.tnj.if_else.utils.helperClasses.EntityCategory;
import com.tnj.if_else.utils.helperClasses.EntitySet;
import com.tnj.if_else.utils.interfaces.EntityClickListener;
import com.tnj.if_else.utils.helperClasses.ClassInventory;
import com.tnj.if_else.viewModels.CookWorkflowModel;

import java.util.ArrayList;

public class CriteriaFragment extends Fragment implements EntityClickListener {

    private FragmentCriteriaBinding binding;
    private CookWorkflowModel model;
    private LoadCriteria task;

    public CriteriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_criteria, container, false);
        model = new ViewModelProvider(getActivity()).get(CookWorkflowModel.class);
        model.showCategory.observe(getViewLifecycleOwner(), aBoolean -> {
            task = new LoadCriteria();
            task.execute(aBoolean);
        });
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(EntitySet set, int position) {
        model.criteria.add(set);
        model.onSelectCriteria.setValue(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (task != null)
            task.cancel(true);
    }

    class LoadCriteria extends AsyncTask<Boolean, Void, ArrayList<?>> {

        @Override
        protected ArrayList<?> doInBackground(Boolean... booleans) {
            return booleans[0] && !isCancelled() ? ClassInventory.getCriteriasByCategory() : ClassInventory.getCriterias();
        }

        @Override
        protected void onPostExecute(ArrayList<?> objects) {
            super.onPostExecute(objects);
            if(model.showCategory.getValue())
                model.criteriaAdapter = new CategoryAdapter((ArrayList<EntityCategory>)objects)
                        .setColor(getContext(),model.getRandomColorForAdapter())
                        .showDetails(model.showEntityDetails)
                        .setClickListener(CriteriaFragment.this);
            else
                model.criteriaAdapter = new EntitySetAdapter((ArrayList<EntitySet>)objects)
                        .setColor(getContext(),model.getRandomColorForAdapter())
                        .showDetails(model.showEntityDetails)
                        .setClickListener(CriteriaFragment.this);

            binding.setAdapter(model.criteriaAdapter);
        }
    }
}
