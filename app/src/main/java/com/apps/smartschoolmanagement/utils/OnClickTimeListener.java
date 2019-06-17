package com.apps.smartschoolmanagement.utils;

import android.content.Context;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class OnClickTimeListener implements OnClickListener {
    EditText activeField;
    FragmentActivity mContext;

    public OnClickTimeListener(EditText field, Context context) {
        this.activeField = field;
        this.mContext = (FragmentActivity) context;
        field.setOnClickListener(this);
    }

    public void onClick(View v) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setSetEditText(this.activeField);
        timePickerFragment.show(this.mContext.getSupportFragmentManager(), "timePicker");
    }
}
