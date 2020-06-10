package com.tnj.if_else.utils.helperClasses.validator;

import com.tnj.if_else.utils.enums.ValidationErrors;

public class PasswordValidator extends Validator {

    private String regEx = "^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z@#()!~,./<>|=`'\":_\\-+;$%^&*?]{8,}$";

    @Override
    public PasswordValidator validate(String stringToValidate) {
        if ("".equals(stringToValidate))
            error = ValidationErrors.REQUIRED;
        else if (!stringToValidate.matches(regEx))
            error = ValidationErrors.PASSWORD_FORMAT_INVALID;
        else
            error = ValidationErrors.NO_ERROR;
        return this;
    }
}
