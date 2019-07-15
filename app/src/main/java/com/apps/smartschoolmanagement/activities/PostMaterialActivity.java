package com.apps.smartschoolmanagement.activities;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.permissions.PermissionHandler;
import com.apps.smartschoolmanagement.permissions.Permissions;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.basehelpers.VolleyMultipartRequest;
import com.google.common.net.HttpHeaders;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.SpinnerHelper;
import com.apps.smartschoolmanagement.utils.URLs;
import com.apps.smartschoolmanagement.utils.filechooser.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.apps.smartschoolmanagement.activities.StudentProfileActivity.stdid;

public class PostMaterialActivity extends JsonClass {
    private static final int FILE_SELECT_CODE = 9;
    SpinnerHelper classes;
    Spinner cls;
    ArrayList<String> filepaths = new ArrayList();
    public List<File> files = new ArrayList();
    Spinner subj;
    SpinnerHelper subject;
    SharedPreferences sp;
    List<String> stdname = new ArrayList<>();
    List<Integer> stdId = new ArrayList<>();
    List<String> subName = new ArrayList<>();
    List<Integer> subID = new ArrayList<>();
    int subjects;
    String channel;
    String urls;
    String file;

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
            urls = URLs.addMaterial + subjects + "&std=" + stdid + "&schoolId=" + channel;
                    Uri image = Uri.fromFile(files.get(0));
            Bitmap bitmap = (Bitmap) BitmapFactory.decodeFile(String.valueOf(image));
            uploadFile(bitmap);
        }
//            Toast.makeText(PostMaterialActivity.this, "Please Select Subject", 0).show();
        }


    private void uploadFile(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.addMaterial + subjects + "&std=" + stdid + "&schoolId=" + channel,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sub", String.valueOf(subjects));
                params.put("std", String.valueOf(stdid));
                params.put("schoolId", channel);
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("file", new DataPart(imagename + ".pdf", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsFromBuffer(byteBuffer);
        return byteBuffer.array();
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

    class getStdssApi implements VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    stdname.add(obj.getString("stdName"));
                    stdId.add(obj.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PostMaterialActivity.this, R.layout.spinner_dropdown_custom, stdname);
            cls.setAdapter(spinnerArrayAdapter);
            Log.e("stdData", jsonArray.toString());
        }
    }

    class getSubjectApi implements VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    subName.add(obj.getString("name"));
                    subID.add(obj.getInt("id"));
                    subjects = obj.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PostMaterialActivity.this, R.layout.spinner_dropdown_custom, subName);
            subj.setAdapter(spinnerArrayAdapter);
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_material);
        setTitle("Post Online Material");
        KProgressHUD progressHUD = KProgressHUD.create(this);
        this.subj = (Spinner) findViewById(R.id.spnr_subject);
        this.cls = (Spinner) findViewById(R.id.spnr_class);
        sp = PreferenceManager.getDefaultSharedPreferences(PostMaterialActivity.this);
        channel = (sp.getString("schoolid", ""));
        getJsonResponse("http://quickedu.co.in/finstdBySchoolId?school=" + channel, getApplicationContext(), new PostMaterialActivity.getStdssApi());
//        this.classes = new SpinnerHelper((Context) this, (int) R.id.spnr_class, URLs.class_codes);
//        this.subject = new SpinnerHelper((Context) this, (int) R.id.spnr_subject, URLs.subject_codes);
        findViewById(R.id.btn_attach).setOnClickListener(new C12561());
        findViewById(R.id.btn_post).setOnClickListener(new C12582());

        this.cls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stdid = stdId.get(i);
                subName.removeAll(subName);
                subID.removeAll(subID);
                getJsonResponse(URLs.getSubject + stdid + "&school=" + channel, PostMaterialActivity.this, new PostMaterialActivity.getSubjectApi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                    if (FileUtils.getPath(this, data.getData()) != null) {
                        this.files.add(new File(FileUtils.getPath(this, data.getData())));
                    } else {
                        Toast.makeText(this, "Please Choose the correct Format for file", 0).show();
                    }
                    findViewById(R.id.remove_attachment).setOnClickListener(new C12593());
                    break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
