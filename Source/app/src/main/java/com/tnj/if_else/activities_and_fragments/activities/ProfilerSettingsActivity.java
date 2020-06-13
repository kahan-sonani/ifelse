package com.tnj.if_else.activities_and_fragments.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tnj.if_else.R;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflowDetailsProxy;
import com.tnj.if_else.databinding.ActivityProfilerSettingsBinding;
import com.tnj.if_else.utils.UI.UICompatibility;
import com.tnj.if_else.utils.helperClasses.ColorUtility;
import com.tnj.if_else.viewModels.ProfilerModel;

public class ProfilerSettingsActivity extends AppCompatActivity {

    private NavController controller;
    private ActivityProfilerSettingsBinding controls;
    private onFabClickListener listener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BuiltInWorkflowDetailsProxy proxy = new ViewModelProvider(this).get(ProfilerModel.class).proxy;
        setTheme(ColorUtility.getTheme(proxy.getColor().name));
        controls = DataBindingUtil.setContentView(this, R.layout.activity_profiler_settings);

        setSupportActionBar(controls.toolbarProfiler);
        getSupportActionBar().setTitle("Profiler Settings");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_up_white);
        controls.toolbarProfiler.setBackgroundColor(ContextCompat.getColor(this,proxy.getColor().color));
        UICompatibility.changeStatusBarColorAndVisibility(this, proxy.getColor().color,View.VISIBLE);

        controller = Navigation.findNavController(this, R.id.nav_host);
        controls.fab.setOnClickListener(v -> listener.onClick(controls.fab));
        controller.addOnDestinationChangedListener((controller, destination, arguments) -> {
                controls.fab.setImageDrawable(ContextCompat.getDrawable(this,
                        (destination.getId() == R.id.profilerListFragment) ? R.drawable.ic_add_profiler : R.drawable.ic_save));
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        controls.toolbarProfiler.setNavigationOnClickListener(v -> {
            if (controller.getCurrentDestination().getId() == R.id.profilerSettings)
                controller.navigate(R.id.profilerListFragment);
            else if (controller.getCurrentDestination().getId() == R.id.profilerListFragment)
                finish();
        });
    }

    public void registerListenerForFAB(onFabClickListener listener) {
        this.listener = listener;
    }

    public void unRegisterListenerForFAB(onFabClickListener listener) {
        if (listener == this.listener) {
            this.listener = null;
        }
    }

    public interface onFabClickListener {
        void onClick(View view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controller = null;
        controls = null;
        listener = null;
    }
}
