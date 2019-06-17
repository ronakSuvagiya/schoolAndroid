package com.apps.smartschoolmanagement.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.smartschoolmanagement.permissions.PermissionHandler;
import com.apps.smartschoolmanagement.permissions.Permissions;
import com.google.common.net.HttpHeaders;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nileshp.multiphotopicker.photopicker.activity.PickImageActivity;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryAddActivity extends JsonClass implements OnClickListener {
    private ProgressDialog dialog = null;
    public List<File> files = new ArrayList();
    private ArrayList<String> pathList = new ArrayList();
    KProgressHUD progressHUD;
    private Button selectImages;
    private EditText title;
    private Button uploadImages;

    /* renamed from: com.apps.smartschoolmanagement.activities.PhotoGalleryAddActivity$1 */
    class C12531 extends PermissionHandler {
        C12531() {
        }

        public void onGranted() {
            PhotoGalleryAddActivity.this.openImagePickerIntent();
        }

        public void onDenied(Context context, ArrayList<String> arrayList) {
            Toast.makeText(PhotoGalleryAddActivity.this, "You have to allow Storage Permissions to use this service", 0).show();
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.PhotoGalleryAddActivity$2 */
    class C12542 implements VolleyCallback {
        C12542() {
        }

        public void onSuccess(String result) {
            if (!TextUtils.isEmpty(result)) {
                Toast.makeText(PhotoGalleryAddActivity.this, result, 0).show();
                PhotoGalleryAddActivity.this.finish();
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery_add);
        setTitle("Add Photo Gallery");
        this.progressHUD = KProgressHUD.create(this).setLabel("Uploading");
        this.title = (EditText) findViewById(R.id.edt_title);
        this.selectImages = (Button) findViewById(R.id.btn_select_images);
        this.uploadImages = (Button) findViewById(R.id.btn_upload);
        this.uploadImages.setOnClickListener(this);
        this.selectImages.setOnClickListener(this);
        this.dialog = new ProgressDialog(this);
        this.dialog.setMessage("Uploading Image...");
        this.dialog.setCancelable(true);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_images:
                Permissions.check(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, "Storage Permission is required to use this service", new Permissions.Options().setSettingsDialogTitle("Warning!").setRationaleDialogTitle(HttpHeaders.WARNING), new C12531());
                return;
            case R.id.btn_upload:
                if (this.title.getText().length() <= 0) {
                    this.title.setError("Add Title");
                    this.title.requestFocus();
                    return;
                } else if (this.files.size() > 0) {
                    this.params.put("album", this.title.getText().toString());
                    try {
                        uploadMultipartData(this.files, "", this.params, URLs.album_create, new C12542());
                        return;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return;
                    }
                } else {
                    Toast.makeText(this, "No Images Selected. Please Select Images", 0).show();
                    return;
                }
            default:
                return;
        }
    }

    public void openImagePickerIntent() {
        Intent mIntent = new Intent(this, PickImageActivity.class);
        mIntent.putExtra(PickImageActivity.KEY_LIMIT_MAX_IMAGE, 5);
        mIntent.putExtra(PickImageActivity.KEY_LIMIT_MIN_IMAGE, 1);
        startActivityForResult(mIntent, 1001);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == -1 && resultCode == -1 && requestCode == 1001) {
            this.pathList = intent.getExtras().getStringArrayList(PickImageActivity.KEY_DATA_RESULT);
            if (this.pathList != null && !this.pathList.isEmpty()) {
                for (int i = 0; i < this.pathList.size(); i++) {
                    this.files.add(new File((String) this.pathList.get(i)));
                }
            }
        }
    }
}
