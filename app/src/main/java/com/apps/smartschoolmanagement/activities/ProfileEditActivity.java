package com.apps.smartschoolmanagement.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog.Builder;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.apps.smartschoolmanagement.permissions.PermissionHandler;
import com.apps.smartschoolmanagement.permissions.Permissions;
import com.google.common.net.HttpHeaders;
//import com.google.firebase.auth.PhoneAuthProvider;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.AddPhotoGalleryActivity.Utils;
import com.apps.smartschoolmanagement.adapters.SpinnerrAdapter;
import com.apps.smartschoolmanagement.fragments.ManagerProfile;
import com.apps.smartschoolmanagement.fragments.StaffProfile;
import com.apps.smartschoolmanagement.fragments.StudentProfile;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.AppSingleton;
import com.apps.smartschoolmanagement.utils.OnClickDateListener;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import com.apps.smartschoolmanagement.utils.basehelpers.BaseActivity;
import com.apps.smartschoolmanagement.utils.filechooser.FileUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileEditActivity extends BaseActivity {
    EditText birthday;
    public String bitmap_string = null;
    String classid = null;
    boolean clicked = false;
    Spinner cls;
    ProgressDialog dialog = null;
    public List<File> files = new ArrayList();
    Spinner gender;
    ImageView profilePic;
    LinearLayout root;
    Spinner subj;
    String subjectid = null;

    /* renamed from: com.apps.smartschoolmanagement.activities.ProfileEditActivity$1 */
    class C12681 implements ImageListener {
        C12681() {
        }

        public void onResponse(ImageContainer imageContainer, boolean b) {
            ProfileEditActivity.this.profilePic.setImageBitmap(imageContainer.getBitmap());
        }

        public void onErrorResponse(VolleyError volleyError) {
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.ProfileEditActivity$2 */
    class C12692 implements VolleyCallback {
        C12692() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                ProfileEditActivity.this.classid = result;
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.ProfileEditActivity$3 */
    class C12703 implements VolleyCallback {
        C12703() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                ProfileEditActivity.this.subjectid = result;
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.ProfileEditActivity$4 */
    class C12724 implements OnClickListener {

        /* renamed from: com.apps.smartschoolmanagement.activities.ProfileEditActivity$4$1 */
        class C12711 extends PermissionHandler {
            C12711() {
            }

            public void onGranted() {
                ProfileEditActivity.this.showDialogBox();
            }

            public void onDenied(Context context, ArrayList<String> arrayList) {
                Toast.makeText(ProfileEditActivity.this, "You have to allow Storage Permissions to use this service", 0).show();
            }
        }

        C12724() {
        }

        public void onClick(View view) {
            Permissions.check(ProfileEditActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, "Storage Permission is required to use this service", new Permissions.Options().setSettingsDialogTitle("Warning!").setRationaleDialogTitle(HttpHeaders.WARNING), new C12711());
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.ProfileEditActivity$5 */
    class C12735 implements VolleyCallback {
        C12735() {
        }

        public void onSuccess(String result) {
            ProfileEditActivity.this.processJsonResponse(result);
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.ProfileEditActivity$7 */
    class C12767 implements OnClickListener {

        /* renamed from: com.apps.smartschoolmanagement.activities.ProfileEditActivity$7$1 */
        class C12751 implements VolleyCallback {
            C12751() {
            }

            public void onSuccess(String result) {
                ProfileEditActivity.this.clicked = true;
                if (ProfileEditActivity.this.clicked) {
                    HomeActivity.saved = true;
                }
                Toast.makeText(ProfileEditActivity.this, "Profile Updated", 0).show();
                if (ProfileInfo.getInstance().getLoginData().get("userPic") != null) {
                    byte[] decodedString = Base64.decode((String) ProfileInfo.getInstance().getLoginData().get("userPic"), 0);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    if (UserStaticData.user_type == 0) {
                        StudentProfile.profilePic.setImageBitmap(decodedByte);
                    } else if (UserStaticData.user_type == 1) {
                        StaffProfile.profilePic.setImageBitmap(decodedByte);
                    } else if (UserStaticData.user_type == 2) {
                        ManagerProfile.profilePic.setImageBitmap(decodedByte);
                    }
                }
                ProfileEditActivity.this.finish();
            }
        }

        C12767() {
        }

        public void onClick(View view) {
            if (UserStaticData.user_type == 0) {
                ProfileEditActivity.this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
            }
            if (UserStaticData.user_type == 1) {
                ProfileEditActivity.this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
            }
            if (UserStaticData.user_type == 2) {
                ProfileEditActivity.this.params.put("admin_id", ProfileInfo.getInstance().getLoginData().get("userId"));
            }
            ProfileEditActivity.this.params.put(Utils.imageName, ((EditText) ProfileEditActivity.this.findViewById(R.id.name)).getText().toString());
            ProfileEditActivity.this.params.put("roll", ((EditText) ProfileEditActivity.this.findViewById(R.id.roll)).getText().toString());
            ProfileEditActivity.this.params.put("birthday", ((EditText) ProfileEditActivity.this.findViewById(R.id.birthday)).getText().toString());
            ProfileEditActivity.this.params.put("subjectName", ((Spinner) ProfileEditActivity.this.findViewById(R.id.subjectName)).getSelectedItem().toString());
            ProfileEditActivity.this.params.put("sex", ((Spinner) ProfileEditActivity.this.findViewById(R.id.sex)).getSelectedItem().toString());
//            ProfileEditActivity.this.params.put(PhoneAuthProvider.PROVIDER_ID, ((EditText) ProfileEditActivity.this.findViewById(R.id.phone)).getText().toString());
            ProfileEditActivity.this.params.put("email", ((EditText) ProfileEditActivity.this.findViewById(R.id.email)).getText().toString());
            ProfileEditActivity.this.params.put("father_name", ((EditText) ProfileEditActivity.this.findViewById(R.id.father_name)).getText().toString());
            ProfileEditActivity.this.params.put("mother_name", ((EditText) ProfileEditActivity.this.findViewById(R.id.mother_name)).getText().toString());
            try {
                ProfileEditActivity.this.uploadMultipartData(ProfileEditActivity.this.files, "", ProfileEditActivity.this.params, URLs.editprofile, new C12751());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.ProfileEditActivity$8 */
    class C12778 implements DialogInterface.OnClickListener {
        C12778() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.ProfileEditActivity$9 */
    class C12789 implements DialogInterface.OnClickListener {
        C12789() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            ProfileEditActivity.this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 88);
            dialogInterface.dismiss();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        setTitle("Edit Profile");
        this.root = (LinearLayout) findViewById(R.id.root_layout);
        this.subj = (Spinner) findViewById(R.id.subjectName);
        this.cls = (Spinner) findViewById(R.id.className);
        this.gender = (Spinner) findViewById(R.id.sex);
        this.profilePic = (ImageView) findViewById(R.id.profilePic);
        if (ProfileInfo.getInstance().getLoginData().get("userPic") != null && ((String) ProfileInfo.getInstance().getLoginData().get("userPic")).length() > 50) {
            byte[] decodedString = Base64.decode((String) ProfileInfo.getInstance().getLoginData().get("userPic"), 0);
            this.profilePic.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        } else if (ProfileInfo.getInstance().getLoginData().get("file_path") != null) {
            AppSingleton.getInstance(this).getImageLoader().get((String) ProfileInfo.getInstance().getLoginData().get("file_path"), new C12681());
        }
        if ("teacher".equals(getIntent().getStringExtra("user"))) {
            findViewById(R.id.layout_class).setVisibility(8);
            findViewById(R.id.layout_father).setVisibility(8);
            findViewById(R.id.layout_mother).setVisibility(8);
        }
        this.birthday = (EditText) findViewById(R.id.birthday);
        this.birthday.setOnClickListener(new OnClickDateListener(this.birthday, this, "future"));
        if (UserStaticData.user_type == 0) {
            findViewById(R.id.layout_subject).setVisibility(8);
        } else if (UserStaticData.user_type == 2) {
            findViewById(R.id.layout_class).setVisibility(8);
            findViewById(R.id.layout_subject).setVisibility(8);
            findViewById(R.id.layout_roll).setVisibility(8);
            findViewById(R.id.layout_father).setVisibility(8);
            findViewById(R.id.layout_mother).setVisibility(8);
            findViewById(R.id.layout_vehicle).setVisibility(8);
        } else {
            findViewById(R.id.layout_roll).setVisibility(8);
            findViewById(R.id.layout_vehicle).setVisibility(8);
        }
//        getSpinnerData(this, URLs.class_codes, this.cls, new C12692());
//        getSpinnerData(this, URLs.subject_codes, this.subj, new C12703());
        findViewById(R.id.profilePic).setOnClickListener(new C12724());
        if (UserStaticData.user_type == 0) {
            this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        } else if (UserStaticData.user_type == 1) {
            this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        } else if (UserStaticData.user_type == 2) {
            this.params.put("admin_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        }
        getJsonResponse(URLs.userprofile, this, new C12735());
//        SpinnerrAdapter adapter = new SpinnerrAdapter(this, R.layout.spinner_selected_item, Arrays.asList(getResources().getStringArray(R.array.gender))) {
//            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    return false;
//                }
//                return true;
//            }
//        };
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
//        this.gender.setAdapter(adapter);
        findViewById(R.id.btn_save).setBackgroundResource(R.drawable.student_button);
        findViewById(R.id.btn_save).setOnClickListener(new C12767());
        this.dialog = new ProgressDialog(this);
        this.dialog.setMessage("Uploading Image...");
        this.dialog.setCancelable(true);
    }

    public void showDialogBox() {
        new Builder(this).setTitle("Avatar").setMessage("Are you sure want to change Profile Picture?").setPositiveButton(17039370, new C12789()).setNegativeButton(17039360, new C12778()).show();
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (this.clicked) {
            HomeActivity.saved = true;
        } else {
            HomeActivity.saved = false;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 88 || resultCode != -1) {
            return;
        }
        if (data == null) {
            Toast.makeText(this, "No Gallery Found", 1).show();
            return;
        }
        try {
            if (FileUtils.getPath(this, data.getData()) != null) {
                this.files.add(new File(FileUtils.getPath(this, data.getData())));
            } else {
                Toast.makeText(this, "Please Select Image", 0).show();
            }
            Bitmap imgBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
            ((ImageView) findViewById(R.id.profilePic)).setImageBitmap(imgBitmap);
            imgBitmap = getResizedBitmap(imgBitmap, 512);
            ((ImageView) findViewById(R.id.profilePic)).setImageBitmap(imgBitmap);
            this.bitmap_string = ImageBase64.encode(imgBitmap);
            ProfileInfo.getInstance().getLoginData().put("userPic", this.bitmap_string);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width;
        int height;
        float bitmapRatio = ((float) image.getWidth()) / ((float) image.getHeight());
        if (bitmapRatio > 1.0f) {
            width = maxSize;
            height = (int) (((float) width) / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (((float) height) * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void processJsonResponse(String response) {
        if (response != null && !response.equalsIgnoreCase("")) {
            this.responseParams.clear();
            try {
                JSONArray jsonArray = new JSONObject(response).getJSONArray("staffprofile");
                Iterator<String> keysIterator = jsonArray.getJSONObject(0).keys();
                while (keysIterator.hasNext()) {
                    String keyStr = (String) keysIterator.next();
                    this.responseParams.put(keyStr, jsonArray.getJSONObject(0).getString(keyStr).replaceAll("\\s+$", ""));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loadData(this.root, this.responseParams);
        }
    }

    public void loadData(ViewGroup parent, HashMap<String, String> returnData) {
        if (returnData != null) {
            int i = 0;
            while (i < parent.getChildCount()) {
                View child = parent.getChildAt(i);
                if (child instanceof ViewGroup) {
                    if (child instanceof Spinner) {
                        Spinner spinner = (Spinner) parent.getChildAt(i);
                        if (spinner.getId() > 0 && returnData.containsKey(getResources().getResourceEntryName(spinner.getId())) && spinner.getAdapter() != null) {
                            i = 0;
                            while (i < spinner.getAdapter().getCount()) {
                                if (((String) returnData.get(getResources().getResourceEntryName(spinner.getId()))).equalsIgnoreCase(spinner.getAdapter().getItem(i).toString())) {
                                    spinner.setSelection(i);
                                    break;
                                }
                                i++;
                            }
                        }
                    }
                    loadData((ViewGroup) child, returnData);
                } else if (parent.getChildAt(i) instanceof EditText) {
                    EditText et = (EditText) parent.getChildAt(i);
                    if (et.getId() > 0 && returnData.containsKey(getResources().getResourceEntryName(et.getId()))) {
                        if (et.getInputType() == 8193) {
                            et.setText(capWordFirstLetter((String) returnData.get(getResources().getResourceEntryName(et.getId()))));
                        } else {
                            et.setText((CharSequence) returnData.get(getResources().getResourceEntryName(et.getId())));
                        }
                    }
                }
                i++;
            }
            return;
        }
        Toast.makeText(this, "No record found", 0).show();
    }
}
