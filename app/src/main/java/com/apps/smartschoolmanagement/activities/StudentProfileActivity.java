package com.apps.smartschoolmanagement.activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.preference.PreferenceManager;
import android.util.Log;
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
import com.apps.smartschoolmanagement.adapters.SpinnerrAdapter;
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
    Spinner admisnno;
    Spinner classes;
    String classid = null;
    ListView listView;
    LinearLayout root;
    String studentid = null;
    SharedPreferences sp;
    List<String> stdname = new ArrayList<>();
    List<Integer> stdId = new ArrayList<>();


    class getStdApi implements  VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i=0;i<jsonArray.length();i++){
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    stdname.add(obj.getString("stdName"));
                    stdId.add(obj.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(StudentProfileActivity.this,R.layout.spinner_dropdown_custom,stdname);
            classes.setAdapter(spinnerArrayAdapter);
            Log.e("stdData",jsonArray.toString());
        }
    }


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        setTitle("Student Profile");
        this.listView = (ListView) findViewById(R.id.listview);
        findViewById(R.id.layout_spnr_class).setVisibility(0);
        this.classes = (Spinner) findViewById(R.id.spnr_class);
        sp = PreferenceManager.getDefaultSharedPreferences(StudentProfileActivity.this);
        String channel = (sp.getString("schoolid", ""));
        Log.e("schoolIdLog",channel);
        getJsonResponse(URLs.getStd+channel,StudentProfileActivity.this,new StudentProfileActivity.getStdApi());
    }
}
