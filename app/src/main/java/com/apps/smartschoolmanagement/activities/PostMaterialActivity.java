package com.apps.smartschoolmanagement.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.apps.smartschoolmanagement.permissions.PermissionHandler;
import com.apps.smartschoolmanagement.permissions.Permissions;
import com.google.common.net.HttpHeaders;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.SpinnerHelper;
import com.apps.smartschoolmanagement.utils.URLs;
import com.apps.smartschoolmanagement.utils.filechooser.FileUtils;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PostMaterialActivity extends JsonClass {
    private static final int FILE_SELECT_CODE = 9;
    SpinnerHelper classes;
    Spinner cls;
    ArrayList<String> filepaths = new ArrayList();
    public List<File> files = new ArrayList();
    Spinner subj;
    SpinnerHelper subject;

    /* renamed from: com.apps.smartschoolmanagement.activities.PostMaterialActivity$1 */
    class C12561 implements OnClickListener {

        /* renamed from: com.apps.smartschoolmanagement.activities.PostMaterialActivity$1$1 */
        class C12551 extends PermissionHandler {
            C12551() {
            }

            public void onGranted() {
                PostMaterialActivity.this.showFileChooser();
            }

            public void onDenied(Context context, ArrayList<String> arrayList) {
                Toast.makeText(PostMaterialActivity.this, "You have to allow Storage Permissions to use this service", 0).show();
            }
        }

        C12561() {
        }

        public void onClick(View view) {
            Permissions.check(PostMaterialActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, "Storage Permission is required to use this service", new Permissions.Options().setSettingsDialogTitle("Warning!").setRationaleDialogTitle(HttpHeaders.WARNING), new C12551());
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.PostMaterialActivity$2 */
    class C12582 implements OnClickListener {

        /* renamed from: com.apps.smartschoolmanagement.activities.PostMaterialActivity$2$1 */
        class C12571 implements VolleyCallback {
            C12571() {
            }

            public void onSuccess(String result) {
                Toast.makeText(PostMaterialActivity.this, result, 0).show();
                PostMaterialActivity.this.finish();
            }
        }

        C12582() {
        }

        public void onClick(View view) {
            if (PostMaterialActivity.this.subj.getSelectedItemPosition() > 0) {
                PostMaterialActivity.this.params.put("subject_id", PostMaterialActivity.this.subject.getmCodeId());
                if (PostMaterialActivity.this.cls.getSelectedItemPosition() > 0) {
                    PostMaterialActivity.this.params.put("class_id", PostMaterialActivity.this.classes.getmCodeId());
                }
                if (PostMaterialActivity.this.files.size() <= 0) {
                    Toast.makeText(PostMaterialActivity.this, "No File Attached", 0).show();
                }
                try {
                    PostMaterialActivity.this.uploadMultipartData(PostMaterialActivity.this.files, "", PostMaterialActivity.this.params, URLs.post_online_material, new C12571());
                    return;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return;
                }
            }
            Toast.makeText(PostMaterialActivity.this, "Please Select Subject", 0).show();
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.PostMaterialActivity$3 */
    class C12593 implements OnClickListener {
        C12593() {
        }

        public void onClick(View v) {
            PostMaterialActivity.this.filepaths.clear();
            PostMaterialActivity.this.findViewById(R.id.layout_attch_remove).setVisibility(8);
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_material);
        setTitle("Post Online Material");
        KProgressHUD progressHUD = KProgressHUD.create(this);
        this.subj = (Spinner) findViewById(R.id.spnr_subject);
        this.cls = (Spinner) findViewById(R.id.spnr_class);
//        this.classes = new SpinnerHelper((Context) this, (int) R.id.spnr_class, URLs.class_codes);
//        this.subject = new SpinnerHelper((Context) this, (int) R.id.spnr_subject, URLs.subject_codes);
        findViewById(R.id.btn_attach).setOnClickListener(new C12561());
        findViewById(R.id.btn_post).setOnClickListener(new C12582());
    }

    private void showFileChooser() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        intent.addCategory("android.intent.category.OPENABLE");
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 9);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Please install a File Manager.", 0).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 9:
                if (resultCode == -1) {
                    findViewById(R.id.layout_attch_remove).setVisibility(0);
                    this.files.clear();
//                    if (FileUtils.getPath(this, data.getData()) != null) {
//                        this.files.add(new File(FileUtils.getPath(this, data.getData())));
//                    } else {
//                        Toast.makeText(this, "Please Choose the correct Format for file", 0).show();
//                    }
                    findViewById(R.id.remove_attachment).setOnClickListener(new C12593());
                    break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
