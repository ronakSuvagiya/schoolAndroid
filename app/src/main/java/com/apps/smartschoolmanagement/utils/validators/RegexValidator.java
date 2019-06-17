package com.apps.smartschoolmanagement.utils.validators;

import java.util.regex.Pattern;

public class RegexValidator extends AbstractValidator {
    private Pattern mRegexPattern;

    public RegexValidator(String regex) {
        this.mRegexPattern = Pattern.compile(regex);
        this.mErrorMessage = "Invalid";
    }

    public boolean isValid(String value) {
        return this.mRegexPattern.matcher(value).matches();
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }
}
