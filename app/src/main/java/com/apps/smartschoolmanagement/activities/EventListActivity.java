package com.apps.smartschoolmanagement.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.EventListAdapter;
import com.apps.smartschoolmanagement.adapters.HolidayListAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventListActivity extends JsonClass {
    View rootView;
    ListView listView;
    SharedPreferences sp;
    String channel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        setTitle("Event List");
        this.listView = (ListView) findViewById(R.id.listviews);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        channel = (sp.getString("schoolid", ""));
        getJsonResponse(URLs.getEventList + channel , this, new EventListActivity.getEventList());
    }
    class getEventList implements JsonClass.VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            Log.e("respo", jsonArray.toString());
            values.clear();
            for (int i = 0; i< jsonArray.length(); i++) {
                try {
                    Log.e("all",">>"+jsonArray.length()) ;
                    findViewById(R.id.error).setVisibility(8);
                    ListData listData = new ListData();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    listData.setAssignment_submit_date(jsonObject1.getString("startDate"));
                    listData.setAssignment_subject(jsonObject1.getString("title"));
                    listData.setAssignment_end_date(jsonObject1.getString("endDate"));
                    listData.setAssignment_start_time(jsonObject1.getString("startTime"));

                    values.add(listData);
                } catch (JSONException e) {
                    e.printStackTrace();
                    findViewById(R.id.error).setVisibility(0);
                }

            }
            listView.setAdapter(new EventListAdapter(EventListActivity.this, R.layout.row_event_list, values));
        }
    }
}