package com.apps.smartschoolmanagement.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextWatcherAdapter implements TextWatcher {
    private final TextWatcherListener listener;
    private final EditText view;

    public interface TextWatcherListener {
        void onTextChanged(EditText editText, String str);
    }

    public TextWatcherAdapter(EditText editText, TextWatcherListener listener) {
        this.view = editText;
        this.listener = listener;
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.listener.onTextChanged(this.view, s.toString());
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {
    }
}
