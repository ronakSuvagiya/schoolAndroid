package com.apps.smartschoolmanagement.activities;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.permissions.Permissions;
import com.google.common.net.HttpHeaders;
import com.apps.smartschoolmanagement.utils.BaseFinishActivity;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddPhotoGalleryActivity extends BaseFinishActivity implements OnClickListener {
    private ProgressDialog dialog = null;
    ArrayList<String> encodedImageList;
    String imageURI;
    ArrayList<Uri> imagesUriList;
    private JSONObject jsonObject;
    private Button selectImages;
    private EditText title;
    private Button uploadImages;

    /* renamed from: com.apps.smartschoolmanagement.activities.AddPhotoGalleryActivity$1 */
    class C11861 extends com.apps.smartschoolmanagement.permissions.PermissionHandler {
        C11861() {
        }

        public void onGranted() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
            intent.setAction("android.intent.action.GET_CONTENT");
            AddPhotoGalleryActivity.this.startActivityForResult(Intent.createChooser(intent, "Choose application"), 100);
        }

        public void onDenied(Context context, ArrayList<String> arrayList) {
            Toast.makeText(AddPhotoGalleryActivity.this, "You have to allow Storage Permissions to use this service", 0).show();
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.AddPhotoGalleryActivity$2 */
    class C11872 implements Listener<JSONObject> {
        C11872() {
        }

        public void onResponse(JSONObject jsonObject) {
            Log.e("Message from server", jsonObject.toString());
            AddPhotoGalleryActivity.this.dialog.dismiss();
            Toast.makeText(AddPhotoGalleryActivity.this.getApplication(), "Images Uploaded Successfully", 0).show();
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.AddPhotoGalleryActivity$3 */
    class C11883 implements ErrorListener {
        C11883() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            Log.e("Message from server", volleyError.toString());
            Toast.makeText(AddPhotoGalleryActivity.this.getApplication(), "Error Occurred", 0).show();
            AddPhotoGalleryActivity.this.dialog.dismiss();
        }
    }

    public class Utils {
        public static final int REQCODE = 100;
        public static final String imageList = "imageList";
        public static final String imageName = "name";
        public static final String urlUpload = "http://sachinverma.co.in/services/upload_multiple_images/upload_images.php";
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery_add);
        setTitle("Add Photo Gallery");
        this.title = (EditText) findViewById(R.id.edt_title);
        this.selectImages = (Button) findViewById(R.id.btn_select_images);
        this.uploadImages = (Button) findViewById(R.id.btn_upload);
        this.uploadImages.setOnClickListener(this);
        this.selectImages.setOnClickListener(this);
        this.dialog = new ProgressDialog(this);
        this.dialog.setMessage("Uploading Image...");
        this.dialog.setCancelable(true);
        this.jsonObject = new JSONObject();
        this.encodedImageList = new ArrayList();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_images:
                Permissions.check(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, "Storage Permission is required to use this service", new Permissions.Options().setSettingsDialogTitle("Warning!").setRationaleDialogTitle(HttpHeaders.WARNING), new C11861());
                return;
            case R.id.btn_upload:
                this.dialog.show();
                JSONArray jsonArray = new JSONArray();
                if (this.encodedImageList.isEmpty()) {
                    Toast.makeText(this, "Please select some images first.", 0).show();
                    return;
                }
                Iterator it = this.encodedImageList.iterator();
                while (it.hasNext()) {
                    jsonArray.put((String) it.next());
                }
                try {
                    this.jsonObject.put(Utils.imageName, this.title.getText().toString().trim());
                    this.jsonObject.put(Utils.imageList, jsonArray);
                } catch (JSONException e) {
                    Log.e("JSONObject Here", e.toString());
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(1, Utils.urlUpload, this.jsonObject, new C11872(), new C11883());
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(6000000, 1, 1.0f));
                Volley.newRequestQueue(this).add(jsonObjectRequest);
                return;
            default:
                return;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == -1 && data != null) {
            try {
                String[] filePathColumn = new String[]{"_data"};
                this.imagesUriList = new ArrayList();
                this.encodedImageList.clear();
                Cursor cursor;
                if (data.getData() != null) {
                    cursor = getContentResolver().query(data.getData(), filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    this.imageURI = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
                    cursor.close();
                } else if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        Uri uri = mClipData.getItemAt(i).getUri();
                        mArrayUri.add(uri);
                        cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        this.imageURI = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
                        Bitmap bitmap = Media.getBitmap(getContentResolver(), uri);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(CompressFormat.JPEG, 100, byteArrayOutputStream);
                        this.encodedImageList.add(Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0));
                        cursor.close();
                    }
                    Toast.makeText(this, "Selected Images are : " + mArrayUri.size(), 1).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", 1).show();
            }
        } else {
            Toast.makeText(this, "You haven't picked Image", 1).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBackPressed() {
        if (this.dialog.isShowing()) {
            this.dialog.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
