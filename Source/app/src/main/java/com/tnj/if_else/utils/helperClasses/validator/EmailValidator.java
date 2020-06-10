package com.tnj.if_else.utils.helperClasses.validator;

import android.util.Patterns;

import com.tnj.if_else.utils.enums.ValidationErrors;

public class EmailValidator extends Validator {

    @Override
    public EmailValidator validate(String stringToValidate) {
        if ("".equals(stringToValidate))
            error = ValidationErrors.REQUIRED;
        else if (!Patterns.EMAIL_ADDRESS.matcher(stringToValidate).matches())
            error = ValidationErrors.EMAIL_FORMAT_INVALID;
        else
            error = ValidationErrors.NO_ERROR;

        return this;
    }
}
