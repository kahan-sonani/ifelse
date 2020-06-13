package com.tnj.if_else.viewModels;

import android.app.Application;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.tnj.if_else.activities_and_fragments.fragments.CookWorkflowFragment;
import com.tnj.if_else.architecture.secondLevelEntities.CustomWorkflow;
import com.tnj.if_else.utils.helperClasses.ColorUtility;
import com.tnj.if_else.utils.helperClasses.EntitySet;

import java.util.ArrayList;

public class CookWorkflowModel extends AndroidViewModel {

    public MutableLiveData<Boolean> onSelectAction;
    public MutableLiveData<Boolean> onSelectTrigger;
    public MutableLiveData<Boolean> onSelectCriteria;

    public ArrayList<EntitySet> actions;
    public ArrayList<EntitySet> triggers;
    public ArrayList<EntitySet> criteria;

    public RecyclerView.Adapter actionAdapter;
    public RecyclerView.Adapter triggerAdapter;
    public RecyclerView.Adapter criteriaAdapter;

    public MutableLiveData<Boolean> showCategory;
    public ObservableBoolean showEntityDetails;

    public CustomWorkflow workflow;
    private int randomColor;

    public CookWorkflowModel(@NonNull Application application) {
        super(application);
        onSelectCriteria = new MutableLiveData<>();
        onSelectTrigger = new MutableLiveData<>();
        onSelectAction = new MutableLiveData<>();

        actions = new ArrayList<>();
        triggers = new ArrayList<>();
        criteria = new ArrayList<>();
        workflow = new CustomWorkflow();

        randomColor = ColorUtility.getRandomColor().color;
        showCategory = new MutableLiveData<>();
        showEntityDetails = new ObservableBoolean();
        showEntityDetails.set(false);
        showCategory.setValue(PreferenceManager.getDefaultSharedPreferences(application.getApplicationContext())
                .getBoolean(CookWorkflowFragment.SHOW_CATEGORY, true));
    }

    public int getRandomColorForAdapter() {
        return randomColor;
    }
}
