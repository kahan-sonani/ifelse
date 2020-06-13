package com.tnj.if_else.activities_and_fragments.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.tnj.if_else.databinding.FragmentSignUpBinding;
import com.tnj.if_else.viewModels.SignUpFragmentViewModel;

public class SignUpFragment extends Fragment implements Button.OnClickListener {

    private SignUpFragmentViewModel model;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        model = new ViewModelProvider(this).get(SignUpFragmentViewModel.class);
        FragmentSignUpBinding controls = FragmentSignUpBinding.inflate(inflater,container,false);
        model.setEmail(getArguments() != null ? getArguments().getString("email") : "");
        controls.setModel(model);
        controls.signUpButton.setOnClickListener(SignUpFragment.this);
        return controls.getRoot();
    }

    @Override
    public void onClick(View view) {
        model.createNewUser();
        model.onCreateAccount.observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean) NavHostFragment.findNavController(SignUpFragment.this)
                    .navigate(SignUpFragmentDirections.actionSignUpFragmentToMainActivity());
        });
    }
}
