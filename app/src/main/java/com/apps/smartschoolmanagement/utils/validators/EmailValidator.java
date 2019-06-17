package com.apps.smartschoolmanagement.utils.validators;

import android.util.Patterns;

public class EmailValidator extends AbstractValidator {
    public EmailValidator() {
        this.mErrorMessage = "Invalid Email Address";
    }

    public boolean isValid(String value) {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches();
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }
}
