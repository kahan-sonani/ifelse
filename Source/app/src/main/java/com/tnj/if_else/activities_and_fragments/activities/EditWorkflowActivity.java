package com.tnj.if_else.activities_and_fragments.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.tnj.if_else.R;
import com.tnj.if_else.databinding.ActivityEditWorkflowBinding;
import com.tnj.if_else.firebaseRepository.schema.FirebaseConfig;
import com.tnj.if_else.utils.UI.UICompatibility;
import com.tnj.if_else.utils.helperClasses.ColorUtility;

import java.util.Objects;

public class EditWorkflowActivity extends AppCompatActivity {

    Bundle options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        options = getIntent().getExtras();
        setTheme(ColorUtility.getTheme(Objects.requireNonNull(options.getString(IntentExtras.COLOR_NAME))));
        ActivityEditWorkflowBinding controls = DataBindingUtil.setContentView(this, R.layout.activity_edit_workflow);
        NavController controller = Navigation.findNavController(this, R.id.edit_host);
        NavGraph graph = controller.getGraph();
        if (options.getInt(IntentExtras.CATEGORY) == FirebaseConfig.CATEGORY.BUILT_IN.ordinal())
            graph.setStartDestination(R.id.editBuiltInWorkflowFragment);
        else
            graph.setStartDestination(R.id.editCustomWorkflowDetailsFragment);
        controller.setGraph(graph);

        UICompatibility.changeStatusBarColorAndVisibility(this, options.getInt(IntentExtras.COLOR), View.VISIBLE);

        setSupportActionBar(controls.workflowDetailTitle);
        controls.workflowDetailTitle.setBackgroundColor(ContextCompat.getColor(this, options.getInt(IntentExtras.COLOR)));
        NavigationUI.setupActionBarWithNavController(this, controller);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_navigate_up_white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        controls.setLifecycleOwner(this);
    }

    public static class IntentExtras{
        public static final String ID = "workflowId";
        public static final String COLOR = "workflowColor";
        public static final String CATEGORY = "workflowCategory";
        public static final String COLOR_NAME = "workflowColorName";
    }
}
