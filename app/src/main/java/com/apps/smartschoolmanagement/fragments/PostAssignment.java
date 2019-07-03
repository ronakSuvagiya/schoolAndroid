package com.apps.smartschoolmanagement.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.Comman.URL;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.LoginActivity;
import com.apps.smartschoolmanagement.activities.StudentProfileActivity;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.OnClickDateListener;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.SpinnerHelper;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.apps.smartschoolmanagement.fragments.StudentAttendance.stdid;

public class PostAssignment extends JsonFragment {
    SpinnerHelper classes;
    String classid = null;
    Spinner cls;
    EditText date;
    View rootView;
    Spinner subj;
    Spinner std;
    SpinnerHelper subject;
    String subjectid = null;
    SharedPreferences sp;
    String channel;
    int div,stds,subjects,teacherMasters;
    List<String> stdname = new ArrayList<>();
    List<Integer> stdId = new ArrayList<>();
    List<String> divName = new ArrayList<>();
    List<Integer> divId = new ArrayList<>();
    List<String> subName = new ArrayList<>();
    List<Integer> subID  = new ArrayList<>();

    class getStdApi implements JsonFragment.VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    stdname.add(obj.getString("stdName"));
                    stdId.add(obj.getInt("id"));
                    stds = obj.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_dropdown_custom, stdname);
            std.setAdapter(spinnerArrayAdapter);
            Log.e("stdData", jsonArray.toString());
        }
    }
    class getDivApi implements JsonFragment.VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    divName.add(obj.getString("name"));
                    divId.add(obj.getInt("id"));
                    div = obj.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_dropdown_custom, divName);
            cls.setAdapter(spinnerArrayAdapter);
            Log.e("divData", jsonArray.toString());
        }
    }

    class getSubjectApi implements JsonFragment.VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    subName.add(obj.getString("name"));
                    subID.add(obj.getInt("id"));
                    subjects  = obj.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_dropdown_custom, subName);
            subj.setAdapter(spinnerArrayAdapter);
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_post_assignments, container, false);
        this.std = (Spinner) this.rootView.findViewById(R.id.spnr_std);
        this.subj = (Spinner) this.rootView.findViewById(R.id.spnr_subject);
        this.cls = (Spinner) this.rootView.findViewById(R.id.spnr_class);
        this.date = (EditText) this.rootView.findViewById(R.id.date);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
         channel = (sp.getString("schoolid", ""));
         teacherMasters = (sp.getInt("teachermaster",0));
        getJsonResponse(URLs.getStd + channel,rootView, new getStdApi());
        std.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                divName.removeAll(divName);
                divId.removeAll(divId);
                stdid = stdId.get(i);
                getJsonResponse(URLs.getDiv + stdid + "&school=" + channel, rootView, new PostAssignment.getDivApi());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        cls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subName.removeAll(subName);
                subID.removeAll(subID);
                stdid = stdId.get(i);
                getJsonResponse(URLs.getSubject + stdid + "&school=" + channel, rootView, new PostAssignment.getSubjectApi());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        this.date.setOnClickListener(new OnClickDateListener(this.date, getActivity(), "past"));
        return this.rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.rootView.findViewById(R.id.btn_post).setOnClickListener(new OnClickListener() {
                                                                         @Override
                                                                         public void onClick(View v) {
                                                                             if (PostAssignment.this.date.getText().toString().length() <= 5 || ((EditText) PostAssignment.this.rootView.findViewById(R.id.edit_post_assignment)).getText().toString().equalsIgnoreCase("")) {
                                                                                 PostAssignment.this.rootView.findViewById(R.id.error).setVisibility(0);
                                                                                 return;
                                                                             } else {
                                                                                 PostAssignment.this.rootView.findViewById(R.id.error).setVisibility(8);
                                                                                 int schoolid = Integer.parseInt(channel);
                                                                                 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
                                                                                 String s=df.format(new Date());
                                                                                 Log.e("div",">>"+ cls.getSelectedItem().toString());
                                                                                 Log.e("std",">>"+stds);
                                                                                 Log.e("school",">>"+schoolid);
                                                                                 Log.e("subject",">>"+subjects);
                                                                                 Log.e("created_on",">>"+s);
                                                                                 Log.e("date",">>"+PostAssignment.this.date.getText().toString());
                                                                                 Log.e("description",">>"+((EditText) PostAssignment.this.rootView.findViewById(R.id.edit_post_assignment)).getText().toString());
                                                                                 Log.e("teacherMaster",">>"+teacherMasters);
                                                                                 try {
                                                                                 RequestQueue MyRequestQueue = Volley.newRequestQueue(getActivity());
                                                                                 JSONObject jsonBody = new JSONObject();
                                                                                     jsonBody.put("created_on", s);
                                                                                     jsonBody.put("date", PostAssignment.this.date.getText().toString());
                                                                                     jsonBody.put("description", ((EditText) PostAssignment.this.rootView.findViewById(R.id.edit_post_assignment)).getText().toString());
                                                                                     jsonBody.put("div", div);
                                                                                     jsonBody.put("school", schoolid);
                                                                                     jsonBody.put("std", stds);
                                                                                     jsonBody.put("subject", subjects);
                                                                                     jsonBody.put("teacherMaster", teacherMasters);
                                                                                     JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLs.PostAssignment, jsonBody, new Response.Listener<JSONObject>() {
                                                                                         @Override
                                                                                         public void onResponse(JSONObject response) {
                                                                                             Toast.makeText(PostAssignment.this.getActivity(), "Assignment Posted", 1).show();
                                                                                         }
                                                                                     },
                                                                                             new Response.ErrorListener() {
                                                                                                 @Override
                                                                                                 public void onErrorResponse(VolleyError error) {
                                                                                                     Toast.makeText(getActivity(), "Assignment not posted try again...", Toast.LENGTH_LONG).show();

                                                                                                 }
                                                                                             });

                                                                                     MyRequestQueue.add(jsonObjectRequest);
                                                                                 } catch (JSONException e) {
                                                                                     e.printStackTrace();
                                                                                 }
                                                                             }
                                                                         }
                                                                     });
    }
}

