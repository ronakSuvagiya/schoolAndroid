package com.apps.smartschoolmanagement.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.ListViewAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationActivity extends JsonClass {
    ListView listView;
    SharedPreferences sp;
    ArrayList<ListData> values = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        setTitle("Notifications");
        this.listView = (ListView) findViewById(R.id.listview);
        sp = PreferenceManager.getDefaultSharedPreferences(NotificationActivity.this);
        String channel = (sp.getString("schoolid", ""));
        String stdid = (sp.getString("studentId", ""));
        getJsonResponse(URLs.getNotificationData + channel + "&std=" + stdid, NotificationActivity.this, new NotificationActivity.getNotificationdatafApi());
    }

    class getNotificationdatafApi implements VolleyCallbackJSONArray {

        @Override
        public void onSuccess(JSONArray jsonArray) {
            values.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    ListData listData = new ListData();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    String title = jsonObject1.getString("title");
                String msg = jsonObject1.getString("data");
                listData.setNotify_message(msg);
                listData.setNotify_title(title);

                    values.add(listData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            listView.setAdapter(new ListViewAdapter(NotificationActivity.this, R.layout.item_layout_notifications, values));

        }
    }
}
