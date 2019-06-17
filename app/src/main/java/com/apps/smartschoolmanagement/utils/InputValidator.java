package com.apps.smartschoolmanagement.utils;

import android.text.TextUtils;
import java.util.regex.Pattern;

public class InputValidator {
    public boolean isValidEmail(String string) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(string).matches();
    }

    public boolean isValidPassword(String string, boolean allowSpecialChars) {
        String PATTERN;
        if (allowSpecialChars) {
            PATTERN = "^[a-zA-Z@#$%]\\w{5,19}$";
        } else {
            PATTERN = "^[a-zA-Z]\\w{5,19}$";
        }
        return Pattern.compile(PATTERN).matcher(string).matches();
    }

    public boolean isNullOrEmpty(String string) {
        return TextUtils.isEmpty(string);
    }

    public boolean isNumeric(String string) {
        return TextUtils.isDigitsOnly(string);
    }
}
