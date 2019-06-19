package com.apps.smartschoolmanagement.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.AddPhotoGalleryActivity.Utils;
import com.apps.smartschoolmanagement.adapters.SpinnerrAdapter;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.CodeValue;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.JsonClass.VolleyCallback;
import com.apps.smartschoolmanagement.utils.OnClickDateListener;
import com.apps.smartschoolmanagement.utils.OnClickTimeListener;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppointmentsActivity extends JsonClass {
    EditText date;
    Spinner delegates;
    String teacherid = null;
    Spinner teachers;
    EditText time;
    ArrayList<CodeValue> values = new ArrayList();

    /* renamed from: com.apps.smartschoolmanagement.activities.AppointmentsActivity$1 */
    class C11891 implements VolleyCallback {
        C11891() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                AppointmentsActivity.this.teacherid = result;
                AppointmentsActivity.this.params.put("delegate", AppointmentsActivity.this.teacherid);
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.AppointmentsActivity$2 */
    class C11912 implements OnClickListener {

        /* renamed from: com.apps.smartschoolmanagement.activities.AppointmentsActivity$2$1 */
        class C11901 implements VolleyCallback {
            C11901() {
            }

            public void onSuccess(String result) {
                Toast.makeText(AppointmentsActivity.this, "Appointment Registered", 0).show();
                AppointmentsActivity.this.finish();
            }
        }

        C11912() {
        }

        public void onClick(View view) {
            if (AppointmentsActivity.this.date.getText().toString().length() <= 5 || AppointmentsActivity.this.time.getText().toString().length() <= 4) {
                Toast.makeText(AppointmentsActivity.this, "Please choose Date & Time of Appointment", 0).show();
                return;
            }
            AppointmentsActivity.this.params.clear();
            if (UserStaticData.user_type == 0) {
                AppointmentsActivity.this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
            } else if (UserStaticData.user_type == 1) {
                AppointmentsActivity.this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
            }
            AppointmentsActivity.this.params.put("dateofappinment", AppointmentsActivity.this.date.getText().toString());
            AppointmentsActivity.this.params.put("time", AppointmentsActivity.this.time.getText().toString());
          //  AppointmentsActivity.this.getJsonResponse(URLs.appointment, AppointmentsActivity.this, new C11901());
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_appointment);
        setTitle("Schedule Appointment");
        KProgressHUD progressHUD = KProgressHUD.create(this);
        this.date = (EditText) findViewById(R.id.date);
        this.date.setOnClickListener(new OnClickDateListener(this.date, this, "past"));
        this.time = (EditText) findViewById(R.id.time);
        this.time.setOnClickListener(new OnClickTimeListener(this.time, this));
        if (UserStaticData.user_type == 0) {
            this.teachers = (Spinner) findViewById(R.id.spnr_teacher);
//            getSpinnerData(this, URLs.all_teacher_codes, this.teachers, new C11891());
        } else {
            findViewById(R.id.layout_delegate).setVisibility(8);
            this.params.put("delegate", "Principal");
        }
        findViewById(R.id.btn_schedule).setBackgroundResource(R.drawable.student_button);
        findViewById(R.id.btn_schedule).setOnClickListener(new C11912());
    }

    public void processJSONResult(JSONObject jsonObject) {
        try {
            CodeValue c = new CodeValue();
            c.setCodeValue(jsonObject.getString("title"));
            this.values.add(c);
            JSONArray jsonArray = jsonObject.getJSONArray("ResponseList");
            for (int i = 0; i < jsonArray.length(); i++) {
                String id = jsonArray.getJSONObject(i).optString("id").toString();
                String value = jsonArray.getJSONObject(i).optString(Utils.imageName).toString();
                CodeValue code = new CodeValue();
                code.setCodeID(id);
                code.setCodeValue(value);
                this.values.add(code);
            }
//            this.delegates.setAdapter(new SpinnerrAdapter((Context) this, 17367049, this.values));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
