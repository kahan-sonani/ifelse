package com.tnj.if_else.utils.helperClasses.validator;

import android.util.Patterns;

import com.tnj.if_else.utils.enums.ValidationErrors;

public class Validations {

    private static String regEx = "^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z@#()!~,./<>|=`'\":_\\-+;$%^&*?]{8,}$";

    public static ValidationErrors emailPattern(String email) {
        ValidationErrors error;

        if ("".equals(email))
            error = ValidationErrors.REQUIRED;
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            error = ValidationErrors.EMAIL_FORMAT_INVALID;
        else
            error = ValidationErrors.NO_ERROR;

        return error;
    }

    public static ValidationErrors passwordPattern(String password) {

        ValidationErrors error;

        if ("".equals(password))
            error = ValidationErrors.REQUIRED;
        else if (!password.matches(regEx))
            error = ValidationErrors.PASSWORD_FORMAT_INVALID;
        else
            error = ValidationErrors.NO_ERROR;
        return error;
    }

    public static ValidationErrors fieldRequired(String field){
        return "".equals(field) ? ValidationErrors.REQUIRED : ValidationErrors.NO_ERROR;
    }

    public static <T> ValidationErrors fieldMatch(T fieldTobeValidated, T fieldToBeCompared){

        ValidationErrors error;

        if ("".equals(fieldTobeValidated))
            error = ValidationErrors.REQUIRED;
        else if (!fieldTobeValidated.equals(fieldToBeCompared))
            error = ValidationErrors.PASSWORD_DOES_NOT_MATCH;
        else
            error = ValidationErrors.NO_ERROR;
        return error;
    }
}
