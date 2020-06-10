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
import com.tnj.if_else.databinding.FragmentTriggerBinding;
import com.tnj.if_else.utils.helperClasses.EntityCategory;
import com.tnj.if_else.utils.helperClasses.EntitySet;
import com.tnj.if_else.utils.interfaces.EntityClickListener;
import com.tnj.if_else.utils.helperClasses.ClassInventory;
import com.tnj.if_else.viewModels.CookWorkflowModel;

import java.util.ArrayList;

public class TriggerFragment extends Fragment implements EntityClickListener {

    private FragmentTriggerBinding binding;
    private CookWorkflowModel model;
    private LoadTrigger task;

    public TriggerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_trigger, container, false);
        model = new ViewModelProvider(getActivity()).get(CookWorkflowModel.class);
        model.showCategory.observe(getViewLifecycleOwner(), aBoolean -> {
            task = new LoadTrigger();
            task.execute(aBoolean);
        });
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(EntitySet set, int position) {
        model.triggers.add(set);
        model.onSelectTrigger.setValue(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (task != null)
            task.cancel(true);
    }
    class LoadTrigger extends AsyncTask<Boolean, Void, ArrayList<?>> {

        @Override
        protected ArrayList<?> doInBackground(Boolean... booleans) {
            return booleans[0] && !isCancelled() ? ClassInventory.getTriggersByCategory() : ClassInventory.getTriggers();
        }

        @Override
        protected void onPostExecute(ArrayList<?> objects) {
            super.onPostExecute(objects);
            if(model.showCategory.getValue())
                model.triggerAdapter = new CategoryAdapter((ArrayList<EntityCategory>)objects)
                        .setColor(getContext(),model.getRandomColorForAdapter())
                        .showDetails(model.showEntityDetails)
                        .setClickListener(TriggerFragment.this);
            else
                model.triggerAdapter = new EntitySetAdapter((ArrayList<EntitySet>)objects)
                        .setColor(getContext(),model.getRandomColorForAdapter())
                        .showDetails(model.showEntityDetails)
                        .setClickListener(TriggerFragment.this);

            binding.setAdapter(model.triggerAdapter);
        }
    }
}

