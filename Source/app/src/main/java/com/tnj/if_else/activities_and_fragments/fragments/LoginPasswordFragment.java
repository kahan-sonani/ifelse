package com.tnj.if_else.activities_and_fragments.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tnj.if_else.R;
import com.tnj.if_else.databinding.FragmentLoginPasswordBinding;
import com.tnj.if_else.viewModels.LoginPasswordFragmentViewModel;

public class LoginPasswordFragment extends Fragment implements Button.OnClickListener{

    private LoginPasswordFragmentViewModel model;

    public LoginPasswordFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        model = new ViewModelProvider(this).get(LoginPasswordFragmentViewModel.class);
        FragmentLoginPasswordBinding controls = FragmentLoginPasswordBinding.inflate(inflater, container, false);
        model.setEmail(getArguments() != null ? getArguments().getString("email") : "");
        controls.setModel(model);
        controls.logIn.setOnClickListener(this);
        controls.forgotPassword.setOnClickListener(v -> showPasswordResetHelperDialog());
        return controls.getRoot();

    }

    @Override
    public void onClick(View view) {
        model.loginUserWithEmailAndPassword();
        model.loginSuccess.observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean) NavHostFragment.findNavController(LoginPasswordFragment.this)
                    .navigate(LoginPasswordFragmentDirections.actionLoginPasswordFragment2ToMainActivity());
        });
    }


    private void showPasswordResetHelperDialog() {
        new MaterialAlertDialogBuilder(getContext())
                .setMessage(getString(R.string.helper_password_link_send_information,model.getEmail().get()))
                .setTitle("Note")
                .setCancelable(false)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("OKAY", (dialog, which) -> {
                    model.sendResetPasswordLink();
                    model.passwordResetLinkSentSuccessfully.observe(getViewLifecycleOwner(), aBoolean -> {
                        if(aBoolean) successPasswordLinkSendDialog(getString(R.string.success_password_reset_link_sent,model.getEmail().get()));
                    });
                })
                .create().show();
    }

    private void successPasswordLinkSendDialog(String errorMessage) {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Success!")
                .setCancelable(true)
                .setMessage(errorMessage)
                .setPositiveButton("OKAY", (dialog, which) -> dialog.dismiss()).create().show();
    }
}
