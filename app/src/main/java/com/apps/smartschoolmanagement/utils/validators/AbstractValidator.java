package com.apps.smartschoolmanagement.utils.validators;

public abstract class AbstractValidator {
    String mErrorMessage = "Invalid Field";

    public abstract String getErrorMessage();

    public abstract boolean isValid(String str);

    public void setErrorMessage(String message) {
        this.mErrorMessage = message;
    }
}
