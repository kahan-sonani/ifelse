package com.tnj.if_else.viewModels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tnj.if_else.firebaseRepository.AuthRepository;
import com.tnj.if_else.utils.enums.ValidationErrors;
import com.tnj.if_else.utils.helperClasses.Event;
import com.tnj.if_else.utils.helperClasses.validator.CompareValidator;
import com.tnj.if_else.utils.helperClasses.validator.EmailValidator;
import com.tnj.if_else.utils.helperClasses.validator.PasswordValidator;
import com.tnj.if_else.utils.interfaces.ErrorResponse;

public class SignUpFragmentViewModel extends ViewModel {

    private EmailValidator emailValidator;

    private PasswordValidator passwordValidator;

    private CompareValidator confirmPasswordValidator;

    public LiveData<Boolean> onCreateAccount;

    public Event<ErrorResponse> errorResponseEvent;

    public ObservableField<ValidationErrors> emailError;

    public ObservableField<ValidationErrors> passwordError;

    public ObservableField<ValidationErrors> confirmError;

    private AuthRepository userRepo;

    private ObservableField<String> email;

    private ObservableField<String> password;

    private ObservableField<String> confirmPassword;


    public SignUpFragmentViewModel() {
        emailValidator = new EmailValidator();
        passwordValidator = new PasswordValidator();
        confirmPasswordValidator = new CompareValidator();

        errorResponseEvent = new Event<>();
        onCreateAccount = new Event<>();

        emailError = new ObservableField<>(ValidationErrors.NO_ERROR);
        passwordError = new ObservableField<>(ValidationErrors.NO_ERROR);
        confirmError = new ObservableField<>(ValidationErrors.NO_ERROR);

        email = new ObservableField<>("");
        password = new ObservableField<>("");
        confirmPassword = new ObservableField<>("");

        userRepo = AuthRepository.getInstance();
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
        emailError.set(emailValidator.validate(email.get()).getError());
        passwordError.set(passwordValidator.validate(password.get()).getError());
        confirmError.set(confirmPasswordValidator.validate(password.get(),confirmPassword.get()).getError());

        /*if(emailError.get() == ValidationErrors.NO_ERROR
                && passwordError.get() == ValidationErrors.NO_ERROR
                && confirmError.get() == ValidationErrors.NO_ERROR) {
            onCreateAccount = Transformations.map(UseCase.createSingleLiveDataEventFromData(userRepo.createUserWithEmailAndPassword(email.get(), password.get())),
                    input -> {
                        if (input.getResponse() instanceof ErrorResponse) {
                            errorResponseEvent.setValue((ErrorResponse) input.getResponse());
                            return false;
                        }
                        return input.getResponse() == SuccessResponse.CREATE_ACCOUNT_SUCCESS;
                    });
        }*/
    }
}
