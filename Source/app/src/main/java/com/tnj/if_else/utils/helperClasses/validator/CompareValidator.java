package com.tnj.if_else.utils.helperClasses.validator;

import com.tnj.if_else.utils.enums.ValidationErrors;

public class CompareValidator extends Validator {

    public CompareValidator validate(String stringToBeValidated,String stringToBeCompared) {

        if ("".equals(stringToBeValidated))
            error = ValidationErrors.REQUIRED;
        else if (!stringToBeValidated.equals(stringToBeCompared))
            error = ValidationErrors.PASSWORD_DOES_NOT_MATCH;
        else
            error = ValidationErrors.NO_ERROR;
        return this;
    }

    @Override
    public Validator validate(String stringToBeValidated) {
        return this;
    }
}
