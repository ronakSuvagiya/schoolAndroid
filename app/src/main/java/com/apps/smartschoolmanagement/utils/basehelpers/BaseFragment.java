package com.apps.smartschoolmanagement.utils.basehelpers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import androidx.appcompat.app.AlertDialog.Builder;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.widget.EditText;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.validators.AbstractValidator;
import com.apps.smartschoolmanagement.utils.validators.EmailValidator;
import com.apps.smartschoolmanagement.utils.validators.NumericValidator;
import com.apps.smartschoolmanagement.utils.validators.PhoneNumberValidator;
import com.apps.smartschoolmanagement.utils.validators.ValidateValidator;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BaseFragment extends JsonFragment {
    public String TAG = getClass().getSimpleName();
    int count = 0;
    String error = null;
    public List<File> files = new ArrayList();
    AbstractValidator mValidator = new ValidateValidator();
    String value = null;

    /* renamed from: com.apps.smartschoolmanagement.utils.basehelpers.BaseFragment$1 */
    class C14561 implements OnClickListener {
        C14561() {
        }

        public void onClick(DialogInterface dialog, int id) {
            BaseFragment.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 99);
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.utils.basehelpers.BaseFragment$2 */
    class C14572 implements OnClickListener {
        C14572() {
        }

        public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
        }
    }

    public static boolean isLocationEnabled(Context context) {
        try {
            if (Secure.getInt(context.getContentResolver(), "location_mode") != 0) {
                return true;
            }
            return false;
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showEnableLocationDialog() {
        Builder alertDialogBuilder = new Builder(getContext(), R.style.ProgressDialogTheme);
        alertDialogBuilder.setMessage("Location Services are not enabled. Do you want to enable them?");
        alertDialogBuilder.setPositiveButton("OK", new C14561());
        alertDialogBuilder.setNegativeButton("CANCEL", new C14572());
        alertDialogBuilder.create().show();
    }

    public boolean isValidForm(List<EditText> fields) {
        this.count = 0;
        for (EditText input : fields) {
            if (TextUtils.isEmpty(input.getText().toString())) {
                setErrorInfo(input, "Field is Required");
                this.count++;
            } else {
                this.value = input.getText().toString();
                if (input.getInputType() == 33 || input.getInputType() == 209) {
                    this.mValidator = new EmailValidator();
                    if (!this.mValidator.isValid(this.value)) {
                        this.error = "Invalid Email Address";
                        setError(input, this.error);
                        this.count++;
                    }
                }
                if (input.getInputType() == 3) {
                    this.mValidator = new PhoneNumberValidator();
                    if (!this.mValidator.isValid(this.value)) {
                        this.error = "Invalid Phone Number";
                        setError(input, this.error);
                        this.count++;
                    }
                }
                if (input.getInputType() == 2) {
                    this.mValidator = new NumericValidator();
                    if (!this.mValidator.isValid(this.value)) {
                        this.error = "Invalid Number";
                        setError(input, this.error);
                        this.count++;
                    }
                }
            }
        }
        if (this.count > 0) {
            return false;
        }
        return true;
    }

    public boolean isValid(EditText input) {
        this.count = 0;
        if (TextUtils.isEmpty(input.getText().toString())) {
            setErrorInfo(input, "Field is Required");
            return false;
        }
        this.value = input.getText().toString();
        if (input.getInputType() == 33 || input.getInputType() == 209) {
            this.mValidator = new EmailValidator();
            if (!this.mValidator.isValid(this.value)) {
                this.error = "Invalid Email Address";
                setError(input, this.error);
                return false;
            }
        }
        if (input.getInputType() == 3) {
            this.mValidator = new PhoneNumberValidator();
            if (!this.mValidator.isValid(this.value)) {
                this.error = "Invalid Phone Number";
                setError(input, this.error);
                return false;
            }
        }
        if (input.getInputType() == 2) {
            this.mValidator = new NumericValidator();
            if (!this.mValidator.isValid(this.value)) {
                this.error = "Invalid Number";
                setError(input, this.error);
                return false;
            }
        }
        return true;
    }

    private void setErrorInfo(EditText input, String msg) {
        Drawable d = getResources().getDrawable(R.drawable.ic_info);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.yellow400));
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(msg);
        ssbuilder.setSpan(fgcspan, 0, msg.length(), 0);
        input.setError(ssbuilder, d);
    }

    private void setError(EditText input, String msg) {
        Drawable d = getResources().getDrawable(R.drawable.ic_info);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        d.setColorFilter(getResources().getColor(R.color.red), Mode.MULTIPLY);
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.red200));
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(msg);
        ssbuilder.setSpan(fgcspan, 0, msg.length(), 0);
        input.setError(ssbuilder, d);
        input.requestFocus();
    }

    public int convertDpToPixels(float dp) {
        return (int) TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics());
    }
}
