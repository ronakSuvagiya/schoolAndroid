package com.apps.smartschoolmanagement.utils;

import android.content.Context;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class OnClickDateListener implements OnClickListener {
    EditText activeField;
    FragmentActivity mContext;
    String selectionType = null;

    public OnClickDateListener(EditText field, Context context) {
        this.activeField = field;
        this.mContext = (FragmentActivity) context;
        field.setOnClickListener(this);
    }

    public OnClickDateListener(EditText field, Context context, String selection) {
        this.activeField = field;
        this.selectionType = selection;
        this.mContext = (FragmentActivity) context;
        field.setOnClickListener(this);
    }

    public void onClick(View v) {
        DatePickerFragment DateFragment = new DatePickerFragment();
        DateFragment.setSetEditText(this.activeField);
        if (this.selectionType != null) {
            DateFragment.setSelectionType(this.selectionType);
        }
        DateFragment.show(this.mContext.getSupportFragmentManager(), "datePicker");
    }
}
