package com.tnj.if_else.utils.helperClasses.validator;

import com.tnj.if_else.utils.enums.ValidationErrors;

public class RequiredValidator extends Validator {
    @Override
    public RequiredValidator validate(String validator) {
        error = "".equals(validator) ? ValidationErrors.REQUIRED : ValidationErrors.NO_ERROR;
        return this;
    }
}
