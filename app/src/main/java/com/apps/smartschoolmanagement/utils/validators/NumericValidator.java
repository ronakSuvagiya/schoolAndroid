package com.apps.smartschoolmanagement.utils.validators;

public class NumericValidator extends AbstractValidator {
    public NumericValidator() {
        this.mErrorMessage = "Invalid Number";
    }

    public boolean isValid(String value) {
        return isNumeric(value);
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    protected boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
