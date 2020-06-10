package com.tnj.if_else.activities_and_fragments.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.tnj.if_else.R;
import com.tnj.if_else.activities_and_fragments.activities.UserAuthentication;
import com.tnj.if_else.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding controls;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        controls = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        controls.logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(getActivity(), UserAuthentication.class);
            startActivity(i);
            getActivity().finish();
        });
        return controls.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
