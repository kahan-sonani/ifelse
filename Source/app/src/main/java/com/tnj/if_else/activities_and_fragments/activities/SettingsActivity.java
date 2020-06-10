package com.tnj.if_else.activities_and_fragments.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.tnj.if_else.R;
import com.tnj.if_else.databinding.SettingsActivityBinding;

public class SettingsActivity extends AppCompatActivity {

    SettingsActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.settings_activity);
        setSupportActionBar(binding.settingsToolbar);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this,R.drawable.ic_close_black));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}