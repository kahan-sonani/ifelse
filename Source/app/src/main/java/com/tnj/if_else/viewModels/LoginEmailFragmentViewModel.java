package com.tnj.if_else.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.tnj.if_else.firebaseRepository.AuthRepository;
import com.tnj.if_else.utils.enums.ValidationErrors;
import com.tnj.if_else.utils.helperClasses.validator.EmailValidator;
import com.tnj.if_else.utils.interfaces.State;

public class LoginEmailFragmentViewModel extends AndroidViewModel {

    private AuthRepository userRepo;

    private EmailValidator emailValidator;

    public ObservableBoolean loading;

    private ObservableField<String> validationError;

    private ObservableField<String> email;

    public MutableLiveData<Boolean> isEmailValidated;

    public LiveData<State> validateEmailLiveData;

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
        emailValidator = new EmailValidator();
        email = new ObservableField<>("");

        isEmailValidated = new MutableLiveData<>(false);

        loading = new ObservableBoolean(false);
        validationError = new ObservableField<>("");

        email.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                validationError.set("".equals(email.get()) ? ValidationErrors.REQUIRED.getMessage(application.getApplicationContext())
                        : ValidationErrors.NO_ERROR.getMessage(application.getApplicationContext()));
            }
        });
    }

    public void validateEmail() {
        loading.set(true);
        validationError.set(emailValidator.validate(email.get()).getError()
                .getMessage(getApplication().getApplicationContext()));
        if(emailValidator.getError() == ValidationErrors.NO_ERROR) {
            validateEmailLiveData = Transformations.map(userRepo.verifyEmail(email.get()),
                    input -> {
                        isEmailValidated.setValue(true);
                        loading.set(input.isFinished());
                        if(input.getResponse().isSuccessful())
                            validationError.set(input.getResponse()
                                    .getMessage(getApplication().getApplicationContext()));
                        return input.getResponse();
                    });
            isEmailValidated.setValue(true);
        }else {
            loading.set(false);
            isEmailValidated.setValue(false);
        }
    }

    public void signInWithGoogleCredentials(final GoogleSignInAccount account) {

        loading.set(true);
        /*signWithGoogleSuccessLiveData = Transformations.map(UseCase.createSingleLiveDataEventFromData(userRepo.signInWithCredentials(account)),
                input -> {
                    loading.set(!input.isFinished());
                    if(!input.getResponse().isSuccessful()){
                        validationError.set((input.getResponse())
                            .getMessage(getApplication().getApplicationContext()));
                        return input.getResponse();
                    }
                    return input.getResponse();
                });*/
    }
}
