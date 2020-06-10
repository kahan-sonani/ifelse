package com.tnj.if_else.utils.enums;

import android.content.Context;

import com.tnj.if_else.R;
import com.tnj.if_else.utils.interfaces.SuccessState;

public enum SuccessResponse implements SuccessState {

    NEW_EMAIL(R.string.success_new_email),
    EXISTING_EMAIL(R.string.success_existing_email),
    EXISTING_EMAIL_GOOGLE(R.string.success_existing_email_google),
    SIGN_IN_SUCCESSFUL(R.string.success_login),
    CREATE_ACCOUNT_SUCCESS(R.string.success_create_account),
    SIGN_IN_WITH_GOOGLE_CREDENTIALS_SUCCESS(R.string.success_sign_in_with_google_credentials),
    PASSWORD_RESET_LINK_SENT(R.string.success_password_reset_link_sent);

    private int successCode;
    private int messageId;

    SuccessResponse(int messageId){
        this.successCode = ordinal();
        this.messageId = messageId;
    }

    @Override
    public int getSuccessCode() {
        return successCode;
    }

    @Override
    public Boolean isSuccessful() {
        return true;
    }

    @Override
    public String getMessage(Context context) {
        return context.getString(messageId);
    }

    @Override
    public String getMessage(Context context, Object... formatArgs) {
        return context.getString(messageId,formatArgs);
    }
}
