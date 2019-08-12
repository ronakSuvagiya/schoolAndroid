package com.apps.smartschoolmanagement.activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.apps.smartschoolmanagement.adapters.AttendancesAdapter;
import com.apps.smartschoolmanagement.adapters.SpinnerrAdapter;
import com.apps.smartschoolmanagement.fragments.StudentAttendance;
import com.apps.smartschoolmanagement.utils.JsonFragment;
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
import java.util.List;

public class StudentProfileActivity extends JsonClass {
    Spinner student;
    Spinner classes;
    String classid = null;
    ListView listView;
    LinearLayout root;
    String studentid = null;
    SharedPreferences sp;

    List<String> stdname = new ArrayList<>();
    List<Integer> stdId = new ArrayList<>();
    List<String> divName = new ArrayList<>();
    List<Integer> divId = new ArrayList<>();
    List<String> StudentName = new ArrayList<>();
    List<Integer> StudentRollNo  = new ArrayList<>();

    ArrayList<ListData> listDatas = new ArrayList<>();
    static Integer stdid;

    class getStdApi implements VolleyCallbackJSONArray {
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
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(StudentProfileActivity.this, R.layout.spinner_dropdown_custom, stdname);
            classes.setAdapter(spinnerArrayAdapter);
            Log.e("stdData", jsonArray.toString());
        }
    }

    class getDivApi implements VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    divName.add(obj.getString("name"));
                    divId.add(obj.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(StudentProfileActivity.this, R.layout.spinner_dropdown_custom, divName);
            student.setAdapter(spinnerArrayAdapter);
            Log.e("divData", jsonArray.toString());
        }
    }

    class StudentApiCall implements VolleyCallbackJSONArray {

        @Override
        public void onSuccess(JSONArray jsonArray) {
            listDatas.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    ListData listData = new ListData();
                    JSONObject obj = jsonArray.getJSONObject(i);
                    StudentName.add("" + obj.getInt("rollNo") + ": " + obj.getString("name"));
                    listData.setStudent_name(obj.getString("name"));
                    StudentRollNo.add(obj.getInt("rollNo"));
                    JSONObject jsonObject = obj.getJSONObject("divId");
                    JSONObject school = obj.getJSONObject("school");
                    listData.setStudent_class(jsonObject.getString("name"));
                    listData.setStudent_id(obj.getString("id"));
                    listData.setStudent_roll(obj.getString("rollNo"));
                    listData.setDivid(jsonObject.getString("id"));
                    listData.setSchool(school.getString("id"));
                    Log.e("id","create" + listData.getStudent_id());
                    listDatas.add(listData);
                    Log.e("listdata", jsonArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ListViewAdapter listViewAdapter = new ListViewAdapter(StudentProfileActivity.this, R.layout.item_layout_profile_student, listDatas);
            listView.setAdapter(listViewAdapter);
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        setTitle("Student Profile");
        this.listView = (ListView) findViewById(R.id.listview);
        findViewById(R.id.layout_spnr_class).setVisibility(0);
        this.classes = (Spinner) findViewById(R.id.spnr_class);
        findViewById(R.id.layout_spnr_month).setVisibility(0);
        this.student = (Spinner) findViewById(R.id.spnr_month);
        sp = PreferenceManager.getDefaultSharedPreferences(StudentProfileActivity.this);
        String channel = (sp.getString("schoolid", ""));
        Log.e("schoolIdLog", channel);
        getJsonResponse(URLs.getStd + channel, StudentProfileActivity.this, new StudentProfileActivity.getStdApi());
//        getJsonResponse(URLs.getStudentByStdAndDiv+stdId+"&div="+divId+"&School="+channel ,StudentProfileActivity.this, new StudentProfileActivity.getDivApi());
        classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                divName.removeAll(divName);
                divId.removeAll(divId);
                stdid = stdId.get(i);
                getJsonResponse(URLs.getDiv + stdid, StudentProfileActivity.this, new StudentProfileActivity.getDivApi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StudentName.removeAll(StudentName);
                StudentRollNo.removeAll(StudentRollNo);
                Integer divid = divId.get(i);
                getJsonResponse(URLs.getStudentByStdAndDiv + stdid + "&div=" + divid + "&School=" + channel, StudentProfileActivity.this, new StudentProfileActivity.StudentApiCall());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
