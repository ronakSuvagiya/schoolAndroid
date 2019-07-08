package com.apps.smartschoolmanagement.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.fragments.PostAssignment;
import com.apps.smartschoolmanagement.permissions.PermissionHandler;
import com.apps.smartschoolmanagement.permissions.Permissions;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.VolleyMultipartRequest;
import com.google.common.net.HttpHeaders;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nileshp.multiphotopicker.photopicker.activity.PickImageActivity;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private Spinner title;
    private Button uploadImages;
    List<String> titlename = new ArrayList<>();
    List<Integer> titleId = new ArrayList<>();
    SharedPreferences sp;
    String channel;
    static Integer titleid;
String imagePath;
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
public void  abc() {

}
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLs.addImgCategory, jsonBody, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        String success = String.valueOf(response.get("message"));
//                        Toast.makeText(PhotoGalleryAddCatActivity.this, success, Toast.LENGTH_LONG).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    finish();
//                }
//            },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(PhotoGalleryAddCatActivity.this, "Enter Category", Toast.LENGTH_LONG).show();
//
//                        }
//                    });
//            MyRequestQueue.add(jsonObjectRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_images:
                Permissions.check(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, "Storage Permission is required to use this service", new Permissions.Options().setSettingsDialogTitle("Warning!").setRationaleDialogTitle(HttpHeaders.WARNING), new C12531());
                return;
            case R.id.btn_upload:
              String urls = URLs.addImg + titleid + "&schoolId=" + channel;
                if (this.files.size() > 0) {
                    RequestQueue MyRequestQueue = Volley.newRequestQueue(PhotoGalleryAddActivity.this);
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("file", imagePath);
                        jsonBody.put("cat", title.getSelectedItem().toString());
                        jsonBody.put("schoolId", channel);

                        VolleyMultipartRequest smr = new VolleyMultipartRequest(Request.Method.POST, urls,
                                new Response.Listener<NetworkResponse>() {
                                    @Override
                                    public void onResponse(NetworkResponse response) {
                                        Log.d("Response", String.valueOf(response));
                                        try {
                                            JSONObject jObj = new JSONObject(String.valueOf(response));
                                            String message = jObj.getString("message");
                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                        } catch (JSONException e) {
                                            // JSON error
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(com.android.volley.VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                        MyRequestQueue.add(smr);
                    } catch (JSONException e) {
                        e.printStackTrace();
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
