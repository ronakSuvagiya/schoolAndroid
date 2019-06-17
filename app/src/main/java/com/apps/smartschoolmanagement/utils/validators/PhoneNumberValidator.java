package com.apps.smartschoolmanagement.utils.validators;

import android.util.Patterns;

public class PhoneNumberValidator extends AbstractValidator {
    public PhoneNumberValidator() {
        this.mErrorMessage = "Invalid Phone Number";
    }

    public boolean isValid(String value) {
        return Patterns.PHONE.matcher(value).matches() && value.length() == 10;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }
}
