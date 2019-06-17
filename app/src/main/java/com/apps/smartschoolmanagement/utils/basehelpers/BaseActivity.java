package com.apps.smartschoolmanagement.utils.basehelpers;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.provider.MediaStore.Images.Media;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.StartActivity;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.KeyboardUtil;
import com.apps.smartschoolmanagement.utils.SharedPreferencesHelper;
import com.apps.smartschoolmanagement.utils.TabPagerAdapter;
import com.apps.smartschoolmanagement.utils.TypefaceSpan;
import com.apps.smartschoolmanagement.utils.validators.AbstractValidator;
import com.apps.smartschoolmanagement.utils.validators.EmailValidator;
import com.apps.smartschoolmanagement.utils.validators.NumericValidator;
import com.apps.smartschoolmanagement.utils.validators.PhoneNumberValidator;
import com.apps.smartschoolmanagement.utils.validators.ValidateValidator;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Timer;

public class BaseActivity extends JsonClass {
    public TabPagerAdapter _adapter;
    AsyncResponse asyncDelegate = null;
    int count = 0;
    String error = null;
    public List<File> files = new ArrayList();
    public Handler handler = new Handler();
    AbstractValidator mValidator = new ValidateValidator();
    public int position = 0;
    public Runnable runnable;
    int selectedPosition = 0;
    public SharedPreferencesHelper sh;
    public Timer swipeTimer = new Timer();
    String value = null;

    /* renamed from: com.apps.smartschoolmanagement.utils.basehelpers.BaseActivity$3 */
    class C14543 implements OnClickListener {
        C14543() {
        }

        public void onClick(DialogInterface dialog, int id) {
            BaseActivity.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 99);
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.utils.basehelpers.BaseActivity$4 */
    class C14554 implements OnClickListener {
        C14554() {
        }

        public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
            BaseActivity.this.recreate();
        }
    }

    public void setTitle(String str) {
        SpannableString s = new SpannableString(str);
        s.setSpan(new TypefaceSpan(this, R.font.meriendaone_regular), 0, s.length(), 33);
        setTitle(s);
    }

    public static final Uri getUriToDrawable(@NonNull Context context, @AnyRes int drawableId) {
        return Uri.parse("android.resource://" + context.getResources().getResourcePackageName(drawableId) + '/' + context.getResources().getResourceTypeName(drawableId) + '/' + context.getResources().getResourceEntryName(drawableId));
    }

    public static final Uri getUriToResource(@NonNull Context context, @AnyRes int resId) throws NotFoundException {
        Resources res = context.getResources();
        return Uri.parse("android.resource://" + res.getResourcePackageName(resId) + '/' + res.getResourceTypeName(resId) + '/' + res.getResourceEntryName(resId));
    }

    public int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

    public String capWordFirstLetter(String fullname) {
        String fname = "";
        StringTokenizer tokenizer = new StringTokenizer(fullname);
        while (tokenizer.hasMoreTokens()) {
            String s2 = tokenizer.nextToken().toLowerCase();
            if (fname.length() == 0) {
                fname = fname + s2.substring(0, 1).toUpperCase() + s2.substring(1);
            } else {
                fname = fname + " " + s2.substring(0, 1).toUpperCase() + s2.substring(1);
            }
        }
        return fname;
    }

    public boolean areSame(EditText a, EditText b) {
        if (a.getText().toString().equals(b.getText().toString())) {
            return true;
        }
        return false;
    }

    public void changeFontStyle(ViewGroup parent, String name) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                changeFontStyle((ViewGroup) child, name);
            } else if (child instanceof TextView) {
                TextView sp = (TextView) child;
                String tag = null;
                if (sp != null) {
                    try {
                        tag = (String) sp.getTag();
                    } catch (Exception exe) {
                        showToast(exe.getMessage());
                        Log.i("error", exe.getMessage());
                    }
                    if (tag != null && "title".equals(tag)) {
                        sp.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + name));
                    }
                }
            }
        }
    }

    public int getRandomNumber(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static boolean deleteDir(File dir) {
        if (dir == null || !dir.isDirectory()) {
            return false;
        }
        String[] children = dir.list();
        for (String file : children) {
            if (!deleteDir(new File(dir, file))) {
                return false;
            }
        }
        return dir.delete();
    }

    public void clearFields(List<EditText> fields) {
        for (EditText editText : fields) {
            editText.setText("");
        }
    }

    public void showCloseDialog() {
        Builder alertDialogBuilder = new Builder(this, R.style.ProgressDialogTheme);
        View view = getLayoutInflater().inflate(R.layout.layout_exit, null);
        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Button logout = (Button) view.findViewById(R.id.btn_logout);
        Button exit = (Button) view.findViewById(R.id.btn_exit);
        if (exit != null) {
            exit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    alertDialog.dismiss();
                    BaseActivity.this.finishAndRemoveTask();
                }
            });
        }
        if (logout != null) {
            logout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    alertDialog.dismiss();
                    BaseActivity.this.sh.logout();
                    BaseActivity.this.startActivity(new Intent(BaseActivity.this, StartActivity.class));
                    BaseActivity.this.finishAffinity();
                }
            });
        }
        alertDialog.show();
    }

    private void showFileChooser() {
        Intent intent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a Photo"), 99);
        } catch (ActivityNotFoundException e) {
            showToast("Please install a File Manager");
        }
    }

    public boolean isAllChecked(List<Spinner> fields) {
        this.count = 0;
        for (Spinner spinner : fields) {
            if (spinner.getSelectedItemPosition() <= 0) {
                this.count++;
            }
        }
        if (this.count != 0) {
            return false;
        }
        return true;
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

    public void setErrorInfo(EditText input, String msg) {
        Drawable d = getResources().getDrawable(R.drawable.ic_info);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        d.setColorFilter(getResources().getColor(R.color.red200), Mode.MULTIPLY);
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.yellow400));
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(msg);
        ssbuilder.setSpan(fgcspan, 0, msg.length(), 0);
        input.setError(ssbuilder, d);
        KeyboardUtil.hideSoftKeyboard(this);
    }

    public void setError(EditText input, String msg) {
        Drawable d = getResources().getDrawable(R.drawable.ic_info);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        d.setColorFilter(getResources().getColor(R.color.red), Mode.MULTIPLY);
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.red200));
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(msg);
        ssbuilder.setSpan(fgcspan, 0, msg.length(), 0);
        input.setError(ssbuilder, d);
        input.requestFocus();
        KeyboardUtil.hideSoftKeyboard(this);
    }

    public int convertDipToPixels(float dips) {
        return (int) ((getResources().getDisplayMetrics().density * dips) + 0.5f);
    }

    public int convertDpToPixels(float dp) {
        return (int) TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics());
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
        Builder alertDialogBuilder = new Builder(this, R.style.ProgressDialogTheme);
        alertDialogBuilder.setMessage("Location Services are not enabled. Do you want to enable them?");
        alertDialogBuilder.setPositiveButton("OK", new C14543());
        alertDialogBuilder.setNegativeButton("CANCEL", new C14554());
        alertDialogBuilder.create().show();
    }

    public int getResourceId(String pVariableName, String pResourcename, String pPackageName) {
        try {
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }

    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }

    public void onBackPressed() {
        exitToBottomAnimation();
        super.onBackPressed();
    }

    protected void enterFromBottomAnimation() {
        overridePendingTransition(R.anim.slide_from_bottom, R.anim.activity_no_animation);
    }

    protected void exitToBottomAnimation() {
        overridePendingTransition(R.anim.activity_no_animation, R.anim.slide_to_bottom);
    }

    public long getFreeMemory() {
        long bytesAvailable;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        if (VERSION.SDK_INT >= 18) {
            bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } else {
            bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        }
        long megAvailable = bytesAvailable / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
        Log.d("Saving File : ", "Available MB : " + megAvailable);
        return megAvailable;
    }

    public static String floatForm(double d) {
        return new DecimalFormat("#.##").format(d);
    }

    public static String bytesToHuman(long size) {
        long Mb = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        long Gb = Mb * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        long Tb = Gb * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        long Pb = Tb * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        long Eb = Pb * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        if (size < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            return floatForm((double) size) + " byte";
        }
        if (size >= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID && size < Mb) {
            return floatForm(((double) size) / ((double) 1024)) + " Kb";
        }
        if (size >= Mb && size < Gb) {
            return floatForm(((double) size) / ((double) Mb)) + " Mb";
        }
        if (size >= Gb && size < Tb) {
            return floatForm(((double) size) / ((double) Gb)) + " Gb";
        }
        if (size >= Tb && size < Pb) {
            return floatForm(((double) size) / ((double) Tb)) + " Tb";
        }
        if (size >= Pb && size < Eb) {
            return floatForm(((double) size) / ((double) Pb)) + " Pb";
        }
        if (size >= Eb) {
            return floatForm(((double) size) / ((double) Eb)) + " Eb";
        }
        return "???";
    }
}
