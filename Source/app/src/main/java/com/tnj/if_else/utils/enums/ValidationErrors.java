package com.tnj.if_else.utils.enums;

import android.content.Context;

import com.tnj.if_else.R;
import com.tnj.if_else.utils.interfaces.ErrorResponse;

public enum ValidationErrors implements ErrorResponse {

    NO_ERROR(R.string.success_validation),
    REQUIRED(R.string.error_field_required),
    PASSWORD_FORMAT_INVALID(R.string.error_password_format_invalid),
    PASSWORD_DOES_NOT_MATCH(R.string.error_password_mismatch),
    EMAIL_FORMAT_INVALID(R.string.error_email_format_invalid);

    ValidationErrors(int messageId){
        this.messageId = messageId;
    }

    private int messageId;

    @Override
    public int getErrorCode() {
        return -1;
    }

    @Override
    public String getMessage(Context context) {
        return toString().equals(NO_ERROR) ? null : context.getString(messageId);
    }

    @Override
    public String getMessage(Context context, Object... formatArgs) {
        return context.getString(messageId,formatArgs);
    }
}
