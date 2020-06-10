package com.tnj.if_else.activities_and_fragments.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.tnj.if_else.R;
import com.tnj.if_else.databinding.ActivitySingleWorkflowManagerBinding;

public class SingleWorkflowActivity extends AppCompatActivity {

    ActivitySingleWorkflowManagerBinding controls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controls = DataBindingUtil.setContentView(this, R.layout.activity_single_workflow_manager);
        setSupportActionBar(controls.cookToolbar);
        controls.appBar.setOutlineProvider(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        controls.cookToolbar.setNavigationOnClickListener(view -> onBackPressed());
        controls.setLifecycleOwner(this);
    }
}
