package com.apps.smartschoolmanagement.utils.validators;

import android.util.Patterns;

public class UrlValidator extends AbstractValidator {
    public UrlValidator() {
        this.mErrorMessage = "Invalid URL";
    }

    public boolean isValid(String value) {
        return Patterns.WEB_URL.matcher(value).matches();
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }
}
