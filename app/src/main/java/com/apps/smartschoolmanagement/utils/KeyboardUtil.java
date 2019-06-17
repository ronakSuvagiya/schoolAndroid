package com.apps.smartschoolmanagement.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {
    public static void hideSoftKeyboard(Activity activity) {
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    public static void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    private KeyboardUtil() {
    }
}
