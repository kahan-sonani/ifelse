package com.tnj.if_else.firebaseRepository;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.tnj.if_else.utils.enums.AuthSuccessResponse;
import com.tnj.if_else.utils.enums.ValidationErrors;
import com.tnj.if_else.utils.helperClasses.Event;
import com.tnj.if_else.utils.helperClasses.FirebaseError;
import com.tnj.if_else.utils.helperClasses.Response;
import com.tnj.if_else.utils.helperClasses.WorkflowResponseError;
import com.tnj.if_else.utils.helperClasses.WorkflowResponseSuccess;
import com.tnj.if_else.utils.helperClasses.validator.Validations;
import com.tnj.if_else.utils.interfaces.ErrorResponse;

import java.util.List;
import java.util.Objects;

public class AuthRepository extends BaseOperations {

    private static AuthRepository repository;

    private AuthRepository() {
    }

    public static AuthRepository getInstance() {
        if (repository == null)
            return repository = new AuthRepository();
        else
            return repository;
    }

    public Event<Response<?>> verifyEmail(final String email) {

        Event<Response<?>> responseLiveData = new Event<>();
        ValidationErrors error;
        if ((error = Validations.emailPattern(email)) == ValidationErrors.NO_ERROR) {
            mAuth.fetchSignInMethodsForEmail(email)
                    .addOnSuccessListener(signInMethodQueryResult -> {
                        List<String> result;
                        if (!Objects.requireNonNull(result = signInMethodQueryResult.getSignInMethods()).isEmpty()) {
                            if (result.size() == 1)
                                responseLiveData.setValue(new WorkflowResponseSuccess<>(result.get(0).equals("google.com") ? AuthSuccessResponse.EXISTING_EMAIL_GOOGLE
                                        : AuthSuccessResponse.EXISTING_EMAIL).finished(true));
                        } else
                            responseLiveData.setValue(new WorkflowResponseSuccess<>(AuthSuccessResponse.NEW_EMAIL).finished(true));
                    })
                    .addOnFailureListener(e -> responseLiveData.setValue(new WorkflowResponseError<ErrorResponse>(FirebaseError.fromException(e)).finished(true)));
        } else responseLiveData.setValue(new WorkflowResponseError<ValidationErrors>(error).finished(true));
        return responseLiveData;
    }

    public Event<Response<?>> createUserWithEmailAndPassword(final String email, final String password, final String confirmPassword) {

        Event<Response<?>> authResultMutableLiveData = new Event<>();

        ValidationErrors emailV = Validations.emailPattern(email), passwordV = Validations.passwordPattern(password), confirmPasswordV = Validations.fieldMatch(password, confirmPassword);

        ValidationErrors error = emailV == ValidationErrors.NO_ERROR ? passwordV == ValidationErrors.NO_ERROR
                ? confirmPasswordV : passwordV : emailV;

        if (error == ValidationErrors.NO_ERROR) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> authResultMutableLiveData.setValue(new WorkflowResponseSuccess<>(AuthSuccessResponse.CREATE_ACCOUNT_SUCCESS).finished(true)))
                    .addOnFailureListener(e -> authResultMutableLiveData.setValue(new WorkflowResponseError(FirebaseError.fromException(e)).finished(true)));
        } else authResultMutableLiveData.setValue(new WorkflowResponseError(error).finished(true));

        return authResultMutableLiveData;
    }

    public Event<Response<?>> loginUserWithEmailAndPassword(final String email, final String password) {
        Event<Response<?>> authResultMutableLiveData = new Event<>();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> authResultMutableLiveData.setValue(new WorkflowResponseSuccess<>().setState(AuthSuccessResponse.SIGN_IN_SUCCESSFUL).finished(true)))
                .addOnFailureListener(e -> authResultMutableLiveData.setValue(new WorkflowResponseError(FirebaseError.fromException(e)).finished(true)));

        return authResultMutableLiveData;
}

    public Event<Response<?>> signInWithCredentials(final GoogleSignInAccount userAccount) {

        Event<Response<?>> authResultMutableLiveData = new Event<>();
        AuthCredential credentials = GoogleAuthProvider.getCredential(userAccount.getIdToken(), null);
        mAuth.signInWithCredential(credentials)
                .addOnSuccessListener(authResult -> authResultMutableLiveData.setValue(new WorkflowResponseSuccess<>(AuthSuccessResponse.SIGN_IN_WITH_GOOGLE_CREDENTIALS_SUCCESS).finished(true)))
                .addOnFailureListener(e -> authResultMutableLiveData.setValue(new WorkflowResponseError(FirebaseError.fromException(e)).finished(true)));
        return authResultMutableLiveData;
    }

    public Event<Response<?>> sendResetPasswordLink(String email) {
        Event<Response<?>> authResultMutableLiveData = new Event<>();
        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(aVoid -> authResultMutableLiveData.setValue(new WorkflowResponseSuccess<>(AuthSuccessResponse.PASSWORD_RESET_LINK_SENT).finished(true)))
                .addOnFailureListener(e -> authResultMutableLiveData.setValue(new WorkflowResponseError(FirebaseError.fromException(e)).finished(true)));
        return authResultMutableLiveData;
    }

    public MutableLiveData<FirebaseUser> getFirebaseUser(){
        return new MutableLiveData<>(mAuth.getCurrentUser());
    }
}
