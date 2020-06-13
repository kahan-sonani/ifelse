package com.tnj.if_else.viewModels;

import android.app.Application;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.tnj.if_else.firebaseRepository.AuthRepository;
import com.tnj.if_else.utils.enums.AuthSuccessResponse;
import com.tnj.if_else.utils.enums.ValidationErrors;
import com.tnj.if_else.utils.helperClasses.Event;
import com.tnj.if_else.utils.interfaces.State;

public class LoginPasswordFragmentViewModel extends AndroidViewModel {

    private AuthRepository userRepo;

    public LiveData<Boolean> loginSuccess;

    private ObservableBoolean loading;

    private ObservableField<String> somethingWentWrong;

    public LiveData<Boolean> passwordResetLinkSentSuccessfully;

    private ObservableField<String> password;

    private ObservableField<String> email;

    public ObservableField<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public ObservableBoolean getLoading() {
        return loading;
    }

    public ObservableField<String> getSomethingWentWrong() {
        return somethingWentWrong;
    }

    public LoginPasswordFragmentViewModel(Application application) {

        super(application);
        loading = new ObservableBoolean(false);
        somethingWentWrong = new ObservableField<>(ValidationErrors.NO_ERROR.getMessage(application.getApplicationContext()));

        loginSuccess = new Event<>();
        passwordResetLinkSentSuccessfully = new Event<>();

        userRepo = AuthRepository.getInstance();

        password = new ObservableField<>("");
        email = new ObservableField<>("");

        password.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                somethingWentWrong.set("".equals(password.get()) ? ValidationErrors.REQUIRED.getMessage(application.getApplicationContext())
                        : ValidationErrors.NO_ERROR.getMessage(application.getApplicationContext()));
            }
        });

    }

    public void loginUserWithEmailAndPassword() {

        loading.set(true);
        loginSuccess = Transformations.map(userRepo.loginUserWithEmailAndPassword(email.get(), password.get()),
                input -> {
                    loading.set(!input.isFinished());
                    if (!input.isSuccessful()) {
                        somethingWentWrong.set((((State) input.getResponse()))
                                .getMessage(getApplication().getApplicationContext()));
                        return false;
                    } else {
                        somethingWentWrong.set(ValidationErrors.NO_ERROR.getMessage(getApplication().getApplicationContext()));
                        return input.getResponse() == AuthSuccessResponse.SIGN_IN_SUCCESSFUL;
                    }
                });
    }

    public void sendResetPasswordLink() {
        loading.set(true);
        passwordResetLinkSentSuccessfully = Transformations.map(userRepo.sendResetPasswordLink(email.get()),
                input -> {
                    loading.set(!input.isFinished());
                    if (!input.isSuccessful()) {
                        somethingWentWrong.set((((State) input.getResponse())).
                                getMessage(getApplication().getApplicationContext()));
                        return false;
                    } else {
                        somethingWentWrong.set(ValidationErrors.NO_ERROR.getMessage(getApplication().getApplicationContext()));
                        return input.getResponse() == AuthSuccessResponse.PASSWORD_RESET_LINK_SENT;
                    }
                });
    }
}
