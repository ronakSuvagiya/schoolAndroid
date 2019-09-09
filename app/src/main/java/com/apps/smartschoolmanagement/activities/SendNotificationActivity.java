package com.apps.smartschoolmanagement.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SendNotificationActivity extends JsonClass {
    Spinner student;
    Spinner spnr_notification_std;
    EditText desc;
    EditText title;
    SharedPreferences sp;
    String channel;
    Button send;
    List<String> stdname = new ArrayList<>();
    List<Integer> stdId = new ArrayList<>();
    int stdids;
    FrameLayout stdfield;
    String All,Std;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        setTitle("Send Notification");
        this.spnr_notification_std = findViewById(R.id.spnr_notification_std);
        stdfield = findViewById(R.id.stdfield);
        this.title = findViewById(R.id.edit_send_title);
        this.desc = findViewById(R.id.edit_send_description);
        send = findViewById(R.id.btn_send_notification);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        channel = (sp.getString("schoolid", ""));
        All = getIntent().getStringExtra("All");
        Std = getIntent().getStringExtra("Std");
        if (Std!=null && Std.equals("Std"))
        {
            stdfield.setVisibility(View.VISIBLE);
            getJsonResponse(URLs.getStd + channel, SendNotificationActivity.this, new getStdApi());
        }
        spnr_notification_std.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stdids = stdId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (All!=null && All.equals("All")) {
                    SendAll();
                } else {
                    SendStd();
                }
            }
        });
    }

    private void SendAll() {
        if (title.getText().toString().equalsIgnoreCase("") &&
                (desc.getText().toString().equalsIgnoreCase(""))) {
            findViewById(R.id.error).setVisibility(0);
            return;
        } else {
            findViewById(R.id.error).setVisibility(8);
            RequestQueue MyRequestQueue = Volley.newRequestQueue(SendNotificationActivity.this);
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("data", desc.getText().toString().trim());
                jsonBody.put("id", channel);
                jsonBody.put("std", "");
                jsonBody.put("type", "All");
                jsonBody.put("title", title.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLs.SendNotification, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        Toast.makeText(SendNotificationActivity.this, response.getString("message"), 1).show();
                        SendNotificationActivity.this.desc.setText("");
                        SendNotificationActivity.this.title.setText("");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SendNotificationActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
            MyRequestQueue.add(jsonObjectRequest);
        }
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
                final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SendNotificationActivity.this, R.layout.spinner_dropdown_custom, stdname);
                spnr_notification_std.setAdapter(spinnerArrayAdapter);
                Log.e("stdData", jsonArray.toString());
            }
        }

    private void SendStd() {
        if (title.getText().toString().equalsIgnoreCase("") &&
                (desc.getText().toString().equalsIgnoreCase(""))) {
            findViewById(R.id.error).setVisibility(0);
            return;
        } else {
            findViewById(R.id.error).setVisibility(8);
            RequestQueue MyRequestQueue = Volley.newRequestQueue(SendNotificationActivity.this);
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("data", desc.getText().toString().trim());
                jsonBody.put("id", channel);
                jsonBody.put("std", stdids);
                jsonBody.put("type", "Std");
                jsonBody.put("title", title.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLs.SendNotification, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        Toast.makeText(SendNotificationActivity.this, response.getString("message"), 1).show();
                        SendNotificationActivity.this.desc.setText("");
                        SendNotificationActivity.this.title.setText("");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SendNotificationActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
            MyRequestQueue.add(jsonObjectRequest);
        }
    }
}

