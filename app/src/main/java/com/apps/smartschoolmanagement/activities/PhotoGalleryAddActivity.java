package com.apps.smartschoolmanagement.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.permissions.PermissionHandler;
import com.apps.smartschoolmanagement.permissions.Permissions;
import com.apps.smartschoolmanagement.utils.basehelpers.VolleyMultipartRequest;
import com.google.common.net.HttpHeaders;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nileshp.multiphotopicker.photopicker.activity.PickImageActivity;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoGalleryAddActivity extends JsonClass implements OnClickListener {
    private ProgressDialog dialog = null;
    public List<File> files = new ArrayList();
    private ArrayList<String> pathList = new ArrayList();
    KProgressHUD progressHUD;
    private Button selectImages;
    private Spinner title;
    private Button uploadImages;
    List<String> titlename = new ArrayList<>();
    List<Integer> titleId = new ArrayList<>();
    SharedPreferences sp;
    String channel;
    static Integer titleid;
    String imagePath;
    Bitmap bitmap;
    String encodedString;
    String urls;
    RelativeLayout layout_loading;

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

    class getImgCatApi implements JsonClass.VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    titlename.add(obj.getString("name"));
                    titleId.add(obj.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PhotoGalleryAddActivity.this, R.layout.spinner_dropdown_custom, titlename);
            title.setAdapter(spinnerArrayAdapter);
            Log.e("titleData", jsonArray.toString());
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery_add);
        setTitle("Add Photo Gallery");

        this.progressHUD = KProgressHUD.create(this).setLabel("Uploading");
        this.title = (Spinner) findViewById(R.id.spnrCat);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        channel = (sp.getString("schoolid", ""));
        getJsonResponse(URLs.getImgCategory + channel, this, new getImgCatApi());
        this.selectImages = (Button) findViewById(R.id.btn_select_images);
        this.uploadImages = (Button) findViewById(R.id.btn_upload);
        layout_loading = findViewById(R.id.layout_loading);

        this.uploadImages.setOnClickListener(this);
        this.selectImages.setOnClickListener(this);
        this.dialog = new ProgressDialog(this);
        this.dialog.setMessage("Uploading Image...");
        this.dialog.setCancelable(true);

        title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                titleid = titleId.get(i);
//                getJsonResponse(URLs.getDiv + stdid + "&school=" + channel, rootView, new PostAssignment.getDivApi());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_images:
                Permissions.check(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, "Storage Permission is required to use this service", new Permissions.Options().setSettingsDialogTitle("Warning!").setRationaleDialogTitle(HttpHeaders.WARNING), new C12531());
                return;
            case R.id.btn_upload:
                this.urls = URLs.addImg + titleid + "&schoolId=" + channel;

                if (this.files.size() > 0) {
                    for(int i=0;i<pathList.size();i++) {
                        Uri image = Uri.fromFile(new File(pathList.get(i)));
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
                            uploadBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
                pathList = intent.getExtras().getStringArrayList(PickImageActivity.KEY_DATA_RESULT);
            if (this.pathList != null && !this.pathList.isEmpty() && pathList.size()<=5) {
                for (int i = 0; i < this.pathList.size(); i++) {
                    this.files.add(new File((String) this.pathList.get(i)));
                    if(pathList.size() == 1)
                    {
                        findViewById(R.id.layout_image1).setVisibility(0);
                    }
                    else if(pathList.size() == 2)
                    {
                        findViewById(R.id.layout_image1).setVisibility(0);
                        findViewById(R.id.layout_image2).setVisibility(0);
                    }

                    else if(pathList.size() == 3)
                    {
                        findViewById(R.id.layout_image1).setVisibility(0);
                        findViewById(R.id.layout_image2).setVisibility(0);
                        findViewById(R.id.layout_image3).setVisibility(0);
                    }

                    else if(pathList.size() == 4)
                    {
                        findViewById(R.id.layout_image1).setVisibility(0);
                        findViewById(R.id.layout_image2).setVisibility(0);
                        findViewById(R.id.layout_image3).setVisibility(0);
                        findViewById(R.id.layout_image4).setVisibility(0);
                    }
                    else if(pathList.size() == 5)
                    {
                        findViewById(R.id.layout_image1).setVisibility(0);
                        findViewById(R.id.layout_image2).setVisibility(0);
                        findViewById(R.id.layout_image3).setVisibility(0);
                        findViewById(R.id.layout_image4).setVisibility(0);
                        findViewById(R.id.layout_image5).setVisibility(0);
                    }
                }
            }
            else {
                Toast.makeText(getApplicationContext(),"Please select less than 5 image at a time",Toast.LENGTH_LONG).show();
            }
//            findViewById(R.id.remove_image5).setOnClickListener(new PhotoGalleryAddActivity.C12593());
        }

    }
    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext
        //our custom volley request
        layout_loading.setVisibility(View.VISIBLE);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.addImg + titleid + "&schoolId=" + channel,
                new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            layout_loading.setVisibility(View.GONE);
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cat", title.getSelectedItem().toString());
                params.put("schoolId", channel);
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("file", new DataPart(imagename + ".jpeg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
//    class C12593 implements OnClickListener {
//        C12593() {
//        }
//
//        public void onClick(View v) {
//            PhotoGalleryAddActivity.this.pathList.clear();
//            PhotoGalleryAddActivity.this.findViewById(R.id.layout_attch_remove).setVisibility(8);
//        }
//    }
}
