package com.apps.smartschoolmanagement.utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.widget.EditText;
import android.widget.TimePicker;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimePickerFragment extends DialogFragment implements OnTimeSetListener {
    Calendar activeDate;
    EditText setEditText;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa", Locale.US);

    /* renamed from: com.apps.smartschoolmanagement.utils.TimePickerFragment$1 */
    class C14511 implements OnClickListener {
        C14511() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (which == -3) {
                dialog.dismiss();
                TimePickerFragment.this.setEditText.setText("");
            }
        }
    }

    public void setSetEditText(EditText setEditText) {
        this.setEditText = setEditText;
        if (!"".equals(setEditText.getText().toString())) {
            try {
                Date d = this.simpleDateFormat.parse(setEditText.getText().toString());
                this.activeDate = Calendar.getInstance();
                this.activeDate.setTime(d);
            } catch (ParseException e) {
            }
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour;
        int minute;
        Calendar c = Calendar.getInstance();
        if ("".equals(this.setEditText.getText().toString())) {
            hour = c.get(11);
            minute = c.get(12);
        } else {
            hour = this.activeDate.get(11);
            minute = this.activeDate.get(12);
        }
        TimePickerDialog cdp = new TimePickerDialog(getActivity(), this, hour, minute, false);
        cdp.setButton(-3, "Clear", new C14511());
        return cdp;
    }

    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Calendar datetime = Calendar.getInstance();
        datetime.set(11, hourOfDay);
        datetime.set(12, minute);
        this.setEditText.setText((datetime.get(10) == 0 ? "12" : Integer.toString(datetime.get(10))) + ":" + (minute < 10 ? "0" + minute : "" + minute) + " " + (hourOfDay < 12 ? "AM" : "PM"));
    }
}
