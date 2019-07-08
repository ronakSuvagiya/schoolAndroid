package com.apps.smartschoolmanagement.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.Comman.URL;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.URLs;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

public class PhotoGalleryAddCatActivity extends AppCompatActivity {
    private EditText Cat;
    private Button uploadCat;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery_add_cat);
        setTitle("Add Category");

        Cat = (EditText) findViewById(R.id.edtCat);
        uploadCat = (Button) findViewById(R.id.btnSave);

        sp = PreferenceManager.getDefaultSharedPreferences(PhotoGalleryAddCatActivity.this);
        String channel = (sp.getString("schoolid", ""));
        int schoolid = Integer.parseInt(channel);
        uploadCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Cat.getText().length() <= 0) {
                    Cat.setError("Add Category");
                    Cat.requestFocus();
                    return;
                } else {
                    RequestQueue MyRequestQueue = Volley.newRequestQueue(PhotoGalleryAddCatActivity.this);
                    JSONObject jsonBody = new JSONObject();
                    try {
                            jsonBody.put("name", Cat.getText().toString().trim());
                            jsonBody.put("school",schoolid);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLs.addImgCategory, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String success = String.valueOf(response.get("message"));
                                Toast.makeText(PhotoGalleryAddCatActivity.this, success, Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            finish();
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(PhotoGalleryAddCatActivity.this, "Enter Category", Toast.LENGTH_LONG).show();

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
