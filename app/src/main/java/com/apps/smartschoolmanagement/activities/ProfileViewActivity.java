package com.apps.smartschoolmanagement.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.AppSingleton;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileViewActivity extends JsonClass {
    ImageView profilePic;
    LinearLayout root;
    int student_id;
    String ids;
    TextView email,name,className,div_name,phone,address,gender,birthday;

    /* renamed from: com.apps.smartschoolmanagement.activities.ProfileViewActivity$1 */
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile_view);
        this.root = (LinearLayout) findViewById(R.id.root_layout);
        this.root.setVisibility(0);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        className = findViewById(R.id.className);
        div_name = findViewById(R.id.div_name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.gender);
        birthday = findViewById(R.id.birthday);
        this.profilePic = (ImageView) findViewById(R.id.file_path);
//        findViewById(R.id.btn_edit).setVisibility(8);
        if (getIntent().getStringExtra("teacherid") != null) {
            this.params.put("teacher_id", getIntent().getStringExtra("teacherid"));
            findViewById(R.id.layout_subject).setVisibility(0);
//            student_id = getIntent().getIntExtra("studentid",0);
//            ids = String.valueOf(student_id);
//            Log.e("student", "idss" + ids);
        } else if (getIntent().getStringExtra("studentid") != null) {
            this.params.put("student_id", getIntent().getStringExtra("studentid"));
            student_id = Integer.parseInt(getIntent().getStringExtra("studentid"));
            Log.e("url",URLs.getStudent+ student_id);
            getJsonResponse(URLs.getStudent + student_id, ProfileViewActivity.this, new ProfileViewActivity.getStudentApi());

//            findViewById(R.id.layout_joining_date).setVisibility(8);
//            findViewById(R.id.layout_experience).setVisibility(8);
//            findViewById(R.id.layout_ctc).setVisibility(8);

        }
//        if (UserStaticData.user_type == 0) {
//            findViewById(R.id.layout_ctc).setVisibility(8);
//        }
    }

    class getStudentApi implements VolleyCallbackJSONObject {
        @Override
        public void onSuccess(JSONObject jsonArray) {
                Log.e("student ", "data_student" + jsonArray.toString());
//        email.setText(jsonArray.getString(""));
            try {
                name.setText(jsonArray.getString("name"));
                phone.setText(jsonArray.getString("mobileNo"));
                address.setText(jsonArray.getString("address"));
                gender.setText(jsonArray.getString("gender"));
                birthday.setText(jsonArray.getString("dob"));

                JSONObject jsonObject = jsonArray.getJSONObject("divId");

                div_name.setText(jsonObject.getString("name"));

                JSONObject jsonObject2 = jsonObject.getJSONObject("stdId");

                className.setText(jsonObject2.getString("stdName"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void processJsonResponse(String response) {
        if (response != null && !response.equalsIgnoreCase("")) {
            this.responseParams.clear();
            try {
                JSONObject jsonObject1 = new JSONObject(response);
                JSONArray jsonArray = jsonObject1.getJSONArray("staffprofile");
                Iterator<String> keysIterator = jsonArray.getJSONObject(0).keys();
                while (keysIterator.hasNext()) {
                    String keyStr = (String) keysIterator.next();
                    this.responseParams.put(keyStr, jsonArray.getJSONObject(0).getString(keyStr));
                }
                JSONArray jsonArray1 = jsonObject1.getJSONArray("attendance");
                keysIterator = jsonArray1.getJSONObject(0).keys();
                while (keysIterator.hasNext()) {
                    if ("Absent".equals(jsonArray1.getJSONObject(0).getString((String) keysIterator.next()))) {
                        findViewById(R.id.attendance_present).setVisibility(8);
                        findViewById(R.id.attendance_absent).setVisibility(0);
                    } else {
                        findViewById(R.id.attendance_present).setVisibility(0);
                        findViewById(R.id.attendance_absent).setVisibility(8);
                    }
                }
                if (jsonArray1.length() < 1) {
                    findViewById(R.id.attendance_present).setVisibility(8);
                    findViewById(R.id.attendance_absent).setVisibility(8);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loadData(this.root, this.responseParams);
        }
    }
}
