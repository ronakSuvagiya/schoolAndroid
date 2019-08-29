package com.apps.smartschoolmanagement.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.AttendancesAdapter;
import com.apps.smartschoolmanagement.fragments.PostAssignment;
import com.apps.smartschoolmanagement.fragments.StudentAttendance;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostComplaintActivity extends JsonClass {
    Spinner student;
    Spinner std;
    EditText desc;
    Spinner cls;
    EditText title;
    SharedPreferences sp;
    String channel;
    Button post;
    int stdid, div, students;
    List<String> stdname = new ArrayList<>();
    List<Integer> stdId = new ArrayList<>();
    List<String> divName = new ArrayList<>();
    List<Integer> divId = new ArrayList<>();
    List<String> studentName = new ArrayList<>();
    List<Integer> studentID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_complaint);
        setTitle("Post Complaint");
        this.std = findViewById(R.id.spnr_std);
        this.student = findViewById(R.id.spnr_student);
        this.cls = findViewById(R.id.spnr_class);
        this.title = findViewById(R.id.edit_post_title);
        this.desc = findViewById(R.id.edit_post_description);
        post = findViewById(R.id.btn_post_complaint);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        channel = (sp.getString("schoolid", ""));

        getJsonResponse(URLs.getStd + channel, this, new PostComplaintActivity.getStdApi());

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().equalsIgnoreCase("") && (desc.getText().toString().equalsIgnoreCase(""))) {
                    findViewById(R.id.error).setVisibility(0);
                    return;
                } else {
                    findViewById(R.id.error).setVisibility(8);
                    RequestQueue MyRequestQueue = Volley.newRequestQueue(PostComplaintActivity.this);
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("description", desc.getText().toString().trim());
                        jsonBody.put("student", students);
                        jsonBody.put("title", title.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLs.PostComplaint, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(PostComplaintActivity.this, "Complaint Posted", 1).show();
                            PostComplaintActivity.this.desc.setText("");
                            PostComplaintActivity.this.title.setText("");
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(PostComplaintActivity.this, "Complaint not posted try again...", Toast.LENGTH_LONG).show();

                                }
                            });
                    MyRequestQueue.add(jsonObjectRequest);
                }
            }
        });
        std.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                divName.removeAll(divName);
                divId.removeAll(divId);
                stdid = stdId.get(i);
                getJsonResponse(URLs.getDiv + stdid , PostComplaintActivity.this, new PostComplaintActivity.getDivApi());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                studentName.removeAll(studentName);
                studentID.removeAll(studentID);
                div = divId.get(i);
                getJsonResponse(URLs.getStudentByStdAndDiv + stdid + "&div=" + div + "&School=" + channel, PostComplaintActivity.this, new PostComplaintActivity.StudentApiCall());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                students = studentID.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    class getStdApi implements JsonClass.VolleyCallbackJSONArray {
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
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PostComplaintActivity.this, R.layout.spinner_dropdown_custom, stdname);
            std.setAdapter(spinnerArrayAdapter);
            Log.e("stdData", jsonArray.toString());
        }
    }

    class getDivApi implements JsonClass.VolleyCallbackJSONArray {
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
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PostComplaintActivity.this, R.layout.spinner_dropdown_custom, divName);
            cls.setAdapter(spinnerArrayAdapter);
            Log.e("divData", jsonArray.toString());
        }
    }

    class StudentApiCall implements JsonClass.VolleyCallbackJSONArray {

        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    studentName.add(obj.getString("name") + " , " + obj.getInt("rollNo"));
                    studentID.add(obj.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PostComplaintActivity.this, R.layout.spinner_dropdown_custom, studentName);
            student.setAdapter(spinnerArrayAdapter);

        }
    }
}
