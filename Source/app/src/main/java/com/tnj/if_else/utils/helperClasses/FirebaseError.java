package com.tnj.if_else.utils.helperClasses;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuthException;
import com.tnj.if_else.utils.interfaces.ErrorResponse;

public class FirebaseError {

    public enum Error {

        UNKNOWN_ERROR(-1),
        ERROR_EMAIL_ALREADY_IN_USE(54),
        ERROR_WRONG_PASSWORD(51),
        ERROR_INVALID_EMAIL(53),
        INTERNET_CONNECTION_ERROR(52);

        private int errorCode;

        Error(int errorCode){
            this.errorCode = errorCode;
        }
    }

    public static ErrorResponse fromException(Exception exception) {

        return new ErrorResponse() {
            @Override
            public int getErrorCode() {
                return getError(exception).errorCode;
            }

            @Override
            public String getMessage(Context context) {
                return exception.getMessage();
            }

            @Override
            public String getMessage(Context context, Object... formatArgs) {
                return exception.getMessage();
            }
        };
    }
    private static Error getError(Exception exception){
        if(exception instanceof FirebaseAuthException){
            try{
                return Error.valueOf(((FirebaseAuthException) exception).getErrorCode());
            }catch (IllegalArgumentException e){
                return Error.UNKNOWN_ERROR;
            }
        } return Error.UNKNOWN_ERROR;
    }
}
