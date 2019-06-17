package com.apps.smartschoolmanagement.utils;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment implements OnDateSetListener {
    Calendar activeDate;
    String selection = null;
    EditText setEditText;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    /* renamed from: com.apps.smartschoolmanagement.utils.DatePickerFragment$1 */
    class C13921 implements OnClickListener {
        C13921() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (which == -3) {
                dialog.dismiss();
                DatePickerFragment.this.setEditText.setText("");
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

    public void setSelectionType(String selectionType) {
        this.selection = selectionType;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year;
        int month;
        int day;
        Calendar c = Calendar.getInstance();
        if ("".equals(this.setEditText.getText().toString())) {
            year = c.get(1);
            month = c.get(2);
            day = c.get(5);
        } else {
            year = this.activeDate.get(1);
            month = this.activeDate.get(2);
            day = this.activeDate.get(5);
        }
        DatePickerDialog cdp = new DatePickerDialog(getActivity(), this, year, month, day);
        if (this.selection != null && "past".equals(this.selection)) {
            cdp.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        } else if (this.selection != null && "future".equals(this.selection)) {
            cdp.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            cdp.setTitle(null);
        }
        cdp.setButton(-3, "Clear", new C13921());
        return cdp;
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, dayOfMonth);
        String month_name = newDate.getDisplayName(2, 1, Locale.getDefault());
        this.setEditText.setText(this.simpleDateFormat.format(newDate.getTime()));
    }
}
