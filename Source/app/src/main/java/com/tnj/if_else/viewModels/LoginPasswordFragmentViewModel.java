package com.tnj.if_else.viewModels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tnj.if_else.firebaseRepository.AuthRepository;
import com.tnj.if_else.utils.helperClasses.Event;
import com.tnj.if_else.utils.helperClasses.validator.RequiredValidator;
import com.tnj.if_else.utils.interfaces.ErrorResponse;

public class LoginPasswordFragmentViewModel extends ViewModel {

    private RequiredValidator requiredValidator;

    private AuthRepository userRepo;

    public LiveData<Boolean> loginSuccess;
    
    public ObservableField<ErrorResponse> somethingWentWrong;

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

    public LoginPasswordFragmentViewModel(){

        somethingWentWrong = new ObservableField<>();
        loginSuccess = new Event<>();
        passwordResetLinkSentSuccessfully  = new Event<>(); 
        requiredValidator = new RequiredValidator();
        userRepo = AuthRepository.getInstance();
        password = new ObservableField<>("");
        email = new ObservableField<>("");

    }

    public void loginUserWithEmailAndPassword() {

        somethingWentWrong.set(requiredValidator.validate(email.get()).getError());

        /*if(requiredValidator.getError() == ValidationErrors.NO_ERROR) {
            loginSuccess = Transformations.map(,),
                    input -> {
                        if (input.getResponse() instanceof ErrorResponse) {
                            somethingWentWrong.set((ErrorResponse) input.getResponse());
                            return false;
                        }
                        return input.getResponse() == SuccessResponse.SIGN_IN_SUCCESSFUL;
                    });
        }*/
    }

    public void sendResetPasswordLink() {
        /*passwordResetLinkSentSuccessfully = Transformations.map(UseCase.createSingleLiveDataEventFromData(userRepo.sendResetPasswordLink(email.get())),
                input -> {
                    if(input.getResponse() instanceof ErrorResponse){
                        somethingWentWrong.set((ErrorResponse) input.getResponse());
                        return false;
                    }
                    return input.getResponse() == SuccessResponse.PASSWORD_RESET_LINK_SENT;
                });*/
    }
}
