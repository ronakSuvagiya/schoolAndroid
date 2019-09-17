package com.apps.smartschoolmanagement.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.preference.PreferenceManager;
import android.util.Log;
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

import java.util.Collections;

public class ExamScheduleActivity extends JsonClass {
    ListView listView;
    KProgressHUD progressHUD;
    SharedPreferences sp;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);
        setTitle("Exams Schedule");
        this.progressHUD = KProgressHUD.create(this).setLabel("Loading");
//        if (UserStaticData.user_type == 0) {
//            findViewById(R.id.layout_selection).setVisibility(8);
//        }
        this.listView = (ListView) findViewById(R.id.listview_exams);
        sp = PreferenceManager.getDefaultSharedPreferences(ExamScheduleActivity.this);
        String channel = (sp.getString("stdId", ""));
        Log.e("student ","iddd"+channel);
        getJsonResponse(URLs.getExamSchedules + channel, ExamScheduleActivity.this, new ExamScheduleActivity.getExamApi());
    }

    class getExamApi implements VolleyCallbackJSONArray {

        @Override
        public void onSuccess(JSONArray jsonArray) {
            values.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    ListData listData = new ListData();
                    JSONObject obj = jsonArray.getJSONObject(i);
                    listData.setExamScheduleID(String.valueOf(obj.getInt("id")));
                    listData.setExam_date(obj.getString("examDate"));
                    listData.setExam_title(obj.getString("title"));
                    listData.setExam_pdf(obj.getString("pdfName"));
                    values.add(listData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (jsonArray.length() < 1) {
                findViewById(R.id.error).setVisibility(0);
            } else {
                findViewById(R.id.error).setVisibility(8);
            }
            ListViewAdapter listViewAdapter = new ListViewAdapter(ExamScheduleActivity.this, R.layout.item_layout_exams, values);
            listView.setAdapter(listViewAdapter);
            Collections.reverse(values);

        }
    }
}
