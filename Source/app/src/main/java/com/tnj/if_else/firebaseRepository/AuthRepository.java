package com.tnj.if_else.firebaseRepository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tnj.if_else.utils.enums.SuccessResponse;
import com.tnj.if_else.utils.helperClasses.Event;
import com.tnj.if_else.utils.helperClasses.FirebaseAuthError;
import com.tnj.if_else.utils.helperClasses.Response;
import com.tnj.if_else.utils.interfaces.State;

import java.util.List;
import java.util.Objects;

public class AuthRepository extends BaseOperations {

    private static AuthRepository repository;

    private AuthRepository(){ }

    public static AuthRepository getInstance(){
        if(repository == null)
            return repository = new AuthRepository();
        else
            return repository;
    }

    public LiveData<Response<? extends State>> verifyEmail(final String email) {
        Event<Response<? extends State>> responseLiveData = new Event<>();
        Log.i("ifelse","verify email()");
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnSuccessListener(signInMethodQueryResult -> {
                    List<String> result;
                    if (!Objects.requireNonNull(result = signInMethodQueryResult.getSignInMethods()).isEmpty()) {
                        if (result.size() == 1)
                            responseLiveData.setValue(new Response<>(result.get(0).equals("google.com") ? SuccessResponse.EXISTING_EMAIL_GOOGLE
                                    : SuccessResponse.EXISTING_EMAIL).finished(true));
                    } else responseLiveData.setValue(new Response<>(SuccessResponse.NEW_EMAIL).finished(true));
                })
                .addOnFailureListener(e -> responseLiveData.setValue(new Response<>(FirebaseAuthError.fromException(e)).finished(true)));
     return responseLiveData;
    }

   /* public LiveData<Response<State>> createUserWithEmailAndPassword(final String email, final String password) {
        *//*mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> authResultMutableLiveData.setValue(new Response<>(SuccessResponse.CREATE_ACCOUNT_SUCCESS).finished(true)))
                .addOnFailureListener(e -> authResultMutableLiveData.setValue(new Response<>(FirebaseAuthError.fromException(e)).finished(true)));
        return authResultMutableLiveData;*//*
    }

    public LiveData<Response<? extends State>> loginUserWithEmailAndPassword(final String email, final String password) {
        Event<Response<? extends State>> authResultMutableLiveData = new Event<>();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> authResultMutableLiveData.setValue(new Response<>(SuccessResponse.SIGN_IN_SUCCESSFUL).finished(true)))
                .addOnFailureListener(e -> authResultMutableLiveData.setValue(new Response<>(FirebaseAuthError.fromException(e)).finished(true)));
        return authResultMutableLiveData;
    }

    public LiveData<Response<? extends State>> signInWithCredentials(final GoogleSignInAccount userAccount) {

        Event<Response<? extends State>> authResultMutableLiveData = new Event<>();
        AuthCredential credentials = GoogleAuthProvider.getCredential(userAccount.getIdToken(), null);
        mAuth.signInWithCredential(credentials)
                .addOnSuccessListener(authResult -> authResultMutableLiveData.setValue(new Response<>(SuccessResponse.SIGN_IN_WITH_GOOGLE_CREDENTIALS_SUCCESS).finished(true)))
                .addOnFailureListener(e -> authResultMutableLiveData.setValue(new Response<>(FirebaseAuthError.fromException(e))));
        return authResultMutableLiveData;
    }

    public LiveData<Response<? extends State>> sendResetPasswordLink(String email) {
        Event<Response<? extends State>> authResultMutableLiveData = new Event<>();

        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(aVoid -> authResultMutableLiveData.setValue(new Response<>(SuccessResponse.PASSWORD_RESET_LINK_SENT)))
                .addOnFailureListener(e -> authResultMutableLiveData.setValue(new Response<>(FirebaseAuthError.fromException(e))));
        return authResultMutableLiveData;
    }*/
}
