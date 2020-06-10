package com.tnj.if_else.utils.helperClasses.validator;

import com.tnj.if_else.utils.enums.ValidationErrors;

public abstract class Validator {

    protected ValidationErrors error;

    public Validator(){
        error = ValidationErrors.NO_ERROR;
    }
    public abstract Validator validate(String stringToValidate);

    public ValidationErrors getError() {
        return error;
    }
}
