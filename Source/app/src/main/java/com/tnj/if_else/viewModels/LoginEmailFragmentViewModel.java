package com.tnj.if_else.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.tnj.if_else.firebaseRepository.AuthRepository;
import com.tnj.if_else.utils.enums.ValidationErrors;
import com.tnj.if_else.utils.helperClasses.Response;
import com.tnj.if_else.utils.interfaces.State;

public class LoginEmailFragmentViewModel extends AndroidViewModel {

    private AuthRepository userRepo;

    public ObservableBoolean loading;

    private ObservableField<String> validationError;

    private ObservableField<String> email;

    public void setEmail(String email) {
        this.email.set(email);
    }

    public ObservableField<String> getEmail() {
        return email;
    }

    public ObservableField<String> getValidationError() {
        return validationError;
    }

    public LoginEmailFragmentViewModel(Application application) {
        super(application);
        userRepo = AuthRepository.getInstance();
        email = new ObservableField<>("");

        loading = new ObservableBoolean(false);
        validationError = new ObservableField<>(ValidationErrors.NO_ERROR.getMessage(application));

        email.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                validationError.set("".equals(email.get()) ? ValidationErrors.REQUIRED.getMessage(application.getApplicationContext())
                        : ValidationErrors.NO_ERROR.getMessage(application.getApplicationContext()));
            }
        });
    }

    public LiveData<State> validateEmail() {

        loading.set(true);
        return Transformations.map(userRepo.verifyEmail(email.get()),
                input -> {
                    loading.set(!input.isFinished());
                    handleResponse(input);
                    return (State)input.getResponse();
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("ifelse", "ViewModel[LoginEmailFragmentViewModel] : OnCleared() called");
    }

    public LiveData<State> signInWithGoogleCredentials(final GoogleSignInAccount account) {

        loading.set(true);
        return Transformations.map(userRepo.signInWithCredentials(account),
                input -> {
                    loading.set(!input.isFinished());
                    handleResponse(input);
                    return (State) input.getResponse();
                });
    }

    private void handleResponse(Response<?> response) {
        validationError.set((response.isSuccessful() ? ValidationErrors.NO_ERROR
                : ((State) response.getResponse())).getMessage(getApplication().getApplicationContext()));
    }
}
