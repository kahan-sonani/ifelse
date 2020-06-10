package com.tnj.if_else.activities_and_fragments.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.google.android.material.snackbar.Snackbar;
import com.tnj.if_else.R;
import com.tnj.if_else.services.WorkflowManager;
import com.tnj.if_else.utils.UI.Snacker;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences,rootKey);
        SwitchPreferenceCompat switchPreferenceCompat = getPreferenceScreen()
                .findPreference(getString(R.string.preference_ifelse_foreground_service));
        switchPreferenceCompat.setOnPreferenceChangeListener((preference, newValue) -> {
            Intent intent = new Intent(getActivity(), WorkflowManager.class);
            if(((Boolean) newValue))
                ContextCompat.startForegroundService(getContext(),intent);
            else if(!getActivity().stopService(intent))
                Snacker.show(getView().getRootView(),"Something went wrong!",
                        Snackbar.LENGTH_LONG,2,false,null);
            return true;
        });
    }
}
