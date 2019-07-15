package com.apps.smartschoolmanagement.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.HolidayListAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HolidayListActivity extends JsonClass {
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
        setContentView(R.layout.activity_holiday_list);
        setTitle("Holiday List");
        this.listView = (ListView) this.rootView.findViewById(R.id.listview);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        channel = (sp.getString("schoolid", ""));
        getJsonResponse(URLs.getHolidayList + channel , this, new HolidayListActivity.getHolidayList());
    }
    class getHolidayList implements JsonClass.VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            Log.e("respo", jsonArray.toString());
            values.clear();
            for (int i = 0; i< jsonArray.length(); i++) {
                try {
                    Log.e("all",">>"+jsonArray.length()) ;
                    rootView.findViewById(R.id.error).setVisibility(8);
                    ListData listData = new ListData();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    listData.setAssignment_submit_date(jsonObject1.getString("date"));
                    listData.setAssignment_subject(jsonObject1.getString("title"));
                    values.add(listData);
                } catch (JSONException e) {
                    e.printStackTrace();
                    rootView.findViewById(R.id.error).setVisibility(0);
                }

            }
            listView.setAdapter(new HolidayListAdapter(HolidayListActivity.this, R.layout.activity_holiday_list_adapter, values));
        }
    }
}
