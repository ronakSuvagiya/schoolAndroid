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
    LinearLayout root,layout_attd;
    String student_id;
    String divid,schollid;
    String ids;
    String StdId;
    int rollno;
    TextView email,name,names,className,div_name,phone,address,gender,birthday;

    /* renamed from: com.apps.smartschoolmanagement.activities.ProfileViewActivity$1 */
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile_view);
        this.root = (LinearLayout) findViewById(R.id.root_layout);
        this.root.setVisibility(0);
        layout_attd = findViewById(R.id.layout_attd);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        names = findViewById(R.id.names);
        className = findViewById(R.id.className);
        div_name = findViewById(R.id.div_name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.gender);
        birthday = findViewById(R.id.birthday);
        setTitle("Student Profile");
        this.profilePic = (ImageView) findViewById(R.id.file_path);
        if (getIntent().getStringExtra("teacherid") != null) {
            this.params.put("teacher_id", getIntent().getStringExtra("teacherid"));
            findViewById(R.id.layout_subject).setVisibility(0);
        } else if (getIntent().getStringExtra("studentid") != null) {
            this.params.put("student_id", getIntent().getStringExtra("studentid"));
            student_id = getIntent().getStringExtra("studentid");
            schollid = getIntent().getStringExtra("schoolid");
            rollno = getIntent().getIntExtra("rollno",0);
            divid = getIntent().getStringExtra("divid");
            Log.e("url",URLs.getStudent+ student_id);
            layout_attd.setVisibility(View.VISIBLE);
            getJsonResponse(URLs.getStudent + student_id, ProfileViewActivity.this, new ProfileViewActivity.getStudentApi());
            layout_attd.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProfileViewActivity.this,StudentAttendanceActivity.class);
                    intent.putExtra("schoolid",schollid);
                    intent.putExtra("studentid",StdId);
                    intent.putExtra("rollno",rollno);
                    intent.putExtra("divid",divid);
                    startActivity(intent);
                }
            });
        }
    }

    class getparentDetails implements  VolleyCallbackJSONObject{

        @Override
        public void onSuccess(JSONObject jSONObject) {
            Log.e("parent", "data_parent" + jSONObject.toString());
            try {
                phone.setText(jSONObject.getString("mobileNo"));
                email.setText(jSONObject.getString("emialID"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    class getStudentApi implements VolleyCallbackJSONObject {
        @Override
        public void onSuccess(JSONObject jsonArray) {
                Log.e("student ", "data_student" + jsonArray.toString());
//        email.setText(jsonArray.getString(""));
            try {
                getJsonResponse(URLs.getPrentByStudent + jsonArray.getString("id"), ProfileViewActivity.this, new ProfileViewActivity.getparentDetails());
                String fname = jsonArray.getString("name");
                String lname = jsonArray.getString("lastName");
                name.setText(jsonArray.getString("name"));
                names.setText(fname + "  " + lname);
                address.setText(jsonArray.getString("address"));
                gender.setText(jsonArray.getString("gender"));
                birthday.setText(jsonArray.getString("dob"));

                JSONObject jsonObject = jsonArray.getJSONObject("divId");

                div_name.setText(jsonObject.getString("name"));

                JSONObject jsonObject2 = jsonObject.getJSONObject("stdId");
                StdId = jsonObject2.getString("id");
                className.setText(jsonObject2.getString("stdName"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
