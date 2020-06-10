package com.tnj.if_else.utils.helperClasses;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuthException;
import com.tnj.if_else.utils.interfaces.ErrorResponse;

public class FirebaseAuthError {

    public enum Error{

        INTERNET_CONNECTION_ERROR(51);

        private int errorCode;

        Error(int errorCode){
            this.errorCode = errorCode;
        }

        public int getErrorCode() {
            return errorCode;
        }
    }
    public static ErrorResponse fromException(Exception exception) {
        int errorCode = exception instanceof FirebaseAuthException ?
                Integer.parseInt(((FirebaseAuthException) exception).getErrorCode()) : -1;

        return new ErrorResponse() {
            @Override
            public int getErrorCode() {
                return errorCode;
            }

            @Override
            public Boolean isSuccessful() {
                return false;
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
}
