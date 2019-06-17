package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.ListViewAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExamScheduleActivity extends JsonClass {
    ListView listView;
    Spinner month;
    KProgressHUD progressHUD;

    /* renamed from: com.apps.smartschoolmanagement.activities.ExamScheduleActivity$1 */
    class C12041 implements VolleyCallback {
        C12041() {
        }

        public void onSuccess(String result) {
            try {
                ExamScheduleActivity.this.processJSONResult(new JSONObject(result));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.ExamScheduleActivity$2 */
    class C12062 implements OnItemSelectedListener {

        /* renamed from: com.apps.smartschoolmanagement.activities.ExamScheduleActivity$2$1 */
        class C12051 implements VolleyCallback {
            C12051() {
            }

            public void onSuccess(String result) {
                try {
                    ExamScheduleActivity.this.processJSONResult(new JSONObject(result));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        C12062() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i > 0) {
                ExamScheduleActivity.this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
                ExamScheduleActivity.this.params.put("month", "" + ExamScheduleActivity.this.month.getSelectedItemPosition());
                ExamScheduleActivity.this.getJsonResponse(URLs.examSchedule, ExamScheduleActivity.this, new C12051());
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);
        setTitle("Exams Schedule");
        this.progressHUD = KProgressHUD.create(this).setLabel("Loading");
        if (UserStaticData.user_type == 0) {
            findViewById(R.id.layout_selection).setVisibility(8);
        }
        this.listView = (ListView) findViewById(R.id.listview_exams);
        this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        getJsonResponse(URLs.examSchedule, this, new C12041());
        this.month = (Spinner) findViewById(R.id.spnr_month);
        this.month.setOnItemSelectedListener(new C12062());
    }

    public void processJSONResult(JSONObject jsonObject) {
        try {
            this.values.clear();
            JSONArray jsonArray = jsonObject.getJSONArray("Response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                ListData listData = new ListData();
                if (!jsonObject1.isNull("exam_details_id")) {
                    listData.setExam_id(jsonObject1.getString("exam_details_id"));
                }
                if (!jsonObject1.isNull("examType")) {
                    listData.setExam_name(jsonObject1.getString("examType"));
                }
                if (!jsonObject1.isNull("date")) {
                    listData.setExam_date(jsonObject1.getString("date") + " " + jsonObject1.getString("time"));
                }
                if (!jsonObject1.isNull("subjectName")) {
                    listData.setExam_comment(jsonObject1.getString("subjectName"));
                }
                this.values.add(listData);
            }
            if (jsonArray.length() < 1) {
                findViewById(R.id.error).setVisibility(0);
            } else {
                findViewById(R.id.error).setVisibility(8);
            }
            this.listView.setAdapter(new ListViewAdapter(this, R.layout.item_layout_exams, this.values));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
