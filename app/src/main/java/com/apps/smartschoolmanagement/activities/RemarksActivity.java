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

public class RemarksActivity extends JsonClass {
    ListView listView;
    Spinner month;

    /* renamed from: com.apps.smartschoolmanagement.activities.RemarksActivity$1 */
    class C12841 implements VolleyCallback {
        C12841() {
        }

        public void onSuccess(String result) {
            try {
                RemarksActivity.this.processJSONResult(new JSONObject(result));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.RemarksActivity$2 */
    class C12862 implements OnItemSelectedListener {

        /* renamed from: com.apps.smartschoolmanagement.activities.RemarksActivity$2$1 */
        class C12851 implements VolleyCallback {
            C12851() {
            }

            public void onSuccess(String result) {
                try {
                    RemarksActivity.this.processJSONResult(new JSONObject(result));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        C12862() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i > 0) {
                RemarksActivity.this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
                RemarksActivity.this.params.put("month", "" + RemarksActivity.this.month.getSelectedItemPosition());
              //  RemarksActivity.this.getJsonResponse(URLs.remarks, RemarksActivity.this, new C12851());
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
        setContentView(R.layout.activity_remarks);
        setTitle("Remarks");
        KProgressHUD progressHUD = KProgressHUD.create(this).setLabel("Loading");
        this.listView = (ListView) findViewById(R.id.listview_remarks);
        this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
      //  getJsonResponse(URLs.remarks, this, new C12841());
        this.month = (Spinner) findViewById(R.id.spnr_month);
        this.month.setOnItemSelectedListener(new C12862());
    }

    public void processJSONResult(JSONObject jsonObject) {
        try {
            this.values.clear();
            JSONArray jsonArray = jsonObject.getJSONArray("Response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                ListData listData = new ListData();
                if (!jsonObject1.isNull("remarks")) {
                    listData.setRemarks(jsonObject1.getString("remarks"));
                }
                if (!jsonObject1.isNull("studentName")) {
                    listData.setRemarks_student(jsonObject1.getString("studentName"));
                }
                if (!jsonObject1.isNull("teacherName")) {
                    listData.setRemarks_teacher(jsonObject1.getString("teacherName"));
                }
                if (!jsonObject1.isNull("created_on")) {
                    listData.setRemarks_date(jsonObject1.getString("created_on"));
                }
                if (!jsonObject1.isNull("type")) {
                    listData.setRemarks_type(jsonObject1.getString("type"));
                }
                this.values.add(listData);
            }
            this.listView.setAdapter(new ListViewAdapter(this, R.layout.item_layout_remarks, this.values));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
