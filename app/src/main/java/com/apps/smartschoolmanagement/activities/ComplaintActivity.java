package com.apps.smartschoolmanagement.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.ComplaintAdapter;
import com.apps.smartschoolmanagement.adapters.HolidayListAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ComplaintActivity extends JsonClass {
    View rootView;
    ListView listView;
    SharedPreferences sp;
    String channel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        setContentView(R.layout.activity_complaint);
        setTitle("Complaint");
        this.listView = (ListView) findViewById(R.id.listviewComplanit);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        channel = (sp.getString("schoolid", ""));
        getJsonResponse(URLs.getComplaint + channel , this, new ComplaintActivity.getComplaint());

    }
    class getComplaint implements JsonClass.VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            Log.e("respo", jsonArray.toString());
            values.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    Log.e("all", ">>" + jsonArray.length());
                    findViewById(R.id.error).setVisibility(8);
                    ListData listData = new ListData();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    listData.setComplaint_desc(jsonObject1.getString("description"));
                    listData.setComplaint_title(jsonObject1.getString("title"));
                    values.add(listData);
                } catch (JSONException e) {
                    e.printStackTrace();
                    findViewById(R.id.error).setVisibility(0);
                }
            }
            listView.setAdapter(new ComplaintAdapter(ComplaintActivity.this, R.layout.row_list_complaint, values));
        }
    }
}
