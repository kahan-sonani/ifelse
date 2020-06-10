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
import com.tnj.if_else.databinding.FragmentActionBinding;
import com.tnj.if_else.utils.helperClasses.EntityCategory;
import com.tnj.if_else.utils.helperClasses.EntitySet;
import com.tnj.if_else.utils.interfaces.EntityClickListener;
import com.tnj.if_else.utils.helperClasses.ClassInventory;
import com.tnj.if_else.viewModels.CookWorkflowModel;

import java.util.ArrayList;

public class ActionFragment extends Fragment implements EntityClickListener {

    private FragmentActionBinding binding;
    private CookWorkflowModel model;
    private LoadAction task;

    public ActionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_action, container, false);
        model = new ViewModelProvider(getActivity()).get(CookWorkflowModel.class);
        model.showCategory.observe(getViewLifecycleOwner(), aBoolean -> {
            task = new LoadAction();
            task.execute(aBoolean);
        });

        binding.setLifecycleOwner(this);
        binding.setClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(EntitySet set, int position) {
        model.actions.add(set);
        model.onSelectAction.setValue(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (task != null)
            task.cancel(true);
        task = null;
    }

    class LoadAction extends AsyncTask<Boolean, Void, ArrayList<?>> {

        @Override
        protected ArrayList<?> doInBackground(Boolean... booleans) {
            return booleans[0] && !isCancelled() ? ClassInventory.getActionsByCategory() : ClassInventory.getActions();
        }

        @Override
        protected void onPostExecute(ArrayList<?> objects) {
            super.onPostExecute(objects);
            if(model.showCategory.getValue())
                model.actionAdapter = new CategoryAdapter((ArrayList<EntityCategory>)objects)
                        .setColor(getContext(),model.getRandomColorForAdapter())
                        .showDetails(model.showEntityDetails)
                        .setClickListener(ActionFragment.this);
            else
                model.actionAdapter = new EntitySetAdapter((ArrayList<EntitySet>)objects)
                        .setColor(getContext(),model.getRandomColorForAdapter())
                        .showDetails(model.showEntityDetails)
                        .setClickListener(ActionFragment.this);

            binding.setAdapter(model.actionAdapter);
        }
    }
}
