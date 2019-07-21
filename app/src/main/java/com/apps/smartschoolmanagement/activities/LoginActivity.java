package com.apps.smartschoolmanagement.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.Comman.URL;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.basehelpers.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {
    public HashMap<String, Integer> imageIds = new HashMap();
    //    String[] managerTitles = new String[]{"Staff Attendance", "Staff Payrolls", "Staff Profile", "Student's Fee Details", "Student Profile", "Appointments", "Add Photo Gallery", "Photo Gallery", "Track Bus", "My Health Status", "Profit & Loss", "Leaves"};
    String[] parentTitles = new String[]{"Assignments", "Marks", "My Attendance", "Exam Schedule", "Online Material", "Library", "Fee Details", "Remarks", "My Health Status", "Photo Gallery", "Take Appointment", "My Leaves", "Staff Profile", "Principal Statement"};
    EditText password;
    String[] staffTitles = new String[]{"Post Assignment", "Post Material", "Notification", "Attendance", "Student Profile", "Student's Mark List", "Schedule", "Exam Schedule", "Complaint", "Add Photo Gallery", "Event List", "Holiday List"};
    String[] titles = null;
    EditText userNames;
    static String user_type = null;
    SharedPreferences sp;

//    protected void onResume() {
//        super.onResume();
//        if (this.password != null && this.userName != null) {
//            this.password.setText("1234");
//            if (UserStaticData.user_type == 0) {
//                this.userName.setText("saikiran@findlogics.in");
//            }
//            if (UserStaticData.user_type == 1) {
//                this.userName.setText("kranthi@findlogics.in");
//            }
//            if (UserStaticData.user_type == 2) {
//                this.userName.setText("nagaraju@findlogics.in");
//            }
//        }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        this.imageIds.put("Assignments", Integer.valueOf(R.drawable.img_assignment));  // selected
        this.imageIds.put("Post Assignment", Integer.valueOf(R.drawable.img_assignment));
        this.imageIds.put("Notification", Integer.valueOf(R.drawable.img_remarks));
        this.imageIds.put("Remarks", Integer.valueOf(R.drawable.img_remarks));
        this.imageIds.put("Add Photo Gallery", Integer.valueOf(R.drawable.img_add_gallery));
        this.imageIds.put("Photo Gallery", Integer.valueOf(R.drawable.img_photo_gallery));// selected
        this.imageIds.put("Post Material", Integer.valueOf(R.drawable.img_online_material));// selected
        this.imageIds.put("Online Material", Integer.valueOf(R.drawable.img_online_material));
        this.imageIds.put("Schedule", Integer.valueOf(R.drawable.img_exam_schedule));// Take Appointment
        this.imageIds.put("Event List", Integer.valueOf(R.drawable.img_appointment));//appointment
        this.imageIds.put("My Health Status", Integer.valueOf(R.drawable.img_health_status));
        this.imageIds.put("Holiday List", Integer.valueOf(R.drawable.img_health_status));
        this.imageIds.put("Student's Health Status", Integer.valueOf(R.drawable.img_health_status));
        this.imageIds.put("Track Bus", Integer.valueOf(R.drawable.img_bus));
        this.imageIds.put("Principal Statement", Integer.valueOf(R.drawable.img_principal_message));
        this.imageIds.put("Staff Attendance", Integer.valueOf(R.drawable.img_attendance));
        this.imageIds.put("Attendance", Integer.valueOf(R.drawable.img_attendance)); // selected
        this.imageIds.put("My Attendance", Integer.valueOf(R.drawable.img_attendance));
        this.imageIds.put("Fee Details", Integer.valueOf(R.drawable.img_fee));
        this.imageIds.put("Student's Fee Details", Integer.valueOf(R.drawable.img_fee));
        this.imageIds.put("Exam Schedule", Integer.valueOf(R.drawable.img_exam_schedule));// selected
        this.imageIds.put("Marks", Integer.valueOf(R.drawable.img_marks_list));
        this.imageIds.put("Student's Mark List", Integer.valueOf(R.drawable.img_marks_list));// selected
        this.imageIds.put("Staff Payrolls", Integer.valueOf(R.drawable.img_pay));
        this.imageIds.put("Student Profile", Integer.valueOf(R.drawable.img_student_profile));// selected
        this.imageIds.put("Staff Profile", Integer.valueOf(R.drawable.img_staff_profile));
        this.imageIds.put("Profit & Loss", Integer.valueOf(R.drawable.img_profit_loss));
        this.imageIds.put("My Leaves", Integer.valueOf(R.drawable.img_leave));
        this.imageIds.put("Leaves", Integer.valueOf(R.drawable.img_leave));
        this.imageIds.put("Library", Integer.valueOf(R.drawable.ic_books));
        this.imageIds.put("Complaint", Integer.valueOf(R.drawable.img_leave));// Leave management
        UserStaticData.imageIds = this.imageIds;
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.user_type = getIntent().getStringExtra("user_type");
        SharedPreferences.Editor e = sp.edit();
        e.putString("usertype", this.user_type);
        e.commit();

        if (this.user_type != null) {
            if ("student".equals(this.user_type)) {
                this.titles = this.parentTitles;
            } else if ("teacher".equals(this.user_type)) {
                this.titles = this.staffTitles;
            }
//            else if ("manager".equals(this.user_type)) {
//                this.titles = this.managerTitles;
//            }
        }
        this.userNames = findViewById(R.id.username);
        this.password = findViewById(R.id.password);
        findViewById(R.id.btn_signin).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    if (LoginActivity.user_type != null) {
                        if ("student".equals(LoginActivity.user_type)) {
                            userLogin();
                        } else if ("teacher".equals(LoginActivity.user_type)) {
                            Logininit();
                        }
                    }
                }

            }
        });
    }

    private  void userLogin(){
        String userNamess = userNames.getText().toString().trim();
        String passss = password.getText().toString().trim();
        try {
            if (findViewById(R.id.layout_loading) != null) {
                findViewById(R.id.layout_loading).setVisibility(0);
            }
            RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("userName", userNamess);
            jsonBody.put("pass", passss);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL.Prent_Login_URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String username = String.valueOf(response.get("userName"));
                        String password = String.valueOf(response.get("password"));
                        String school_id = String.valueOf(response.get("school"));
                        String name = String.valueOf(response.get("name"));
                        String lastName = String.valueOf(response.get("lastName"));
                        String mobileNo = String.valueOf(response.get("mobileNo"));
                        String email = String.valueOf(response.get("emailId"));
                        String dob = String.valueOf(response.get("dob"));
                        String gender = String.valueOf(response.get("gender"));
                        JSONObject jsonObject = new JSONObject(school_id);
                        String Schoolid = String.valueOf(jsonObject.get("id"));
                        String stu = String.valueOf(response.get("stu"));
                        JSONObject student = new JSONObject(stu);
                        String studentID = String.valueOf(student.get("id"));
                        String divID = String.valueOf(student.get("divId"));
                        JSONObject Div = new JSONObject(divID);
                        String DivID1 = String.valueOf(Div.get("id"));
                        String stdA = String.valueOf(Div.get("stdId"));
                        JSONObject stdobj = new JSONObject(stdA);
                        String stdID = String.valueOf(stdobj.get("id"));
                        SharedPreferences.Editor e = sp.edit();
                        e.putString("user_name", username);
                        e.putString("password", password);
                        e.putString("schoolid", Schoolid);
                        e.putString("name",  name);
                        e.putString("lastName",lastName);
                        e.putString("mobileNo",mobileNo);
                        e.putString("emails", email);
                        e.putString("dob", dob);
                        e.putString("gender", gender);
                        e.putString("studentId", studentID);
                        e.putString("stdId",stdID);
                        e.putString("DivId",DivID1);
                        e.commit();
                        if (LoginActivity.this.findViewById(R.id.layout_loading) != null) {
                            LoginActivity.this.findViewById(R.id.layout_loading).setVisibility(8);
                        }
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("titles", titles);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Somethings is wrong", Toast.LENGTH_LONG).show();
                    }

                    finish();
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (LoginActivity.this.findViewById(R.id.layout_loading) != null) {
                                LoginActivity.this.findViewById(R.id.layout_loading).setVisibility(8);
                            }
                            Toast.makeText(LoginActivity.this, "Invalid Username and Password", Toast.LENGTH_LONG).show();
                        }
                    });
            MyRequestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Logininit() {
        String userNamess = userNames.getText().toString().trim();
        String passss = password.getText().toString().trim();
        try {
            if (findViewById(R.id.layout_loading) != null) {
                findViewById(R.id.layout_loading).setVisibility(0);
            }
            RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("userName", userNamess);
            jsonBody.put("pass", passss);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL.Login_URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String username = String.valueOf(response.get("userName"));
                        String password = String.valueOf(response.get("password"));
                        String school_id = String.valueOf(response.get("schoolId"));
                        String name = String.valueOf(response.get("name"));
                        String lastName = String.valueOf(response.get("lastName"));
                        String mobileNo = String.valueOf(response.get("mobileNo"));
                        String email = String.valueOf(response.get("emailId"));
                        String dob = String.valueOf(response.get("dob"));
                        String gender = String.valueOf(response.get("gender"));
                        int teachermaster = (int) response.get("id");
                        JSONObject jsonObject = new JSONObject(school_id);
                        String Schoolid = String.valueOf(jsonObject.get("id"));
                        SharedPreferences.Editor e = sp.edit();
                        e.putString("user_name", username);
                        e.putString("password", password);
                        e.putString("schoolid", Schoolid);
                        e.putString("NAme", name);
                        e.putString("EMail", email);
                        e.putString("LAstName",lastName);
                        e.putString("MObileNo",mobileNo);
                        e.putString("DOb", dob);
                        e.putString("GEnder", gender);
                        e.putInt("teachermaster", teachermaster);
                        Log.e("dob",dob);
                        e.commit();

                        if (LoginActivity.this.findViewById(R.id.layout_loading) != null) {
                            LoginActivity.this.findViewById(R.id.layout_loading).setVisibility(8);
                        }
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("titles", titles);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Somethings is wrong", Toast.LENGTH_LONG).show();
                    }

                    finish();
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (LoginActivity.this.findViewById(R.id.layout_loading) != null) {
                                LoginActivity.this.findViewById(R.id.layout_loading).setVisibility(8);
                            }
                            Toast.makeText(LoginActivity.this, "Invalid Username and Password", Toast.LENGTH_LONG).show();
                        }
                    });
            MyRequestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//
//
//        api.savePost(userName, pass).enqueue(new Callback<loginModel>() {
//            @Override
//            public void onResponse(Call<loginModel> call, Response<loginModel> response) {
////                Log.e("response", ">>" + response.body().getUserName());
//                if (response.isSuccessful()) {
//                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                    intent.putExtra("titles", titles);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<loginModel> call, Throwable t) {
//
//                Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_LONG).show();
////
//            }
//        });
//    }
//    public void processJsonResponse(String response) {
//        String error = null;
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            if (!jsonObject.isNull("error")) {
//                error = jsonObject.getString("error");
//            }
//            if (TextUtils.isEmpty(error)) {x
//                JSONObject object = jsonObject.getJSONObject("ResponseList");
//                Iterator<String> keysIterator = object.keys();
//                while (keysIterator.hasNext()) {
//                    String keyStr = (String) keysIterator.next();
//                    if (isJsonObject(keyStr)) {
//                        processJsonResponse(keyStr);
//                    } else {
//                        ProfileInfo.getInstance().getLoginData().put(keyStr, object.getString(keyStr));
//                    }
//                }
//                this.responseParams.putAll(ProfileInfo.getInstance().getLoginData());
//                Intent intent = new Intent(this, HomeActivity.class);
//                intent.putExtra("titles", this.titles);
//                startActivity(intent);
//                return;
//            }
//            Toast.makeText(this, error, 0).show();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
