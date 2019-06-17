package com.apps.smartschoolmanagement.activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.Comman.URL;
import com.apps.smartschoolmanagement.adapters.SpinnerrAdapter;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.google.gson.JsonArray;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.AddPhotoGalleryActivity.Utils;
import com.apps.smartschoolmanagement.adapters.ListViewAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentProfileActivity extends JsonClass {
    Spinner admisnno;
    Spinner classes;
    String classid = null;
    ListView listView;
    LinearLayout root;
    String studentid = null;
    SharedPreferences sp;


    /* renamed from: com.apps.smartschoolmanagement.activities.StudentProfileActivity$1 */
    class C13081 implements VolleyCallback {

        /* renamed from: com.apps.smartschoolmanagement.activities.StudentProfileActivity$1$1 */
        class C13071 implements VolleyCallback {
            C13071() {
            }

            public void onSuccess(String result) {
                try {
                    StudentProfileActivity.this.processJSONResult(new JSONObject(result));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        C13081() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                StudentProfileActivity.this.classid = result;
            }
            StudentProfileActivity.this.params.put("class_id", StudentProfileActivity.this.classid);
            StudentProfileActivity.this.getJsonResponse(URLs.allstudentslist, StudentProfileActivity.this, new C13071());
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        setTitle("Student Profile");
        KProgressHUD progressHUD = KProgressHUD.create(this);
        this.listView = (ListView) findViewById(R.id.listview);
        findViewById(R.id.layout_spnr_class).setVisibility(0);
        this.classes = (Spinner) findViewById(R.id.spnr_class);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        show_Student_Data();
//        getSpinnerData(this, "http://quickedu.co.in/finBySchoolId?school=1", this.classes, new C13081());
    }

    public void processJSONResult(JSONObject jsonObject) {
        try {
            this.values.clear();
            JSONArray jsonArray = jsonObject.getJSONArray("ResponseList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                ListData listData = new ListData();
                if (!jsonObject1.isNull("id")) {
                    listData.setStudent_id(jsonObject1.getString("id"));
                }
                if (!jsonObject1.isNull(Utils.imageName)) {
                    listData.setStudent_name(jsonObject1.getString(Utils.imageName));
                }
                if (!jsonObject1.isNull("className")) {
                    listData.setStudent_class(jsonObject1.getString("className"));
                }
                if (!jsonObject1.isNull("roll")) {
                    listData.setStudent_roll(jsonObject1.getString("roll"));
                }
                if (!jsonObject1.isNull("file_path")) {
                    listData.setStudent_photo(jsonObject1.getString("file_path"));
                }
                this.values.add(listData);
            }
            this.listView.setAdapter(new ListViewAdapter(this, R.layout.item_layout_profile_student, this.values));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void show_Student_Data() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String school_id = sp.getString("schoolid", null);
// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL.STUDENT_STD + school_id, null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        String Full_Url = URL.STUDENT_STD + school_id;
                        Log.e("Response", response.toString());
                        String stdname = null;
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jresponse = jsonArray.getJSONObject(i);
                                    stdname = String.valueOf(jresponse.get("stdName"));

                                    ArrayList<String> strings = new ArrayList<>();
                                    strings.add(stdname);


                                    SpinnerrAdapter adapter = new SpinnerrAdapter(StudentProfileActivity.this, R.layout.spinner_selected_item, strings) {
                                        public boolean isEnabled(int position) {
                                            if (position == 0) {
                                                return false;
                                            }
                                            return true;
                                        }
                                    };
                                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
                                    classes.setAdapter(adapter);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        );

        queue.add(getRequest);

    }
}
