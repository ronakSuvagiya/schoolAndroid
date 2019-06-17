package com.apps.smartschoolmanagement.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.apps.smartschoolmanagement.Comman.comman;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.UserStaticData;

import java.util.HashMap;

public class StartActivity extends AppCompatActivity {
    EditText reg_code;
    Button btn_reg_code;
    public HashMap<String, Integer> imageIds = new HashMap();

    String input;

     String user_type = null;
    SharedPreferences sp;
    String[] parentTitles = new String[]{"Assignments", "Marks", "My Attendance", "Exam Schedule", "Online Material", "Library", "Fee Details", "Remarks", "My Health Status", "Photo Gallery", "Take Appointment", "My Leaves", "Staff Profile", "Principal Statement"};
    String[] staffTitles = new String[]{"Post Assignment", "Post Material", "Post Remarks", "Attendance", "Student Profile", "Student's Mark List", "Track Bus", "Library", "Health Status", "Photo Gallery", "Leave Management", "Take Appointment"};
    String[] titles = null;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_type);
        this.imageIds.put("Assignments", Integer.valueOf(R.drawable.img_assignment));
        this.imageIds.put("Post Assignment", Integer.valueOf(R.drawable.img_assignment));
        this.imageIds.put("Post Remarks", Integer.valueOf(R.drawable.img_remarks));
        this.imageIds.put("Remarks", Integer.valueOf(R.drawable.img_remarks));
        this.imageIds.put("Add Photo Gallery", Integer.valueOf(R.drawable.img_add_gallery));
        this.imageIds.put("Photo Gallery", Integer.valueOf(R.drawable.img_photo_gallery));
        this.imageIds.put("Post Material", Integer.valueOf(R.drawable.img_online_material));
        this.imageIds.put("Online Material", Integer.valueOf(R.drawable.img_online_material));
        this.imageIds.put("Take Appointment", Integer.valueOf(R.drawable.img_appointment));
        this.imageIds.put("Appointments", Integer.valueOf(R.drawable.img_appointment));
        this.imageIds.put("My Health Status", Integer.valueOf(R.drawable.img_health_status));
        this.imageIds.put("Health Status", Integer.valueOf(R.drawable.img_health_status));
        this.imageIds.put("Student's Health Status", Integer.valueOf(R.drawable.img_health_status));
        this.imageIds.put("Track Bus", Integer.valueOf(R.drawable.img_bus));
        this.imageIds.put("Principal Statement", Integer.valueOf(R.drawable.img_principal_message));
        this.imageIds.put("Staff Attendance", Integer.valueOf(R.drawable.img_attendance));
        this.imageIds.put("Attendance", Integer.valueOf(R.drawable.img_attendance));
        this.imageIds.put("My Attendance", Integer.valueOf(R.drawable.img_attendance));
        this.imageIds.put("Fee Details", Integer.valueOf(R.drawable.img_fee));
        this.imageIds.put("Student's Fee Details", Integer.valueOf(R.drawable.img_fee));
        this.imageIds.put("Exam Schedule", Integer.valueOf(R.drawable.img_exam_schedule));
        this.imageIds.put("Marks", Integer.valueOf(R.drawable.img_marks_list));
        this.imageIds.put("Student's Mark List", Integer.valueOf(R.drawable.img_marks_list));
        this.imageIds.put("Staff Payrolls", Integer.valueOf(R.drawable.img_pay));
        this.imageIds.put("Student Profile", Integer.valueOf(R.drawable.img_student_profile));
        this.imageIds.put("Staff Profile", Integer.valueOf(R.drawable.img_staff_profile));
        this.imageIds.put("Profit & Loss", Integer.valueOf(R.drawable.img_profit_loss));
        this.imageIds.put("My Leaves", Integer.valueOf(R.drawable.img_leave));
        this.imageIds.put("Leaves", Integer.valueOf(R.drawable.img_leave));
        this.imageIds.put("Library", Integer.valueOf(R.drawable.ic_books));
        this.imageIds.put("Leave Management", Integer.valueOf(R.drawable.img_leave));
        UserStaticData.imageIds = this.imageIds;
        reg_code = findViewById(R.id.reg_code);
        btn_reg_code = findViewById(R.id.btn_reg_code);

        sp =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        user_type = sp.getString("usertype", null);
//        user_type =  String.valueOf(sp.contains("usertype"));
        Log.e("usertype", ">>" + user_type);

        if (user_type != null) {
            if ("student".equals(user_type)) {
                this.titles = this.parentTitles;
            } else if ("teacher".equals(user_type)) {
                this.titles = this.staffTitles;
            }
        }

            if ((sp.contains("user_name") && sp.contains("password"))) {
                Intent intent = new Intent(StartActivity.this, HomeActivity.class);
                intent.putExtra("titles",titles);
                startActivity(intent);
                finish();
            }

            btn_reg_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    input = reg_code.getText().toString();
                    if (reg_code.getText().toString().length() == 0) {
                        reg_code.setError("Enter Registration Code");
                    } else if (!(comman.P_REG_CODE.matches(input)) && !(comman.T_REG_CODE.matches(input))) {
                        reg_code.setError("Enter Valid Registration Code");
                    } else if (comman.P_REG_CODE.matches(input)) {
                        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                        intent.putExtra("user_type", "student");
                        UserStaticData.user_type = 0;
                        UserStaticData.login_type = "student";
                        startActivity(intent);
                        finish();
                    } else if (comman.T_REG_CODE.matches(input)) {
                        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                        intent.putExtra("user_type", "teacher");
                        UserStaticData.user_type = 1;
                        UserStaticData.login_type = "teacher";
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

//
//    public void onClick(View view) {
//        Intent intent = new Intent(this, LoginActivity.class);
//        switch (view.getId()) {
//            case R.id.btn_manager:
//                intent.putExtra("user_type", "manager");
//                UserStaticData.user_type = 2;
//                UserStaticData.login_type = "admin";
//                startActivity(intent);
//                return;
//            case R.id.btn_student_parent:
//                intent.putExtra("user_type", "student");
//                UserStaticData.user_type = 0;
//                UserStaticData.login_type = "student";
//                startActivity(intent);
//                return;
//            case R.id.btn_teacher:
//                intent.putExtra("user_type", "teacher");
//                UserStaticData.user_type = 1;
//                UserStaticData.login_type = "teacher";
//                startActivity(intent);
//                return;
//            default:
//                return;
//        }
//    }
//}
