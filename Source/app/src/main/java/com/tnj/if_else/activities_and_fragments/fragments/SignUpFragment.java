package com.tnj.if_else.activities_and_fragments.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.tnj.if_else.databinding.FragmentSignUpBinding;
import com.tnj.if_else.utils.UI.Snacker;
import com.tnj.if_else.utils.interfaces.ErrorResponse;
import com.tnj.if_else.viewModels.SignUpFragmentViewModel;

public class SignUpFragment extends Fragment implements Button.OnClickListener {

    private SignUpFragmentViewModel model;
    private FragmentSignUpBinding controls;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        model = new ViewModelProvider(this).get(SignUpFragmentViewModel.class);
        controls = FragmentSignUpBinding.inflate(inflater,container,false);
        model.setEmail(getArguments() != null ? getArguments().getString("email") : "");
        controls.setModel(model);
        controls.signUpButton.setOnClickListener(SignUpFragment.this);

        model.onCreateAccount.observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean) NavHostFragment.findNavController(SignUpFragment.this)
                .navigate(SignUpFragmentDirections.actionSignUpFragmentToMainActivity());
        });
        model.errorResponseEvent.observe(getViewLifecycleOwner(), this::showError);
        return controls.getRoot();
    }

    @Override
    public void onClick(View view) {
        model.createNewUser();
    }

    private void startLoadingUI() {
        controls.confirmPasswordLayout.setErrorEnabled(false);
        controls.passLayout.setErrorEnabled(false);
        controls.emailLayout.setErrorEnabled(false);
        controls.scrollMain.fullScroll(0);
        controls.signUpButton.setEnabled(false);
        controls.progressBarLayout.getRoot().setVisibility(View.VISIBLE);
    }

    private void endLoadingUI() {
        controls.signUpButton.setEnabled(true);
        controls.progressBarLayout.getRoot().setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        controls = null;
    }

    private void showError(ErrorResponse errorResponse){
        Snacker.show(controls.getRoot(), errorResponse.getMessage(getContext())
                , Snackbar.LENGTH_INDEFINITE, 4, true, () -> SignUpFragment.this.onClick(null));
    }
}
