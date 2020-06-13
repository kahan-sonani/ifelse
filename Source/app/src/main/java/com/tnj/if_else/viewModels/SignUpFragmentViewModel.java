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

public class SignUpFragmentViewModel extends AndroidViewModel {

    private ObservableBoolean loading;

    public LiveData<Boolean> onCreateAccount;

    private ObservableField<String> errorResponseEvent;

    private AuthRepository userRepo;

    private ObservableField<String> email;

    private ObservableField<String> password;

    private ObservableField<String> confirmPassword;


    public ObservableField<String> getErrorResponseEvent() {
        return errorResponseEvent;
    }

    public ObservableBoolean getLoading() {
        return loading;
    }

    public SignUpFragmentViewModel(Application application) {
        super(application);

        loading = new ObservableBoolean();

        errorResponseEvent = new ObservableField<>(ValidationErrors.NO_ERROR.getMessage(application.getApplicationContext()));
        onCreateAccount = new Event<>();

        email = new ObservableField<>("");
        password = new ObservableField<>("");
        confirmPassword = new ObservableField<>("");
        userRepo = AuthRepository.getInstance();

        email.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                validateFields(email.get());
            }
        });

        password.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                validateFields(password.get());
            }
        });
        confirmPassword.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                validateFields(confirmPassword.get());
            }
        });
    }


    public void setEmail(String email) {
        this.email.set(email);
    }

    public ObservableField<String> getEmail() {
        return email;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public ObservableField<String> getConfirmPassword() {
        return confirmPassword;
    }

    public void createNewUser() {

        loading.set(true);
        onCreateAccount = Transformations.map(userRepo.createUserWithEmailAndPassword(email.get(), password.get(),confirmPassword.get()),
                input -> {
                    loading.set(!input.isFinished());
                    if (!input.isSuccessful()) {
                        errorResponseEvent.set(((State) input.getResponse()).getMessage(getApplication().getApplicationContext()));
                        return false;
                    } else {
                        errorResponseEvent.set(((State) input.getResponse()).getMessage(getApplication().getApplicationContext()));
                        return input.getResponse() == AuthSuccessResponse.CREATE_ACCOUNT_SUCCESS;
                    }
                });
    }

    private void validateFields(String value) {
        errorResponseEvent.set("".equals(value) ? ValidationErrors.REQUIRED.getMessage(getApplication().getApplicationContext())
                : ValidationErrors.NO_ERROR.getMessage(getApplication().getApplicationContext()));
    }
}
