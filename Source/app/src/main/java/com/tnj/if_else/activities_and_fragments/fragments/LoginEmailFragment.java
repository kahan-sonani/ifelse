package com.tnj.if_else.activities_and_fragments.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.tnj.if_else.R;
import com.tnj.if_else.databinding.FragmentLoginEmailBinding;
import com.tnj.if_else.utils.enums.SuccessResponse;
import com.tnj.if_else.utils.helperClasses.GoogleSignInClientIFELSE;
import com.tnj.if_else.viewModels.LoginEmailFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginEmailFragment extends Fragment implements Button.OnClickListener {

    private static final int RC_SIGN_IN = 1000;
    private LoginEmailFragmentViewModel model;
    private FragmentLoginEmailBinding controls;

    public LoginEmailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        model = new ViewModelProvider(this).get(LoginEmailFragmentViewModel.class);
        controls = FragmentLoginEmailBinding.inflate(inflater, container, false);
        controls.setModel(model);
        controls.next.setOnClickListener(this);
        controls.googleProvidersButton.setOnClickListener(this);

        model.isEmailValidated.observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                model.validateEmailLiveData.observe(getViewLifecycleOwner(), state -> {
                    Log.i("ifelse",model.toString());
                    if(state.isSuccessful()) {
                        NavController controller = NavHostFragment.findNavController(LoginEmailFragment.this);
                        Bundle bundle = new Bundle();
                        bundle.putString("email", model.getEmail().get());
                        switch (((SuccessResponse) state)) {
                            case NEW_EMAIL:
                                controller.navigate(R.id.signUpFragment, bundle);
                                break;
                            case EXISTING_EMAIL:
                            case EXISTING_EMAIL_GOOGLE:
                                controller.navigate(R.id.loginPasswordFragment2, bundle);
                                break;
                        }
                    }
                });
            }
        });

       /* model.signWithGoogleSuccessLiveData.observe(getViewLifecycleOwner(), (Observer<State>) state -> {
            if (state.isSuccessful() && ((SuccessResponse) state) == SuccessResponse.SIGN_IN_WITH_GOOGLE_CREDENTIALS_SUCCESS)
                NavHostFragment.findNavController(LoginEmailFragment.this)
                        .navigate(LoginEmailFragmentDirections.actionLoginEmailFragment2ToMainActivity());
        });*/
        return controls.getRoot();
    }

    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.next) {
           model.validateEmail();
        } else if (view.getId() == R.id.google_providers_button) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .requestProfile()
                    .build();
            GoogleSignInClient client = GoogleSignIn.getClient(getContext(), gso);
            Intent signInIntent = client.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                model.setEmail(account.getEmail());
                model.signInWithGoogleCredentials(account);
                GoogleSignInClientIFELSE.getClient(getContext()).signOut();
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
}

